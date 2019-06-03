package com.study.machannels.Own;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.study.machannels.Pinfo;
import com.study.machannels.R;
import com.study.machannels.Search.PointInfoActivity;

import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class diseaseHistoryActivity extends AppCompatActivity {

    private OkHttpClient client;
    private TextView s1,s2,s3,s4,s5,m1,m2,m3,m4,m5,p1,p2,p3,p4,p5,d1,d2,d3,d4,d5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historyinfo);
        Bundle bundle=getIntent().getExtras();

        final int hid=bundle.getInt("hid");
        client=new OkHttpClient();
        new Thread(){
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url("http://192.168.137.1:5000/history?hid="+String.valueOf(hid))
                        .get()
                        .build();

                try{
                    Response response = client.newCall(request).execute();
                    //System.out.println(response.body().string());
                    Gson gson = new Gson();
                    Type type = new TypeToken<History>() {}.getType();
                    final History history = gson.fromJson(response.body().string(), type);
                    //System.out.println(pinfo.toString());
                    diseaseHistoryActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            s1=(TextView) findViewById(R.id.s1);
                            //System.out.println(location.getText().toString());
                            s1.setText(history.s1);

                            s2=(TextView) findViewById(R.id.s2);
                            s2.setText(history.s2);

                            s3=(TextView) findViewById(R.id.s3);
                            s3.setText(history.s3);

                            s4=(TextView) findViewById(R.id.s4);
                            s4.setText(history.s4);

                            s5=(TextView) findViewById(R.id.s5);
                            s5.setText(history.s5);

                            d1=(TextView) findViewById(R.id.d1);
                            d1.setText(history.d1);

                            d2=(TextView) findViewById(R.id.d2);
                            d2.setText(history.d2);

                            d3=(TextView) findViewById(R.id.d3);
                            d3.setText(history.d3);

//                            d4=(TextView) findViewById(R.id.d4);
//                            d4.setText(history.d4);
//
//                            d5=(TextView) findViewById(R.id.d5);
//                            d5.setText(history.d5);

                            m1=(TextView) findViewById(R.id.m1);
                            m1.setText(history.m1);

                            m2=(TextView) findViewById(R.id.m2);
                            m2.setText(history.m2);

                            m3=(TextView) findViewById(R.id.m3);
                            m3.setText(history.m3);

//                            m4=(TextView) findViewById(R.id.m4);
//                            m4.setText(history.m4);
//
//                            m5=(TextView) findViewById(R.id.m5);
//                            m5.setText(history.m5);

                            p1=(TextView) findViewById(R.id.p1);
                            p1.setText(history.p1);

                            p2=(TextView) findViewById(R.id.p2);
                            p2.setText(history.p2);

                            p3=(TextView) findViewById(R.id.p3);
                            p3.setText(history.p3);

//                            p4=(TextView) findViewById(R.id.p4);
//                            p4.setText(history.p4);
//
//                            p5=(TextView) findViewById(R.id.p5);
//                            p5.setText(history.p5);

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
//        TextView name=(TextView) findViewById(R.id.pname);
        //TextView id=(TextView) findViewById(R.id.pid);
//        name.setText(pname);
        //id.setText(String.valueOf(pid));



        Log.e("listview","else执行");



    }
}