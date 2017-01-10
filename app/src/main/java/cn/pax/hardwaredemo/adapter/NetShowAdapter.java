package cn.pax.hardwaredemo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.pax.hardwaredemo.R;
import cn.pax.hardwaredemo.bean.WifiScanResultBean;
import cn.pax.hardwaredemo.util.ViewHolderUtil;

import static cn.pax.hardwaredemo.R.id.iv_net_wifi_level;
import static cn.pax.hardwaredemo.R.id.tv_net_connected_show;
import static cn.pax.hardwaredemo.R.id.tv_net_name_item;

/**
 * Created by chendd on 2017/1/9.
 * WIFI信息,展示wifi信息列表
 */

public class NetShowAdapter extends BaseAdapter {
    private static final String TAG = "NetShowAdapter";
    Context mContext;
    List<WifiScanResultBean> mScanWifiList;
    LayoutInflater mLayoutInflater;


    public NetShowAdapter(Context mContext, List<WifiScanResultBean> mScanWifiList) {
        this.mContext = mContext;
        this.mScanWifiList = mScanWifiList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mScanWifiList.size();
    }

    @Override
    public Object getItem(int position) {
        return mScanWifiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WifiScanResultBean mResultBean = mScanWifiList.get(position);
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.adapter_net_item, parent, false);
            mViewHolder = new ViewHolder();
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.mName = (TextView) convertView.findViewById(R.id.tv_net_name_item);
        mViewHolder.mConnected = (TextView) convertView.findViewById(R.id.tv_net_connected_show);
        mViewHolder.mLevel = (ImageView) convertView.findViewById(R.id.iv_net_wifi_level);

        mViewHolder.mName.setText(mResultBean.getSSID());
        int level = mResultBean.getLevel();
        if (level >= -50) {
            mViewHolder.mLevel.setImageResource(R.mipmap.signer4);
        } else if (level < -50 && level >= -70) {
            mViewHolder.mLevel.setImageResource(R.mipmap.signer3);
        } else if (level < -70 && level >= -80) {
            mViewHolder.mLevel.setImageResource(R.mipmap.signer2);
        } else {
            mViewHolder.mLevel.setImageResource(R.mipmap.signer1);
        }

        //判断连接状态
        if (mResultBean.getStatus() == 0) {
            mViewHolder.mConnected.setVisibility(View.VISIBLE);
        } else {
            mViewHolder.mConnected.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView mName;
        ImageView mLevel;
        TextView mConnected;
    }

}
