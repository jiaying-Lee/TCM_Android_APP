package com.study.machannels.Search;

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

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchListAdapter extends BaseAdapter {
    private OkHttpClient client;
    private OkHttpClient client2;
    private Bundle bundle = new Bundle();
    private Pinfo pinfo;
    //private String pname;

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Map<String, Object>> meridianData;
    private List<List<Map<String, Object>>> pointData;
    public int clickPosition = -1;
//    private OnMenuClickListenser mOnMenuClickListenser = new OnMenuClickListenser();


    public SearchListAdapter(Context context,List<Map<String, Object>> meridianData,List<List<Map<String, Object>>> pointData){
        this.mContext = context;
        this.meridianData=meridianData;
        this.pointData=pointData;
        this.mLayoutInflater = LayoutInflater.from(context);
    }



    public final class Zujian{
        //View itemView;
        public TextView meridian;
        public TextView point;
        public TextView pname2;
        public GridLayout menu;
        public ImageView  selectorImg;


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
        //public TextView meridian;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //ViewHolder holder = new ViewHolder();
        //final MyViewHolder vh;
        final Zujian zujian;
        client=new OkHttpClient();
        client2=new OkHttpClient();

        if(convertView == null){
            zujian=new Zujian();
            convertView = mLayoutInflater.inflate(R.layout.search_list_item,null);


            /*convertView.setTag(holder);
            holder.point = convertView.findViewById(R.id.point);*/
            zujian.meridian=(TextView)convertView.findViewById(R.id.meridian);
            //zujian.point=(TextView)convertView.findViewById(R.id.point1);
            //zujian.pname2=(TextView)convertView.findViewById(R.id.point2);
            zujian.menu = (GridLayout) convertView.findViewById(R.id.point_list);
            zujian.selectorImg=convertView.findViewById(R.id.checkbox);
            convertView.setTag(zujian);
        }else {
            //holder = (ViewHolder) convertView.getTag();
            zujian=(Zujian)convertView.getTag();
        }

        //holder.point.setText("a");

        //zujian.image.setBackgroundResource((Integer)data.get(position).get("image"));
        zujian.meridian.setText((String)meridianData.get(position).get("mname"));
/*        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);
        layoutParams.height = 0;
        layoutParams.width = 0;*/



        for(int i=0;i<pointData.get(position).size();i++){

            final Button btn=new Button(mContext);
            btn.setWidth(40);
            //textView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            //textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            final String pname=(String)pointData.get(position).get(i).get("pname");
            btn.setText(pname);
            @android.support.annotation.IdRes
            final int tag=(int)pointData.get(position).get(i).get("pid");
            //zujian.meridian.setId(tag);
            btn.setId(tag);//设置ID，可有可无，也可以在R文件中添加字符串，然后在这里使用引用的方式使用
            //设置它的行 和 权重 有了权重才能水平均匀分布
            //由于方法重载，注意这个地方的1.0f 必须是float，
            //使用Spec定义子控件的位置和比重
            GridLayout.Spec rowSpec = GridLayout.spec(i/3, 1.0f);
            GridLayout.Spec columnSpec = GridLayout.spec(i%3, 1.0f);
            //将Spec传入GridLayout.LayoutParams并设置宽高为0，必须设置宽高，否则视图异常
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
            params.setGravity(Gravity.LEFT);

            zujian.menu.addView(btn,params);
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PointInfoActivity.class);
                    //启动

                    //bundle.putInt("photo", photo[arg2]);
                    bundle.putString("pname",pname);
                    bundle.putInt("pid", tag);
                    //Intent intent = new Intent();

                    //intent.setClass(MainActivity.this, MoveList.class);
                    Log.i("pname", btn.getText().toString());

                    new Thread(){
                        @Override
                        public void run() {
                            //String string = edit.getText().toString();
                            //String name= String.valueOf(position+1);
                            //String pass= "123456";
                            String url = "http://192.168.137.1:5000/pinfo";//替换成自己的服务器地址
                            //OkHttpClient client = new OkHttpClient();
                            FormBody.Builder formBuilder = new FormBody.Builder();
                            formBuilder.add("pid", String.valueOf(tag));
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


                    intent.putExtras(bundle);

                    mContext.startActivity(intent);
                }
            });



        }


        if (clickPosition == position) {//当条目为刚才点击的条目时
            if (zujian.selectorImg.isSelected()) {//当条目状态图标为选中时，说明该条目处于展开状态，此时让它隐藏，并切换状态图标的状态。
                zujian.selectorImg.setSelected(false);
                zujian.menu.setVisibility(View.GONE);
                zujian.selectorImg.setImageResource(R.mipmap.right);

                Log.e("listview","if执行");
                clickPosition=-1;//隐藏布局后需要把标记的position去除掉，否则，滑动listview让该条目划出屏幕范围，
                // 当该条目重新进入屏幕后，会重新恢复原来的显示状态。经过打log可知每次else都执行一次 （条目第二次进入屏幕时会在getview中寻找他自己的状态，相当于重新执行一次getview）
                //因为每次滑动的时候没标记得position填充会执行click
            } else {//当状态条目处于未选中时，说明条目处于未展开状态，此时让他展开。同时切换状态图标的状态。
                zujian.selectorImg.setSelected(true);
                zujian.menu.setVisibility(View.VISIBLE);

                zujian.selectorImg.setImageResource(R.mipmap.down);
                //zujian.menu.findViewById(1);
                //System.out.println(zujian.meridian.getId());





            }

        } else {//当填充的条目position不是刚才点击所标记的position时，让其隐藏，状态图标为false。

            //每次滑动的时候没标记得position填充会执行此处，把状态改变。所以如果在以上的if (vh.selectorImg.isSelected()) {}中不设置clickPosition=-1；则条目再次进入屏幕后，还是会进入clickposition==position的逻辑中
            //而之前的滑动（未标记条目的填充）时，执行此处逻辑，已经把状态图片的selected置为false。所以上面的else中的逻辑会在标记过的条目第二次进入屏幕时执行，如果之前的状态是显示，是没什么影响的，再显示一次而已，用户看不出来，但是如果是隐藏状态，就会被重新显示出来
            zujian.menu.setVisibility(View.GONE);
            zujian.selectorImg.setSelected(false);
            zujian.selectorImg.setImageResource(R.mipmap.right);

            Log.e("listview","状态改变");
        }
/*        zujian.hide_1.setOnClickListener(this);
        vh.hide_2.setOnClickListener(this);
        vh.hide_3.setOnClickListener(this);
        vh.hide_4.setOnClickListener(this);
        vh.hide_5.setOnClickListener(this);*/
        zujian.selectorImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Main2Activity.this, "被点了", Toast.LENGTH_SHORT).show();
                clickPosition = position;//记录点击的position
                notifyDataSetChanged();//刷新adapter重新填充条目。在重新填充的过程中，被记录的position会做展开或隐藏的动作，具体的判断看上面代码
                //在此处需要明确的一点是，当adapter执行刷新操作时，整个getview方法会重新执行，也就是条目重新做一次初始化被填充数据。
                //所以标记position，不会对条目产生影响，执行刷新后 ，条目重新填充当，填充至所标记的position时，我们对他处理，达到展开和隐藏的目的。
                //明确这一点后，每次点击代码执行逻辑就是 onclick（）---》getview（）
            }
        });


        return convertView;
    }



}

