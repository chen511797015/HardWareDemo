package cn.pax.hardwaredemo.util;

import android.util.SparseArray;
import android.view.View;


/**
 * Created by chendd on 2017/1/4.
 * 万能ViewHolder工具
 */

public class ViewHolderUtil {
    public static <T extends View> T get(View mView, int id) {
        SparseArray<View> mViewHolder = (SparseArray<View>) mView.getTag();
        if (mViewHolder == null) {
            mViewHolder = new SparseArray<>();
            mView.setTag(mViewHolder);
        }

        View mChildView = mViewHolder.get(id);
        if (mChildView == null) {
            mChildView = mView.findViewById(id);
            mViewHolder.put(id, mChildView);
        }
        return (T) mChildView;
    }
}
