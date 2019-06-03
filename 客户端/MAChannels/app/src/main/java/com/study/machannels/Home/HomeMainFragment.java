package com.study.machannels.Home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.study.machannels.R;

import java.util.ArrayList;
import java.util.List;


public class HomeMainFragment extends Fragment{

    private ListView mLv1;
    private List<HomeContent> contentList = new ArrayList<HomeContent>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_main, container, false);

        initContents();
        mLv1 = view.findViewById(R.id.lv_1);
        mLv1.setAdapter(new HomeListAdapter(HomeMainFragment.super.getActivity(),R.layout.home_list_item,contentList));
        mLv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = Uri.parse("www.baidu.com");
                switch (position){
                    case 0:
                        uri= Uri.parse("https://baijiahao.baidu.com/s?id=1571708935015358&wfr=spider&for=pc");
                        break;
                    case 1:
                        uri= Uri.parse("https://baijiahao.baidu.com/s?id=1571426458048969&wfr=spider&for=pc");
                        break;
                    case 2:
                        uri= Uri.parse("https://baijiahao.baidu.com/s?id=1581069016663741001&wfr=spider&for=pc");
                        break;
                    case 3:
                        uri= Uri.parse("https://baijiahao.baidu.com/s?id=1556735666946600&wfr=spider&for=pc");
                        break;
                    case 4:
                        uri= Uri.parse("https://baijiahao.baidu.com/s?id=1588732185548918758&wfr=spider&for=pc");
                        break;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        return view;
    }

    private void initContents() {
        HomeContent content0 = new HomeContent(R.mipmap.hone_content0,"经络的重要性，重视经络就是重视健康！","2017-07-01",
                "经络是中国祖先的独特的伟大发现，“经络”一词最早见于《黄帝内经》。");
        contentList.add(content0);
        HomeContent content1 = new HomeContent(R.mipmap.hone_content1,"经络是否通畅——你的健康由你自己决定","2017-06-28",
                "经络是人体重要的生命系统，经络畅通、经气充足就意味着整个人体五脏六腑、肢体官窍及皮肉筋骨是一个完整的有机整体，能够维持人体生命活动的正常运行。");
        contentList.add(content1);
        HomeContent content2 = new HomeContent(R.mipmap.hone_content2,"经络健康之经络便捷记忆法","2017-10-13",
                "经络是人体天然药库，而要控制这个天赐的宝藏，只需要明白12经络的运行原理就足够了，这就是中医整个经络学说的要素。");
        contentList.add(content2);
        HomeContent content3 = new HomeContent(R.mipmap.hone_content3,"经络与健康，解开你身体密码","2017-01-18",
                "经络不通该怎么办?经络不通的解决方法有哪些?如何通过经络养生的方法来疏通经络呢?");
        contentList.add(content3);
        HomeContent content4 = new HomeContent(R.mipmap.hone_content4,"无毒一身轻——经络排毒，让身体更清洁、更健康","2017-01-05",
                "平时有时间就进行经络按摩，不仅可以促进血液循环及新陈代谢，轻轻松松帮助身体排除毒素及废物，还能提高人体的免疫力，那些恼人的小毛病也会跟你说bye-bye。");
        contentList.add(content4);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
