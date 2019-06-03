package com.example.oogopslag_final;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oogopslag_final.Common.Common;
import com.plattysoft.leonids.ParticleSystem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity {
    private Button btnAddKist;
    private Button btnBeheer;
    private Button btnViewCell;
    TextView txtView, txtEmitR, txtEmitL;
    String LoggedInUser;
    String LoggedInUserRole;
    String LoggedInUserBirthdate;
    String LoggedInUserBirthday;
    Boolean hasBday = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtView = findViewById(R.id.txtHi);
        txtEmitR = findViewById(R.id.txtEmitRight);
        txtEmitL = findViewById(R.id.txtEmitLeft);


        LoggedInUser = Common.currentUser.getName();
        LoggedInUserRole = Common.currentUser.getRole();
        LoggedInUserBirthdate = Common.currentUser.getBirthday();
        LoggedInUserBirthday = LoggedInUserBirthdate.substring(0, 3);
        //System.out.println(LoggedInUserBirthday);

        DateFormat dateFormat = new SimpleDateFormat("d/M");
        Date date = new Date();
        String today = dateFormat.format(date);
        //System.out.println(today);
        if(today.equals(LoggedInUserBirthday)){
            Toast.makeText(this, "Gefeliciteerd " + LoggedInUser + "!", Toast.LENGTH_SHORT).show();
            txtView.setText("Fijne verjaardag!");
            hasBday = true;
            new ParticleSystem(HomeActivity.this, 400, R.drawable.confeti2, 20000)
                    .setSpeedRange(0.01f, 0.5f)
                    .setAcceleration(0.00005f, 90)
                    .setRotationSpeedRange(-15f, 15f)
                    .oneShot(findViewById(R.id.txtHi), 400);
            new ParticleSystem(HomeActivity.this, 400, R.drawable.confeti3, 20000)
                    .setSpeedRange(0.01f, 0.5f)
                    .setAcceleration(0.00005f, 90)
                    .setRotationSpeedRange(-15f, 15f)
                    .oneShot(findViewById(R.id.txtHi), 400);

        }
        else{
            Toast.makeText(HomeActivity.this, "Hi " + LoggedInUser + " !", Toast.LENGTH_SHORT).show();
            txtView.setText("Hallo: " +  LoggedInUser);

        }




        btnAddKist = findViewById(R.id.btn_addkist);
        btnAddKist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasBday){
                    happyBirthday(btnAddKist);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            openAddKist();
                        }
                    }, 1000);

                }
                else{
                    openAddKist();
                }


            }
        });

        btnBeheer = findViewById(R.id.btn_beheer);
        btnBeheer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasBday){
                    happyBirthday(btnBeheer);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
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
                    }, 1000);

                }
                else{
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


            }
        });

        btnViewCell = findViewById(R.id.btn_viewcell);
        btnViewCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasBday){
                    happyBirthday(btnViewCell);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            openViewCell();
                        }
                    }, 1000);

                }
                else{
                    openViewCell();
                }
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

    public void happyBirthday(View view){
        View target = view;
        new ParticleSystem(HomeActivity.this, 80, R.drawable.confeti2, 1000)
                .setSpeedRange(0.2f, 0.5f)
                .setAcceleration(0.00005f, 90)
                .setRotationSpeedRange(-15f, 15f)
                .oneShot(target, 80);
        new ParticleSystem(HomeActivity.this, 80, R.drawable.confeti3, 1000)
                .setSpeedRange(0.2f, 0.5f)
                .oneShot(target, 80);


    }







    }








