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

public class Beheer extends AppCompatActivity {

    Button btn_AddCell, btn_JumpRas;
    long numCells;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beheer);

        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_cells = database.getReference("Cells");

        btn_AddCell = (Button) findViewById(R.id.btnAddCell);
        btn_JumpRas = (Button) findViewById(R.id.btnJumpRas);

        table_cells.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                numCells = dataSnapshot.getChildrenCount();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_AddCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                //Date date = new Date();
                String cellname = "Cell" + (numCells + 1);

                System.out.println(cellname);
                table_cells.child(cellname).setValue(cellname);
                finish();





            }
        });

        btn_JumpRas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpToRas();
            }
        });
    }

    public void JumpToRas(){
        Intent intent = new Intent(this, AddRas.class);
        startActivity(intent);
        finish();

    }


}
