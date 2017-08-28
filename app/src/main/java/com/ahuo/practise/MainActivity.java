package com.ahuo.practise;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ahuo.practise.widget.CircleView;

public class MainActivity extends AppCompatActivity {

    private int mProgress;

    private CircleView mCircleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCircleView= (CircleView) findViewById(R.id.circleView);
        mHandler.sendEmptyMessageDelayed(11, 50);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mProgress>100){
                mProgress=0;
            }
            mCircleView.setProgress(mProgress);
            mProgress++;
            mHandler.sendEmptyMessageDelayed(11, 50);
        }
    };
}
