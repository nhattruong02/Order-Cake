package com.example.bcake.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bcake.Adapter.ListProductAdapter;
import com.example.bcake.Api.ApiService;
import com.example.bcake.Api.RetrofitClient;
import com.example.bcake.Model.Product;
import com.example.bcake.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nikartm.support.ImageBadgeView;

public class BoughtProduct extends AppCompatActivity {
    private EditText txtSearchProduct;
    private GridView gvProduct;
    private ListProductAdapter listProductAdapter;
    private List<Product> products = new ArrayList<>();
    private ImageBadgeView imageBadgeViewCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bought_product);
        init();
        UtilityClass.showProgressDialog(this);
        imageBadgeViewCart.setBadgeValue(Integer.parseInt(UtilityClass.retrieveData(this,"NumberCart","")));
        if (UtilityClass.checkI(BoughtProduct.this)) {
            showProduct();
        }else {
            Toast.makeText(BoughtProduct.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
        }
        txtSearchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
        imageBadgeViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentCart();
            }
        });
        gvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product = products.get(i);
                intentDetailProduct(product);
            }
        });
    }

    private void filter(String text) {
        ArrayList<Product> filteredList = new ArrayList<>();
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(p);
            }
        }
        listProductAdapter.filterList(filteredList);
    }
    private void intentDetailProduct(Product product) {
        Intent intent = new Intent(this, DetailProduct.class);
        intent.putExtra("Id_Product",product.getId_Product());
        startActivity(intent);
    }
    private void showProduct() {
        int Id_User = Integer.parseInt(UtilityClass.retrieveData(this,"Id_User",""));
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<ArrayList<Product>> call = apiservice.GetBoughtProduct(Id_User);
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                products = response.body();
                if(products != null || !products.isEmpty()){
                    listProductAdapter = new ListProductAdapter(BoughtProduct.this,R.layout.line_product,products);
                    gvProduct.setAdapter(listProductAdapter);
                    listProductAdapter.setData(products);
                    UtilityClass.HideProgressDialog();
                }
                else{
                    Toast.makeText(BoughtProduct.this, "Không có sản phẩm!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                }

            }
            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Toast.makeText(BoughtProduct.this, "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();
            }
        });

    }

    private void intentCart() {
        Intent intent = new Intent(this, ManageCart.class);
        startActivity(intent);
    }
    private void init(){
        txtSearchProduct = findViewById(R.id.txt_search_bought_product);
        gvProduct = findViewById(R.id.gv_bought_product);
        imageBadgeViewCart = findViewById(R.id.img_search_bought_product);
    }
}