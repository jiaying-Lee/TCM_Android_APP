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

public class DiagnoiseListAdapter extends BaseAdapter {
    private OkHttpClient client;
    private OkHttpClient client2;
    private Bundle bundle = new Bundle();
    private Pinfo pinfo;
    private int resource;
    //private String pname;

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Map<String, Object>> deseaseData;
    public int clickPosition = -1;
//    private OnMenuClickListenser mOnMenuClickListenser = new OnMenuClickListenser();


    public DiagnoiseListAdapter(Context context, List<Map<String, Object>> deseaseData,int resource){
        this.mContext = context;
        this.deseaseData=deseaseData;
        //this.mLayoutInflater = LayoutInflater.from(context);
        this.resource = resource;
    }
    @Override
    public int getCount() {

        return deseaseData.size();
    }

    @Override
    public Object getItem(int index) {

        return deseaseData.get(index);
    }

    @Override
    public long getItemId(int index) {

        return index;
    }




    public final class Zujian{
        //View itemView;
        public TextView meridian;
        public TextView desease;
        public TextView title;


    }




    static class ViewHolder{
        //public TextView meridian;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //ViewHolder holder = new ViewHolder();
        //final MyViewHolder vh;
        final Zujian zujian;


        if(convertView == null){
            zujian=new Zujian();
//            convertView = mLayoutInflater.inflate(R.layout.diagnose_result_item,null);
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(resource, null);


            /*convertView.setTag(holder);
            holder.point = convertView.findViewById(R.id.point);*/

            zujian.desease=(TextView)convertView.findViewById(R.id.desease);
            zujian.meridian=(TextView)convertView.findViewById(R.id.meridian);
            zujian.title=(TextView)convertView.findViewById(R.id.title);
            //zujian.point=(TextView)convertView.findViewById(R.id.point1);
            //zujian.pname2=(TextView)convertView.findViewById(R.id.point2);
            convertView.setTag(zujian);
        }else {
            //holder = (ViewHolder) convertView.getTag();
            zujian=(Zujian)convertView.getTag();
        }

        //holder.point.setText("a");

        //zujian.image.setBackgroundResource((Integer)data.get(position).get("image"));
        System.out.println(deseaseData.get(position).get("desease"));
        zujian.desease.setText((String)deseaseData.get(position).get("desease"));
        zujian.meridian.setText((String)deseaseData.get(position).get("meridian"));
        zujian.title.setText("可能患病"+String.valueOf(position));
/*        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);
        layoutParams.height = 0;
        layoutParams.width = 0;*/

        return convertView;
    }



}

