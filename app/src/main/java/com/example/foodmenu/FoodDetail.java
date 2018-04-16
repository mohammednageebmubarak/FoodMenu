package com.example.foodmenu;

import android.renderscript.Sampler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.foodmenu.Common.Common;
import com.example.foodmenu.Database.Database;
import com.example.foodmenu.Model.Food;
import com.example.foodmenu.Model.Order;
import com.example.foodmenu.Model.Rating;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.lang.reflect.Array;
import java.util.Arrays;

public class FoodDetail extends AppCompatActivity implements RatingDialogListener{
    TextView food_name,food_price,food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsing_toolbar_layout;
    FloatingActionButton btnCart,btnRaring;
    RatingBar ratingBar;
    ElegantNumberButton number_button;
    FirebaseDatabase database;
    DatabaseReference foods,ratingTbl;
    Food currentFood;
    String foodId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        //Firebase
        database=FirebaseDatabase.getInstance();
        foods=database.getReference("Foods");
        ratingTbl= database.getReference("Rating");

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
        btnRaring =(FloatingActionButton)findViewById(R.id.btnRating);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);

        btnRaring.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             showRatingDialog();

                                         }
                                     }

        );
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


                //get food ID from intent
        if (getIntent()!=null)
            foodId=getIntent().getStringExtra("FoodId");
        if (!foodId.isEmpty())
        {
            getDetailFood(foodId);
            getRatingFood(foodId);
        }


    }

    private void getRatingFood(String foodId) {
        Query foodRating = ratingTbl.orderByChild("foodId").equalTo(foodId);
        foodRating.addValueEventListener(new ValueEventListener() {
            int count = 0,sum = 0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Rating item = postSnapshot.getValue(Rating.class);
                    sum+=Integer.parseInt(item.getRateValue());
                    count++;
                }
                if (count != 0)
                {
                    float average = sum/count;
                    ratingBar.setRating(average);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showRatingDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setDefaultRating(1)
                .setNoteDescriptions(Arrays.asList("Vary bad","Not good","good","Vary Good","Excellent"))
                .setTitle("Rate this food")
                .setDescription("Please select some stars and give your feedback")
                .setTitleTextColor(R.color.colorPrimary)
                .setNoteDescriptionTextColor(R.color.colorPrimary)
                .setHint("Write your comment here")
                .setHintTextColor(R.color.colorPrimary)
                .setCommentTextColor(android.R.color.white)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .create(FoodDetail.this)
                .show();

    }

    private void getDetailFood(String foodId) {
    foods.child(foodId).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            currentFood = dataSnapshot.getValue(Food.class);

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


    //RatingDialogListener Methods
    @Override
    public void onPositiveButtonClicked(int value, String comments) {
        final Rating rating = new Rating(Common.currentUser.getPhone(),
                foodId,
                String.valueOf(value),
                comments);
        ratingTbl.child(Common.currentUser.getPhone()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(Common.currentUser.getPhone()).exists())
                {
                    ratingTbl.child(Common.currentUser.getPhone()).removeValue();
                    ratingTbl.child(Common.currentUser.getPhone()).setValue(rating);
                }
                else
                    ratingTbl.child(Common.currentUser.getPhone()).setValue(rating);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }
}
