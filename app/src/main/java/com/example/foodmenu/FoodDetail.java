package com.example.foodmenu;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.foodmenu.Database.Database;
import com.example.foodmenu.Model.Food;
import com.example.foodmenu.Model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetail extends AppCompatActivity {
    TextView food_name,food_price,food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsing_toolbar_layout;
    FloatingActionButton btnCart;
    ElegantNumberButton number_button;

    String foodId="";

    FirebaseDatabase database;
    DatabaseReference foods;

    Food currentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        //Firebase
        database=FirebaseDatabase.getInstance();
        foods=database.getReference("Foods");

        //Init view
        number_button=(ElegantNumberButton)findViewById(R.id.number_button);


        food_description=(TextView)findViewById(R.id.food_description);
        food_name=(TextView)findViewById(R.id.food_name);
        food_price=(TextView)findViewById(R.id.food_price);
        food_image=(ImageView)findViewById(R.id.img_food);
        collapsing_toolbar_layout=(CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsing_toolbar_layout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsing_toolbar_layout.setCollapsedTitleTextAppearance(R.style.CollapcedAppbar);

        btnCart =(FloatingActionButton)findViewById(R.id.btnCart);



        //get food ID from intent
        if (getIntent()!=null)
            foodId=getIntent().getStringExtra("FoodId");
        if (!foodId.isEmpty())
        {
            getDetailFood(foodId);
        }

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(

                        foodId,
                        currentFood.getName(),
                        number_button.getNumber(),
                        currentFood.getPrice(),
                        currentFood.getDiscount()
                ));
                Toast.makeText(FoodDetail.this, "Added to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void getDetailFood(String foodId) {
    foods.child(foodId).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            currentFood =dataSnapshot.getValue(Food.class);

            //set image
            Picasso.with(getBaseContext()).load(currentFood.getImage())
                    .into(food_image);
            collapsing_toolbar_layout.setTitle(currentFood.getName());

            food_price.setText(currentFood.getPrice());
            food_name.setText(currentFood.getName());
            food_description.setText(currentFood.getDescription());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
    }
}
