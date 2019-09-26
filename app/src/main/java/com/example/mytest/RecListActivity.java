package com.example.mytest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytest.Adapter.MyRecyclerviewAdapter;
import com.example.mytest.DTO.RecipeDTO;
import com.example.mytest.RTask.ListDelete;
import com.example.mytest.RTask.ListSelect;

import java.util.ArrayList;



public class RecListActivity extends AppCompatActivity {

    ListSelect listSelect;

    ArrayList<RecipeDTO> recipeDTOArrayList;
    Button btnUpdate, btnDelete,btnInsertView;

    RecyclerView recyclerView;
    MyRecyclerviewAdapter adapter;

    RecipeDTO selItem = null;
    String seltitle, selsubtitle,selid;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_rec_list);

        btnInsertView = findViewById(R.id.btnInsertView);

        btnInsertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 리사이클러 뷰 시작
        recipeDTOArrayList = new ArrayList();
        adapter = new MyRecyclerviewAdapter(recipeDTOArrayList);
        recyclerView = findViewById(R.id.reclist);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        // 리싸이클러 뷰 항목이 눌릴때
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(MyRecyclerviewAdapter.ItemViewHolder holder, View view, int position) {

                selItem = adapter.getItem(position);
                Toast.makeText(RecListActivity.this, "아이템 선택됨 : " + selItem.getId(), Toast.LENGTH_SHORT).show();

            }
        });
        // 리사이클러 뷰 종료

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        listSelect = new ListSelect(recipeDTOArrayList, adapter);
        listSelect.execute();

        //레시피 수정
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selItem != null){
                    Log.d("sub1:update1", selItem.getId());

                    Intent intent = new Intent(getApplicationContext(), RecUpdate.class);
                    intent.putExtra("selItem", selItem);
                    /*
                    intent.putExtra("id",selid);
                    intent.putExtra("title",seltitle);
                    intent.putExtra("title",selsubtitle);
                    */
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(getApplicationContext(), "항목 선택을 해 주세요",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //삭제
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selItem != null){
                    ListDelete listDelete = new ListDelete(selItem.getId());
                    listDelete.execute();

                    // 화면갱신
                    Intent refresh = new Intent(getApplicationContext(), RecListActivity.class);
                    startActivity(refresh);
                    finish();

                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getApplicationContext(), "항목 선택을 해 주세요",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    // 이미 화면이 있을때 받는곳
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("Sub1", "onNewIntent() 호출됨");

        // 새로고침하면서 이미지가 겹치는 현상 없애기 위해...
        adapter.removeAllItem();


    }

    private void processIntent(Intent intent){
        if(intent != null){
            listSelect = new ListSelect(recipeDTOArrayList, adapter);
            listSelect.execute();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}