package com.example.logowaniep1;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SpisUzytkownikowAdapter extends RecyclerView.Adapter<SpisUzytkownikowAdapter.SpisUzytkownikowHolder> implements Filterable {
    private ArrayList<SpisUzytkownikowItem> uzytkownicyLista;
    private ArrayList<SpisUzytkownikowItem> uzytkownicyListaFull;
    private OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listenerWe){
        listener = listenerWe;
    }

    public static class SpisUzytkownikowHolder extends RecyclerView.ViewHolder{
        public ImageView zdjecieSpisUzytkownikow;
        public TextView nazwaSpisUzytkownikow;
        public TextView emailSpisUzytkownikow;

        public SpisUzytkownikowHolder(@NonNull View itemView, final OnItemClickListener listenerWe) {
            super(itemView);
            zdjecieSpisUzytkownikow = itemView.findViewById(R.id.zdjecieSpisUzytkownika);
            nazwaSpisUzytkownikow = itemView.findViewById(R.id.nazwaSpisUzytkownikow);
            emailSpisUzytkownikow = itemView.findViewById(R.id.emailSpisUzyktonikow);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listenerWe != null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listenerWe.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public SpisUzytkownikowAdapter(ArrayList<SpisUzytkownikowItem> uzytkownicyListaWe){
        uzytkownicyLista = uzytkownicyListaWe;
        uzytkownicyListaFull = new ArrayList<>(uzytkownicyListaWe);

        if(uzytkownicyLista.isEmpty())
            Log.i("empty","Uzytkownicy Lista");
        if(uzytkownicyListaFull.isEmpty())
            Log.i("empty","Uzytkownicy Lista FULL");



    }

    @NonNull
    @Override
    public SpisUzytkownikowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.spis_uzytkownikow_item, parent, false);
        SpisUzytkownikowHolder spisUzytkownikowHolder = new SpisUzytkownikowHolder(v, listener);
        return spisUzytkownikowHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SpisUzytkownikowHolder holder, int position) {
        SpisUzytkownikowItem currentItem = uzytkownicyLista.get(position);
        holder.zdjecieSpisUzytkownikow.setImageResource(currentItem.getZdjecieUzytkownika());
        holder.nazwaSpisUzytkownikow.setText(currentItem.getNazwaUzytkownika());
        holder.emailSpisUzytkownikow.setText(currentItem.getEmailUzytkownika());
    }

    @Override
    public int getItemCount() {
        return uzytkownicyLista.size();
    }

    @Override
    public Filter getFilter() {
        return spisUzytkownikowFilter;
    }
    private  Filter spisUzytkownikowFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.i("co","Jestem tu chociaz?");
            List<SpisUzytkownikowItem> filteredList = new ArrayList<>();
            if( constraint == null || constraint.length() == 0)
            {
                filteredList.addAll(uzytkownicyListaFull);
            }
            else
            {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(SpisUzytkownikowItem item : uzytkownicyListaFull)
                {
                    if(item.getNazwaUzytkownika().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            uzytkownicyLista.clear();
            uzytkownicyLista.addAll((List)results.values);
            if(((List) results.values).isEmpty())
            Log.i("Petla" , "Koniec :((((");
            notifyDataSetChanged();
        }
    };
}
