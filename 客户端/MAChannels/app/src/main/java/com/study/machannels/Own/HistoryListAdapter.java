package com.study.machannels.Own;

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
import android.widget.Toast;

import com.study.machannels.MainActivity;
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

public class HistoryListAdapter extends BaseAdapter {
    private List<Map<String, Object>> dataList;
    private Context mContext;
    private int resource;
    private LayoutInflater mLayoutInflater;
    private Bundle bundle = new Bundle();
    private OkHttpClient client;
    /**
     * 有参构造
     *
     * @param context
     *            界面
     * @param dataList
     *            数据
     * @param resource
     *            列表项资源文件
     */
    public HistoryListAdapter(Context context, List<Map<String, Object>> dataList, int resource) {
        this.mContext = context;
        this.dataList=dataList;
//        this.mLayoutInflater = LayoutInflater.from(context);
        this.resource = resource;

    }

    @Override
    public int getCount() {

        return dataList.size();
    }

    @Override
    public Object getItem(int index) {

        return dataList.get(index);
    }

    @Override
    public long getItemId(int index) {

        return index;
    }

    @Override
    public View getView(int index, View view, ViewGroup arg2) {
        // 声明内部类
        Util util = null;
//        client=new OkHttpClient();
//        // 中间变量
//        final int flag = index;
        /**
         * 根据listView工作原理，列表项的个数只创建屏幕第一次显示的个数。
         * 之后就不会再创建列表项xml文件的对象，以及xml内部的组件，优化内存，性能效率
         */
        if (view == null) {
            util = new Util();
            // 给xml布局文件创建java对象
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(resource, null);
             view = mLayoutInflater.inflate(R.layout.diseasehistory_list_item,null);
            // 指向布局文件内部组件

            util.item_date = (TextView) view.findViewById(R.id.item_date);
            util.item_d1 = (TextView) view.findViewById(R.id.item_d1);
            util.item_m1 = (TextView) view.findViewById(R.id.item_m1);
//            util.imageView = (ImageView) view.findViewById(R.id.item_img);
            util.button_info = (Button) view.findViewById(R.id.button_info);
            util.button_delete = (Button) view.findViewById(R.id.button_delete);
            // 增加额外变量
            view.setTag(util);
        } else {
            util = (Util) view.getTag();
        }
        // 获取数据显示在各组件
        final Map<String, Object> map = dataList.get(index);
        util.item_d1.setText((String) map.get("d1"));
        util.item_date.setText((String) map.get("date"));
        util.item_m1.setText((String) map.get("m1"));
//        util.imageView.setImageResource((Integer) map.get("img"));

//        // 删除按钮，添加点击事件
//        util.button_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                dataList.remove(flag);
//                notifyDataSetChanged();
//                Map<String, Object> map = dataList.get(flag);
//                String str = "已删除\n标题：" + map.get("title") + "\n内容："
//                        + map.get("content") + "\n日期：" + map.get("date");
//                Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
//            }
//        });
//        // 详情按钮，添加点击事件
//        util.button_info.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
///*                Map<String, Object> map = dataList.get(flag);
//                String str = "标题：" + map.get("title") + "\n内容："
//                        + map.get("content") + "\n日期：" + map.get("date");
//                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();*/
////                final int hid=(int)dataList.get().get(i).get("pid");
//                int hid = (int) map.get("hid");
//                Intent intent = new Intent();
//                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);//或者使用Activity.startActivity(intent)
//                          intent.putExtra("hid", hid);
//                intent.setClass(mContext, diseaseHistoryActivity.class);
//                mContext.startActivity(intent);
//
//            }
//        });
        return view;
    }

}


/**
 * 内部类，用于辅助适配
 *
 */
class Util {
//    ImageView imageView;
    TextView item_d1, item_m1,item_date;
    Button button_info, button_delete;

}

