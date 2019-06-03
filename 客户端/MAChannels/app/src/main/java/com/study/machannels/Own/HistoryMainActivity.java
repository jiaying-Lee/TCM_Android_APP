package com.study.machannels.Own;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


import com.study.machannels.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HistoryMainActivity extends AppCompatActivity {
    private ListView listView;
    private List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_main);
        listView=(ListView)findViewById(R.id.listview);
//        initDataList();//初始化数据
//        HistoryListAdapter adapter = new HistoryListAdapter(this, list, R.layout.diseasehistory_list_item);
//        listView.setAdapter(adapter);
//    }
        client=new OkHttpClient();
    /**
     * 初始化适配器需要的数据格式
     */
//    private void initDataList() {

        MyThread thread = new MyThread(){
            @Override
            public void run() {
                //String string = edit.getText().toString();
                Request request = new Request.Builder()
                        .url("http://192.168.137.1:5000/history?hid=1")
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
                    ArrayList<History> historyList = new ArrayList<>();
                    for (JsonElement history : jsonArray) {
                        //使用GSON，直接转成Bean对象
                        History historyBean = gson.fromJson(history, History.class);
                        historyList.add(historyBean);
                    }



                    for (int i = 0; i < historyList.size(); i++) {
                        Map<String, Object> map=new HashMap<String, Object>();
                        //map.put("image", R.drawable.ic_launcher);
                        map.put("hid", historyList.get(i).hid);
                        map.put("date", historyList.get(i).date);
                        map.put("d1", historyList.get(i).d1);
                        map.put("m1", historyList.get(i).m1);
                        //map.put("info", "这是一个详细信息" + i);
                        list.add(map);

                        HistoryMainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //TextView textView = new TextView(getActivity());
                                //这里的Textview的父layout是ListView，所以要用ListView.LayoutParams
                                // ListView.LayoutParams layoutParams = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.WRAP_CONTENT);

                                HistoryListAdapter adapter = new HistoryListAdapter(HistoryMainActivity.this, list, R.layout.diseasehistory_list_item);
                                listView.setAdapter(adapter);
//                                if(list.size()==0){
//                                    noproblem.setVisibility(View.VISIBLE);
//                                }


                                //System.out.println(list);
                    /*HistoryMainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //TextView textView = new TextView(getActivity());
                            //这里的Textview的父layout是ListView，所以要用ListView.LayoutParams
                            // ListView.LayoutParams layoutParams = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.WRAP_CONTENT);

                            HistoryListAdapter adapter = new HistoryListAdapter(HistoryMainActivity.this, list, R.layout.diseasehistory_list_item);
                            listView.setAdapter(adapter);*/


                            }
                        });
                    }






                }catch (Exception e) {
                    Log.i("json------", e.getMessage() + "/" + e.getCause());
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
