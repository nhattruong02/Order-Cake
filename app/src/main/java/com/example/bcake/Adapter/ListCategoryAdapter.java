package com.example.bcake.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bcake.Model.Category;
import com.example.bcake.Model.Favorite;
import com.example.bcake.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListCategoryAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Category> categoriesList;

    public ListCategoryAdapter(Context context, int layout, List<Category> categoriesList) {
        this.context = context;
        this.layout = layout;
        this.categoriesList= categoriesList;
    }

    @Override
    public int getCount() {
        return categoriesList.size();
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
        TextView txtName;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListCategoryAdapter.ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new ListCategoryAdapter.ViewHolder();
            holder.imgAvatar = view.findViewById(R.id.img_line_category);
            holder.txtName = view.findViewById(R.id.txt_line_category_name);
            view.setTag(holder);
        } else {
            holder = (ListCategoryAdapter.ViewHolder) view.getTag();
        }
        Category category = categoriesList.get(i);
        if (category.getImage() == null) {
            holder.imgAvatar.setImageResource(R.drawable.icon_facebook);
        } else {
            Picasso.get().load(category.getImage()).into(holder.imgAvatar);
        }
        holder.txtName.setText(category.getName());
        return view;
    }
}