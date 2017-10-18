package com.ahuo.fire.mychat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ahuo.tool.util.MyLog;
import com.ahuo.tool.util.ToastUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String CHARCODE = "utf-8";
    //public static final String IP = "192.168.21.194";
    public static final String IP = "47.93.247.240";

    private final static int PORT = 8821;
    private EditText mEtClientInput;
    private TextView mTvServerBack;
    private Button mBtSend;
    private ScrollView mScrollView;

    private Socket mSocket;
    private BufferedReader mBufferedReader;
    private OutputStream mSocketOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        new Thread() {
            @Override
            public void run() {
                super.run();
                initSocket();
            }
        }.start();
        initData();
    }


    private void initView() {
        mEtClientInput = (EditText) findViewById(R.id.et_client_input);
        mTvServerBack = (TextView) findViewById(R.id.tv_server_back);
        mScrollView= (ScrollView) findViewById(R.id.scrollView);
        mBtSend = (Button) findViewById(R.id.bt_send);
        mBtSend.setOnClickListener(this);
        MyLog.e("init");
    }

    private void initSocket() {
        try {
            mSocket = new Socket(IP, PORT);
            if (mSocket==null||mSocket.isClosed()){
                ToastUtil.showToast("服务器没开，请稍后重试！");
                return;
            }
            // 接收服务器的反馈
            MyLog.e("-----------");
            mBufferedReader = new BufferedReader(new InputStreamReader(
                    mSocket.getInputStream()));
            String res;
            //String res = mBufferedReader.readLine();
            while ((res = mBufferedReader.readLine()) != null) {
                MyLog.e("=========");
                receiveMsg(res);
            }
        } catch (IOException e) {
            MyLog.e(e.getMessage());
            e.printStackTrace();
        }
    }

    private void initData() {


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_send:
                final String msg = mEtClientInput.getText().toString().trim();
                if (TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast("输入为空！");
                    return;
                }
                if (msg.equals("exit")) {
                    finish();
                    return;
                }
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        sendMsg(android.os.Build.MODEL+":  "+msg);
                    }
                }.start();
                break;
            default:

                break;
        }
    }

    private void receiveMsg(String msg) {
        if (msg != null) {
            msg = Util.decode(msg, CHARCODE);
            System.out.println("c3:" + msg);
            final String finalRes = msg;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTvServerBack.append(finalRes + "\r\n");
                    mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }

    private void sendMsg(String msg) {
        try {
            // 发送消息
            // base64 编码，防止中文乱码
            msg = Util.encode(msg.getBytes(CHARCODE));
            msg = msg + "\r\n";
            mSocketOut = mSocket.getOutputStream();
            mSocketOut.write(msg.getBytes(CHARCODE));
            mSocketOut.flush();

        } catch (IOException e) {
            MyLog.e(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Thread() {
            @Override
            public void run() {
                super.run();
                sendMsg("exit");
                closeSocket();
            }
        }.start();
    }

    private void closeSocket() {
        try {
            if (mSocket != null)
                mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (mBufferedReader != null)
                mBufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mSocketOut != null) {
            try {
                mSocketOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
