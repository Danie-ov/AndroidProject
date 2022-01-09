package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class SignUpActivity extends AppCompatActivity {

    private MaterialButton Signup_BTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Signup_BTN = findViewById(R.id.Signup_BTN);
        Signup_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationDone();
            }
        });
    }

    private void animationDone(){
        Intent intent= new Intent(this,NavDrawerMenu.class);
        startActivity(intent);
        finish();
    }
}