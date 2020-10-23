package com.example.logowaniep1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ObiektAdapter extends RecyclerView.Adapter<ObiektAdapter.ObiektViewHolder> {

    private ArrayList<ObiektyItem> obiektyList;
    private onItemClickListener listenerSzczegoly;

    public interface onItemClickListener
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listenerSzcz)
    {
        listenerSzczegoly = listenerSzcz;
    }

    public static class ObiektViewHolder extends  RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public TextView textView1, textView2,textView3;
        public String headUser;


        public ObiektViewHolder(View itemView,final onItemClickListener listenerSzczegoly)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView4);
            textView1 = itemView.findViewById(R.id.cenaTVObiekt);
            textView2 = itemView.findViewById(R.id.godzinyTVObiekt);
            textView3 = itemView.findViewById(R.id.dniTVObiekt);

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



        }
    }

    @NonNull
    @Override
    public ObiektViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.obiekt_obiektu,parent,false);

        ObiektViewHolder ObiektyViewHolder = new ObiektViewHolder(v,listenerSzczegoly);
        return ObiektyViewHolder;
    }

    public ObiektAdapter(ArrayList <ObiektyItem> WejscieObiektyList)
    {
        obiektyList = WejscieObiektyList;
    }


    @Override
    public void onBindViewHolder(@NonNull ObiektViewHolder holder, int position) {
        ObiektyItem currentItem = obiektyList.get(position);

        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textView1.setText(currentItem.getOText1());
        holder.textView2.setText(currentItem.getOText2());
        holder.textView3.setText(currentItem.getOText3());

    }

    @Override
    public int getItemCount() {
        return obiektyList.size();
    }
}
