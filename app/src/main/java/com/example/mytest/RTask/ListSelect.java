package com.example.mytest.RTask;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.mytest.Adapter.MyRecyclerviewAdapter;
import com.example.mytest.DTO.RecipeDTO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.example.mytest.Common.CommonMethod.ipConfig;

// doInBackground 파라미터 타입, onProgressUpdate파라미터 타입, onPostExecute 파라미터 타입 순서
// AsyncTask <Params, Progress, Result> 순서임
public class ListSelect extends AsyncTask<Void, Void, Void> {
    // 생성자
    ArrayList<RecipeDTO> recipeDTOArrayList;
    MyRecyclerviewAdapter adapter;
    ProgressDialog progressDialog;

    public ListSelect(ArrayList<RecipeDTO> recipeDTOArrayList, MyRecyclerviewAdapter adapter) {
        this.recipeDTOArrayList = recipeDTOArrayList;
        this.adapter = adapter;
    }


    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        recipeDTOArrayList.clear();
        String result = "";
        String postURL = ipConfig + "/app/recSelect";

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            readJsonStream(inputStream);

        } catch (Exception e) {
            Log.d("Sub1", e.getMessage());
            e.printStackTrace();
        }finally {
            if(httpEntity != null){
                httpEntity = null;
            }
            if(httpResponse != null){
                httpResponse = null;
            }
            if(httpPost != null){
                httpPost = null;
            }
            if(httpClient != null){
                httpClient = null;
            }

        }

        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(progressDialog != null){
            progressDialog.dismiss();
        }

        Log.d("Sub1", "List Select Complete!!!");

        adapter.notifyDataSetChanged();
    }

    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                recipeDTOArrayList.add(readMessage(reader));
            }
            reader.endArray();
        } finally {
            reader.close();
        }
    }

    public RecipeDTO readMessage(JsonReader reader) throws IOException {
        String id = "", title = "", subtitle = "", cat1="",cat2="",cat3="",cat4="",portion="",
                time="",degree="",writedate="";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("id")) {
                id = reader.nextString();
            } else if (readStr.equals("title")) {
                title = reader.nextString();
            } else if (readStr.equals("subtitle")) {
                subtitle = reader.nextString();
            } else if (readStr.equals("cat1")) {
                cat1 = reader.nextString();
            }else if (readStr.equals("cat2")) {
                cat2 = reader.nextString();
            }else if (readStr.equals("cat3")) {
                cat3 = reader.nextString();
            }else if (readStr.equals("cat4")) {
                cat4 = reader.nextString();
            }else if (readStr.equals("portion")) {
                portion = reader.nextString();
            }else if (readStr.equals("time")) {
                time = reader.nextString();
            }else if (readStr.equals("degree")) {
                degree = reader.nextString();
            }else if (readStr.equals("writedate")) {
                writedate = reader.nextString();
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("listselect:myitem", id + "," + title + "," + subtitle + ", " + cat1 + cat2 + cat3 + cat4 +  ", " +portion + time + degree + ", " + writedate);
        return new RecipeDTO(id, title, subtitle,cat1,cat2,cat3,cat4,portion,time,degree,writedate);

    }


}
