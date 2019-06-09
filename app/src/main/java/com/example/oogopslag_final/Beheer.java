package com.example.oogopslag_final;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.oogopslag_final.Common.Common;
import com.example.oogopslag_final.Model.Cell;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Beheer extends AppCompatActivity {

    Button btn_JumpCell, btn_JumpRas, btn_JumpRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beheer);

        btn_JumpCell = findViewById(R.id.btnJumpCell);
        btn_JumpRas = findViewById(R.id.btnJumpRas);
        btn_JumpRegister = findViewById(R.id.btnRegister);


        btn_JumpCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Beheer.this, celBeheer.class);
                startActivity(intent);
                finish();

            }
        });

        btn_JumpRas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Beheer.this, AddRas.class);
                startActivity(intent);
                finish();
            }
        });

        btn_JumpRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Beheer.this, Register.class);
                startActivity(intent);
                finish();

            }
        });
    }




}
