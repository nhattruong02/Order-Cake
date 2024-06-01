package com.example.bcake.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bcake.Adapter.ListCartAdapter;
import com.example.bcake.Adapter.ListProductAdapter;
import com.example.bcake.Adapter.ListReviewAdapter;
import com.example.bcake.Api.ApiService;
import com.example.bcake.Api.RetrofitClient;
import com.example.bcake.Model.Cart;
import com.example.bcake.Model.Favorite;
import com.example.bcake.Model.Product;
import com.example.bcake.Model.Review;
import com.example.bcake.Model.Shop;
import com.example.bcake.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProduct extends AppCompatActivity {
    private Dialog dialog;
    private ListView lvReview;
    private ListReviewAdapter listReviewAdapter;
    private ArrayList<Review> reviews = new ArrayList<>();;
    private int Quantity = 1;
    private ImageView imgProduct;
    private TextView txtName,txtPrice,txtDescription,txtQuantity,txtNumberReview,txtNameShop,txtMoreInfo;
    private EditText edtMessage,edtReview;
    private Button btnAddToCart,btnPlus,btnMinus,btnReview;
    private ImageButton btnAddToFavorite;
    private CircleImageView civShop;
    private RatingBar rbReview;
    private int Id_Shop;
    private Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        init();
        UtilityClass.showProgressDialog(this);
        Intent intent = getIntent();
        int Id_Product = intent.getIntExtra("Id_Product",0);
        int Id_User = Integer.parseInt(UtilityClass.retrieveData(this,"Id_User",""));
        if (UtilityClass.checkI(DetailProduct.this)) {
            setDataProduct(Id_Product);
        }else {
            Toast.makeText(DetailProduct.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
        }
        if (UtilityClass.checkI(DetailProduct.this)) {
            showReview(Id_Product);
        }else {
            Toast.makeText(DetailProduct.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
        }
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilityClass.checkI(DetailProduct.this)) {
                    addToCart(Id_User,Id_Product);
                }else {
                    Toast.makeText(DetailProduct.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilityClass.checkI(DetailProduct.this)) {
                    addToFavorite(Id_User,Id_Product);
                }else {
                    Toast.makeText(DetailProduct.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Quantity++;
                txtQuantity.setText(String.valueOf(Quantity).toString().trim());
            }
        });
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Quantity == 1){
                    Toast.makeText(DetailProduct.this, "Số lượng phải lớn hơn 1", Toast.LENGTH_SHORT).show();
                }else{
                    Quantity--;
                    txtQuantity.setText(String.valueOf(Quantity).toString().trim());
                }

            }
        });
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilityClass.checkI(DetailProduct.this)) {
                    postReview(Id_User,Id_Product);
                }else {
                    Toast.makeText(DetailProduct.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        civShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailProduct.this,ViewShop.class);
                intent.putExtra("Id_Shop",Id_Shop);
                startActivity(intent);
            }
        });
        txtMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogMoreInfo();
            }
        });
    }

    private void showDialogMoreInfo() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_more_information);
        String size = null,shape = null ,flavor = null,weight = null;
        TextView txtSize = dialog.findViewById(R.id.txt_size);
        TextView txtShape = dialog.findViewById(R.id.txt_shape);
        TextView txtFlavor = dialog.findViewById(R.id.txt_flavor);
        TextView txtWeight = dialog.findViewById(R.id.txt_weight);
        if(product.getSize() != null){
            size =  product.getSize().toString().trim();
        }
        if(product.getShape() != null){
            shape = product.getShape().toString().trim();
        }
        if(product.getFlavor() != null){
            flavor = product.getFlavor().toString().trim();
        }
        if(product.getWeight() != null){
            weight = product.getWeight().toString().trim();
        }
        if(size == null && shape == null && flavor == null && weight == null){
            Toast.makeText(this, "Không có thông tin thêm", Toast.LENGTH_SHORT).show();
        }
        else {
            if (size != null) {
                txtSize.setText("Kích thước:\t" + size);
            } else {
                txtSize.setVisibility(View.GONE);
            }

            if (shape != null) {
                txtShape.setText("Hình dạng:\t" + shape);
            } else {
                txtShape.setVisibility(View.GONE);
            }

            if (flavor != null) {
                txtFlavor.setText("Vị:\t" + flavor);
            } else {
                txtFlavor.setVisibility(View.GONE);
            }

            if (weight != null) {
                txtWeight.setText("Cân nặng:\t" + weight);
            } else {
                txtWeight.setVisibility(View.GONE);
            }
            dialog.show();
        }
    }

    private void showShop(int idShop) {
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<Shop> call = apiservice.GetShopById(idShop);
        call.enqueue(new Callback<Shop>() {
            @Override
            public void onResponse(Call<Shop> call, Response<Shop> response) {
                if(response.body() != null){
                    Shop shop = response.body();
                    if (shop == null) {
                        civShop.setImageResource(R.drawable.icon_facebook);
                    } else {
                        Picasso.get().load(shop.getAvatar()).into(civShop);
                    }
                    txtNameShop.setText(shop.getName());
                    UtilityClass.HideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<Shop> call, Throwable t) {
                Toast.makeText(DetailProduct.this, "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();
            }
        });
    }

    private void postReview(int Id_User,int Id_Product) {
        float numberStar = rbReview.getRating();
        String comment = edtReview.getText().toString().trim();
        DateFormat format =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String time = format.format(Calendar.getInstance().getTime());
        if(comment.equals("")){
            Toast.makeText(this, "Hãy thêm đánh giá cho sản phẩm!", Toast.LENGTH_SHORT).show();
        }
        else{
            UtilityClass.showProgressDialog(this);
            ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<Boolean> call = apiservice.PostReview(Id_User,Id_Product,numberStar,comment,time);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body() == true){
                    UtilityClass.HideProgressDialog();
                    showReview(Id_Product);
                    Toast.makeText(DetailProduct.this, "Thành công!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(DetailProduct.this, "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();
            }
        });
        }
    }

    private void addToFavorite(int Id_User,int Id_Product) {
        String name = txtName.getText().toString().trim();
        UtilityClass.showProgressDialog(this);
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<Favorite> call = apiservice.AddToFavorite(Id_User,Id_Product);
        call.enqueue(new Callback<Favorite>() {
            @Override
            public void onResponse(Call<Favorite> call, Response<Favorite> response) {
                    if (response.body() != null) {
                        Toast.makeText(DetailProduct.this, "Đã thêm " + name + " vào sở thích", Toast.LENGTH_SHORT).show();
                        UtilityClass.HideProgressDialog();

                    } else {
                        Toast.makeText(DetailProduct.this, "Sản phẩm đã nằm trong sở thích của bạn", Toast.LENGTH_SHORT).show();
                        UtilityClass.HideProgressDialog();
                    }
                }


            @Override
            public void onFailure(Call<Favorite> call, Throwable t) {
                Toast.makeText(DetailProduct.this, "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();
            }
        });
    }

    private void addToCart(int Id_User,int Id_Product) {
        String name = txtName.getText().toString().trim();
        UtilityClass.showProgressDialog(this);
        int quantity =  Integer.parseInt(txtQuantity.getText().toString().trim());
        String message = edtMessage.getText().toString().trim();
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<Cart> call = apiservice.AddToCart(Id_User,Id_Product,quantity,message);
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if(response.body() != null){
                    Toast.makeText(DetailProduct.this, "Đã thêm " + name +" vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                Toast.makeText(DetailProduct.this, "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();
            }
        });
    }

    private void setDataProduct(int Id_Product) {
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<Product> call = apiservice.GetProductById(Id_Product);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                product = response.body();
                if(product != null){
                    txtName.setText(product.getName().toString().trim());
                    txtPrice.setText(String.valueOf(product.getPrice()).toString().trim());
                    if (product.getImage().equals("")) {
                        imgProduct.setImageResource(R.drawable.splashscreen);
                    } else {
                        Picasso.get().load(product.getImage()).into(imgProduct);
                    }
                    if(product.getDescription() != null) {
                        txtDescription.setText(product.getDescription().toString().trim());
                    }
                    Id_Shop = product.getId_Shop();
                    showShop(Id_Shop);
                    UtilityClass.HideProgressDialog();
                }
                else{
                    Toast.makeText(DetailProduct.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                }

            }
            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(DetailProduct.this, "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();
            }
        });
    }

    private void showReview(int Id_Product) {
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<ArrayList<Review>> call = apiservice.GetReviewByProduct(Id_Product);
        call.enqueue(new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                reviews = response.body();
                txtNumberReview.setText("(" + reviews.size() + ")");
                if(reviews != null){
                    listReviewAdapter = new ListReviewAdapter(DetailProduct.this,R.layout.line_review,reviews);
                    lvReview.setAdapter(listReviewAdapter);
                    UtilityClass.HideProgressDialog();

                }
                else{
                    Toast.makeText(DetailProduct.this, "Không có đánh giá!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                }

            }
            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {
                Toast.makeText(DetailProduct.this, "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();
            }
        });

    }

    private void init(){
        civShop = findViewById(R.id.civ_detail_product_avatar);
        txtNameShop = findViewById(R.id.txt_detail_product_name_shop);
        imgProduct =findViewById(R.id.img_detail_product_image);
        txtName = findViewById(R.id.txt_detail_product_name);
        txtDescription = findViewById(R.id.txt_detail_product_description);
        txtPrice = findViewById(R.id.txt_detail_product_price);
        txtQuantity = findViewById(R.id.txt_detail_product_quantity);
        edtMessage = findViewById(R.id.edt_detail_product_message);
        edtReview = findViewById(R.id.edt_detail_product_review);
        btnReview = findViewById(R.id.btn_detail_product_review);
        btnAddToCart = findViewById(R.id.btn_detail_product_cart);
        btnPlus = findViewById(R.id.btn_detail_product_plus);
        btnMinus = findViewById(R.id.btn_detail_product_minus);
        btnAddToFavorite = findViewById(R.id.btn_detail_product_favorite);
        lvReview = findViewById(R.id.lv_review);
        rbReview = findViewById(R.id.rb_detail_product_star);
        txtNumberReview = findViewById(R.id.txt_detail_product_number_review);
        txtMoreInfo = findViewById(R.id.txt_detail_product_more_infomation);
    }
}