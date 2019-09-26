package com.example.mytest.RTask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.nio.charset.Charset;

import static com.example.mytest.Common.CommonMethod.ipConfig;

public class ListInsert extends AsyncTask<Void, Void, Void> {

    String title, subtitle,cat1,cat2,cat3,cat4,portion,time,degree;

    public ListInsert(String title, String subtitle, String cat1, String cat2, String cat3,
                      String cat4, String portion, String time, String degree) {
        this.title = title;
        this.subtitle = subtitle;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.cat3 = cat3;
        this.cat4 = cat4;
        this.portion = portion;
        this.time = time;
        this.degree = degree;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            // 문자열 및 데이터 추가

            Log.d("insert", "InsertClass" + title + subtitle + cat1 + cat2 + cat3 + cat4);

            builder.addTextBody("title", title, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("subtitle", subtitle, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("cat1", cat1, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("cat2", cat2, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("cat3", cat3, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("cat4", cat4, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("portion", portion, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("time", time, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("degree", degree, ContentType.create("Multipart/related", "UTF-8"));




            String postURL = ipConfig + "/app/recInsert";

            // 전송
            //InputStream inputStream = null;
            HttpClient httpClient = AndroidHttpClient.newInstance("Android");
            HttpPost httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("Insert", "추가성공");

    }

}
