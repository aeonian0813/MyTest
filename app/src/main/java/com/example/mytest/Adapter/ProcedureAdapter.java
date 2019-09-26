package com.example.mytest.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytest.OnproceItemClickListener;
import com.example.mytest.DTO.Procedure;
import com.example.mytest.R;

import java.util.ArrayList;

public class ProcedureAdapter extends RecyclerView.Adapter<ProcedureAdapter.ViewHolder> implements OnproceItemClickListener {
    ArrayList<Procedure> items = new ArrayList<>();

    OnproceItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.cok_procedure, parent, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("main:adapter",""+position);

        Procedure item = items.get(position);
        holder.setitem(item);
    }
    public void removeItem(int item){
        items.remove(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Procedure item){
        items.add(item);
    }

    public void allRemoveitem(){
        items.clear();
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {

    }

    public void setOnItemClickLstner(OnproceItemClickListener listener){
        this.listener = listener;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cok_procedure_proc_tv;
        EditText cok_procedure_sub_et;
        ImageView cok_procedure_img_iv;

        public ViewHolder(@NonNull View itemView, final OnproceItemClickListener listener) {
            super(itemView);
            cok_procedure_proc_tv = itemView.findViewById(R.id.cok_procedure_proc_tv);
            cok_procedure_sub_et = itemView.findViewById(R.id.cok_procedure_sub_et);
            cok_procedure_img_iv = itemView.findViewById(R.id.cok_procedure_img_iv);

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

        public void setitem(Procedure item) {
            cok_procedure_proc_tv.setText(item.getCok_procedure_proc());
            cok_procedure_sub_et.setHint(item.getCok_procedure_sub());
            cok_procedure_img_iv.setBackgroundResource(item.getCok_procedure_img());
        }
    }
}