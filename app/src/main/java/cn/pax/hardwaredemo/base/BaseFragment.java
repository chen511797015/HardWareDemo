package cn.pax.hardwaredemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by chendd on 2017/1/4.
 */

public abstract class BaseFragment extends Fragment {


    private int mLayoutID;
    protected View mView;

    public void onCreate(@Nullable Bundle savedInstanceState, int mLayoutID) {
        super.onCreate(savedInstanceState);
        this.mLayoutID = mLayoutID;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(mLayoutID, container, false);
        return mView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findView();
        initEvent();
        init();

    }

    protected abstract void findView();

    protected abstract void initEvent();

    protected abstract void init();
}
