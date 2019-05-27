package com.example.oogopslag_final;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oogopslag_final.Model.Ras;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddRas extends AppCompatActivity {
    EditText edit_ras;
    Button btn_ras, btn_delras;
    Boolean rasExists = false;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_rassen = database.getReference("Rassen");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ras);
        edit_ras = (EditText) findViewById(R.id.edtAddRas);
        btn_ras = (Button) findViewById(R.id.btnAddRas);
        btn_delras = (Button) findViewById(R.id.btnRemoveRas);


        btn_ras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aName = edit_ras.getText().toString();
                addRas(aName);




            }
        });

        btn_delras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rName = edit_ras.getText().toString();
                removeRas(rName);


            }
        });


    }

    public void addRas(String name){
        final String aName = name;
        System.out.println(aName);
        table_rassen.child(aName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    Toast.makeText(AddRas.this, "Ras: " + aName + " bestaat al", Toast.LENGTH_SHORT).show();



                }
                else{
                    Ras ras = new Ras(aName);
                    table_rassen.child(aName).setValue(ras);
                    Toast.makeText(AddRas.this, "Ras: " + aName + " toegevoegd!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }



    public void removeRas(String name){
        final String rName = name;
        table_rassen.child(rName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    table_rassen.child(rName).removeValue();
                    Toast.makeText(AddRas.this, "Ras: " + rName + " verwijderd!", Toast.LENGTH_SHORT).show();
                    finish();

                }
                else{
                    Toast.makeText(AddRas.this, "Ras: " + rName + " bestaat niet!", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }
}
