package com.study.machannels.Diagnosis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.machannels.Pinfo;
import com.study.machannels.R;
import com.study.machannels.Search.PointInfoActivity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChoiceAdapter extends BaseAdapter {


    //private String pname;

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Map<String, Object>> phenotypeData;
    public int clickPosition = -1;
    private Bundle bundle = new Bundle();
    private OkHttpClient client;



    public ChoiceAdapter(Context context, List<Map<String, Object>> phenotypeData){
        this.mContext = context;
        this.phenotypeData=phenotypeData;
        this.mLayoutInflater = LayoutInflater.from(context);
    }



    public final class Zujian{
        public CheckBox phenotype;


    }


    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder{
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        client=new OkHttpClient();
        final Zujian zujian;

        if(convertView == null){
            zujian=new Zujian();
            convertView = mLayoutInflater.inflate(R.layout.head_list_item,null);
            zujian.phenotype=convertView.findViewById(R.id.headcheck);
            convertView.setTag(zujian);
        }else {
            zujian=(Zujian)convertView.getTag();
        }


        zujian.phenotype.setText((String)phenotypeData.get(position).get("description"));

        final int pid=(int)phenotypeData.get(position).get("pid");
        @android.support.annotation.IdRes
        int tag=pid+1000;
        zujian.phenotype.setId(tag);
        zujian.phenotype.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        //String string = edit.getText().toString();
                        //String name= String.valueOf(position+1);
                        //String pass= "123456";
                        String url = "http://192.168.137.1:5000/diagnose";//替换成自己的服务器地址
                        //OkHttpClient client = new OkHttpClient();
                        FormBody.Builder formBuilder = new FormBody.Builder();
                        formBuilder.add("pid", String.valueOf(pid));
                        //formBuilder.add("password", pass);
                        Request request = new Request.Builder()
                                .url(url)
                                .post(formBuilder.build())
                                .build();


                        try{
                            Call call = client.newCall(request);
                            call.enqueue(new Callback()
                            {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    //Toast.makeText(mContext,"请求失败",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    //Toast.makeText(mContext,"成功",Toast.LENGTH_SHORT).show();
                                }

                            });



                        }catch (Exception e){
                            Log.i("json------", e.getMessage()+"/"+e.getCause());
                        }
                    }
                }.start();
            }
        });







        return convertView;
    }




}

