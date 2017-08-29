package com.ahuo.practise;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ahuo.practise.widget.CircleView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private int mProgress;

    private CircleView mCircleView;

    private int mAngle;

    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mCircleView = (CircleView) findViewById(R.id.circleView);
        mCircleView.startDotAnimator();
        mHandler.sendEmptyMessageDelayed(11, 500);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mProgress > 100) {
                mProgress = 0;
            }
            mCircleView.setProgress(mProgress);
            mProgress++;
            mHandler.sendEmptyMessageDelayed(11, 500);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        // 取消注册
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        // 真机上获取触发的传感器类型
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ORIENTATION:
                // 获取与Z轴的夹角
                float zAngle = values[2];
                if (mAngle != (int) zAngle) {
                    Log.e("MainActivity", "角度偏差: [" + mAngle + "] 度");
                    mAngle = (int) zAngle;
                    mCircleView.setAngle(mAngle);

                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
