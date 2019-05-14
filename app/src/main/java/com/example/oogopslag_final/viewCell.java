package com.example.oogopslag_final;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.oogopslag_final.Model.Kist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class viewCell extends AppCompatActivity {

    ArrayList<Kist> kisten = new ArrayList<>();
    Button btn_viewCel;
    EditText edit_viewCel;
    ListView lv;
    private KistAdapter kistAdapter;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_cells = database.getReference("Cells");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cel);
        btn_viewCel = findViewById(R.id.btnViewCel);
        edit_viewCel = findViewById(R.id.edtViewCel);
        lv = findViewById(R.id.kistenView);

        btn_viewCel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCell();
            }
        });


    }

    public void showCell(){
        kisten.clear();
        final DatabaseReference table_cell = table_cells.child("Cell" + edit_viewCel.getText());

        table_cell.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Kist kist = snapshot.getValue(Kist.class);
                    kisten.add(kist);
                    loadListView();





                    //System.out.println(kist.getCel());
                    //System.out.println(snapshot);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    public void loadListView(){
        kistAdapter = new KistAdapter(this, kisten);
        lv.setAdapter(kistAdapter);






    }
}
