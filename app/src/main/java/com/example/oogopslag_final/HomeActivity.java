package com.example.oogopslag_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oogopslag_final.Common.Common;

public class HomeActivity extends AppCompatActivity {
    private Button btnAddKist;
    private Button btnBeheer;
    private Button btnViewCell;
    TextView txtView;
    String LoggedInUser;
    String LoggedInUserRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtView = findViewById(R.id.txtHi);

        LoggedInUser = Common.currentUser.getName();
        LoggedInUserRole = Common.currentUser.getRole();
        Toast.makeText(HomeActivity.this, "Hi " + LoggedInUser + " !", Toast.LENGTH_SHORT).show();
        txtView.setText("Hallo: " +  LoggedInUser);


        btnAddKist = findViewById(R.id.btn_addkist);
        btnAddKist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddKist();

            }
        });

        btnBeheer = findViewById(R.id.btn_beheer);
        btnBeheer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(LoggedInUserRole);
                if(LoggedInUserRole.equals("Admin"))
                {
                    Toast.makeText(HomeActivity.this, "Succes, role: " + LoggedInUserRole, Toast.LENGTH_SHORT).show();
                    openBeheer();

                }
                else if(LoggedInUserRole.equals("Gebruiker"))
                {
                    Toast.makeText(HomeActivity.this, "Failed, role: " + LoggedInUserRole, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(HomeActivity.this, "Failed, unknown role: " + LoggedInUserRole, Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnViewCell = findViewById(R.id.btn_viewcell);
        btnViewCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    openViewCell();
            }
        });



    }

    public void openAddKist(){
        Intent intent = new Intent(this, AddKist.class);
        startActivity(intent);

    }

    public void openBeheer(){
        Intent intent = new Intent(this, Beheer.class);
        startActivity(intent);

    }

    public void openViewCell(){
        Intent intent = new Intent(this, viewCell.class);
        startActivity(intent);

    }






}
