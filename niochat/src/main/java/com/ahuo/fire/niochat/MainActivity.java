package com.ahuo.fire.niochat;

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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtClientInput;
    private TextView mTvServerBack;
    private Button mBtSend;
    private ScrollView mScrollView;

    private Selector selector = null;
    private static final String IP = "192.168.21.194";
    //public static final String IP = "47.93.247.240";
    private static final int PORT = 9900;
    private Charset charset = Charset.forName("UTF-8");
    private SocketChannel mSocketChannel = null;
    private String name = "";
    private static String USER_EXIST = "system message: user exist, please change a name";
    private static String USER_CONTENT_SPLITE = "#@#";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    initSocket();
                } catch (IOException e) {
                    MyLog.e(e.getMessage() + "eee");
                    e.printStackTrace();
                }
            }
        }.start();

        initData();

    }

    private void initView() {

        mEtClientInput = (EditText) findViewById(R.id.et_client_input);
        mTvServerBack = (TextView) findViewById(R.id.tv_server_back);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mBtSend = (Button) findViewById(R.id.bt_send);
        mBtSend.setOnClickListener(this);
        MyLog.e("init");

    }

    private void initSocket() throws IOException {

        selector = Selector.open();

        // 连接远程主机的IP和端口
        mSocketChannel = SocketChannel.open(new InetSocketAddress(IP, PORT));

        mSocketChannel.configureBlocking(false);
        mSocketChannel.register(selector, SelectionKey.OP_READ);

        // 开辟一个新线程来读取从服务器端的数据
        new Thread(new ClientThread()).start();


    }

    private void sendMsg(String msg) {

        if ("".equals(name)) {
            name = msg;
            msg = name + USER_CONTENT_SPLITE;
        } else {
            msg = name + USER_CONTENT_SPLITE + msg;
        }
        try {

            mSocketChannel.write(charset.encode(msg));// sc既能写也能读，这边是写
        } catch (IOException e) {
            MyLog.e(e.getMessage() + "eee--");
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

    private class ClientThread implements Runnable {
        public void run() {
            try {
                while (true) {
                    int readyChannels = selector.select();
                    if (readyChannels == 0)
                        continue;
                    Set selectedKeys = selector.selectedKeys(); // 可以通过这个方法，知道可用通道的集合
                    Iterator keyIterator = selectedKeys.iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey sk = (SelectionKey) keyIterator.next();
                        keyIterator.remove();
                        dealWithSelectionKey(sk);
                    }
                }
            } catch (IOException io) {
            }
        }

        private void dealWithSelectionKey(SelectionKey sk) throws IOException {
            if (sk.isReadable()) {
                // 使用 NIO 读取 Channel中的数据，这个和全局变量sc是一样的，因为只注册了一个SocketChannel
                // sc既能写也能读，这边是读
                SocketChannel sc = (SocketChannel) sk.channel();

                ByteBuffer buff = ByteBuffer.allocate(1024);
                String content = "";
                while (sc.read(buff) > 0) {
                    buff.flip();
                    content += charset.decode(buff);
                }
                // 若系统发送通知名字已经存在，则需要换个昵称
                if (USER_EXIST.equals(content)) {
                    name = "";
                }
                final String finalContent = content;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvServerBack.append(finalContent + "\r\n");
                        mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
                sk.interestOps(SelectionKey.OP_READ);
            }
        }
    }
}
