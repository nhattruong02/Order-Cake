package com.example.bcake.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bcake.Adapter.ListCategoryAdapter;
import com.example.bcake.Adapter.ListProductAdapter;
import com.example.bcake.Api.ApiService;
import com.example.bcake.Api.RetrofitClient;
import com.example.bcake.Model.Category;
import com.example.bcake.Model.Product;
import com.example.bcake.Model.Shop;
import com.example.bcake.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewShop extends AppCompatActivity {
    private CircleImageView imgAvatar;
    private TextView txtName, txtDescription;
    private GridView gvCategory;
    private GridView gvBestSeller;
    private GridView gvNewProduct;
    private ListProductAdapter listProductAdapter;
    private ListCategoryAdapter listCategoryAdapter;
    private ArrayList<Category> categories = new ArrayList<>();
    private ArrayList<Product> newProducts = new ArrayList<>();
    private ArrayList<Product> bestSellerProducts = new ArrayList<>();
    private ImageView btnback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_shop);
        UtilityClass.showProgressDialog(this);
        init();
        Intent intent = getIntent();
        int Id_Shop = intent.getIntExtra("Id_Shop",0);
        if (UtilityClass.checkI(ViewShop.this)) {
            setInfomationShop(Id_Shop);
        }else {
            Toast.makeText(ViewShop.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
        }
        if (UtilityClass.checkI(ViewShop.this)) {
            showListBestSeller(Id_Shop);

        }else {
            Toast.makeText(ViewShop.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
        }
        if (UtilityClass.checkI(ViewShop.this)) {
            showListNewProduct(Id_Shop);

        }else {
            Toast.makeText(ViewShop.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
        }
        if (UtilityClass.checkI(ViewShop.this)) {
            showListCategory(Id_Shop);

        }else {
            Toast.makeText(ViewShop.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
        }
        gvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ViewShop.this, SearchByCategory.class);
                intent.putExtra("Id_Category", categories.get(i).getId());
                startActivity(intent);
            }
        });
        gvNewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product = newProducts.get(i);
                intentDetailProduct(product);
            }
        });
        gvBestSeller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product = bestSellerProducts.get(i);
                intentDetailProduct(product);
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentMain();
            }
        });
    }

    private void intentDetailProduct(Product product) {
        Intent intent = new Intent(this, DetailProduct.class);
        intent.putExtra("Id_Product",product.getId_Product());
        intent.putExtra("Id_Shop",product.getId_Shop());
        intent.putExtra("Name",product.getName());
        intent.putExtra("Image",product.getImage());
        intent.putExtra("Price",product.getPrice());
        intent.putExtra("Description",product.getDescription());
        startActivity(intent);
    }

    private void setInfomationShop(int Id_Shop){
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<Shop> call = apiservice.GetShopById(Id_Shop);
        call.enqueue(new Callback<Shop>() {
            @Override
            public void onResponse(Call<Shop> call, Response<Shop> response) {
                Shop shop = response.body();
                if(newProducts != null){
                    txtName.setText(shop.getName());
                    if(shop.getDescription() == null){
                        txtDescription.setText("Địa chỉ:\t"+shop.getAddress());
                    }else{
                        txtDescription.setText(shop.getDescription()+"\nĐịa chỉ:\t"+shop.getAddress());
                    }if (shop.getAvatar().equals("")) {
                        imgAvatar.setImageResource(R.drawable.icon_facebook);
                    } else {
                        Picasso.get().load(shop.getAvatar()).into(imgAvatar);
                    }
                    UtilityClass.HideProgressDialog();
                }
                else{
                    Toast.makeText(ViewShop.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                }

            }
            @Override
            public void onFailure(Call<Shop> call, Throwable t) {
                Toast.makeText(ViewShop.this, "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();

            }
        });
    }
    private void intentMain() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void showListNewProduct(int Id_Shop) {
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<ArrayList<Product>> call = apiservice.GetNewProductByShop(Id_Shop);
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                newProducts = response.body();
                if(newProducts != null){
                    listProductAdapter = new ListProductAdapter(ViewShop.this,R.layout.line_product,newProducts);
                    gvNewProduct.setAdapter(listProductAdapter);
                    UtilityClass.HideProgressDialog();
                }
                else{
                    Toast.makeText(ViewShop.this, "Không có sản phẩm!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                }

            }
            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Toast.makeText(ViewShop.this, "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();
            }
        });

    }

    private void showListBestSeller(int Id_Shop) {
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<ArrayList<Product>> call = apiservice.GetProductBestSellerByShop(Id_Shop);
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                bestSellerProducts = response.body();
                if(bestSellerProducts != null){
                    listProductAdapter = new ListProductAdapter(ViewShop.this,R.layout.line_product,bestSellerProducts);
                    gvBestSeller.setAdapter(listProductAdapter);
                    UtilityClass.HideProgressDialog();
                }
                else{
                    Toast.makeText(ViewShop.this, "Không có sản phẩm!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();

                }

            }
            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Toast.makeText(ViewShop.this, "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();

            }
        });
    }

    private void showListCategory(int Id_Shop) {
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<ArrayList<Category>> call = apiservice.GetCategoriesByShop(Id_Shop);
        call.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                categories = response.body();
                if(categories != null){
                    listCategoryAdapter = new ListCategoryAdapter(ViewShop.this,R.layout.line_category,categories);
                    gvCategory.setAdapter(listCategoryAdapter);
                    UtilityClass.HideProgressDialog();

                }
                else{
                    Toast.makeText(ViewShop.this, "Không có loại sản phẩm!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();

                }

            }
            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                Toast.makeText(ViewShop.this, "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();

            }
        });
    }

    private void init(){
        gvCategory = findViewById(R.id.gv_by_category);
        gvBestSeller = findViewById(R.id.gv_best_seller);
        gvNewProduct = findViewById(R.id.gv_new_Cake);
        btnback = findViewById(R.id.img_shop_back);
        txtName = findViewById(R.id.txt_shop_name);
        txtDescription = findViewById(R.id.txt_shop_description);
        imgAvatar = findViewById(R.id.civ_view_shop_avatar);
    }
}