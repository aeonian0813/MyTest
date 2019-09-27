package com.example.mytest;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytest.Adapter.ProcedureAdapter;
import com.example.mytest.Adapter.StuffAdapter;
import com.example.mytest.DTO.Procedure;
import com.example.mytest.DTO.Stuff;
import com.example.mytest.RTask.ListInsert;
import com.winfo.photoselector.PhotoSelector;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView rec_ImageView;
    TextView cok_portion_tv,cok_time_tv,cok_degree_tv,rec_save;
    RecyclerView stuffrecycle,spicrecycle,procrecycle;
    Button stuffadd,spicadd,procadd;
    ArrayAdapter<CharSequence> adapter_cat1,adapter_cat2,adapter_cat3,adapter_cat4 ;
    String cat1_c ="";
    String cat2_c ="";
    String cat3_c="";
    String cat4_c="";



    EditText etTitle, etSubTitle;
    String title = "", subtitle = "";
    Spinner cat1;

    //adapter
    StuffAdapter stuffadapter = new StuffAdapter();
    StuffAdapter spicadapter = null;
    ProcedureAdapter procadapter = new ProcedureAdapter();

    String portion_c = "" , time_c = "" , degree_c = "";


    //camera
    private static final int SINGLE_CODE = 1;//单选
    private static final int LIMIT_CODE = 2;//多选限制数量
    private static final int CROP_CODE = 3;//剪切裁剪
    private static final int UN_LIMITT_CODE = 4;//多选不限制数量



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDangerousPermissions();
        etTitle = findViewById(R.id.rec_title);
        etSubTitle = findViewById(R.id.rec_subtitle);



        //대표이미지
        rec_ImageView = findViewById(R.id.rec_ImageView);
        rec_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoSelector.builder()
                        .setSingle(true)
                        .start(MainActivity.this, SINGLE_CODE);


            }
        });




        //RecycleView 화면구성
        stuffrecycle = findViewById(R.id.stuffrecycle);
        stuffadd = findViewById(R.id.stuffAdd);
        stuffadapter.addItem(new Stuff("예) 돼지고기","예) 300g"));
        stuffadapter.addItem(new Stuff("예) 당근","예) 1/5개"));
        stuffadapter.addItem(new Stuff("예) 소고기","예) 250g"));
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,1);
        stuffrecycle.setLayoutManager(linearLayoutManager);


        //재료 액티비티
        stuffrecycle.setAdapter(stuffadapter);
        stuffadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stuffadapter.addItem(new Stuff("예) 돼지고기","예) 300g"));
                stuffadapter.notifyDataSetChanged();
            }
        });
        spicrecycle = findViewById(R.id.spicrecycle);
        spicadd = findViewById(R.id.spicAdd);
        spicadapter = new StuffAdapter();
        spicadapter.addItem(new Stuff("예) 간장","예) 1.5T"));
        spicadapter.addItem(new Stuff("예) 맛술","예) 2T"));
        spicadapter.addItem(new Stuff("예) 다진마늘","예) 1T"));
        GridLayoutManager linearLayoutManager1 = new GridLayoutManager(this,1);
        spicadapter.OnStuffItemClickListener(new OnStuffItemClickListener() {
            @Override
            public void onItemClick(StuffAdapter.ViewHolder holder, View view, int position) {
                Log.d("spicadat", String.valueOf(spicadapter.getItemCount()) + ", " + spicadapter.getItem(position));
            }
        });

        spicrecycle.setNestedScrollingEnabled(false);
        spicrecycle.setLayoutManager(linearLayoutManager1);


        //양념 액티비티
        spicrecycle.setAdapter(spicadapter);
        spicadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spicadapter.addItem(new Stuff( "예) 간장","예) 1.5T"));

                Log.d("spicada", String.valueOf(spicadapter.getItemCount()));
                spicadapter.notifyDataSetChanged();
            }

        });

        procrecycle = findViewById(R.id.procrecycle);
        procadd = findViewById(R.id.procAdd);
        procadapter = new ProcedureAdapter();
        procadapter.addItem(new Procedure(""+(procadapter.getItemCount()+1),"예) 준비된 양념으로 먼저 고기를 조물조물 재워둡니다.",R.drawable.pic_none3));
        procadapter.addItem(new Procedure(""+(procadapter.getItemCount()+1),"예) 고기가 반쯤 익어갈때 양파와 함께 볶습니다.",R.drawable.pic_none3));
        procadapter.addItem(new Procedure(""+(procadapter.getItemCount()+1),"예) 그 사이 양파와 버섯, 대파도 썰어서 준비하세요.",R.drawable.pic_none3));
        GridLayoutManager linearLayoutManager2 = new GridLayoutManager(this,1);
        procrecycle.setLayoutManager(linearLayoutManager2);

        //조리순서 액티비티
        procrecycle.setAdapter(procadapter);
        procadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                procadapter.addItem(new Procedure(""+(procadapter.getItemCount()+1),"예) 준비된 양념으로 먼저 고기를 조물조물 재워둡니다.",R.drawable.pic_none3));
                procadapter.notifyDataSetChanged();
            }
        });

        //Swipe delete 부분 왼쪽으로 밀어서 삭제
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder target, int i) {
                int position = target.getAdapterPosition();
                stuffadapter.removeItem(position);
                stuffadapter.notifyDataSetChanged();

            }
        });
        ItemTouchHelper helper1 = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder target, int i) {
                int position = target.getAdapterPosition();
                spicadapter.removeItem(position);
                spicadapter.notifyDataSetChanged();

            }
        });
        ItemTouchHelper helper2 = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder target, int i) {
                int position = target.getAdapterPosition();
                procadapter.removeItem(position);
                procadapter.notifyDataSetChanged();

            }
        });

        //swipe recyclerview 에 삽입하는곳
        helper.attachToRecyclerView(stuffrecycle);
        helper1.attachToRecyclerView(spicrecycle);
        helper2.attachToRecyclerView(procrecycle);




        //카테고리
        cat1 = findViewById(R.id.cat1);
        adapter_cat1 = ArrayAdapter.createFromResource(this,R.array.cat1, android.R.layout.simple_spinner_item);
        adapter_cat1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat1.setAdapter(adapter_cat1);
        cat1.setOnItemSelectedListener(this);

        Spinner cat2 = findViewById(R.id.cat2);
        adapter_cat2 = ArrayAdapter.createFromResource(this,R.array.cat2, android.R.layout.simple_spinner_item);
        adapter_cat2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat2.setAdapter(adapter_cat2);
        cat2.setOnItemSelectedListener(this);

        Spinner cat3 = findViewById(R.id.cat3);
        adapter_cat3 = ArrayAdapter.createFromResource(this,R.array.cat3, android.R.layout.simple_spinner_item);
        adapter_cat3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat3.setAdapter(adapter_cat3);
        cat3.setOnItemSelectedListener(this);

        Spinner cat4 = findViewById(R.id.cat4);
        adapter_cat4 = ArrayAdapter.createFromResource(this,R.array.cat4, android.R.layout.simple_spinner_item);
        adapter_cat4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat4.setAdapter(adapter_cat4);
        cat4.setOnItemSelectedListener(this);

        cok_portion_tv=findViewById(R.id.cok_portion_tv);
        cok_time_tv=findViewById(R.id.cok_time_tv);
        cok_degree_tv=findViewById(R.id.cok_degree_tv);
        cok_portion_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(1);
            }
        });
        cok_time_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(2);
            }
        });
        cok_degree_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(3);
            }
        });


        //레시피 저장
        rec_save = findViewById(R.id.rec_save);
        rec_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title2 = etTitle.getText().toString();
                String subtitle2 = etSubTitle.getText().toString();
                String portion,time,degree;

                title = title2;
                subtitle = subtitle2;
                portion = portion_c;
                time = time_c;
                degree = degree_c;

                Log.d("insert", title + "," + subtitle+ cat1_c+"," + cat2_c + ","+cat3_c+","+cat4_c+","+portion_c+","+time_c+","+degree_c);

                ListInsert insert = new ListInsert(title ,subtitle,cat1_c,cat2_c,cat3_c,cat4_c,portion,time,degree);
                insert.execute();


                Intent showIntent = new Intent(getApplicationContext(), RecListActivity.class);
                showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
                        Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
                        Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
                startActivity(showIntent);

                finish();
            }
        });
        TextView rec_insert = findViewById(R.id.rec_insert);
        rec_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecListActivity.class);
                startActivity(intent);
            }
        });


    }


/*    private void openRecListActivity() {
        Intent intent = new Intent(this, RecListActivity.class);
        startActivity(intent);
    }*/



    //권한설정
    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            /*Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();*/
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }
    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    */





    /*//handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch ( requestCode ) {
            case PERMISSION_CODE: {
                if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                    //permission was granted
                    pickImageFromGallery();
                }else {
                    //permission was denied
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            //set image to image View
            rec_ImageView.setImageURI(data.getData());
        }
    }*/
    //카테고리 선택 됐을 경우 Toast
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        switch ( adapterView.getId() ){
            case R.id.cat1 :
                if( adapterView.getSelectedItemPosition() > 0) {
                    cat1_c = String.valueOf(adapterView.getSelectedItemPosition());
                    /*cat1_c = adapterView.getItemAtPosition(position).toString();*/
                }
                break;
            case R.id.cat2 :
                if( adapterView.getSelectedItemPosition() > 0) {
                    cat2_c = String.valueOf(adapterView.getSelectedItemPosition());
                    /*cat2_c = adapterView.getItemAtPosition(position).toString();*/
                }
                break;
            case R.id.cat3 :
                if( adapterView.getSelectedItemPosition() > 0) {
                    cat3_c = String.valueOf(adapterView.getSelectedItemPosition());
                    /*cat3_c = adapterView.getItemAtPosition(position).toString();*/
                }
                break;
            case R.id.cat4 :
                if( adapterView.getSelectedItemPosition() > 0) {
                    cat4_c = String.valueOf(adapterView.getSelectedItemPosition());
                    /*cat4_c = adapterView.getItemAtPosition(position).toString();*/
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    //카테고리 종류별 넣는탭
    void show(final int cnt)
    {
        String[] cok_portion1 = new String[0];
        if(cnt == 1) {
            cok_portion1 = getResources().getStringArray(R.array.cok_portion);
        }else if(cnt == 2) {
            cok_portion1 = getResources().getStringArray(R.array.cok_time);
        }else if(cnt == 3) {
            cok_portion1 = getResources().getStringArray(R.array.cok_degree);
        }


        // 카테고리 stringarray 배열 하나씩 넣기
        final List<String> ListItems = new ArrayList<>();
        for(int i = 0 ; i < cok_portion1.length; i++){
            ListItems.add(cok_portion1[i]);
        }

        final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

        final List SelectedItems  = new ArrayList();
        int defaultItem = 0;
        SelectedItems.add(defaultItem);


        //요리정보
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(cnt==1)builder.setTitle("인원");
        else if(cnt==2)builder.setTitle("시간");
        else if(cnt==3)builder.setTitle("난이도");
        builder.setSingleChoiceItems(items, defaultItem,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SelectedItems.clear();
                        SelectedItems.add(which);
                    }
                });


        //요리정보 확인 눌렀을 경우
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String msg="";

                        if (!SelectedItems.isEmpty()) {
                            int index = (int) SelectedItems.get(0);
                            msg = ListItems.get(index);
                            if(cnt==1) {
                                cok_portion_tv.setText(msg);
                                portion_c = String.valueOf(index);
                                /*portion_c = msg;
                                Toast.makeText(MainActivity.this, portion_c, Toast.LENGTH_SHORT).show();*/
                            }else if(cnt==2) {
                                cok_time_tv.setText(msg);
                                time_c = String.valueOf(index);
                                /*time_c = msg;
                                Toast.makeText(MainActivity.this, time_c, Toast.LENGTH_SHORT).show();*/
                            } else if(cnt==3) {
                                cok_degree_tv.setText(msg);
                                degree_c = String.valueOf(index);
                                /*degree_c = msg;
                                Toast.makeText(MainActivity.this, degree_c, Toast.LENGTH_SHORT).show();*/
                            }
                        }
                        /*Toast.makeText(getApplicationContext(),
                                "Items Selected.\n"+ msg , Toast.LENGTH_LONG)
                                .show();*/
                    }
                });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }



}


