package com.example.oogopslag_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Login_SignUp extends AppCompatActivity {
    Button btn_login, btn_register;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        btn_login = findViewById(R.id.btnLogin);
        btn_register = findViewById(R.id.btn_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });



    }

    public void login(){
        Intent loginIntent = new Intent(Login_SignUp.this, Login.class);
        startActivity(loginIntent);
        //finish();

    }

    public void register(){
        Intent regIntent = new Intent(Login_SignUp.this, Register.class);
        startActivity(regIntent);
        //finish();


    }
}
