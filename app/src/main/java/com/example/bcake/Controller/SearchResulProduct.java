package com.example.bcake.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
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

public class SearchResulProduct extends AppCompatActivity {
    private TextView txtSearchProduct;
    private GridView gvProduct;
    private ListProductAdapter listProductAdapter;
    private List<Product> products = new ArrayList<>();
    private ImageBadgeView imageBadgeViewCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_product);
        init();
        Intent intent = getIntent();
        String txtSearch = intent.getStringExtra("textSearch");
        imageBadgeViewCart.setBadgeValue(Integer.parseInt(UtilityClass.retrieveData(this,"NumberCart","")));
        showProduct(txtSearch);
        txtSearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentSearchProduct();
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
    private void intentDetailProduct(Product product) {
        Intent intent = new Intent(this, DetailProduct.class);
        intent.putExtra("Id_Product",product.getId_Product());
        startActivity(intent);
    }
    private void showProduct(String txtSearch) {
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<ArrayList<Product>> call = apiservice.SearchProductResult(txtSearch);
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                products = response.body();
                if(products != null){
                    listProductAdapter = new ListProductAdapter(SearchResulProduct.this,R.layout.line_product,products);
                    gvProduct.setAdapter(listProductAdapter);
                }
                else{
                    Toast.makeText(SearchResulProduct.this, "Không có sản phẩm!", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Toast.makeText(SearchResulProduct.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void intentSearchProduct() {
        Intent intent = new Intent(this, SearchProduct.class);
        startActivity(intent);
    }
    private void intentCart() {
        Intent intent = new Intent(this, ManageCart.class);
        startActivity(intent);
    }
    private void init(){
        txtSearchProduct = findViewById(R.id.txt_search_result_product);
        gvProduct = findViewById(R.id.gv_search_result_product);
        imageBadgeViewCart = findViewById(R.id.img_search_result_product_cart);
    }
}