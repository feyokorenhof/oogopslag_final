package com.example.oogopslag_final;

import android.content.Intent;
import android.net.wifi.aware.PublishConfig;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import com.example.oogopslag_final.Model.Kist;
import com.example.oogopslag_final.Model.Ras;
import com.example.oogopslag_final.Model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.oogopslag_final.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddKist extends AppCompatActivity {

    //Get an instance of the Firebase database
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    //Get a reference to the 'Cells' tree, from the database
    final DatabaseReference table_cells = database.getReference("Cells");

    Spinner spinner_ras;
    Spinner spinner_maat;
    Spinner spinner_kwaliteit;
    Spinner spinner_selectcell;
    private Button button;

    EditText editDate;
    EditText editAmount;

    String selectedRas;
    String selectedMaat;
    String selectedKwaliteit;
    String selectedCell;
    String useDate;

    long numCells;
    long numKist;
    long currentCount;

    String currentCel;


// Add spinner content.

    List<String> list_ras = new ArrayList<>();
    List<String> list_maat = new ArrayList<>();
    List<String> list_kwaliteit = new ArrayList<>();
    List<String> list_selectcell = new ArrayList<>();
    ArrayList<Kist> kisten = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Assign the proper layout to AddKist.java
        setContentView(R.layout.activity_addkist);
        // Initialize button.
        button = findViewById(R.id.btnAddKist);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addKist();
            }

        });

        // Create spinners.
        spinner_ras = findViewById(R.id.spinner_ras);
        spinner_maat = findViewById(R.id.spinner_maat);
        spinner_kwaliteit = findViewById(R.id.spinner_kwaliteit);
        spinner_selectcell = findViewById(R.id.spinner_selectcell);


        //Add list content
        //list_ras.add("Agria");
        list_maat.add("1");
        list_maat.add("2");
        list_kwaliteit.add("Perfect");
        list_kwaliteit.add("Goed");
        list_kwaliteit.add("Matig");
        list_kwaliteit.add("Slecht");
        //Call the setup function
        Setup();




    }



    public void addKist () {
        //kisten.clear();
        //System.out.println(selectedCell);
        Setup();



        // Set numKist to 0 before counting

        //System.out.println(kisten.size());

        // Get current date.
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        Toast.makeText(this, selectedRas + selectedMaat + selectedKwaliteit + selectedCell, Toast.LENGTH_SHORT).show();
        final String value = selectedRas + "," + selectedMaat + ","
                + selectedKwaliteit + "," + selectedCell;


        String ref = "Cell" + selectedCell;
        currentCel = ref;
        editDate = findViewById(R.id.editDate);
        String userDate = editDate.getText().toString();

        if(userDate.equals("")){
            useDate = date.toString();


        }
        else{
            useDate = userDate;

        }


        Kist kist = new Kist(selectedRas, selectedMaat, selectedKwaliteit, selectedCell, useDate);
        //final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_usercell = database.getReference();

        String kistName = "Kist" + (numKist + 1);
        String cellRef = "Cell" + selectedCell;
        //System.out.println(selectedCell);


        table_usercell.child("Cells").child(cellRef).child(kistName).setValue(kist);


//            Intent i = new Intent(this, Cells.class);
//            i.putExtra("key", value);
//            startActivity(i);

    }


    public void Setup(){




        //initAdapters();
        addListeners();


    }

    public void addListeners(){
        //Add a ValueEventListener to the 'Cells' tree
        //Count the children and store it in numCells (Amount of cells in the database)
        //Keep count of the count to prevent the listener from triggering on accident -
        // and thus adding cells twice to the list/spinner content
        table_cells.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                numCells = dataSnapshot.getChildrenCount();
                if(currentCount != numCells){
                    for (long i = 1; i <= numCells; i++) {
                        String num = Long.toString(i);
                        String snum = Long.toString(numCells);
                        list_selectcell.add(num);
                        if (num.equals(snum)) {
                            //Update the spinner adapters with new content (cells)
                            initAdapters();
                            //Looping through children is done. Set count to amount of children -
                            // to jump out of the for loop
                            currentCount = numCells;

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Add a ValueEventListener to the 'Rassen' tree
        final DatabaseReference table_rassen = database.getReference("Rassen");
        table_rassen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Clear the array before counting
                list_ras.clear();
                //Loop through the children
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.getValue() != null){ //Make sure that the received data is valid
                        Ras ras = ds.getValue(Ras.class); //Put the received data into a ras class: Model -> ras
                        if(ras != null){
                            list_ras.add(ras.getRas()); //Add the 'ras' to the list
                        }
                    }
                }

                initAdapters(); //Update the spinner adapters with new content (rassen)
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //System.out.println(editAmount.getText().toString());
        //!Hardcoded to 'Cell1' currently; needs to be dynamic!
        //!Variable selectedCell is not available in the onCreate function!
        //!initAdapters() is called from within the onCreate() but onCreate() -
        //! finishes before actually calling initAdapters() so the variable isn't -
        //! available yet. Normal behaviour but needs rethinking
        final DatabaseReference table_numkist = table_cells.child("Cell" + selectedCell);
        table_numkist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kisten.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Kist kist = ds.getValue(Kist.class);
                    kisten.add(kist);
                    System.out.println(kisten.size());

                }
                numKist = kisten.size();
                initAdapters();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        initAdapters();


    }

    public void initAdapters() {

        // ArrayAdapter for spinner list_ras.
        ArrayAdapter<String> adapter_ras = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_ras);
        adapter_ras.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ras.setAdapter(adapter_ras);
        spinner_ras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String itemvalue = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddKist.this, "Selected: " + itemvalue, Toast.LENGTH_SHORT).show();
                selectedRas = itemvalue;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // ArrayAdapter for spinner list_maat.
        ArrayAdapter<String> adapter_maat = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_maat);
        adapter_maat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_maat.setAdapter(adapter_maat);
        spinner_maat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String itemvalue = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddKist.this, "Selected: " + itemvalue, Toast.LENGTH_SHORT).show();
                selectedMaat = itemvalue;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // ArrayAdapter for spinner list_kwaliteit.
        ArrayAdapter<String> adapter_kwaliteit = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_kwaliteit);
        adapter_kwaliteit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_kwaliteit.setAdapter(adapter_kwaliteit);
        spinner_kwaliteit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String itemvalue = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddKist.this, "Selected: " + itemvalue, Toast.LENGTH_SHORT).show();
                selectedKwaliteit = itemvalue;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // ArrayAdapter for spinner list_selectcell.
        ArrayAdapter<String> adapter_selectcell = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_selectcell);
        adapter_selectcell.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_selectcell.setAdapter(adapter_selectcell);
        spinner_selectcell.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String itemvalue = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddKist.this, "Selected: " + itemvalue, Toast.LENGTH_SHORT).show();
                selectedCell = itemvalue;
                //System.out.println(selectedCell);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }




    }

