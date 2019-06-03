package com.study.machannels.Own;

/*import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.machannels.Home.HomeListAdapter;
import com.study.machannels.R;*/


/*
public class OwnMainFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_own_main, container, false);
    }
}
*/


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.study.machannels.LoginActivity;
import com.study.machannels.MainActivity;
import com.study.machannels.R;


public class OwnMainFragment extends Fragment{

    //private LinearLayout ownIndex;
    private TextView name;
    private TextView sex;
    private TextView age;
    //private List<BaseItem> mData = null;
    private Button mBtnTextView;  // define a text view button


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //View view = inflater.inflate(R.layout.fragment_own_main, container, false);
        View view = inflater.inflate(R.layout.fragment_own_main, container, false);
        name=view.findViewById(R.id.name_info);
        name.setText("今天为什么又热了");
        sex=view.findViewById(R.id.sex_info);
        sex.setText("仙女");
        age=view.findViewById(R.id.age_info);
        age.setText("2");
        mBtnTextView = view.findViewById(R.id.disease_case);  // get the button, it is in activity_main.xml
        mBtnTextView.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View view) {
                                                startActivity(new Intent(getActivity(), HistoryMainActivity.class));
                                            }
                                        });

        mBtnTextView = view.findViewById(R.id.exit);  // get the button, it is in activity_main.xml
        mBtnTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        //ownIndex = view.findViewById(R.id.own);

/*        this.mData = new ArrayList<BaseItem>();
        this.mData.add(new ItemBean1(ViewHolder1.ITEM_VIEW_TYPE_1,"imagePath1","imagePath2"));
//        this.mData.add(new ItemBean1(ViewHolder1.ITEM_VIEW_TYPE_1, "name2", "iamgePath2"));
//        this.mData.add(new ItemBean1(ViewHolder1.ITEM_VIEW_TYPE_1, "name2", "iamgePath2"));
        this.mData.add(new ItemBean2(ViewHolder2.ITEM_VIEW_TYPE_2, "用户名", "好冷啊"));
        this.mData.add(new ItemBean2(ViewHolder2.ITEM_VIEW_TYPE_2, "性别", "女"));
        this.mData.add(new ItemBean2(ViewHolder2.ITEM_VIEW_TYPE_2, "年龄", "22"));
        this.mData.add(new ItemBean3(ViewHolder3.ITEM_VIEW_TYPE_3, "个人病历"));
        this.mData.add(new ItemBean3(ViewHolder3.ITEM_VIEW_TYPE_3, "更多"));



        ownIndex.setAdapter(new OwnListAdapter(com.study.machannels.Own.OwnMainFragment.super.getActivity(),this.mData));*/

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


}
