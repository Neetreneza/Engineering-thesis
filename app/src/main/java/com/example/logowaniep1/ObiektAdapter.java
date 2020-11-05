package com.example.logowaniep1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ObiektAdapter extends RecyclerView.Adapter<ObiektAdapter.ObiektViewHolder> implements Filterable {

    private ArrayList<ObiektyItem> obiektyList;
    private ArrayList<ObiektyItem> obiektyListFull;
    private onItemClickListener listenerSzczegoly;


    public interface onItemClickListener
    {
        void onItemClick(int position);
        void onPlusClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listenerSzcz)
    {
        listenerSzczegoly = listenerSzcz;
    }

    public static class ObiektViewHolder extends  RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public TextView textView1, textView2,textView3,textView4,textView5,textView6;
        public String headUser;
        public Button dodajB;


        public ObiektViewHolder(View itemView,final onItemClickListener listenerSzczegoly)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView4);
            textView1 = itemView.findViewById(R.id.cenaTVObiekt);
            textView2 = itemView.findViewById(R.id.godzinyTVObiekt);
            textView3 = itemView.findViewById(R.id.dniTVObiekt);
            textView4 = itemView.findViewById(R.id.nazwaSzczegoly);
            textView5 = itemView.findViewById(R.id.ulicaNumerSzczegoly);
            textView6 = itemView.findViewById(R.id.krytySzatniaSzczegoly);
            dodajB = itemView.findViewById(R.id.plusObiektB);


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
            dodajB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listenerSzczegoly!=null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listenerSzczegoly.onPlusClick(position);
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
        obiektyListFull = new ArrayList<>(obiektyList);
    }


    @Override
    public void onBindViewHolder(@NonNull ObiektViewHolder holder, int position) {
        ObiektyItem currentItem = obiektyList.get(position);

        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textView1.setText(currentItem.getOText1());
        holder.textView2.setText(currentItem.getOText2());
        holder.textView3.setText(currentItem.getOText3());
        holder.textView4.setText(currentItem.getOText4());
        holder.textView5.setText(currentItem.getOText5());
        holder.textView6.setText(currentItem.getOText6());


    }

    @Override
    public int getItemCount() {
        return obiektyList.size();
    }

    @Override
    public Filter getFilter() {
        return obiektFilter;
    }

    private  Filter obiektFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ObiektyItem> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(obiektyListFull);
            }
            else
            {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(ObiektyItem item: obiektyListFull)
                {
                    if(item.getOText1().toLowerCase().contains(filterPattern)|| item.getOText3().toLowerCase().contains(filterPattern) || item.getOText4().toLowerCase().contains(filterPattern)||item.getOText5().toLowerCase().contains(filterPattern)||item.getOText6().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            obiektyList.clear();
            obiektyList.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };
}
