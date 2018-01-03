package com.example.mubarak.foodmenu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mubarak.foodmenu.Common.Common;
import com.example.mubarak.foodmenu.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;


public class SignUp extends AppCompatActivity {
    MaterialEditText edtPhone,edtName,edtPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Impelement Wedgets
        edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);
        edtName = (MaterialEditText)findViewById(R.id.edtName);
        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);


        //Init Firebase Database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");


        //Event onClick
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();


                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        //check if user PhoneNumber is exiest in database
                        if(dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            //User information
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Phone number is already exists", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                        else
                        {
                            User user = new User(edtName.getText().toString() , edtPassword.getText().toString());
                            table_user.child(edtPhone.getText().toString()).setValue(user);
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this,"SignUp Done Seccessfuly",Toast.LENGTH_SHORT).show();

                            Intent homeIntent = new Intent(SignUp.this,Home.class);
                            Common.currentUser = user;
                            Toast.makeText(SignUp.this,"Opening HOME page",Toast.LENGTH_SHORT).show();
                            startActivity(homeIntent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
