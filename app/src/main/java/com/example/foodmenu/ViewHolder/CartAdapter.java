package com.example.foodmenu.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.foodmenu.Interface.ItemClickListener;
import com.example.foodmenu.Model.Order;
import com.example.foodmenu.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.foodmenu.Common.Common.DELETE;


public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> listData =new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> listData,Context context){
        this.listData = listData;
        this.context = context;
    }


    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.cart_layout,parent,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        TextDrawable drawable=TextDrawable.builder()
                .buildRound(""+listData.get(position).getQuantity(), Color.RED);
        holder.img_cart_count.setImageDrawable(drawable);

        Locale locale =new Locale("en","us");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price= (Integer.parseInt(listData.get(position).getPrice()))*Integer.parseInt(listData.get(position).getQuantity());
        holder.txt_cart_price.setText(fmt.format(price));

        holder.txt_cart_name.setText(listData.get(position).getProdectName());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
