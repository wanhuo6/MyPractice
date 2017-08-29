package com.ahuo.practise.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.ahuo.practise.R;

/**
 * Created by ahuo on 17-8-28.
 */

public class CircleView extends View {

    private Context mContext;

    private Paint mCirClePaint;

    private Paint mSwingPaint;

    private Paint mBallPaint;

    private int mProgress;

    private int mWidth = 1000;

    private int mHeight = 1000;

    private int mCircleRadius=mWidth/4;

    private float mSwingProgress;

    private ValueAnimator animator;

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

        mBallPaint=new Paint();
        mBallPaint.setColor(mContext.getResources().getColor(R.color.colorPrimary));
        mBallPaint.setAntiAlias(true);
        mBallPaint.setStrokeWidth(3);
        mBallPaint.setStrokeCap(Paint.Cap.ROUND);
        mBallPaint.setStyle(Paint.Style.FILL);

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
            canvas.drawLine(2*mCircleRadius, 0, 2*mCircleRadius, 50, mCirClePaint);
            // 旋转的度数 = 100 / 360
            canvas.rotate(3.6f, 2*mCircleRadius,mCircleRadius);
        }
     /*   while (mSwingProgress>80){
            mSwingProgress=mSwingProgress-80;
        }
        while (mSwingProgress<-80){
            mSwingProgress=mSwingProgress+80;
        }*/
        canvas.restore();
        canvas.save();
        canvas.rotate(3.6f*mSwingProgress, 2*mCircleRadius, mCircleRadius);
        canvas.drawLine(2*mCircleRadius, mCircleRadius, 2*mCircleRadius, 7*mCircleRadius/2, mSwingPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(3.6f*mSwingProgress, 2*mCircleRadius, mCircleRadius);
        canvas.drawCircle(2*mCircleRadius,mCircleRadius/4*15,mCircleRadius/4, mBallPaint);
        canvas.restore();

    }

    public void startDotAnimator() {
        animator = ValueAnimator.ofFloat(-5, 5);
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSwingProgress = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
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
