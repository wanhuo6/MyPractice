package com.ahuo.practise.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.ahuo.practise.R;
import com.ahuo.practise.widget.ProgressButtonView;
import com.ahuo.tool.util.ToastUtil;

public class MainActivity extends AppCompatActivity {


    private ProgressButtonView mProgressButton;




    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();

// new Thread(new ProgressRunable()).start();
    }




    private void initView() {
        mProgressButton = findViewById(R.id.progressButton);
        mProgressButton.setLongClickListener(new ProgressButtonView.LongClickListener() {
            @Override
            public void complete() {
                ToastUtil.showToast("结束");
            }
        });

    }


   /* private void startplay() {
        mHandler.post(new Runnable() {
            @Override


            public void run() {
                if (mCurrentProgress < mTotalProgress) {

                    if (isClick) {// 一直长按
                        mCurrentProgress += 1;
                        mHandler.postDelayed(this, 10);
                        mTasksView.setProgress(mCurrentProgress);
                    } else {// 中途放弃长按
                        // if (mCurrentProgress >= 50) {// 进度超过50%直接走到100%，

                        mCurrentProgress -= 1;
                        mHandler.postDelayed(this, 10);
                        mTasksView.setProgress(mCurrentProgress);
                        //  mTasksView.setProgress(mCurrentProgress);
                        //} else {// 进度没到50%重置为0
                        //   mCurrentProgress = 0;
                        //   mTasksView.setProgress(mCurrentProgress);
                        // }
                    }
                } else {
                    mTasksView.setProgress(0);
                    ToastUtil.showToast("成功" + mCurrentProgress);
                }
            }

        });*/

   // }
}
