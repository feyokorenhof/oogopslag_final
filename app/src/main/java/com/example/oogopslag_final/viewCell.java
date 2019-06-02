package com.example.oogopslag_final;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.github.mikephil.charting.charts.PieChart;
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

public class viewCell extends AppCompatActivity {

    private static String TAG = "viewCell";

    ArrayList<Kist> kisten = new ArrayList<>();
    LinkedHashMap<String, Integer> rassen = new LinkedHashMap<>();
    ArrayList<PieEntry> yEntries = new ArrayList<>();
    ArrayList<String> xEntries = new ArrayList<>();
    PieChart pieChart;
    int[] colors = {Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN, Color.RED, Color.YELLOW, Color.GRAY, Color.BLACK, Color.WHITE, Color.DKGRAY};

    Button btn_viewCel, btn_hideQuery;
    EditText edit_viewCel, edit_viewRow;
    ListView lv;
    TableLayout tv;
    TextView currentView;
    ProgressBar pgrBar;
    private View chart;
    int count;
    int currentCel = 0;
    private KistAdapter kistAdapter;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_cells = database.getReference("Cells");





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
        tv = findViewById(R.id.table_layout);
        pieChart = findViewById(R.id.piechart);
        lv.setVisibility(View.GONE);
        tv.setVisibility(View.GONE);
        pgrBar.setVisibility(View.GONE);
        pieChart.setVisibility(View.VISIBLE);
        pieChart.getDescription().setText("Verdeling Rassen");
        pieChart.setNoDataText("Kies een cel of swipe naar beneden!");
        pieChart.setNoDataTextColor(Color.BLACK);
        pieChart.setNoDataTextTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        pieChart.setRotationEnabled(false);
        pieChart.setHoleRadius(25f);
        pieChart.setCenterText("Verdeling rassen");
        pieChart.setCenterTextSize(10);
        pieChart.setDrawEntryLabels(true);
        Legend legend = pieChart.getLegend();
        legend.setTextSize(14);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        pieChart.setOnTouchListener(new swipeListener(viewCell.this){
            public void onSwipeTop() {
                Toast.makeText(viewCell.this, "pc: top", Toast.LENGTH_SHORT).show();
                String txtCell = edit_viewCel.getText().toString();
                int cellnum = currentCel - 1;
                showCell(cellnum);

            }
            public void onSwipeRight() {
                Toast.makeText(viewCell.this, "pc: right", Toast.LENGTH_SHORT).show();
                tv.setVisibility(View.VISIBLE);
                pieChart.setVisibility(View.GONE);
                showTable(currentCel);

            }
            public void onSwipeLeft() {
                Toast.makeText(viewCell.this, "pc: left", Toast.LENGTH_SHORT).show();
                tv.setVisibility(View.VISIBLE);
                pieChart.setVisibility(View.GONE);
                showTable(currentCel);
            }
            public void onSwipeBottom() {
                Toast.makeText(viewCell.this, "pc: bottom", Toast.LENGTH_SHORT).show();
                String txtCell = edit_viewCel.getText().toString();
                int cellnum = currentCel + 1;
                showCell(cellnum);

            }

        });

        tv.setOnTouchListener(new swipeListener(viewCell.this){
            public void onSwipeTop(){
                Toast.makeText(viewCell.this, "sv: top", Toast.LENGTH_SHORT).show();
                showTable(currentCel - 1);

            }
            public void onSwipeRight(){
                Toast.makeText(viewCell.this, "sv: right", Toast.LENGTH_SHORT).show();
                pieChart.setVisibility(View.VISIBLE);
                tv.setVisibility(View.GONE);
                showCell(currentCel);
            }
            public void onSwipeLeft(){
                Toast.makeText(viewCell.this, "sv: left", Toast.LENGTH_SHORT).show();
                pieChart.setVisibility(View.VISIBLE);
                tv.setVisibility(View.GONE);
                showCell(currentCel);
            }
            public void onSwipeBottom(){
                Toast.makeText(viewCell.this, "sv: bottom", Toast.LENGTH_SHORT).show();
                showTable(currentCel + 1);

            }
        });





        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());
                int pos1 = e.toString().indexOf("(sum): ");
                String rassen = e.toString().substring(pos1 + 7);
                Log.d(TAG, "Selected: " + rassen);



            }

            @Override
            public void onNothingSelected() {

            }
        });



        btn_viewCel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cref = edit_viewCel.getText().toString();
                int cellnum = Integer.parseInt(cref);
                tv.setVisibility(View.GONE);
                pieChart.setVisibility(View.VISIBLE);
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
        yEntries.clear();
        xEntries.clear();
        String cellRef = "Cell" + cref;
        String rowRef = "row" + edit_viewRow.getText();
        currentCel = cref;
        currentView.setText(cellRef + " " + rowRef);
        final DatabaseReference table_cellBird = table_cells.child(cellRef);
        final DatabaseReference table_cellzoom = table_cells.child(cellRef).child(rowRef);

        if(edit_viewRow.getText().toString().equals("")){
            lv.setVisibility(View.GONE);
            btn_hideQuery.setVisibility(View.VISIBLE);
            btn_viewCel.setVisibility(View.GONE);
            edit_viewCel.setVisibility(View.GONE);
            edit_viewRow.setVisibility(View.GONE);
            pgrBar.setVisibility(View.VISIBLE);
            pieChart.setVisibility(View.VISIBLE);
            System.out.println("Bird");
            table_cellBird.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //count = 0;
                        //rassen.clear();
                        //kisten.clear();
                        //System.out.println(snapshot.getValue());
                        if(snapshot.getKey().contains("row")){
                            for(DataSnapshot snapshotrow : snapshot.getChildren()){
                                Kist kist = snapshotrow.getValue(Kist.class);
                                kisten.add(kist);
                                if(rassen.containsKey(kist.getRas())){
                                    count = rassen.get(kist.getRas());
                                    rassen.put(kist.getRas(),count + 1);




                                }
                                else{
                                    //count = 0;
                                    rassen.put(kist.getRas(), 1);
                                }

                                //loadListView();
                            }
                            //System.out.println(rassen);

                            //System.out.println(rassen.size());


                        }
                        else{
                            Kist kist = snapshot.getValue(Kist.class);
                            kisten.add(kist);
                            //loadListView();

                        }








                        //System.out.println(kist.getCel());
                        //System.out.println(snapshot);

                    }
                    float count = kisten.size();
                    float division = count / 780;
                    float percent = division * 100;
                    //System.out.println(percent);
                    pgrBar.setProgress(Math.round(percent));
                    createChart(rassen);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




        }
        else{
            lv.setVisibility(View.VISIBLE);
            pieChart.setVisibility(View.GONE);

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

    public void createChart(LinkedHashMap map){
        LinkedHashMap<String, Integer> lal = map;

        //System.out.println(lal);
        for(int i = 0; i < rassen.size(); i++){
            String key = rassen.keySet().toArray()[i].toString();
            //System.out.println(key);
            yEntries.add(new PieEntry(rassen.get(key)));
            xEntries.add(key);

        }

        List<LegendEntry> entries = new ArrayList<>();
        Legend legend = pieChart.getLegend();
        for(int i = 0; i < rassen.size(); i++){
            LegendEntry entry = new LegendEntry();
            entry.formColor = colors[i];
            String key = rassen.keySet().toArray()[i].toString();
            entry.label = key;
            entries.add(entry);
        }
        legend.setCustom(entries);


        PieDataSet pieDataSet = new PieDataSet(yEntries, "Verdeling Rassen");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(20);
        pieDataSet.setColors(colors);


        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    public void showTable(int cref){
        kisten.clear();
        rassen.clear();
        String cellRef = "Cell" + cref;
        currentCel = cref;
        currentView.setText(cellRef);
        final DatabaseReference table_cellBird = table_cells.child(cellRef);
        lv.setVisibility(View.GONE);
        pieChart.setVisibility(View.GONE);
        tv.setVisibility(View.VISIBLE);
        btn_hideQuery.setVisibility(View.VISIBLE);
        pgrBar.setVisibility(View.VISIBLE);
        btn_viewCel.setVisibility(View.GONE);
        edit_viewCel.setVisibility(View.GONE);
        edit_viewRow.setVisibility(View.GONE);

        System.out.println("Bird");
        table_cellBird.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot.getKey().contains("row")){
                        for(DataSnapshot snapshotrow : snapshot.getChildren()){
                            Kist kist = snapshotrow.getValue(Kist.class);
                            kisten.add(kist);
                            if(rassen.containsKey(kist.getRas())){
                                count = rassen.get(kist.getRas());
                                rassen.put(kist.getRas(),count + 1);




                            }
                            else{
                                //count = 0;
                                rassen.put(kist.getRas(), 1);
                            }

                            //loadListView();
                        }
                        //System.out.println(rassen);

                        //System.out.println(rassen.size());


                    }
                    else{
                        Kist kist = snapshot.getValue(Kist.class);
                        kisten.add(kist);
                        //loadListView();

                    }








                    //System.out.println(kist.getCel());
                    //System.out.println(snapshot);
                    float count = kisten.size();
                    float division = count / 780;
                    float percent = division * 100;
                    //System.out.println(percent);
                    pgrBar.setProgress(Math.round(percent));

                }

                //System.out.println(rassen);

                createTable(rassen);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







    }

    public void createTable(LinkedHashMap map){
        LinkedHashMap<String, Integer> lal = map;
        tv.removeAllViews();
        for (int i = 0; i < lal.size(); i++) {

            TableRow row = new TableRow(viewCell.this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            TextView textViewKey = new TextView(viewCell.this);
            String key = lal.keySet().toArray()[i].toString();
            textViewKey.setText(key);
            TextView textViewVal = new TextView(viewCell.this);
            textViewVal.setText(lal.get(key).toString());
            row.addView(textViewKey);
            row.addView(textViewVal);

            tv.addView(row,i);
        }

    }
}
