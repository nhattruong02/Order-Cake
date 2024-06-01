package com.example.bcake.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bcake.Model.Product;
import com.example.bcake.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListProductAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Product> productsList;

    public ListProductAdapter(Context context, int layout, List<Product> productsList) {
        this.context = context;
        this.layout = layout;
        this.productsList = productsList;
    }
    public void setData(List<Product> list) {
        this.productsList = list;
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<Product> filterlist) {
        productsList = filterlist;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return productsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder {
        ImageView imgAvatar;
        TextView txtName, txtPrice;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListProductAdapter.ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new ListProductAdapter.ViewHolder();
            holder.imgAvatar = view.findViewById(R.id.img_line_Cake);
            holder.txtName = view.findViewById(R.id.txt_line_name_Cake);
            holder.txtPrice = view.findViewById(R.id.txt_line_price_Cake);
            view.setTag(holder);
        } else {
            holder = (ListProductAdapter.ViewHolder) view.getTag();
        }
        Product product = productsList.get(i);
        if (product.getImage() == null) {
            holder.imgAvatar.setImageResource(R.drawable.icon_facebook);
        } else {
            Picasso.get().load(product.getImage()).into(holder.imgAvatar);
        }
        holder.txtName.setText(product.getName());
        holder.txtPrice.setText(product.getPrice()+"");
        return view;
    }
}
