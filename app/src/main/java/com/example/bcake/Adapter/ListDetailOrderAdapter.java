package com.example.bcake.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.example.bcake.Api.ApiService;
import com.example.bcake.Api.RetrofitClient;
import com.example.bcake.Controller.UtilityClass;
import com.example.bcake.Model.Cart;
import com.example.bcake.Model.DetailOrder;
import com.example.bcake.Model.Favorite;
import com.example.bcake.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListDetailOrderAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<DetailOrder> detailOrderList;
    private AlertDialog alertDialog;

    public ListDetailOrderAdapter(Context context, int layout, List<DetailOrder> detailOrderList) {
        this.context = context;
        this.layout = layout;
        this.detailOrderList = detailOrderList;
    }

    @Override
    public int getCount() {
        return detailOrderList.size();
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
        ImageView imgProduct;
        TextView txtName, txtPrice,txtQuantity;
        CardView cardView;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListDetailOrderAdapter.ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new ListDetailOrderAdapter.ViewHolder();
            holder.cardView = view.findViewById(R.id.cv_detail_order);
            holder.imgProduct = view.findViewById(R.id.img_line_detail_order);
            holder.txtName = view.findViewById(R.id.txt_line_detail_order_name);
            holder.txtPrice = view.findViewById(R.id.txt_line_detail_order_price);
            holder.txtQuantity = view.findViewById(R.id.txt_line_detail_order_quantity);
            view.setTag(holder);
        } else {
            holder = (ListDetailOrderAdapter.ViewHolder) view.getTag();
        }
        DetailOrder detailOrder = detailOrderList.get(i);
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(detailOrder.getMessage());
                if(detailOrder.getMessage() == null){
                    Toast.makeText(context, "Bạn chưa để lại lời nhắn cho\t" +detailOrder.getNameOfProduct()+"!", Toast.LENGTH_SHORT).show();
                }else{
                    alertDialog=  builder.create();
                    alertDialog.show();
                }
                return true;
            }
        });
        if (detailOrder.getImage() == null) {
            holder.imgProduct.setImageResource(R.drawable.icon_facebook);
        } else {
            Picasso.get().load(detailOrder.getImage()).into(holder.imgProduct);
        }
        holder.txtName.setText(detailOrder.getNameOfProduct());
        holder.txtPrice.setText(detailOrder.getPrice()+ "");
        holder.txtQuantity.setText(detailOrder.getQuantity()+ "");

        return view;
    }
}
