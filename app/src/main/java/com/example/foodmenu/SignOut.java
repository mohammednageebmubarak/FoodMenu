package com.example.foodmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

public class SignOut extends AppCompatActivity {

    EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_dialog);



        //imp wedgets
        edtPassword = (EditText) findViewById(R.id.txtuserpass);



    }
}
