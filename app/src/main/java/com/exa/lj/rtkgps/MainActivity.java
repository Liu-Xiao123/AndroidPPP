package com.exa.lj.rtkgps;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView latview;
    private TextView lonView;
    private TextView heiView;
    private TextView sdnView;
    private TextView sdeView;
    private TextView sduView;
    private TextView satnumView;
    private TextView positionmodeView;
    private TextView timeview;
    private Button runButton;
    private Button setButton;

    private static final int msgkey1 = 1;
    private String findcbuffer;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");//将rtklib编译为so库，起名为“native-lib”
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        latview=(TextView) findViewById(R.id.latview);
        lonView=(TextView) findViewById(R.id.lonView);
        heiView=(TextView) findViewById(R.id.heiView);
        sdnView=(TextView) findViewById(R.id.sdnView);
        sdeView=(TextView) findViewById(R.id.sdeView);
        sduView=(TextView) findViewById(R.id.sduView);
        satnumView=(TextView) findViewById(R.id.satnumView);
        positionmodeView=(TextView) findViewById(R.id.positionmodeView);
        timeview=(TextView) findViewById(R.id.timeview);
        runButton = (Button) findViewById(R.id.runButton);
        runButton.setOnClickListener(new runButtonListener());

        setButton = (Button) findViewById(R.id.setButton);
//        setButton.setOnClickListener(new setButtonListener());


    }

//    class setButtonListener implements View.OnClickListener {
//
//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent();
//            intent.setClass(MainActivity.this, MActivitySet.class);
//            MainActivity.this.startActivity(intent);
//        }
//    }

    class runButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            //从JNI获取结果，传递到java层显示
            stringFromJNI();
            //界面显示线程启动
            new ViewThread().start();

            Timer timer=new Timer();
            final Resolvebuffer resbuffer=new Resolvebuffer();

            timer.scheduleAtFixedRate(new TimerTask(){

                @Override
                public void run() {
                    String[] outputinform=new String[8];
                    findcbuffer="abc";
                    //从c层获取结果字符串到Java层，存储到findcbuffer
                    accessField();

                    resbuffer.resolvebuffer(findcbuffer,outputinform);
                    StaticVal.strbuffer=findcbuffer;
                    StaticVal.strB=outputinform[0];
                    StaticVal.strL=outputinform[1];
                    StaticVal.strH=outputinform[2];
                    StaticVal.strN=outputinform[3];
                    StaticVal.strE=outputinform[4];
                    StaticVal.strU=outputinform[5];
                    StaticVal.strPOSITIONTYPE=outputinform[6];
                    StaticVal.strSATNUM=outputinform[7];


                    try {
                        String context=String.format("%s",StaticVal.strbuffer);
                        saveToSDCard("ResultData.txt",context);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            },1000,1000);

        }
    }

    class ViewThread extends Thread{
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(300);
                    Message msg = new Message();
                    msg.what = msgkey1;
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } while (true);
        }
    }

    private Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case msgkey1:
//                    final Resolvebuffer resbuffer=new Resolvebuffer();
//                    String[] outputinform=new String[8];
//                    findcbuffer="abc";
//                    accessField();
//                    resbuffer.resolvebuffer(findcbuffer,outputinform);
//                    StaticVal.strbuffer=findcbuffer;
//                    StaticVal.strB=outputinform[0];
//                    StaticVal.strL=outputinform[1];
//                    StaticVal.strH=outputinform[2];
//                    StaticVal.strN=outputinform[3];
//                    StaticVal.strE=outputinform[4];
//                    StaticVal.strU=outputinform[5];
//                    StaticVal.strPOSITIONTYPE=outputinform[6];
//                    StaticVal.strSATNUM=outputinform[7];
//                    try {
//                        String context=String.format("%s",StaticVal.strbuffer);
//                        saveToSDCard("ResultData.txt",context);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    long sysTime = System.currentTimeMillis();
                    CharSequence sysTimeStr = DateFormat.format("yyyy年MM月dd日-HH时mm分ss秒", sysTime);
                    latview.setText(StaticVal.strB);
                    lonView.setText(StaticVal.strL);
                    heiView.setText(StaticVal.strH);
                    sdnView.setText(StaticVal.strN);
                    sdeView.setText(StaticVal.strE);
                    sduView.setText(StaticVal.strU);
                    satnumView.setText(StaticVal.strSATNUM);
                    positionmodeView.setText(StaticVal.strPOSITIONTYPE);
                    timeview.setText(sysTimeStr);

            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 1, R.string.exit);
        menu.add(0, 2, 2, R.string.about);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            StaticVal.threadStatus = false;
            System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }


    public boolean saveToSDCard(String filename, String content) throws Exception {
        File file = new File(Environment.getExternalStorageDirectory(), filename);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
        //  FileOutputStream outStream = new FileOutputStream(file);
        bw.write(content);
        bw.flush();
        return  true;
        //        outStream.close();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native void accessField();

}
