package com.example.foodmenu;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodmenu.Common.Common;
import com.example.foodmenu.Database.Database;
import com.example.foodmenu.Model.Order;
import com.example.foodmenu.Model.Request;
import com.example.foodmenu.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    Button btnPlace;
    List<Order> cart = new ArrayList<>();

    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        //init
        recyclerView =(RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);

        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total);
        btnPlace = (Button)findViewById(R.id.btnPlaceOrder);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        loadListFood();



    }

        private void showAlertDialog() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
            alertDialog.setTitle("");
            alertDialog.setMessage("");
            final EditText edtAdress = new EditText(Cart.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            edtAdress.setLayoutParams(lp);
            alertDialog.setView(edtAdress);
            alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Request request = new Request(
                            Common.currentUser.getPhone(),
                            Common.currentUser.getName(),
                            edtAdress.getText().toString(),
                            txtTotalPrice.getText().toString(),
                            cart
                    );
                    //key
                    requests.child(String.valueOf(System.currentTimeMillis()))
                    .setValue(request);

                    //Delete Cart
                    new Database(getBaseContext()).cleanCart();
                    Toast.makeText(Cart.this, "Thank you ,Order Place", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialog.show();

         }
        private void loadListFood() {

        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);

        //Calcolate total price
        int total = 0;
        for(Order order:cart)
            total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
        Locale locale =new Locale("en","us");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));

    }


}
