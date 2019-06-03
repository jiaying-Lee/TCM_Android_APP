package com.study.machannels.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.machannels.R;

import java.util.List;

public class HomeListAdapter extends ArrayAdapter {
    private final int resourceId;


    public HomeListAdapter(Context context,int textViewResourceId, List<HomeContent> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        HomeContent homeContent = (HomeContent) getItem(position); // 获取当前项的Fruit实例

        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        ImageView contentImage = (ImageView) view.findViewById(R.id.home_iv);//获取该布局内的图片视图
        TextView contentTitle = (TextView) view.findViewById(R.id.home_tv_title);//获取该布局内的文本视图
        TextView contentTime = (TextView) view.findViewById(R.id.home_tv_time);
        TextView contentContent = (TextView) view.findViewById(R.id.home_tv_content);

        contentImage.setImageResource(homeContent.getImage());//为图片视图设置图片资源
        contentTitle.setText(homeContent.getTitle());//为文本视图设置文本内容
        contentTime.setText(homeContent.getTime());//为文本视图设置文本内容
        contentContent.setText(homeContent.getContent());//为文本视图设置文本内容
        return view;
    }
}
