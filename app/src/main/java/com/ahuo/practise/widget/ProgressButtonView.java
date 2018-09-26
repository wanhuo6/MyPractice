package com.ahuo.practise.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ahuo.practise.R;

/**
 * description :
 * author : LiuHuiJie
 * created on : 2018/9/25
 */
public class ProgressButtonView extends View { // 画实心圆的画笔
    // 画圆环的画笔
    private Paint mProgressPaint;
    // 圆环颜色
    private int mProgressColor;
    // 圆环半径
    private float mProgressRadius;
    // 圆环宽度
    private float mProgressWidth;
    //进度条间隙
    private float mProgressSpace;
    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;
    // 总进度
    private int mTotalProgress = 100;
    // 当前进度
    private int mProgress;

    private Bitmap mBitmap;

    private int mImgWidth;

    private int mImgHeight;

    private boolean isClick;

    private LongClickListener mLongClickListener;

    public ProgressButtonView(Context context) {
        this(context, null);
    }

    public ProgressButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBitmap();
        initAttrs(attrs);
        initVariable();
    }

    private void initBitmap() {
        mBitmap = BitmapFactory.decodeResource(getContext().getResources(),
                R.mipmap.icon_main_history);
        mImgWidth = mBitmap.getWidth();
        mImgHeight = mBitmap.getHeight();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typeArray = getContext().getTheme().obtainStyledAttributes(attrs,
                R.styleable.ProgressButtonView, 0, 0);
        mProgressWidth = typeArray.getDimension(
                R.styleable.ProgressButtonView_progressWidth, 5);
        mProgressSpace = typeArray.getDimension(
                R.styleable.ProgressButtonView_progressSpace, 0);
        mProgressColor = typeArray.getColor(
                R.styleable.ProgressButtonView_progressColor, 0xFFFFFFFF);
        mProgressRadius = mImgWidth / 2 + mProgressWidth / 2 + mProgressSpace;
    }

    public void setLongClickListener(LongClickListener longClickListener) {
        this.mLongClickListener = longClickListener;
    }


    private void initVariable() {
        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mProgressWidth);


    }


    @Override


    protected void onDraw(Canvas canvas) {

        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;

        RectF oval = new RectF();
        oval.left = (mXCenter - mProgressRadius);
        oval.top = (mYCenter - mProgressRadius);
        oval.right = mProgressRadius * 2 + (mXCenter - mProgressRadius);
        oval.bottom = mProgressRadius * 2 + (mYCenter - mProgressRadius);
        canvas.drawArc(oval, -90,
                ((float) mProgress / mTotalProgress) * 360, false,
                mProgressPaint);
        canvas.drawBitmap(mBitmap, mXCenter - mImgWidth / 2, mYCenter
                - mImgHeight / 2, null);


    }


    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mProgress < mTotalProgress) {
                if (isClick) {
                    setProgress(++mProgress);
                } else {
                    if (mProgress <= 0) {
                        stopPlay();
                        return;
                    }
                    setProgress(--mProgress);
                }
            } else {
                if (mLongClickListener != null) {
                    mLongClickListener.complete();
                }
                stopPlay();
                return;
            }
            mHandler.sendEmptyMessage(33);
        }
    };

    private void stopPlay() {
        setProgress(0);
        //  mHandler.removeCallbacksAndMessages(null);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {// 返回true的话，单击事件、长按事件不可以被触发
            case MotionEvent.ACTION_DOWN:
                isClick = true;
                // TODO: 2018/9/25  
                mHandler.removeCallbacksAndMessages(null);
                startPlay();
                return true;
            case MotionEvent.ACTION_UP:
                isClick = false;
                mHandler.removeCallbacksAndMessages(null);
                startPlay();
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void startPlay() {
        if (mHandler == null) {
            return;
        }
        mHandler.sendEmptyMessageDelayed(33, 10);

    }


    public interface LongClickListener {
        void complete();
    }

    private void onDestory() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

}


