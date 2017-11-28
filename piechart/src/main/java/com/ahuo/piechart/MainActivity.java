package com.ahuo.piechart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private  PieChartView mPieChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPieChartView=findViewById(R.id.pieChartView);
        mPieChartView.setInterestMoneyPercent(0.3f);
    }
}
