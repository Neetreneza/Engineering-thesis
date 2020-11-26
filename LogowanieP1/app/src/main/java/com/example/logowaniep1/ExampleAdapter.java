package com.example.logowaniep1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private ArrayList<ExampleItem> exampleList;
    private onItemClickListener listenerSzczegoly;

    public interface onItemClickListener
    {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listenerSzcz)
    {
        listenerSzczegoly = listenerSzcz;
    }

    public static class ExampleViewHolder extends  RecyclerView.ViewHolder
    {
        public ImageView imageView, imageView2;
        public TextView textView1, textView2, start, koniec, miejsce;
        public String headUser, obiektId, opis;


        public ExampleViewHolder(View itemView,final onItemClickListener listenerSzczegoly)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            imageView2 = itemView.findViewById(R.id.imageView2); //Usuwajka
            start =  itemView.findViewById(R.id.startTextView);
            koniec = itemView.findViewById(R.id.koniecTextView);
            miejsce = itemView.findViewById(R.id.miejsceTV);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listenerSzczegoly!=null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listenerSzczegoly.onItemClick(position);
                        }
                    }
                }
            });

            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listenerSzczegoly!=null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listenerSzczegoly.onDeleteClick(position);
                        }
                    }

                }
            });

        }
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.obiekkt_wydarzenia,parent,false);

        ExampleViewHolder exampleViewHolder = new ExampleViewHolder(v,listenerSzczegoly);
        return exampleViewHolder;
    }

    public ExampleAdapter(ArrayList<ExampleItem> WejscieExampleList)
    {
        exampleList = WejscieExampleList;
    }


    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleItem currentItem = exampleList.get(position);

        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textView1.setText(currentItem.getText1());
        holder.textView2.setText(currentItem.getText2());
        holder.imageView2.setImageResource(currentItem.getImageResource2());
        holder.start.setText(currentItem.getStart());
        holder.koniec.setText(currentItem.getKoniec());
        holder.headUser = currentItem.getHeadUser();
        String miejsce = currentItem.getMiejsceE();
        String tempMiejsce = miejsce;
        if(miejsce.length()>20)
            tempMiejsce = miejsce.substring(0,18)+"...";
        holder.miejsce.setText(tempMiejsce);
    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }
}
