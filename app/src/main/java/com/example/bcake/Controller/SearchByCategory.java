package com.example.bcake.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
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

public class SearchByCategory extends AppCompatActivity {
    private EditText edtSearchProduct;
    private GridView gvProduct;
    private ListProductAdapter listProductAdapter;
    private List<Product> products = new ArrayList<>();
    private ImageBadgeView imageBadgeViewCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_category);
        init();
        UtilityClass.showProgressDialog(this);
        Intent intent = getIntent();
        int Id_Category = intent.getIntExtra("Id_Category",0);
        String name = intent.getStringExtra("name");
        String below = intent.getStringExtra("below");
        String middle = intent.getStringExtra("middle");
        String upper = intent.getStringExtra("upper");
        imageBadgeViewCart.setBadgeValue(Integer.parseInt(UtilityClass.retrieveData(this,"NumberCart","")));
        if(name != null){
            showProductByName(name);
        } else if (below != null) {
            showProductBelow();
        } else if (middle != null) {
            showProductMiddle();
        } else if (upper != null) {
            showProductUpper();
        }else if(Id_Category != 0){
            showProductByCategory(Id_Category);
        }
        edtSearchProduct.setOnClickListener(new View.OnClickListener() {
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

    private void showProductByCategory(int id_category) {
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<ArrayList<Product>> call = apiservice.GetProductByCategory(id_category);
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                products = response.body();
                if (products != null) {
                    listProductAdapter = new ListProductAdapter(SearchByCategory.this, R.layout.line_product, products);
                    gvProduct.setAdapter(listProductAdapter);
                    UtilityClass.HideProgressDialog();
                } else {
                    Toast.makeText(SearchByCategory.this, "Không có sản phẩm!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();

                }

            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Toast.makeText(SearchByCategory.this, "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();

            }
        });
    }

    private void showProductBelow() {
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<ArrayList<Product>> call = apiservice.GetProductBelow();
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                products = response.body();
                if (products != null) {
                    listProductAdapter = new ListProductAdapter(SearchByCategory.this, R.layout.line_product, products);
                    gvProduct.setAdapter(listProductAdapter);
                    UtilityClass.HideProgressDialog();
                } else {
                    Toast.makeText(SearchByCategory.this, "Không có sản phẩm!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Toast.makeText(SearchByCategory.this, "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();
            }
        });
    }

    private void showProductMiddle() {
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<ArrayList<Product>> call = apiservice.GetProductMiddle();
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                products = response.body();
                if (products != null) {
                    listProductAdapter = new ListProductAdapter(SearchByCategory.this, R.layout.line_product, products);
                    gvProduct.setAdapter(listProductAdapter);
                    UtilityClass.HideProgressDialog();
                } else {
                    Toast.makeText(SearchByCategory.this, "Không có sản phẩm!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Toast.makeText(SearchByCategory.this, "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();
            }
        });
    }

    private void showProductUpper() {
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<ArrayList<Product>> call = apiservice.GetProductUpper();
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                products = response.body();
                if (products != null) {
                    listProductAdapter = new ListProductAdapter(SearchByCategory.this, R.layout.line_product, products);
                    gvProduct.setAdapter(listProductAdapter);
                    UtilityClass.HideProgressDialog();
                } else {
                    Toast.makeText(SearchByCategory.this, "Không có sản phẩm!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Toast.makeText(SearchByCategory.this, "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();
            }
        });
    }

    private void showProductByName(String name) {
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<ArrayList<Product>> call = apiservice.GetProductByName(name);
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                products = response.body();
                if (products != null) {
                    listProductAdapter = new ListProductAdapter(SearchByCategory.this, R.layout.line_product, products);
                    gvProduct.setAdapter(listProductAdapter);
                    UtilityClass.HideProgressDialog();
                } else {
                    Toast.makeText(SearchByCategory.this, "Không có sản phẩm!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Toast.makeText(SearchByCategory.this, "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();
            }
        });
    }

    private void intentDetailProduct(Product product) {
        Intent intent = new Intent(this, DetailProduct.class);
        intent.putExtra("Id_Product", product.getId_Product());
        intent.putExtra("Id_Shop", product.getId_Shop());
        intent.putExtra("Name", product.getName());
        intent.putExtra("Image", product.getImage());
        intent.putExtra("Price", product.getPrice());
        intent.putExtra("Description", product.getDescription());
        startActivity(intent);
    }


    private void intentSearchProduct() {
        Intent intent = new Intent(this, SearchProduct.class);
        startActivity(intent);
    }

    private void intentCart() {
        Intent intent = new Intent(this, ManageCart.class);
        startActivity(intent);
    }

    private void init() {
        edtSearchProduct = findViewById(R.id.edt_search_by_category);
        gvProduct = findViewById(R.id.gv_search_by_category);
        imageBadgeViewCart = findViewById(R.id.img_search_by_category_cart);
    }
}