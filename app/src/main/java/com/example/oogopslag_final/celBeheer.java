package com.example.oogopslag_final;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.oogopslag_final.Model.Cell;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class celBeheer extends AppCompatActivity {

    Button btn_AddCell, btn_DelCell;
    long numCells;
    Spinner spinner_cell;
    List<String> list_cellen = new ArrayList<>();
    String selectedCell, delCell;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cel_beheer);

        btn_AddCell = findViewById(R.id.btnAddCell);
        btn_DelCell = findViewById(R.id.btnRemoveCell);
        spinner_cell = findViewById(R.id.spinner_cell);

        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_cells = database.getReference("Cells");

        table_cells.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list_cellen.clear();
                numCells = dataSnapshot.getChildrenCount();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    list_cellen.add(ds.getKey());
                }
                initAdapters();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_AddCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cellname = "Cell" + (numCells + 1);
                Cell cell = new Cell(cellname);
                table_cells.child(cellname).setValue(cell);
                Toast.makeText(celBeheer.this, "Cel: " + cellname + " is toegevoegd!", Toast.LENGTH_SHORT).show();
//                finish();

            }
        });

        btn_DelCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delCell = selectedCell;
                int dcell = Integer.parseInt(delCell.substring(4));
                System.out.println(dcell);
                if(!delCell.equals("")){
                    if(dcell >= numCells){
                        table_cells.child(delCell).removeValue();
                        Toast.makeText(celBeheer.this, "Cel: " + delCell + " is verwijderd!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(celBeheer.this, "Error; je kunt alleen de laatste cel verwijderen!", Toast.LENGTH_SHORT).show();


                    }


                }
                else{
                    Toast.makeText(celBeheer.this, "Kies een cel!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void initAdapters(){
        // ArrayAdapter for spinner list_users.
        ArrayAdapter<String> adapter_users = new ArrayAdapter<String>(this, R.layout.my_spinner, list_cellen);
        //adapter_users.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cell.setAdapter(adapter_users);
        spinner_cell.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String itemvalue = parent.getItemAtPosition(position).toString();
                //Toast.makeText(AddKist.this, "Selected: " + itemvalue, Toast.LENGTH_SHORT).show();
                selectedCell = itemvalue;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
