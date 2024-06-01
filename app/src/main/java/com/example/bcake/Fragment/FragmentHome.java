package com.example.bcake.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bcake.Adapter.ListProductAdapter;
import com.example.bcake.Adapter.ListShopAdapter;
import com.example.bcake.Api.ApiService;
import com.example.bcake.Api.RetrofitClient;
import com.example.bcake.Controller.DetailProduct;
import com.example.bcake.Controller.ManageCart;
import com.example.bcake.Controller.SearchByCategory;
import com.example.bcake.Controller.SearchProduct;
import com.example.bcake.Controller.UtilityClass;
import com.example.bcake.Model.Product;
import com.example.bcake.Model.Shop;
import com.example.bcake.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nikartm.support.ImageBadgeView;

public class FragmentHome extends Fragment {
    private ImageBadgeView imgCart;
    private ImageView imgBelow,imgUpper,imgMiddle,imgMatcha,imgChocolate,imgStrawberry;
    private RecyclerView rcvShop;
    private TextView txtSearch;
    private GridView gvProduct;
    private ListShopAdapter listShopAdapter;
    private ListProductAdapter listProductAdapter;
    private ViewFlipper viewFlipper;
    private int[] arrayImage = {R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4};
    private ArrayList<Shop> shops = new ArrayList<>();;
    private ArrayList<Product> products = new ArrayList<>();
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        setViewFlipper();
        int Id_User = Integer.parseInt(UtilityClass.retrieveData(getActivity(),"Id_User",""));
        UtilityClass.showProgressDialog(getActivity());
        if (UtilityClass.checkI(getActivity())) {
            showListShop();
        }else {
            Toast.makeText(getActivity(), "Không có kết nối internet", Toast.LENGTH_SHORT).show();
        }
        if (UtilityClass.checkI(getActivity())) {
            showListProdcut();
        }else {
            Toast.makeText(getActivity(), "Không có kết nối internet", Toast.LENGTH_SHORT).show();
        }
        if (UtilityClass.checkI(getActivity())) {
            showNumberCart(Id_User);
        }else {
            Toast.makeText(getActivity(), "Không có kết nối internet", Toast.LENGTH_SHORT).show();
        }
        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentSearchProduct();
            }
        });
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentCart();
            }
        });
        gvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int Id_Product = products.get(i).getId_Product();
                intentDetailProduct(Id_Product);
            }
        });
        imgBelow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchByCategory.class);
                intent.putExtra("below", "below");
                startActivity(intent);
            }
        });
        imgUpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchByCategory.class);
                intent.putExtra("upper", "upper");
                startActivity(intent);
            }
        });
        imgMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchByCategory.class);
                intent.putExtra("middle", "middle");
                startActivity(intent);
            }
        });
        imgStrawberry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchByCategory.class);
                intent.putExtra("name", "dâu tây");
                startActivity(intent);
            }
        });
        imgChocolate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchByCategory.class);
                intent.putExtra("name", "chocolate");
                startActivity(intent);
            }
        });
        imgMatcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchByCategory.class);
                intent.putExtra("name", "matcha");
                startActivity(intent);
            }
        });
        return view;
    }

    private void showNumberCart(int Id_User) {
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<Integer> call = apiservice.GetNumberCartByUser(Id_User);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    imgCart.setBadgeValue(response.body());
                    UtilityClass.HideProgressDialog();
                    UtilityClass.saveData(getActivity(),"NumberCart",String.valueOf(response.body()));
                }
                else{
                    Toast.makeText(getActivity(), "Lỗi!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                }

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();
            }
        });
    }

    private void intentSearchProduct() {
        Intent intent = new Intent(getActivity(), SearchProduct.class);
        startActivity(intent);
    }
    private void intentDetailProduct(int Id_Product) {
        Intent intent = new Intent(getActivity(), DetailProduct.class);
        intent.putExtra("Id_Product",Id_Product);
        startActivity(intent);
    }

    private void intentCart() {
        Intent intent = new Intent(getActivity(), ManageCart.class);
        startActivity(intent);
    }

    private void showListProdcut() {
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<ArrayList<Product>> call = apiservice.GetProductBestSeller();
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                products = response.body();
                if(products != null){
                    listProductAdapter = new ListProductAdapter(getActivity(),R.layout.line_product,products);
                    gvProduct.setAdapter(listProductAdapter);
                    UtilityClass.HideProgressDialog();
                }
                else{
                    Toast.makeText(getActivity(), "Không có sản phẩm!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                }

            }
            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();
            }
        });
    }
    private String splitAddress(){
        String address = UtilityClass.retrieveData(getActivity(),"Address","");
        String[] parts = address.split(",");
        String result = parts[parts.length - 1].trim();
        Log.d("addressuser",result);
        return  result;
    }
    private void showListShop() {
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<ArrayList<Shop>> call = apiservice.GetNearByShop(splitAddress().toString());
        call.enqueue(new Callback<ArrayList<Shop>>() {
            @Override
            public void onResponse(Call<ArrayList<Shop>> call, Response<ArrayList<Shop>> response) {
                shops = response.body();
                if(products != null){
                    listShopAdapter = new ListShopAdapter(getActivity());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
                    rcvShop.setLayoutManager(linearLayoutManager);
                    listShopAdapter.setData(shops);
                    rcvShop.setAdapter(listShopAdapter);
                    UtilityClass.HideProgressDialog();
                }
                else{
                    Toast.makeText(getActivity(), "Không có cửa hàng!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Shop>> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();
            }
        });


    }

    private void init() {
        imgBelow = view.findViewById(R.id.img_home_below_200);
        imgUpper = view.findViewById(R.id.img_home_upper_500);
        imgMiddle = view.findViewById(R.id.img_home_200_to_500);
        imgChocolate = view.findViewById(R.id.img_home_chocolate);
        imgMatcha = view.findViewById(R.id.img_home_matcha);
        imgStrawberry = view.findViewById(R.id.img_home_strawberry);
        viewFlipper = view.findViewById(R.id.viewfipper);
        rcvShop = view.findViewById(R.id.rcv_shop);
        gvProduct = view.findViewById(R.id.gv_home_best_seller);
        imgCart = view.findViewById(R.id.img_cart);
        txtSearch = view.findViewById(R.id.txt_search);
    }

    private void setViewFlipper() {
        for (int i = 0; i < arrayImage.length; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(arrayImage[i]);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
    }
}
