package com.study.machannels.Search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.study.machannels.Pinfo;
import com.study.machannels.Point;
import com.study.machannels.R;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PointInfoActivity extends AppCompatActivity {
    private OkHttpClient client;
    private TextView location;
    private TextView treatment;
    private TextView operation;
    private TextView anatomy;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_info2);

        Bundle bundle=getIntent().getExtras();
        //int id=bundle.getInt("photo");
        String pname=bundle.getString("pname");
        final int pid=bundle.getInt("pid");
        client=new OkHttpClient();
        new Thread(){
            @Override
            public void run() {
                //String string = edit.getText().toString();
                Request request = new Request.Builder()
                        .url("http://192.168.137.1:5000/pinfo?pid="+String.valueOf(pid))
                        .get()
                        .build();

                try{
                    Response response = client.newCall(request).execute();
                    //System.out.println(response.body().string());
                    Gson gson = new Gson();
                    Type type = new TypeToken<Pinfo>() {}.getType();
                    final Pinfo pinfo = gson.fromJson(response.body().string(), type);
                    //System.out.println(pinfo.toString());
                    PointInfoActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            location=(TextView) findViewById(R.id.location);
                            //System.out.println(location.getText().toString());
                            location.setText(pinfo.location);

                            treatment=(TextView) findViewById(R.id.treatment);
                            treatment.setText(pinfo.treatment);

                            operation=(TextView) findViewById(R.id.operation);
                            operation.setText(pinfo.operation);

                            anatomy=(TextView) findViewById(R.id.anatomy);
                            anatomy.setText(pinfo.anatomy);
                        }
                    });

                }catch (Exception e){
                    Log.i("json------", e.getMessage()+"/"+e.getCause());
                }
            }
        }.start();
        //String message="test";
        //ImageView Iv=(ImageView) findViewById(R.id.Iv);
        //Iv.setImageResource(id);
        TextView name=(TextView) findViewById(R.id.pname);
        //TextView id=(TextView) findViewById(R.id.pid);
        name.setText(pname);
        //id.setText(String.valueOf(pid));



        Log.e("listview","else执行");



    }
}
