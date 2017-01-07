package cn.pax.hardwaredemo.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;

/**
 * Created by chendd on 2017/1/7.
 * 开关按钮
 */

public class SwitchButton extends CompoundButton {

    private static final String TAG = "SwitchButton";

    Context mContext;

    public SwitchButton(Context context) {
        super(context, null);
        this.mContext = context;
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }
}
