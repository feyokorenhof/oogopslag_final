package com.example.oogopslag_final;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.LinkedHashMap;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.TextviewHolder> {

    private LinkedHashMap<String, Integer> data;
    private OnPlaatsListener mOnPlaatsListener;

    public RecyclerAdapter(LinkedHashMap<String, Integer> map, OnPlaatsListener onPlaatsListener){
        this.data = map;
        this.mOnPlaatsListener = onPlaatsListener;

    }

    @NonNull
    @Override
    public TextviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
        TextviewHolder textviewHolder = new TextviewHolder(view, mOnPlaatsListener);



        return textviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TextviewHolder viewHolder, int i) {
        String ras_id = data.keySet().toArray()[i].toString();
        String amount_id = data.get(ras_id).toString();
        viewHolder.text_ras.setText(ras_id);
        viewHolder.text_amount.setText(amount_id);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class TextviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text_ras, text_amount;
        OnPlaatsListener onPlaatsListener;

        public TextviewHolder(@NonNull View itemView, OnPlaatsListener onPlaatsListener) {
            super(itemView);
            text_ras = itemView.findViewById(R.id.text_ras);
            text_amount = itemView.findViewById(R.id.text_amount);
            this.onPlaatsListener = onPlaatsListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPlaatsListener.onPlaatsClick(getAdapterPosition());

        }
    }

    public interface OnPlaatsListener{

        void onPlaatsClick(int position);


        }
    }





