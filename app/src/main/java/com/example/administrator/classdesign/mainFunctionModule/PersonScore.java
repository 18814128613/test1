package com.example.administrator.classdesign.mainFunctionModule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.administrator.classdesign.LoginTool;
import com.example.administrator.classdesign.MainActivity;
import com.example.administrator.classdesign.R;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/10.
 */
public class PersonScore extends Activity {
    //返回主页面的图片按钮
    private ImageView back_from_person_score;
    private List<Map<String,Object>> l;
    private View v;
    private Handler handler;
    private SimpleAdapter simpleAdapter;
    private ListView lv;
    private ImageView return_main;//返回主页按钮
    private TextView title1;  //页面标题
    protected void onCreate(Bundle savedInstanceStated){
        super.onCreate(savedInstanceStated);
        setContentView(R.layout.person_score_layout1);
//        back_from_person_score=(ImageView)findViewById(R.id.back_from_person_score);
//        //为图片按钮绑定监听器
//        back_from_person_score.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in=new Intent(PersonScore.this,MainActivity.class);
//                startActivity(in);
//                finish();
//            }
//        });
        lv=(ListView)findViewById(R.id.person_score_content_id);
        return_main=(ImageView)findViewById(R.id.return_main);
        title1=(TextView)findViewById(R.id.title1);
        title1.setText("个人成绩");
        //为return_main图片按钮添加事件
        return_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(PersonScore.this,MainActivity.class);
                startActivity(in);
            }
        });
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    l = LoginTool.getScore();
                    simpleAdapter = new SimpleAdapter(PersonScore.this, l, R.layout.person_score_content, new String[]{"0","1","2","3"}, new int[]{R.id.ps1, R.id.ps2,
                            R.id.ps3,R.id.ps4});
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    System.out.print("msg:"+l.toString());
                    lv.setAdapter(simpleAdapter);
                }
            }
        };
    }
}
