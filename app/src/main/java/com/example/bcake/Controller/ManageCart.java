package com.example.bcake.Controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bcake.Adapter.ListCartAdapter;
import com.example.bcake.Adapter.ListOrderAdapter;
import com.example.bcake.Api.ApiService;
import com.example.bcake.Api.RetrofitClient;
import com.example.bcake.Model.Cart;
import com.example.bcake.Model.CreateOrder;
import com.example.bcake.Model.Order;
import com.example.bcake.Model.Product;
import com.example.bcake.R;
import com.example.bcake.Zalo.Constant.AppInfo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ManageCart extends AppCompatActivity {
    private ListView lvCart;
    private TextView txtTotal,txtAddress;
    private Button btnpayment;
    private ListCartAdapter listCartAdapter;
    private ArrayList<Cart> carts = new ArrayList<>();
    private Spinner spnPaymentMethod;
    private List<Order> orderedList = new ArrayList<>();
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();
        if (UtilityClass.checkI(ManageCart.this)) {
            showCart();
        }else {
            Toast.makeText(ManageCart.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.arr_payment_method));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPaymentMethod.setAdapter(adapter);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ZaloPaySDK.init(AppInfo.APP_ID, Environment.SANDBOX);
        btnpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilityClass.checkI(ManageCart.this)) {
                    paymentOrder();
                }else {
                    Toast.makeText(ManageCart.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        lvCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int Id_Product = carts.get(i).getId_Product();
                intentDetailProduct(Id_Product);
            }
        });
    }


    private void intentDetailProduct(int Id_Product) {
        Intent intent = new Intent(this, DetailProduct.class);
        intent.putExtra("Id_Product", Id_Product);
        startActivity(intent);
    }

    private void paymentOrder() {
        String paymentMethod = spnPaymentMethod.getSelectedItem().toString().trim();
        switch (paymentMethod) {
            case "Thanh toán khi nhận hàng":
                if (UtilityClass.checkI(ManageCart.this)) {
                    Payment("Thanh toán khi nhận hàng");

                }else {
                    Toast.makeText(ManageCart.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
                }
                break;
            case "ZaloPay":
                if (UtilityClass.checkI(ManageCart.this)) {
                    paymentZalo();
                }else {
                    Toast.makeText(ManageCart.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void Payment(String paymentMethod) {
        UtilityClass.showProgressDialog(this);
        int Id_User =Integer.parseInt(UtilityClass.retrieveData(ManageCart.this,"Id_User",""));
        ApiService apiService = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<List<Order>> call = apiService.UserPostOrder(Id_User,carts,paymentMethod);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                orderedList = response.body();
                if (response.body() != null) {
                    Toast.makeText(ManageCart.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                } else {
                    Toast.makeText(ManageCart.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(ManageCart.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();
            }
        });
    }

    private void paymentZalo() {
        Payment("ZaloPay");
        CreateOrder orderApi = new CreateOrder();
        try {
            String txtTotalBill = txtTotal.getText().toString().trim().replace("đ", "");
            int dotIndex = txtTotalBill.lastIndexOf('.');
            if(dotIndex != -1) {
                txtTotalBill = txtTotalBill.substring(0, dotIndex);
            }
            JSONObject data = orderApi.createOrder(txtTotalBill);
            String code = data.getString("return_code");
            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");
                ZaloPaySDK.getInstance().payOrder(ManageCart.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(String s, String s1, String s2) {
                        ApiService apiService = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
                        Call<Boolean> call = apiService.PutOrderZalo(orderedList,token);
                        call.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                if(response.body() == true){
                                    Toast.makeText(ManageCart.this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(ManageCart.this, "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Toast.makeText(ManageCart.this, "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onPaymentCanceled(String s, String s1) {
                        deleteOrderZalo(orderedList);
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                        deleteOrderZalo(orderedList);
                    }
                });
            }

        } catch (Exception e) {
            deleteOrderZalo(orderedList);
        }
    }
    private void deleteOrderZalo(List<Order> orderedList){
        ApiService apiService = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<Boolean> call = apiService.DeleteOrderZalo(orderedList);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body() == true){
                    Toast.makeText(ManageCart.this, "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ManageCart.this, "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(ManageCart.this, "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void showCart() {
        UtilityClass.showProgressDialog(this);
        txtAddress.setText("Địa chỉ nhận hàng: " + UtilityClass.retrieveData(this,"Address",""));
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<ArrayList<Cart>> call = apiservice.GetCartByUser(Integer.parseInt(
        UtilityClass.retrieveData(this,"Id_User","")));
        call.enqueue(new Callback<ArrayList<Cart>>() {
            @Override
            public void onResponse(Call<ArrayList<Cart>> call, Response<ArrayList<Cart>> response) {
                carts = response.body();
                if(carts != null){
                    listCartAdapter = new ListCartAdapter(ManageCart.this,R.layout.line_cart,carts,txtTotal);
                    lvCart.setAdapter(listCartAdapter);
                    if(carts.size() > 0){
                        txtTotal.setText(Total(carts)+"\tđ");

                    }else{
                        txtTotal.setText("0 đ");
                    }
                    UtilityClass.HideProgressDialog();
                }
                else{
                    Toast.makeText(ManageCart.this, "Không có sản phẩm!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Cart>> call, Throwable t) {
                Toast.makeText(ManageCart.this, "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();
            }
        });
    }
    private double Total(List<Cart> cartList){
        double total =0;
        for(int i=0; i < cartList.size(); i++){
            total += cartList.get(i).getQuantity() * cartList.get(i).getPrice();
        }
        return total;
    }

    private void init(){
        txtAddress = findViewById(R.id.txt_cart_address);
        lvCart = findViewById(R.id.lv_cart);
        txtTotal = findViewById(R.id.txttotal);
        btnpayment = findViewById(R.id.btn_order);
        spnPaymentMethod = findViewById(R.id.spn_payment_method);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}