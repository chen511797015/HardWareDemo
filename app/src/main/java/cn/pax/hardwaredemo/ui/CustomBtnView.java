package cn.pax.hardwaredemo.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by chendd on 2017/1/4.
 */

public class CustomBtnView extends RelativeLayout {

    private static final String TAG = "CustomBtnView";

    Context mContext;
    Paint mPaint;//自定义画笔

    float radiusX = 0f;
    float radiusY = 0f;
    float mRadius = 0f;
    float mRadiusValue = 0f;
    int mAscent;


    public CustomBtnView(Context context) {
        super(context);
    }

    public CustomBtnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        //解决自定义View不绘制问题
        setWillNotDraw(false);

        //初始化画笔
        initPaint();

    }


    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#808080"));
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(1);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取宽高
        mRadiusValue = measureWidth(widthMeasureSpec) > measureHeight(heightMeasureSpec) ? measureWidth(widthMeasureSpec) : measureHeight(heightMeasureSpec);

    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text
            result = getPaddingLeft() + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by
                // measureSpec
                result = Math.min(result, specSize);// 60,480
            }
        }

        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        mAscent = (int) mPaint.ascent();
        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text (beware: ascent is a negative number)
            result = (int) (-mAscent + mPaint.descent()) + getPaddingTop() + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by
                // measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(radiusX, radiusY, mRadius, mPaint);

        Log.e(TAG, "onDraw: ");

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float eventX = event.getX();
            float eventY = event.getY();
            radiusX = eventX;
            radiusY = eventY;
            ObjectAnimator a = ObjectAnimator.ofFloat("xx", "xx", 0, 1);
            a.setDuration(1000);
            a.start();
            a.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value = (float) valueAnimator.getAnimatedValue();
                    mRadius = mRadiusValue * value;
                    invalidate();
                }
            });
        }

        return true;
    }

}
