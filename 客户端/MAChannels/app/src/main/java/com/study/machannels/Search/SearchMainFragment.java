package com.study.machannels.Search;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.study.machannels.Home.HomeListAdapter;
import com.study.machannels.Home.HomeMainFragment;
import com.study.machannels.Meridian;
import com.study.machannels.Point;
import com.study.machannels.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SearchMainFragment extends Fragment {
    private ListView search_list;
    private OkHttpClient client;
    private OkHttpClient client2;
    public List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
    public List<Map<String, Object>> list2=new ArrayList<Map<String,Object>>();
    public List<List<Map<String, Object>>> list_all=new ArrayList<List<Map<String, Object>>>();
    //private HashMap<Integer, HashMap<String, Object>> map=new HashMap<Integer, HashMap<String, Object>>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_search_main, container, false);
        View view = inflater.inflate(R.layout.fragment_search_main, container, false);

        search_list = view.findViewById(R.id.search_list);
        client=new OkHttpClient();
        client2=new OkHttpClient();

        new Thread(){
            @Override
            public void run() {
                //String string = edit.getText().toString();
                Request request = new Request.Builder()
                        .url("http://192.168.137.1:5000/meridian")
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
                    ArrayList<Meridian> meridianList = new ArrayList<>();
                    for (JsonElement meridian : jsonArray) {
                        //使用GSON，直接转成Bean对象
                        Meridian meridianBean = gson.fromJson(meridian, Meridian.class);
                        meridianList.add(meridianBean);
                    }



                    for (int i = 0; i < meridianList.size(); i++) {
                        Map<String, Object> map=new HashMap<String, Object>();
                        //map.put("image", R.drawable.ic_launcher);
                        map.put("mname", meridianList.get(i).mname);
                        //map.put("mid", meridianList.get(i).mid);
                        //map.put("info", "这是一个详细信息" + i);
                        list.add(map);

                    }
                    //System.out.println(list);


                }catch (Exception e){
                    Log.i("json------", e.getMessage()+"/"+e.getCause());
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                //String string = edit.getText().toString();
                Request request = new Request.Builder()
                        .url("http://192.168.137.1:5000/point")
                        .get()
                        .build();

                try{
                    Response response = client2.newCall(request).execute();
                    Gson gson = new Gson();
                    //java.lang.reflect.Type type = new TypeToken<Point>() {}.getType();
                    String strByJson = response.body().string();

                    //Json的解析类对象
                    JsonParser parser = new JsonParser();
                    //将JSON的String 转成一个JsonArray对象
                    JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();
                    ArrayList<Point> pointList = new ArrayList<>();
                    for (JsonElement point : jsonArray) {
                        //使用GSON，直接转成Bean对象
                        Point pointBean = gson.fromJson(point, Point.class);
                        pointList.add(pointBean);
                    }


                    for (int i = 0; i < pointList.size(); i++) {

                        Map<String, Object> map=new HashMap<String, Object>();
                        //map.put("image", R.drawable.ic_launcher);
                        map.put("pname", pointList.get(i).pname);
                        map.put("pid", pointList.get(i).pid);
                        map.put("mid", pointList.get(i).mid);
                        //map.put("info", "这是一个详细信息" + i);
                        list2.add(map);
                    }
                    for(int i=0;i<19;i++) {
                        List<Map<String, Object>> list_tmp=new ArrayList<Map<String,Object>>();
                        for (int j=0;j<list2.size();j++){
                //System.out.println(list2.get(j).get("mid"));
                            if (list2.get(j).get("mid") == Integer.valueOf(i + 1)) {
                                list_tmp.add(list2.get(j));
                            }

                        }

                        list_all.add(list_tmp);
                        //System.out.println(String.valueOf(j));
                    }
                    //System.out.println(list_all);
                    //System.out.println(list2);

                }catch (Exception e){
                    Log.i("json------", e.getMessage()+"/"+e.getCause());
                }
            }
        }.start();


        search_list.setAdapter(new SearchListAdapter(getActivity(),list,list_all));


        return view;
    }

}
