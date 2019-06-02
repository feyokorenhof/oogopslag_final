package com.example.oogopslag_final;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oogopslag_final.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class Register extends AppCompatActivity {

    EditText edtName, edtPassword;
    Button btnRegister, btnDelUser, btnBirthday;
    Calendar calendar;
    DatePickerDialog dpd;
    String date, role;
    CheckBox checkAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtName = findViewById(R.id.edit_reguser);
        edtPassword = findViewById(R.id.edit_regpassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnDelUser = findViewById(R.id.btnDelUser);
        btnBirthday = findViewById(R.id.btn_bday);
        checkAdmin = findViewById(R.id.checkAdmin);

        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");



        btnBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                dpd = new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date = Integer.toString(dayOfMonth) + "/" + Integer.toString(month + 1) + "/"
                         + Integer.toString(year);
                    }
                }, day, month, year);
                dpd.show();


//                btnBirthday.setVisibility(View.GONE);
//                btnRegister.setVisibility(View.GONE);
//                btnDelUser.setVisibility(View.GONE);
//                edtName.setVisibility(View.GONE);
//                edtPassword.setVisibility(View.GONE);
//                checkAdmin.setVisibility(View.GONE);


            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final ProgressDialog mDialog = new ProgressDialog(Register.this);
//                mDialog.setMessage("Please wait...");
//                mDialog.show();
                if(checkAdmin.isChecked()){
                    role = "Admin";
                }else{
                    role = "Gebruiker";
                }

                table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Check if name already exists
                        String aName = edtName.getText().toString();
                        String aPass = edtPassword.getText().toString();

                        if(dataSnapshot.child(aName).exists())
                        {
//                            mDialog.dismiss();
                            Toast.makeText(Register.this, "Gebruiker: " + aName + " bestaat al!", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            if(aPass.equals("") || aPass.equals(" ")) {
                                Toast.makeText(Register.this, "Vul een wachtwoord in!", Toast.LENGTH_SHORT).show();
                            }
                            else{
//                                mDialog.dismiss();
                                User user = new User(edtName.getText().toString(), edtPassword.getText().toString(), role, date);
                                System.out.println(user.getBirthday());
                                table_user.child(edtName.getText().toString()).setValue(user);
                                Toast.makeText(Register.this, "Gebruiker: " + aName + "is toegevoegd!", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        btnDelUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Check if name already exists
                        String delName = edtName.getText().toString();
                        if(dataSnapshot.child(delName).exists())                        {

//                            mDialog.dismiss();
                            table_user.child(delName).removeValue();
                            Toast.makeText(Register.this, "Gebruiker " + delName + " is verwijderd!", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(Register.this, "Gebruiker " + delName + " bestaat niet!", Toast.LENGTH_SHORT).show();
//
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
