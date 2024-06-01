package com.example.bcake.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bcake.Model.Order;
import com.example.bcake.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ListOrderAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Order> orderList;
    public ListOrderAdapter(Context context, int layout, List<Order> orderList) {
        this.context = context;
        this.layout = layout;
        this.orderList = orderList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        ImageView imgAvatar;
        TextView txtName,txtTime,txtStatus,txtLocation,txtPaymentStatus,txtPaymentMethod;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListOrderAdapter.ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new ListOrderAdapter.ViewHolder();
            holder.txtPaymentStatus = view.findViewById(R.id.txt_line_order_status_payment);
            holder.txtPaymentMethod = view.findViewById(R.id.txt_line_order_payment_method);
            holder.txtName = view.findViewById(R.id.txt_line_order_name);
            holder.txtTime = view.findViewById(R.id.txt_line_order_time);
            holder.txtStatus = view.findViewById(R.id.txt_line_order_status);
            holder.txtLocation = view.findViewById(R.id.txt_line_order_location);
            view.setTag(holder);
        } else {
            holder = (ListOrderAdapter.ViewHolder) view.getTag();
        }
        Order order = orderList.get(i);
        holder.txtName.setText(order.getNameOfShop());
        if(order.isPaymentStatus() == true){
            holder.txtPaymentStatus.setText("Trạng thái TT: Đã thanh toán");
        }else{
            holder.txtPaymentStatus.setText("Trạng thái TT: Chưa thanh toán");
        }
        holder.txtPaymentMethod.setText("PTTT:\t" +order.getPaymentMethod());
        try {
            holder.txtTime.setText(new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault()).parse(order.getOderTime())));
        } catch (Exception e) {
        }
        holder.txtStatus.setText(order.getStatus().toString().trim());
        holder.txtLocation.setText("Địa chỉ cửa hàng:\t"+order.getAddress().toString().trim());
        return view;
    }
}