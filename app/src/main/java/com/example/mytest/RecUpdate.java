package com.example.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytest.DTO.RecipeDTO;
import com.example.mytest.RTask.ListUpdate;

public class RecUpdate extends AppCompatActivity {

    EditText et_title, et_subtitle;
    String rec_title, rec_subtitle,rec_id;
    TextView rec_update;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        //레시피 수정
        et_title = findViewById(R.id.rec_uptitle);
        et_subtitle = findViewById(R.id.rec_upsubtitle);

        // 보내온 값 파싱
        Intent intent = getIntent();
        RecipeDTO selItem = (RecipeDTO) intent.getSerializableExtra("selItem");

        rec_title = selItem.getTitle();
        rec_subtitle = selItem.getSub_title();
        rec_id = selItem.getId();

        /*
        rec_id = intent.getStringExtra("id");
        rec_title = intent.getStringExtra("title");
        rec_subtitle = intent.getStringExtra("subtitle");
*/
        // 가져온 값 써 넣기
        et_title.setText(rec_title);
        et_subtitle.setText(rec_subtitle);

        rec_update = findViewById(R.id.rec_update);

        rec_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rec_title = et_title.getText().toString();
                rec_subtitle = et_subtitle.getText().toString();

                ListUpdate listUpdate = new ListUpdate(rec_id,rec_title, rec_subtitle);
                listUpdate.execute();

                Toast.makeText(getApplicationContext(), "수정성공", Toast.LENGTH_LONG).show();

                Intent showIntent = new Intent(getApplicationContext(), RecListActivity.class);
                showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
                        Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
                        Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
                startActivity(showIntent);

                finish();
            }
        });

    }
}