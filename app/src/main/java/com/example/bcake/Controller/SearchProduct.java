package com.example.bcake.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bcake.Adapter.ListCartAdapter;
import com.example.bcake.Adapter.ListProductAdapter;
import com.example.bcake.Api.ApiService;
import com.example.bcake.Api.RetrofitClient;
import com.example.bcake.Model.Cart;
import com.example.bcake.Model.Product;
import com.example.bcake.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nikartm.support.ImageBadgeView;

public class SearchProduct extends AppCompatActivity {
    private EditText edtSearchProduct;
    private ListView lvSuggestProduct;
    private ArrayAdapter<String> adapter;

    private List<String> listNameProduct = new ArrayList<>();
    private ImageBadgeView imageBadgeViewCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        init();
        imageBadgeViewCart.setBadgeValue(Integer.parseInt(UtilityClass.retrieveData(this,"NumberCart","")));
        lvSuggestProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                IntentSearchResulProduct(listNameProduct.get(i));
            }
        });
        edtSearchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchProduct(edtSearchProduct.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        imageBadgeViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentCart();
            }
        });
    }
    private void IntentSearchResulProduct(String textSearch){
        Intent intent = new Intent(this,SearchResulProduct.class);
        intent.putExtra("textSearch",textSearch);
        startActivity(intent);
    }
    private void searchProduct(String text) {
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<ArrayList<String>> call = apiservice.GetNameProduct(text);
        call.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                listNameProduct = response.body();
                if(listNameProduct != null){
                    adapter = new ArrayAdapter<>(SearchProduct.this, android.R.layout.simple_list_item_1,listNameProduct );
                    lvSuggestProduct.setAdapter(adapter);
                }
                else{
                    Toast.makeText(SearchProduct.this, "Không có sản phẩm!", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Toast.makeText(SearchProduct.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void intentCart() {
        Intent intent = new Intent(this, ManageCart.class);
        startActivity(intent);
    }
    private void init(){
        edtSearchProduct = findViewById(R.id.edt_search_product);
        lvSuggestProduct = findViewById(R.id.lv_search_product);
        imageBadgeViewCart = findViewById(R.id.img_search_product_cart);
    }
}