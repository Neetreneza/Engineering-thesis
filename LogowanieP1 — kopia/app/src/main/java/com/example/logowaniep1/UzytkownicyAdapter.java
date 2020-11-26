package com.example.logowaniep1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UzytkownicyAdapter extends RecyclerView.Adapter<UzytkownicyAdapter.UzytkownicyViewHolder> {

    private ArrayList<UzytkownicyItem> uzytkownicyList;

    public static class UzytkownicyViewHolder extends RecyclerView.ViewHolder{
    public ImageView imageView;
    public TextView textView;

        public UzytkownicyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.zdjecieUzytkownikaListaObiekt);
            textView = itemView.findViewById(R.id.nazwaUzytkownikaListaObiekt);
        }
    }

    public UzytkownicyAdapter(ArrayList<UzytkownicyItem> uzytkownicyListWe)
    {
        uzytkownicyList = uzytkownicyListWe;
    }

    @NonNull
    @Override
    public UzytkownicyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.obiekt_uzytkownicy, parent, false);
        UzytkownicyViewHolder uzytkownicyViewHolder = new UzytkownicyViewHolder(v);
        return uzytkownicyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UzytkownicyViewHolder holder, int position) {
        UzytkownicyItem currentItem = uzytkownicyList.get(position);

        holder.imageView.setImageResource(currentItem.getImageResourceUzytkownika());
        holder.textView.setText(currentItem.getNazwaUzytkownika());

    }

    @Override
    public int getItemCount() {
        return uzytkownicyList.size();
    }


}

