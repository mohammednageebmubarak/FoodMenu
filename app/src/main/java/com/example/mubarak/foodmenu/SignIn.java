package com.example.mubarak.foodmenu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mubarak.foodmenu.Common.Common;
import com.example.mubarak.foodmenu.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {

    EditText edtPhone,edtPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);



        //imp wedgets
        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);




        //Init firebase

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");



        //SignIn button functions:
        //Listener
        btnSignIn .setOnClickListener(new View.OnClickListener(){
                @Override



            public void onClick(View view){
                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                            mDialog.setMessage("Please wait...");
                            mDialog.show();

                            //check if user exiest in database
                            if(dataSnapshot.child(edtPhone.getText().toString()).exists()) {


                                //User information
                                mDialog.dismiss();

                                User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                                if (user.getPassword().equals(edtPassword.getText().toString())) {
                                    {
                                        Intent homeIntent = new Intent(SignIn.this,Home.class);
                                        Common.currentUser = user;

                                        startActivity(homeIntent);

                                        finish();

                                    }


                                } else
                                    Toast.makeText(SignIn.this, "Sign In Faild", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                mDialog.dismiss();
                                Toast.makeText(SignIn.this,"User not exiest",Toast.LENGTH_SHORT).show();
                            }




                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(SignIn.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                        }
                    });



            }
        });

    }
}
