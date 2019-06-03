package com.study.machannels.Diagnosis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.study.machannels.Desease2Meridian;
import com.study.machannels.R;
import com.study.machannels.Search.PointInfoActivity;
import com.study.machannels.Search.SearchListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DiagnoseResultActivity extends AppCompatActivity {
    private OkHttpClient client;
    public List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
    public TextView tv_desease;
    public TextView tv_meridian;
    public TextView noproblem;

    private ListView desease_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose_result);
        desease_list=findViewById(R.id.desease_list);
        noproblem=findViewById(R.id.noproblem);
        client=new OkHttpClient();
        MyThread thread = new MyThread(){
            @Override
            public void run() {
                //String string = edit.getText().toString();
                Request request = new Request.Builder()
                        .url("http://192.168.137.1:5000/diagnose?user=1")
                        .get()
                        .build();

                try{
                    Response response = client.newCall(request).execute();
                    //System.out.println(response);
                    Gson gson = new Gson();
                    //java.lang.reflect.Type type = new TypeToken<Point>() {}.getType();
                    String strByJson = response.body().string();

                    //Json的解析类对象
                    JsonParser parser = new JsonParser();
                    //将JSON的String 转成一个JsonArray对象
                    JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();
                    final ArrayList<Desease2Meridian> deseaseList = new ArrayList<>();
                    for (JsonElement desease : jsonArray) {
                        //使用GSON，直接转成Bean对象
                        Desease2Meridian deseaseBean = gson.fromJson(desease, Desease2Meridian.class);
                        deseaseList.add(deseaseBean);
                    }



                    for (int i = 0; i < deseaseList.size(); i++) {
                        Map<String, Object> map=new HashMap<String, Object>();
                        //map.put("image", R.drawable.ic_launcher);
                        final String desease_content=deseaseList.get(i).desease;
                        final String meridian_content=deseaseList.get(i).meridian;
                        map.put("desease", deseaseList.get(i).desease);
                        map.put("meridian", deseaseList.get(i).meridian);

                        //map.put("mid", meridianList.get(i).mid);
                        //map.put("info", "这是一个详细信息" + i);
                        list.add(map);
                        //System.out.println(list);

                        //Toast.makeText(getActivity(),deseaseList.get(0).desease,Toast.LENGTH_LONG).show();
                        DiagnoseResultActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //TextView textView = new TextView(getActivity());
                                //这里的Textview的父layout是ListView，所以要用ListView.LayoutParams
                               // ListView.LayoutParams layoutParams = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.WRAP_CONTENT);

                                DiagnoiseListAdapter adapter = new DiagnoiseListAdapter(DiagnoseResultActivity.this, list, R.layout.diagnose_result_item);
                                desease_list.setAdapter(adapter);
                                if(list.size()==0){
                                    noproblem.setVisibility(View.VISIBLE);
                                }


//                                desease_list.addView(desease,layoutParams);
//                                desease_list.addView(meridian,layoutParams);


                                /*tv_desease=(TextView) findViewById(R.id.desease);
                                //System.out.println(location.getText().toString());
                                tv_desease.setText( deseaseList.get(0).desease);

                                tv_meridian=(TextView) findViewById(R.id.meridian);
                                tv_meridian.setText(deseaseList.get(0).meridian);*/


                            }
                        });
                    }
                    //desease_list.setAdapter(new DiagnoiseListAdapter(DiagnoseResultActivity.this,list));
                    //System.out.println(list);



                    //System.out.println(list);

                }catch (Exception e){
                    Log.i("json------", e.getMessage()+"/"+e.getCause());
                }
            }
        };
        thread.start();
        try{
            thread.join();
        }catch(InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(thread.getValue());






    }
}
class MyThread extends Thread{//模拟一个子线程

    @Override
    public void run(){
        try{
            Thread.sleep(1000); //模拟子线程执行
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        value = new java.util.Random().nextInt(1000);
    }


    public int getValue(){
        return value;
    }

    private int value = 0;
}