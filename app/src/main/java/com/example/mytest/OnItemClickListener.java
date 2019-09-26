package com.example.mytest;

import android.view.View;

import com.example.mytest.Adapter.MyRecyclerviewAdapter;

public interface OnItemClickListener {
    void onItemClick(MyRecyclerviewAdapter.ItemViewHolder holder, View view, int position);
}
