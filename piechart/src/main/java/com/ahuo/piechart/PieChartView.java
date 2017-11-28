package com.ahuo.piechart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ahuo on 17-11-28.
 */

public class PieChartView extends View {

    private int mLoanMoneyColor;
    private int mInterestMoneyColor;
    private int mRadius;
    private Paint mPaint;
    private int mWidth;//控件宽度
    private int mHeight;//控件高度
    private int mCircleWidth;//环形宽度
    private float mInterestMoneyPercent;//利息占比

    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PieChartView, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.PieChartView_loanMoneyColor:
                    mLoanMoneyColor = array.getColor(attr, Color.parseColor("#fac62d"));
                    break;
                case R.styleable.PieChartView_interestMoneyColor:
                    mInterestMoneyColor = array.getColor(attr, Color.parseColor("#65cff6"));
                    break;
                case R.styleable.PieChartView_circleWidth:
                    mCircleWidth = array.getDimensionPixelSize(attr, dpToPx(context, 20));
                    break;
                case R.styleable.PieChartView_radius:
                    mRadius = array.getDimensionPixelSize(attr, dpToPx(context, 20));
                    break;
                default:
                    break;

            }
        }
        array.recycle();
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(mCircleWidth);//设置扇形宽度
        RectF mRectF = new RectF(mCircleWidth / 2, mCircleWidth / 2, mRadius * 2 - mCircleWidth / 2, mRadius * 2 - mCircleWidth / 2);//其实就是个绘制圆弧的规则
        float startAngle = -90;
        float loanSweepAngle = 360;
        mPaint.setColor(mLoanMoneyColor);
        canvas.drawArc(mRectF, startAngle, loanSweepAngle, false, mPaint);
        startAngle = -90;
        float InterestSweep = mInterestMoneyPercent * 360;
        mPaint.setColor(mInterestMoneyColor);
        canvas.drawArc(mRectF, startAngle, InterestSweep, false, mPaint);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            mWidth = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST)
            {
                mWidth = mRadius * 2;
            }
        }
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY)
        {
            mHeight = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST)
            {
                mHeight = mRadius * 2;
            }
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    public static int dpToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //设置利息占比刷新view interestMoneyPercent<1
    public void setInterestMoneyPercent(float interestMoneyPercent) {
        mInterestMoneyPercent = interestMoneyPercent;
        invalidate();
    }

}
