package com.example.oogopslag_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oogopslag_final.Common.Common;
import com.example.oogopslag_final.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity {

    EditText edtUser, edtPassword;
    Button btnLogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edtUser = findViewById(R.id.edit_user);
        edtPassword = findViewById(R.id.edit_password);
        btnLogin = findViewById(R.id.btnLogin);

        // Init Firebase

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // Check if user exists
                        if (dataSnapshot.child(edtUser.getText().toString()).exists()) {


                            //Get User information
                            User user = dataSnapshot.child(edtUser.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(edtPassword.getText().toString())) {
                                Common.currentUser = dataSnapshot.child(edtUser.getText().toString()).getValue(User.class);

                                openHomePage();
                                Toast.makeText(Login.this, "Sign in succesful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Login.this, "Sign in failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                        else{
                            Toast.makeText(Login.this, "User doesn't exist", Toast.LENGTH_SHORT).show();

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public void openHomePage(){
        Intent homeIntent = new Intent(Login.this, HomeActivity.class);
        startActivity(homeIntent);
        finish();

    }
}
