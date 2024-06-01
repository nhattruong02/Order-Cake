package com.example.bcake.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.bcake.Model.Category;
import com.example.bcake.Model.Review;
import com.example.bcake.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListReviewAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Review> reviewsList;

    public ListReviewAdapter(Context context, int layout, List<Review> reviewsList) {
        this.context = context;
        this.layout = layout;
        this.reviewsList= reviewsList;
    }

    @Override
    public int getCount() {
        return reviewsList.size();
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
        TextView txtName,txtTime, txtComment;
        RatingBar ratingBar;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListReviewAdapter.ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new ListReviewAdapter.ViewHolder();
            holder.imgAvatar = view.findViewById(R.id.img_line_review);
            holder.txtTime = view.findViewById(R.id.txt_line_review_time);
            holder.txtComment = view.findViewById(R.id.txt_line_review_comment);
            holder.ratingBar = view.findViewById(R.id.rb_line_review);
            holder.txtName = view.findViewById(R.id.txt_line_review_name_user);
            view.setTag(holder);
        } else {
            holder = (ListReviewAdapter.ViewHolder) view.getTag();
        }
        Review review = reviewsList.get(i);
        if (review.getImage() == null) {
            holder.imgAvatar.setImageResource(R.drawable.splashscreen);
        } else {
            Picasso.get().load(review.getImage()).into(holder.imgAvatar);
        }
        holder.txtName.setText(review.getNameUser());
        long currentTime =0;
        long reviewTime =0;
        long resultTime =0;
        String time = "";
        SimpleDateFormat format= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            currentTime = System.currentTimeMillis();
            time = format.format(review.getTime());
            reviewTime  = format.parse(time.trim()).getTime();
        } catch (ParseException e) {
        }
        resultTime = currentTime - reviewTime;
        if(TimeUnit.MILLISECONDS.toMinutes(resultTime) <= 60)
        {
            holder.txtTime.setText(TimeUnit.MILLISECONDS.toMinutes(resultTime) +"'");
        }
        else if(TimeUnit.MILLISECONDS.toHours(resultTime) <= 24){
            holder.txtTime.setText(TimeUnit.MILLISECONDS.toHours(resultTime) +"h");
        }
        else if(TimeUnit.MILLISECONDS.toDays(resultTime) >=1 ){
            holder.txtTime.setText(format.format(review.getTime()).substring(0,10));
        }
        holder.txtComment.setText(review.getComment());
        holder.ratingBar.setRating(review.getNumberOfStar());

        return view;
    }
}