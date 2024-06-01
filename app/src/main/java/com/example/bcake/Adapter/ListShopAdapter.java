package com.example.bcake.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bcake.Controller.ViewShop;
import com.example.bcake.Model.Shop;
import com.example.bcake.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListShopAdapter extends RecyclerView.Adapter<ListShopAdapter.ShopViewHolder> {
    private Context context;
    private List<Shop> ShopList;


    public ListShopAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Shop> list) {
        this.ShopList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListShopAdapter.ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_shop, parent, false);
        return new ListShopAdapter.ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListShopAdapter.ShopViewHolder holder, int position) {
        Shop shop = ShopList.get(position);
        if (shop.getAvatar() == null) {
            holder.imgAvatar.setImageResource(R.drawable.icon_facebook);
        } else {
            Picasso.get().load(shop.getAvatar()).into(holder.imgAvatar);

        }
        holder.imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewShop.class);
                intent.putExtra("Id_Shop",shop.getId());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (ShopList != null) {
            return ShopList.size();
        }
        return 0;
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgAvatar;
        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.civ_line_avatar_shop);

        }
    }

}