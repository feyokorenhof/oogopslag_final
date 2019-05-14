package com.example.oogopslag_final;

import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oogopslag_final.Model.Kist;

import java.util.ArrayList;
import java.util.List;

public class KistAdapter extends ArrayAdapter<Kist>{

    private Context mContext;
    private List<Kist> kistList = new ArrayList<>();

    public KistAdapter(@NonNull Context context, ArrayList<Kist> list) {
        super(context, 0 , list);
        mContext = context;
        kistList = list;

    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        notifyDataSetChanged();
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        Kist currentKist = kistList.get(position);
        TextView ras = listItem.findViewById(R.id.textView_ras);
        ras.setText(currentKist.getRas());
        TextView maat = listItem.findViewById(R.id.textView_maat);
        maat.setText(currentKist.getMaat());
        TextView kwaliteit = listItem.findViewById(R.id.textView_kwaliteit);
        kwaliteit.setText(currentKist.getKwaliteit());
        TextView cel = listItem.findViewById(R.id.textView_cel);
        cel.setText(currentKist.getCel());
        TextView datum = listItem.findViewById(R.id.textView_datum);
        datum.setText(currentKist.getDatum());

        return listItem;
    }

}
