package com.example.mytest;

import android.view.View;

import com.example.mytest.Adapter.ProcedureAdapter;

public interface OnproceItemClickListener {
    void onItemClick(ProcedureAdapter.ViewHolder holder, View view, int position);
}
