package cn.pax.hardwaredemo.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.pax.hardwaredemo.R;

/**
 * Created by chendd on 2017/1/7.
 * 蓝牙信息展示适配器
 */

public class BluetoothShowAdapter extends BaseAdapter {


    Context mContext;
    List<BluetoothDevice> mList;

    public BluetoothShowAdapter(Context mContext, List<BluetoothDevice> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BluetoothDevice mDevice = mList.get(position);
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_bluetooth_item, parent, false);
            mViewHolder = new ViewHolder();
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.tv_bluetooth_name_item = (TextView) convertView.findViewById(R.id.tv_bluetooth_name_item);
        mViewHolder.tv_bluetooth_address_item = (TextView) convertView.findViewById(R.id.tv_bluetooth_address_item);
        mViewHolder.tv_bluetooth_connected_show = (TextView) convertView.findViewById(R.id.tv_bluetooth_connected_show);
        mViewHolder.tv_bluetooth_address_item.setText(mDevice.getAddress());
        mViewHolder.tv_bluetooth_name_item.setText(mDevice.getName());

        if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
            mViewHolder.tv_bluetooth_connected_show.setVisibility(View.VISIBLE);
        } else if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
            mViewHolder.tv_bluetooth_connected_show.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }


    static class ViewHolder {

        public TextView tv_bluetooth_name_item;
        public TextView tv_bluetooth_address_item;
        public TextView tv_bluetooth_connected_show;

    }
}
