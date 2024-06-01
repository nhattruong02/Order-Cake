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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bcake.Adapter.ListCartAdapter;
import com.example.bcake.Adapter.ListOrderAdapter;
import com.example.bcake.Adapter.ListShopAdapter;
import com.example.bcake.Api.ApiService;
import com.example.bcake.Api.RetrofitClient;
import com.example.bcake.Controller.DetailProduct;
import com.example.bcake.Controller.ManageDetailOrder;
import com.example.bcake.Controller.UtilityClass;
import com.example.bcake.Model.Cart;
import com.example.bcake.Model.Order;
import com.example.bcake.Model.Shop;
import com.example.bcake.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentOrder extends Fragment {
    private ListView lvOrder;
    private ListOrderAdapter listOrderAdapter;
    private ArrayList<Order> orders = new ArrayList<>();;
    private View view;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        init();
        if (UtilityClass.checkI(getActivity())) {
            showOrder();
        }else {
            Toast.makeText(getActivity(), "Không có kết nối internet", Toast.LENGTH_SHORT).show();
        }
        lvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ManageDetailOrder.class);
                intent.putExtra("Id_Order",orders.get(i).getId());
                intent.putExtra("Address",orders.get(i).getAddressOfCustomer());
                startActivity(intent);
            }
        });
        return view;
    }

    private void showOrder() {
        UtilityClass.showProgressDialog(getActivity());
        ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<ArrayList<Order>> call = apiservice.GetOrderByUser(Integer.parseInt(
                UtilityClass.retrieveData(getActivity(),"Id_User","")));
        call.enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                orders = response.body();
                if(orders != null){
                    listOrderAdapter = new ListOrderAdapter(getActivity(),R.layout.line_order,orders);
                    lvOrder.setAdapter(listOrderAdapter);
                    UtilityClass.HideProgressDialog();
                }
                else{
                    Toast.makeText(getActivity(), "Không có đơn đặt hàng!", Toast.LENGTH_SHORT).show();
                    UtilityClass.HideProgressDialog();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
                UtilityClass.HideProgressDialog();
            }
        });
    }

    private void init() {
        lvOrder = view.findViewById(R.id.lv_order);

    }

}
