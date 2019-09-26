package com.example.mytest.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytest.DTO.Stuff;
import com.example.mytest.OnStuffItemClickListener;
import com.example.mytest.R;

import java.util.ArrayList;

public class StuffAdapter extends RecyclerView.Adapter<StuffAdapter.ViewHolder> implements OnStuffItemClickListener {
    ArrayList<Stuff> arrayList = new ArrayList<>();

    OnStuffItemClickListener listener;

    public StuffAdapter(ArrayList<Stuff> arrayList){
        this.arrayList = arrayList;
    }

    public StuffAdapter() {

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.cok_stuff, parent, false);

        return new ViewHolder(itemView, this);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("main:adapter",""+position);

        Stuff item = arrayList.get(position);
        holder.setitem(item);
    }

    @Override
    public int getItemCount() {   return arrayList.size();   }

    public void addItem(Stuff item){
        arrayList.add(item);
    }

    public Stuff getItem(int position){
        return arrayList.get(position);
    }

    public void setItem(int position, Stuff item){
        arrayList.set(position, item);
    }

    public void removeItem(int item){
        arrayList.remove(item);
    }

    public void allRemoveitem(){
        arrayList.clear();
    }

    public void setItems(ArrayList<Stuff> arrayList){
        this.arrayList = arrayList;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {

    }

    public void OnStuffItemClickListener(OnStuffItemClickListener listener){
       this.listener = listener;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_et, subtitle_et;

        public ViewHolder(@NonNull View itemView, final OnStuffItemClickListener listener) {
            super(itemView);
            title_et = itemView.findViewById(R.id.title_et);
            subtitle_et = itemView.findViewById(R.id.subtitle_et);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ViewHolder.this,view,position);
                    }
                }
            });
        }

        public void setitem(Stuff item) {
            title_et.setHint(item.getTitle());
            subtitle_et.setHint(item.getSubtitle());
        }
    }

}