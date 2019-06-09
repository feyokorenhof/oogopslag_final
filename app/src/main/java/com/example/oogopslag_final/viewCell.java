package com.example.oogopslag_final;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oogopslag_final.Model.Kist;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;



public class viewCell extends AppCompatActivity implements RecyclerAdapter.OnPlaatsListener {

    private static String TAG = "viewCell";

    ArrayList<Kist> kisten = new ArrayList<>();
    static LinkedHashMap<String, Integer> rassen = new LinkedHashMap<>();
    Button btn_viewCel, btn_hideQuery;
    EditText edit_viewCel, edit_viewRow;
    ListView lv;
    TextView currentView;
    ProgressBar pgrBar;
    int count;
    int currentCel = 0;
    private KistAdapter kistAdapter;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_cells = database.getReference("Cells");
    RecyclerAdapter adapter;





    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cel);
        btn_viewCel = findViewById(R.id.btnViewCel);
        btn_hideQuery = findViewById(R.id.btn_hide);
        btn_hideQuery.setVisibility(View.GONE);
        edit_viewCel = findViewById(R.id.edtViewCel);
        edit_viewRow = findViewById(R.id.edtViewRow);
        currentView = findViewById(R.id.txtView);
        pgrBar = findViewById(R.id.bar_ruimte);
        lv = findViewById(R.id.kistenView);
        lv.setVisibility(View.GONE);
        pgrBar.setVisibility(View.GONE);





        btn_viewCel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cref = edit_viewCel.getText().toString();
                int cellnum = Integer.parseInt(cref);
                showCell(cellnum);
            }
        });

        btn_hideQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_viewCel.setVisibility(View.VISIBLE);
                edit_viewCel.setVisibility(View.VISIBLE);
                edit_viewRow.setVisibility(View.VISIBLE);
                pgrBar.setVisibility(View.GONE);
                btn_hideQuery.setVisibility(View.GONE);

            }
        });


    }

    public void showCell(int cref){
        kisten.clear();
        rassen.clear();
        String cellRef = "Cell" + cref;
        String rowRef = "row" + edit_viewRow.getText();
        currentCel = cref;
        currentView.setText(cellRef + " " + rowRef);
        final DatabaseReference table_cellBird = table_cells.child(cellRef);
        final DatabaseReference table_cellzoom = table_cells.child(cellRef).child(rowRef);
        lv.setVisibility(View.GONE);
        btn_hideQuery.setVisibility(View.VISIBLE);
        pgrBar.setVisibility(View.VISIBLE);
        btn_viewCel.setVisibility(View.GONE);
        edit_viewCel.setVisibility(View.GONE);
        edit_viewRow.setVisibility(View.GONE);

        if(edit_viewRow.getText().toString().equals("")) {

            System.out.println("Bird");
            table_cellBird.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (!snapshot.getKey().equals("cName")) {
                            Kist kist = snapshot.getValue(Kist.class);
                            kisten.add(kist);
                            if (rassen.containsKey(kist.getRas())) {
                                count = rassen.get(kist.getRas());
                                rassen.put(kist.getRas(), count + 1);

                            } else {
                                rassen.put(kist.getRas(), 1);

                            }

                        }
                        //System.out.println(kist.getCel());
                        //System.out.println(snapshot);
                        float count = kisten.size();
                        float division = count / 780;
                        float percent = division * 100;
                        //System.out.println(percent);
                        pgrBar.setProgress(Math.round(percent));

                    }

                    showTable(rassen);

                    //System.out.println(rassen);



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }



        else{
            lv.setVisibility(View.VISIBLE);

            table_cellzoom.addValueEventListener(new ValueEventListener() {
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









    }

    public void loadListView(){
        kistAdapter = new KistAdapter(this, kisten);
        lv.setAdapter(kistAdapter);






    }

    public void showTable(LinkedHashMap data) {
        System.out.println(data);
        RecyclerView recyclerView = findViewById(R.id.celView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 13, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
        adapter = new RecyclerAdapter(data, this);
        recyclerView.setAdapter(adapter);



    }

    @Override
    public void onPlaatsClick(int position) {
        System.out.println(position);




    }

}
