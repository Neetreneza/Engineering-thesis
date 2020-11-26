package com.example.logowaniep1;

import android.view.LayoutInflater;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ZarzadzanieAdminAdapter extends RecyclerView.Adapter<ZarzadzanieAdminAdapter.ExampleViewHolder> {

    private ArrayList<ObiektDoWeryfikacji> mObiektyList;
    private onItemClickListener mListener;

    //interfejs do wykrycia wciśnięcia
    public interface onItemClickListener{
        //void onItemClick(int position); //ta metoda jest wykorzystywana w aktywności żeby określić, który element wciśnięto
        void onSzczegolyClick(int position);
        void onAkceptujClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTekstView1, mTekstView2, mTekstView3;
        public Button szczegolyButton, akceptujButton;

        //konstruktor ViewHolder
        public ExampleViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView4);
            mTekstView1 = itemView.findViewById(R.id.nazwa);
            mTekstView2 = itemView.findViewById(R.id.data);
            mTekstView3 = itemView.findViewById(R.id.miejscowosc);
            szczegolyButton = (Button) itemView.findViewById(R.id.szczegolyObiekt);
            akceptujButton = (Button) itemView.findViewById(R.id.akceputjObiekt);

            szczegolyButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onSzczegolyClick(position); //przekazuję do interfejsu pozycję elementu, który został wciśnięty
                        }
                    }
                }
            });

            akceptujButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onAkceptujClick(position); //przekazuję do interfejsu pozycję elementu, który został wciśnięty
                        }
                    }
                }
            });
        }
    }

    public ZarzadzanieAdminAdapter(ArrayList<ObiektDoWeryfikacji> obiektyList) {
        mObiektyList = obiektyList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.zarzadzanie_obiekt, parent, false);

        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ObiektDoWeryfikacji obecnyObiekt = mObiektyList.get(position);

        holder.mImageView.setImageResource(obecnyObiekt.getImageResource());
        holder.mTekstView1.setText(obecnyObiekt.getNazwa());
        holder.mTekstView2.setText(obecnyObiekt.getData().toString());
        holder.mTekstView3.setText(obecnyObiekt.getMiejscowosc());
    }

    @Override
    public int getItemCount() {
        return mObiektyList.size();
    }
}