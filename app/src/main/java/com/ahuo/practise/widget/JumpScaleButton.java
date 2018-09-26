package com.ahuo.practise.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ahuo.practise.R;


/**
 * description :
 * author : LiuHuiJie
 * created on : 2018/9/26
 */
public class JumpScaleButton extends RelativeLayout {

    private RelativeLayout mRlParent;

    private TextView mTvButton;

    private boolean mFlag;

    public JumpScaleButton(Context context) {
        this(context, null);

    }


    public JumpScaleButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JumpScaleButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_jump_scale_button, this);
        mRlParent=findViewById(R.id.rl_parent);
        mTvButton = findViewById(R.id.tv_button);
        mTvButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mTvButton.setScaleX(0.96f);
                        mTvButton.setScaleY(0.96f);
                        break;
                    case MotionEvent.ACTION_UP:
                        mTvButton.setScaleX(1f);
                        mTvButton.setScaleY(1f);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        mTvButton.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                mFlag=!mFlag;
                if (mFlag){
                    Animator anim = ViewAnimationUtils.createCircularReveal(mRlParent,(int) ((Float.valueOf(mTvButton.getLeft() + mTvButton.getRight()) / 2)), (int) ((Float.valueOf(mTvButton.getTop() + mTvButton.getBottom())) / 2), Float.valueOf(mTvButton.getWidth())/2,Float.valueOf(mRlParent.getHeight()));
                    anim.setDuration(10000);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mRlParent.setVisibility(View.VISIBLE);
                        }

                        @Override public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            mRlParent.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                        }
                    });
                    anim.start();
                }else{
                    Animator anim = ViewAnimationUtils.createCircularReveal(mRlParent,(int) ((Float.valueOf(mTvButton.getLeft() + mTvButton.getRight()) / 2)), (int) ((Float.valueOf(mTvButton.getTop() + mTvButton.getBottom())) / 2), Float.valueOf(mRlParent.getHeight()),Float.valueOf(mTvButton.getWidth())/2);
                    anim.setDuration(10000);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mRlParent.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                        }

                        @Override public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            mRlParent.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                        }
                    });
                    anim.start();
                }
            }
        });

    }



}
