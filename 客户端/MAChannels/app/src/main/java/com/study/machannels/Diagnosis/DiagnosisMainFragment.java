package com.study.machannels.Diagnosis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.study.machannels.Desease2Meridian;
import com.study.machannels.Meridian;
import com.study.machannels.R;
import com.study.machannels.Search.PointInfoActivity;
import com.study.machannels.ViewPagerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class DiagnosisMainFragment extends Fragment {

    private ViewPager mVP_Choice;
    //private ImageView mIV_chest,mIV_back,mIV_hand,mIV_foot;
    private RelativeLayout mIV_head,mIV_cardiovascular,mIV_respiratory,mIV_chest,mIV_abdomen,mIV_genitourinary,mIV_skeletal,mIV_muscle,mIV_neurologic;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>(5);
    private ViewPagerAdapter mViewPagerAdapter;
    public int clickPosition =-1;
    private int position=0;
    private Button bt_diagnosis;
    private OkHttpClient client;
    public List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
    public Bundle bundle = new Bundle();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diagnosis_main, container, false);


        mIV_head = view.findViewById(R.id.rll_head);
        mIV_cardiovascular = view.findViewById(R.id.rll_cardiovascular);
        mIV_respiratory = view.findViewById(R.id.rll_respiratory);
        mIV_chest = view.findViewById(R.id.rll_chest);
        mIV_abdomen = view.findViewById(R.id.rll_abdomen);
        mIV_genitourinary = view.findViewById(R.id.rll_genitourinary);
        mIV_skeletal = view.findViewById(R.id.rll_skeletal);
        mIV_muscle = view.findViewById(R.id.rll_muscle);
        mIV_neurologic = view.findViewById(R.id.rll_neurologic);
        bt_diagnosis=view.findViewById(R.id.bt_diagnosis);




        TextView head=view.findViewById(R.id.tv_head);
        TextView cardiovascular=view.findViewById(R.id.tv_cardiovascular);
        TextView respiratory=view.findViewById(R.id.tv_respiratory);
        TextView chest=view.findViewById(R.id.tv_chest);
        TextView abdomen=view.findViewById(R.id.tv_abdomen);
        TextView genitourinary=view.findViewById(R.id.tv_genitourinary);
        TextView skeletal=view.findViewById(R.id.tv_skeletal);
        TextView muscle=view.findViewById(R.id.tv_muscle);
        TextView neurologic=view.findViewById(R.id.tv_neurologic);

        head.setText("头颈");
        cardiovascular.setText("心血管");
        respiratory.setText("呼吸");
        chest.setText("胸部");
        abdomen.setText("腹部");
        genitourinary.setText("泌尿生殖");
        skeletal.setText("骨骼");
        muscle.setText("肌肉软组织");
        neurologic.setText("神经");


        mIV_head.setBackgroundColor(getResources().getColor(R.color.colorWhite));



        mIV_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVP_Choice.setCurrentItem(0,true);

                mIV_head.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                mIV_cardiovascular.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_respiratory.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_chest.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_abdomen.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_genitourinary.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_skeletal.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_muscle.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_neurologic.setBackgroundColor(getResources().getColor(R.color.lightGray));


            }
        });


        mIV_cardiovascular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVP_Choice.setCurrentItem(1,true);

                mIV_head.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_cardiovascular.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                mIV_respiratory.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_chest.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_abdomen.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_genitourinary.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_skeletal.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_muscle.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_neurologic.setBackgroundColor(getResources().getColor(R.color.lightGray));


            }
        });
        mIV_respiratory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVP_Choice.setCurrentItem(2,true);
                //mIV_head.setBackgroundColor(getResources().getColor(R.color.colorWhite));
               // clickPosition=0;
               // mIV_head.setSelected(true);

                mIV_head.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_cardiovascular.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_respiratory.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                mIV_chest.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_abdomen.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_genitourinary.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_skeletal.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_muscle.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_neurologic.setBackgroundColor(getResources().getColor(R.color.lightGray));


            }
        });
        mIV_chest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVP_Choice.setCurrentItem(3,true);
                //mIV_head.setBackgroundColor(getResources().getColor(R.color.colorWhite));
               // clickPosition=0;
               // mIV_head.setSelected(true);

                mIV_head.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_cardiovascular.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_respiratory.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_chest.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                mIV_abdomen.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_genitourinary.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_skeletal.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_muscle.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_neurologic.setBackgroundColor(getResources().getColor(R.color.lightGray));


            }
        });
        mIV_abdomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVP_Choice.setCurrentItem(4,true);
                //mIV_head.setBackgroundColor(getResources().getColor(R.color.colorWhite));
               // clickPosition=0;
               // mIV_head.setSelected(true);

                mIV_head.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_cardiovascular.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_respiratory.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_chest.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_abdomen.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                mIV_genitourinary.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_skeletal.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_muscle.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_neurologic.setBackgroundColor(getResources().getColor(R.color.lightGray));


            }
        });
        mIV_genitourinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVP_Choice.setCurrentItem(5,true);
                //mIV_head.setBackgroundColor(getResources().getColor(R.color.colorWhite));
               // clickPosition=0;
               // mIV_head.setSelected(true);

                mIV_head.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_cardiovascular.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_respiratory.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_chest.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_abdomen.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_genitourinary.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                mIV_skeletal.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_muscle.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_neurologic.setBackgroundColor(getResources().getColor(R.color.lightGray));


            }
        });
        mIV_skeletal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVP_Choice.setCurrentItem(6,true);
                //mIV_head.setBackgroundColor(getResources().getColor(R.color.colorWhite));
               // clickPosition=0;
               // mIV_head.setSelected(true);

                mIV_head.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_cardiovascular.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_respiratory.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_chest.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_abdomen.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_genitourinary.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_skeletal.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                mIV_muscle.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_neurologic.setBackgroundColor(getResources().getColor(R.color.lightGray));


            }
        });
        mIV_muscle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVP_Choice.setCurrentItem(7,true);
                //mIV_head.setBackgroundColor(getResources().getColor(R.color.colorWhite));
               // clickPosition=0;
               // mIV_head.setSelected(true);

                mIV_head.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_cardiovascular.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_respiratory.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_chest.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_abdomen.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_genitourinary.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_skeletal.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_muscle.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                mIV_neurologic.setBackgroundColor(getResources().getColor(R.color.lightGray));


            }
        });
        mIV_neurologic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVP_Choice.setCurrentItem(8,true);
                //mIV_head.setBackgroundColor(getResources().getColor(R.color.colorWhite));
               // clickPosition=0;
               // mIV_head.setSelected(true);

                mIV_head.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_cardiovascular.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_respiratory.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_chest.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_abdomen.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_genitourinary.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_skeletal.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_muscle.setBackgroundColor(getResources().getColor(R.color.lightGray));
                mIV_neurologic.setBackgroundColor(getResources().getColor(R.color.colorWhite));


            }
        });



        mVP_Choice = view.findViewById(R.id.vp_choice);


        mFragmentList.add(new ChoiceHeadFragment());
        mFragmentList.add(new ChoiceCardiovascularFragment());
        mFragmentList.add(new ChoiceRespiratoryFragment());
        mFragmentList.add(new ChoiceChestFragment());
        mFragmentList.add(new ChoiceAbdomenFragment());
        mFragmentList.add(new ChoiceGenitourinaryFragment());
        mFragmentList.add(new ChoiceSkeletalFragment());
        mFragmentList.add(new ChoiceMuscleFragment());
        mFragmentList.add(new ChoiceNeurologicFragment());

        client=new OkHttpClient();
        bt_diagnosis.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DiagnoseResultActivity.class);

                //intent.putExtras(bundle);

                getActivity().startActivity(intent);
            }
        });



        mViewPagerAdapter = new ViewPagerAdapter(this.getChildFragmentManager(),mFragmentList);
        mVP_Choice.setAdapter(mViewPagerAdapter);
        mVP_Choice.setOffscreenPageLimit(5);
        mVP_Choice.setCurrentItem(0);

        mVP_Choice.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });








        return view;
    }
}
