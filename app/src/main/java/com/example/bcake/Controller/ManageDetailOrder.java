package com.example.bcake.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bcake.Adapter.ListCartAdapter;
import com.example.bcake.Adapter.ListDetailOrderAdapter;
import com.example.bcake.Adapter.ListProductAdapter;
import com.example.bcake.Api.ApiService;
import com.example.bcake.Api.RetrofitClient;
import com.example.bcake.Model.DetailOrder;
import com.example.bcake.Model.Favorite;
import com.example.bcake.Model.Order;
import com.example.bcake.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageDetailOrder extends AppCompatActivity {
    private ListView lvDetailOrder;
    private TextView txtTotal,txtAddress;
    private ListDetailOrderAdapter listDetailOrderAdapter;
    private ArrayList<DetailOrder> detailOrders = new ArrayList<>();
    private Button btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        init();
        Intent intent = getIntent();
        String address = intent.getStringExtra("Address");
        int Id_Order = intent.getIntExtra("Id_Order",0);
        if (UtilityClass.checkI(ManageDetailOrder.this)) {
            showDetailOrder(Id_Order,address);
        }else {
            Toast.makeText(ManageDetailOrder.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilityClass.checkI(ManageDetailOrder.this)) {
                    cancelOrder(Id_Order);
                }else {
                    Toast.makeText(ManageDetailOrder.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cancelOrder(int Id_Order) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa đơn đặt hàng này không!")
                .setCancelable(false)
                .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                }).setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                    ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);

                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        UtilityClass.showProgressDialog(ManageDetailOrder.this);
                        Call<Order> call = apiservice.DeleteOrder(Id_Order);
                        call.enqueue(new Callback<Order>() {
                            @Override
                            public void onResponse(Call<Order> call, Response<Order> response) {
                                if (response.body() != null) {
                                    Toast.makeText(ManageDetailOrder.this, "Hủy thành công!", Toast.LENGTH_SHORT).show();
                                    UtilityClass.HideProgressDialog();
                                    Intent intent = new Intent(ManageDetailOrder.this,MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(ManageDetailOrder.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                                    UtilityClass.HideProgressDialog();
                                }
                            }

                            @Override
                            public void onFailure(Call<Order> call, Throwable t) {
                                Toast.makeText(ManageDetailOrder.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                                UtilityClass.HideProgressDialog();
                            }
                        });
                    }
                }).show();
    }

    private void showDetailOrder(int Id_Order, String address) {
        UtilityClass.showProgressDialog(this);
        txtAddress.setText("Địa chỉ nhận hàng: " + address);
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<ArrayList<DetailOrder>> call = apiservice.GetDetailOrder(Id_Order);
        call.enqueue(new Callback<ArrayList<DetailOrder>>() {
            @Override
            public void onResponse(Call<ArrayList<DetailOrder>> call, Response<ArrayList<DetailOrder>> response) {
                detailOrders = response.body();
                if(detailOrders != null){
                    listDetailOrderAdapter = new ListDetailOrderAdapter(ManageDetailOrder.this,R.layout.line_detail_order,detailOrders);
                    lvDetailOrder.setAdapter(listDetailOrderAdapter);
                    if(detailOrders.size() > 0){
                        txtTotal.setText(String.valueOf(Total(detailOrders)));

                    }else{
                        txtTotal.setText("0 đ");
                    }
                    UtilityClass.HideProgressDialog();
                }
                else{
                    Toast.makeText(ManageDetailOrder.this, "Không có sản phẩm!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<DetailOrder>> call, Throwable t) {
                Toast.makeText(ManageDetailOrder.this, "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();
            }
        });
    }
    private double Total(List<DetailOrder> detailOrderList){
        double total =0;
        for(int i=0; i< detailOrderList.size(); i++){
            total = detailOrderList.get(i). getQuantity()* detailOrderList.get(i).getPrice();
        }
        return total;
    }
    private void init(){
        txtAddress = findViewById(R.id.txt_detail_order_address);
        lvDetailOrder = findViewById(R.id.lv_detail_order);
        txtTotal = findViewById(R.id.txt_total_detail_order);
        btnCancel= findViewById(R.id.btn_cancel_detail_order);
    }
}
