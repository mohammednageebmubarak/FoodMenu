package com.example.foodmenu.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodmenu.Interface.ItemClickListener;
import com.example.foodmenu.R;


public class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtcomment;
    public ImageView imageView;

    private ItemClickListener itemClickListener;

    public CommentViewHolder(View itemView) {
        super(itemView);

        txtcomment = (TextView)itemView.findViewById(R.id.comment);
        imageView = (ImageView)itemView.findViewById(R.id.comment_profile_image);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);

    }


}
