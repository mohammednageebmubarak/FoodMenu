package com.example.foodmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.foodmenu.Interface.ItemClickListener;
import com.example.foodmenu.Model.Food;
import com.example.foodmenu.Model.Rating;
import com.example.foodmenu.ViewHolder.CommentViewHolder;
import com.example.foodmenu.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CommentList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference commentList;
    FirebaseRecyclerAdapter<Rating,CommentViewHolder> adapter;
    String foodId ="";
    Rating comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Comments");
        setSupportActionBar(toolbar);

        database=FirebaseDatabase.getInstance();
        commentList=database.getReference("Rating");

        recyclerView= findViewById(R.id.recycler_comment);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        foodId = getIntent().getStringExtra("foodId");
        if (!foodId.isEmpty()) {
            loadListcomment(foodId);
        }
    }

    private void loadListcomment(String foodId) {
        adapter = new FirebaseRecyclerAdapter<Rating, CommentViewHolder>(
                Rating.class,
                R.layout.comment_item,
                CommentViewHolder.class,
                commentList.orderByChild("foodId")
                .equalTo(foodId)) {

            @Override
            protected void populateViewHolder(CommentViewHolder viewHolder, Rating model, int position) {
                viewHolder.txtcomment.setText(model.getComment());

/*
                final Rating local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodDetail = new Intent(CommentList.this,FoodDetail.class);
                        foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });*/
            }

            /*@Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.food_image);


                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodDetail = new Intent(CommentList.this,FoodDetail.class);
                                foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });
            }*/
        };
        recyclerView.setAdapter(adapter);
    }
}
