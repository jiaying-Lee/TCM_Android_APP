package com.study.machannels.Diagnosis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.study.machannels.Phenotype;
import com.study.machannels.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ChoiceSkeletalFragment extends Fragment {
    private ListView type_list;
    private OkHttpClient client;
    public List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choice_head, container, false);

        type_list = view.findViewById(R.id.type_list);
        final int tid=42;
        client=new OkHttpClient();

        new Thread(){
            @Override
            public void run() {
                //String string = edit.getText().toString();
                Request request = new Request.Builder()
                        .url("http://192.168.137.1:5000/phenotype?tid="+String.valueOf(tid))
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
                    ArrayList<Phenotype> phenotypeList = new ArrayList<>();
                    for (JsonElement phenotype : jsonArray) {
                        //使用GSON，直接转成Bean对象
                        Phenotype phenotypeBean = gson.fromJson(phenotype, Phenotype.class);
                        phenotypeList.add(phenotypeBean);
                    }



                    for (int i = 0; i < phenotypeList.size(); i++) {
                        Map<String, Object> map=new HashMap<String, Object>();
                        //map.put("image", R.drawable.ic_launcher);
                        map.put("pid", phenotypeList.get(i).pid);
                        map.put("description", phenotypeList.get(i).description);
                        list.add(map);

                    }
                    //System.out.println(list);


                }catch (Exception e){
                    Log.i("json------", e.getMessage()+"/"+e.getCause());
                }
            }
        }.start();


        type_list.setAdapter(new ChoiceAdapter(getActivity(),list));

        return view;

    }
}
