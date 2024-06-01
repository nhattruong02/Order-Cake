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
import com.example.bcake.Model.Favorite;
import com.example.bcake.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCartAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Cart> cartsList;
    private TextView total;
    private ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
    private AlertDialog alertDialog;

    public ListCartAdapter(Context context, int layout, List<Cart> cartsList,TextView total) {
        this.context = context;
        this.layout = layout;
        this.cartsList = cartsList;
        this.total = total;
    }

    @Override
    public int getCount() {
        return cartsList.size();
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
        CardView cardView;
        ImageView imgProduct,imgDelete;
        TextView txtName, txtPrice,txtQuantity;
        Button btnPlus, btnMinus;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListCartAdapter.ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new ListCartAdapter.ViewHolder();
            holder.cardView = view.findViewById(R.id.cv_cart);
            holder.imgDelete = view.findViewById(R.id.img_line_cart_delete);
            holder.imgProduct = view.findViewById(R.id.img_line_cart);
            holder.txtName = view.findViewById(R.id.txt_line_cart_name);
            holder.txtPrice = view.findViewById(R.id.txt_line_cart_price);
            holder.txtQuantity = view.findViewById(R.id.txt_line_cart_quantity);
            holder.btnPlus = view.findViewById(R.id.btn_line_cart_plus);
            holder.btnMinus = view.findViewById(R.id.btn_line_cart_minus);

            view.setTag(holder);
        } else {
            holder = (ListCartAdapter.ViewHolder) view.getTag();
        }
        Cart cart = cartsList.get(i);
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(cart.getMessage());
                if(cart.getMessage() == null){
                    Toast.makeText(context, "Bạn chưa để lại lời nhắn cho\t" +cart.getName()+"!", Toast.LENGTH_SHORT).show();
                }else{
                    alertDialog=  builder.create();
                    alertDialog.show();
                }
                return true;
            }
        });
        if (cart.getImage() == null) {
            holder.imgProduct.setImageResource(R.drawable.icon_facebook);
        } else {
            Picasso.get().load(cart.getImage()).into(holder.imgProduct);
        }
        holder.txtName.setText(cart.getName());
        holder.txtPrice.setText(cart.getPrice()+ "đ");
        holder.txtQuantity.setText(cart.getQuantity()+"");
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total.setText(Double.parseDouble(total.getText().toString().trim().replace("đ","")) + cart.getPrice() + "\t đ");
                cart.setQuantity(cart.getQuantity() + 1);
                updateQuantity(cart.getId_Product(), cart.getQuantity());
                holder.txtQuantity.setText(String.valueOf(cart.getQuantity() ));
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cart.getQuantity() > 1){
                    total.setText(Double.parseDouble(total.getText().toString().trim().replace("đ","")) - cart.getPrice() + "\t đ");
                    cart.setQuantity(cart.getQuantity() - 1);
                    updateQuantity(cart.getId_Product(), cart.getQuantity());
                    holder.txtQuantity.setText(String.valueOf(cart.getQuantity()));
                }else{
                    Toast.makeText(context, "Số lượng phải lớn hơn 1", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có muốn xóa\t" + cart.getName() + "\tkhông!")
                        .setCancelable(false)
                        .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                UtilityClass.showProgressDialog(context);
                                Call<Cart> call = apiservice.DeleteCart(cart.getId());
                                call.enqueue(new Callback<Cart>() {
                                    @Override
                                    public void onResponse(Call<Cart> call, Response<Cart> response) {
                                        if (response.body() != null) {
                                            Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                            total.setText(Double.parseDouble(total.getText().toString().trim().replace("đ","")) - (cart.getPrice() * cart.getQuantity()) + "\t đ");
                                            cartsList.remove(cart);
                                            notifyDataSetChanged();
                                            UtilityClass.HideProgressDialog();
                                        } else {
                                            Toast.makeText(context, "Lỗi!", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Cart> call, Throwable t) {
                                        Toast.makeText(context, "Lỗi!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).show();
            }
        });
        return view;
    }
    private  void updateQuantity(int Id_Product,int quantity){
        int Id_User = Integer.parseInt(UtilityClass.retrieveData(context,"Id_User",""));
        Call<Cart> call = apiservice.UpdateQuantityCart(Id_User,Id_Product,quantity);
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {

            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
            }
        });
    }
}

