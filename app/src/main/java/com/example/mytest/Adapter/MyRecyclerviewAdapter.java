package com.example.mytest.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytest.DTO.RecipeDTO;
import com.example.mytest.OnItemClickListener;
import com.example.mytest.R;

import java.util.ArrayList;

public class MyRecyclerviewAdapter extends RecyclerView.Adapter<MyRecyclerviewAdapter.ItemViewHolder>
                            implements OnItemClickListener {

    ArrayList<RecipeDTO> arrayList;
    OnItemClickListener listener;

    public MyRecyclerviewAdapter(ArrayList<RecipeDTO> arrayList){
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.rec_list, parent, false);

        return new ItemViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Log.d("main:adapter",""+position);

        RecipeDTO item = arrayList.get(position);
        holder.setitem(item);
    }

    @Override
    public int getItemCount() {   return arrayList.size();   }

    public void addItem(RecipeDTO item){
        arrayList.add(item);
    }

    public RecipeDTO getItem(int position){
        return arrayList.get(position);
    }

    public void setItem(int position, RecipeDTO item){
        arrayList.set(position, item);
    }

    public void removeItem(int item){
        arrayList.remove(item);
    }

    // 리사이클러뷰 내용 모두 지우기
    public void removeAllItem(){
        arrayList.clear();
    }

    public void setItems(ArrayList<RecipeDTO> arrayList){
        this.arrayList = arrayList;
    }

    // OnItemClickListener 를 implements 해서 반드시 구현해야 하는 매소드
    @Override
    public void onItemClick(ItemViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }

    // 외부에서 클릭리스너를 부를 수 있도록 매소드를 만들어 놓는다
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // 어댑터에 매소드 만들기
    // 모든 항목 백그라운드 색 흰색으로 바꿈
    /*public void setBackgroundColor(ItemViewHolder holder){
        for(int i = 0; i < arrayList.size(); i++){
            onBindViewHolder(holder, i);
        }

    }*/

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView id,title, subtitle;

        public ItemViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            id = itemView.findViewById(R.id.recid);
            title = itemView.findViewById(R.id.rectitle);
            subtitle = itemView.findViewById(R.id.recsubtitle);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ItemViewHolder.this,view,position);
                    }
                }
            });
        }

        public void setitem(RecipeDTO item) {
            id.setText(item.getId());
            title.setText(item.getTitle());
            subtitle.setText(item.getSub_title());
        }
    }

}