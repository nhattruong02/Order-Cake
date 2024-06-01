package com.example.bcake.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bcake.Adapter.ListCartAdapter;
import com.example.bcake.Adapter.ListFavoriteAdapter;
import com.example.bcake.Adapter.ListOrderAdapter;
import com.example.bcake.Api.ApiService;
import com.example.bcake.Api.RetrofitClient;
import com.example.bcake.Controller.DetailProduct;
import com.example.bcake.Controller.SearchByCategory;
import com.example.bcake.Controller.UtilityClass;
import com.example.bcake.Controller.ViewShop;
import com.example.bcake.Model.Cart;
import com.example.bcake.Model.Favorite;
import com.example.bcake.Model.Order;
import com.example.bcake.Model.Product;
import com.example.bcake.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentFavorite extends Fragment {
    private ListView lvFavorite;
    private ListFavoriteAdapter listFavoriteAdapter;
    private ArrayList<Favorite> favorites = new ArrayList<>();
    private View view;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorite, container, false);
        init();
        if (UtilityClass.checkI(getActivity())) {
            showFavorite();

        }else {
            Toast.makeText(getActivity(), "Không có kết nối internet", Toast.LENGTH_SHORT).show();
        }
        lvFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int Id_Product = favorites.get(i).getId_Product();
                intentDetailProduct(Id_Product);
            }
        });
        return view;
    }
    private void intentDetailProduct(int Id_Product){
        Intent intent = new Intent(getActivity(), DetailProduct.class);
        intent.putExtra("Id_Product", Id_Product);
        startActivity(intent);
    }
    private void showFavorite() {
        UtilityClass.showProgressDialog(getActivity());
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<ArrayList<Favorite>> call = apiservice.GetFavoriteByUser((Integer.parseInt(
                UtilityClass.retrieveData(getActivity(),"Id_User",""))));
        call.enqueue(new Callback<ArrayList<Favorite>>() {
            @Override
            public void onResponse(Call<ArrayList<Favorite>> call, Response<ArrayList<Favorite>> response) {
                favorites = response.body();
                if(favorites != null){
                    listFavoriteAdapter = new ListFavoriteAdapter(getActivity(),R.layout.line_favorite,favorites);
                    lvFavorite.setAdapter(listFavoriteAdapter);
                    UtilityClass.HideProgressDialog();
                }
                else{
                    Toast.makeText(getActivity(), "Không có sở thích!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Favorite>> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();
            }
        });


    }

    private void init() {
        lvFavorite = view.findViewById(R.id.lv_favorite);
    }
}
