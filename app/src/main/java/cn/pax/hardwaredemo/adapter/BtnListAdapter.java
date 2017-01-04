package cn.pax.hardwaredemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import cn.pax.hardwaredemo.R;

/**
 * Created by chendd on 2017/1/4.
 * 主界面列表展示适配器
 */

public class BtnListAdapter extends BaseAdapter {

    int[] resId = {R.mipmap.home_about, R.mipmap.home_network, R.mipmap.home_print, R.mipmap.home_display};
    String[] data = {"本机", "网络", "打印机", "显示屏"};

    Context mContext;


    public BtnListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_btn_item, null);

        }


        return null;
    }
}
