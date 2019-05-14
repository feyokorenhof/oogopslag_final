package com.example.oogopslag_final;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.oogopslag_final.Model.Ras;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddRas extends AppCompatActivity {
    EditText edit_ras;
    Button btn_ras, btn_delras;

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
                addRas(edit_ras.getText().toString());

            }
        });

        btn_delras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeRas(edit_ras.getText().toString());
            }
        });


    }

    public void addRas(String name){
        String aName = name;
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_rassen = database.getReference("Rassen");
        Ras ras = new Ras(aName);
        table_rassen.child(aName).setValue(ras);


    }

    public void removeRas(String name){
        String rName = name;
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_rassen = database.getReference("Rassen");
        table_rassen.child(rName).removeValue();


    }
}
