package com.example.mytest;

import android.view.View;

import com.example.mytest.Adapter.StuffAdapter;

public interface OnStuffItemClickListener {
    void onItemClick(StuffAdapter.ViewHolder holder, View view, int position);
}
