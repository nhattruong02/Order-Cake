package com.example.bcake.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bcake.Api.ApiService;
import com.example.bcake.Api.RetrofitClient;
import com.example.bcake.Controller.UtilityClass;
import com.example.bcake.Model.Favorite;
import com.example.bcake.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFavoriteAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Favorite> favoritesList;

    public ListFavoriteAdapter(Context context, int layout, List<Favorite> favoritesList) {
        this.context = context;
        this.layout = layout;
        this.favoritesList = favoritesList;
    }

    @Override
    public int getCount() {
        return favoritesList.size();
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
        ImageView imgAvatar, imgDelete;
        TextView txtName, txtPrice;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListFavoriteAdapter.ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new ListFavoriteAdapter.ViewHolder();
            holder.imgDelete = view.findViewById(R.id.img_line_favorite_delete);
            holder.imgAvatar = view.findViewById(R.id.img_line_favorite);
            holder.txtName = view.findViewById(R.id.txt_line_favorite_name);
            holder.txtPrice = view.findViewById(R.id.txt_line_favorite_price);
            view.setTag(holder);
        } else {
            holder = (ListFavoriteAdapter.ViewHolder) view.getTag();
        }
        Favorite favorite = favoritesList.get(i);
        if (favorite.getImage() == null) {
            holder.imgAvatar.setImageResource(R.drawable.icon_facebook);
        } else {
            Picasso.get().load(favorite.getImage()).into(holder.imgAvatar);
        }
        holder.txtName.setText(favorite.getName());
        holder.txtPrice.setText(favorite.getPrice() + "đ");
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có muốn xóa\t" + favorite.getName() + "\tkhông!")
                        .setCancelable(false)
                        .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);

                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                UtilityClass.showProgressDialog(context);
                                Call<Favorite> call = apiservice.DeleteFavorite(favorite.getId());
                                call.enqueue(new Callback<Favorite>() {
                                    @Override
                                    public void onResponse(Call<Favorite> call, Response<Favorite> response) {
                                        if (response.body() != null) {
                                            Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                            favoritesList.remove(favorite);
                                            notifyDataSetChanged();
                                            UtilityClass.HideProgressDialog();
                                        } else {
                                            Toast.makeText(context, "Lỗi!", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Favorite> call, Throwable t) {
                                        Toast.makeText(context, "Lỗi!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).show();
            }
        });
        return view;
    }
}