package com.ahuo.practise.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ahuo.practise.R;
import com.ahuo.tool.util.CommonUtils;
import com.ahuo.tool.util.MyLog;

/**
 * description :
 * author : LiuHuiJie
 * created on : 2017-9-28
 */
public class SunView extends View {

    private int mScreenWidth;

    private int mCircleRadius;

    private Paint mCirClePaint;

    private Paint mSunPaint;

    public SunView(Context context) {
        this(context, null);
    }

    public SunView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SunView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mScreenWidth= CommonUtils.getScreenWidthpx(getContext());
        MyLog.e("宽度"+mScreenWidth+"---高度"+CommonUtils.getScreenHeightpx(getContext()));
        mCircleRadius=mScreenWidth/3;

        mCirClePaint=new Paint();
        mCirClePaint.setColor(getContext().getResources().getColor(R.color.colorPrimary));
        mCirClePaint.setAntiAlias(true);
        mCirClePaint.setStrokeWidth(3);
        mCirClePaint.setStrokeCap(Paint.Cap.ROUND);
        mCirClePaint.setStyle(Paint.Style.STROKE);

        mSunPaint=new Paint();
        mSunPaint.setColor(getContext().getResources().getColor(R.color.colorAccent));
        mSunPaint.setAntiAlias(true);
        mSunPaint.setStrokeWidth(3);
        mSunPaint.setStrokeCap(Paint.Cap.ROUND);
        mSunPaint.setStyle(Paint.Style.STROKE);



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        RectF rect = new RectF(mScreenWidth/2 - mCircleRadius, CommonUtils.dpTopx(getContext(),20), mScreenWidth/2
                +mCircleRadius,CommonUtils.dpTopx(getContext(),20)+2*mCircleRadius);
        canvas.drawArc(rect,180,180,false,mCirClePaint);
        canvas.save();
        canvas.restore();
        canvas.drawCircle(mScreenWidth/2-mCircleRadius,mCircleRadius+CommonUtils.dpTopx(getContext(),20),CommonUtils.dpTopx(getContext(),20),mSunPaint);
        canvas.rotate(90,mScreenWidth/2,mCircleRadius+CommonUtils.dpTopx(getContext(),20));
        canvas.drawCircle(mScreenWidth/2-mCircleRadius,mCircleRadius+CommonUtils.dpTopx(getContext(),20),CommonUtils.dpTopx(getContext(),20),mSunPaint);
        canvas.rotate(180,mScreenWidth/2,mCircleRadius+CommonUtils.dpTopx(getContext(),20));
        canvas.drawCircle(mScreenWidth/2-mCircleRadius,mCircleRadius+CommonUtils.dpTopx(getContext(),20),CommonUtils.dpTopx(getContext(),20),mSunPaint);
        canvas.save();


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
