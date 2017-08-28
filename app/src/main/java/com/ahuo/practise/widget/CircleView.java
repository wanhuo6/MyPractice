package com.ahuo.practise.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ahuo.practise.R;

/**
 * Created by ahuo on 17-8-28.
 */

public class CircleView extends View {

    private Context mContext;

    private Paint mCirClePaint;

    private Paint mSwingPaint;

    private int mProgress;

    private int mWidth = 500;

    private int mHeight = 500;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        mContext = getContext();
        mCirClePaint = new Paint();
        mCirClePaint.setColor(mContext.getResources().getColor(R.color.colorAccent));
        mCirClePaint.setAntiAlias(true);
        mCirClePaint.setStrokeWidth(3);
        mCirClePaint.setStrokeCap(Paint.Cap.ROUND);
        mCirClePaint.setStyle(Paint.Style.STROKE);

        mSwingPaint = new Paint();

        mSwingPaint.setColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        mSwingPaint.setAntiAlias(true);
        mSwingPaint.setStrokeWidth(3);
        mSwingPaint.setStrokeCap(Paint.Cap.ROUND);
        mSwingPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //刻度线
        canvas.save();
        for (int i = 0; i < 100; i++) {
            if (mProgress > i) {
                mCirClePaint.setColor(mContext.getResources().getColor(R.color.colorAccent));
            } else {
                mCirClePaint.setColor(mContext.getResources().getColor(R.color.colorPrimary));
            }
            canvas.drawLine(mWidth / 2, 0, mWidth / 2, 50, mCirClePaint);
            // 旋转的度数 = 100 / 360
            canvas.rotate(3.6f, mWidth / 2, mHeight / 2);
        }
        canvas.restore();
        canvas.save();
        canvas.drawLine(mWidth / 2, mWidth / 2, mWidth / 2, -600, mSwingPaint);
        canvas.restore();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        final int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        final int heightSpecSize = MeasureSpec.getMode(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, mHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, mHeight);
        }
    }

    /**
     * 设置进度
     */
    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }

}
