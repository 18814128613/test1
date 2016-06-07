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
 * Created by Administrator on 2016/5/14.
 */
public class PreviousCost extends Activity {
    private List<Map<String,Object>> l;
    private View v;
    private Handler handler;
    private SimpleAdapter simpleAdapter;
    private ListView lv;
    private ImageView return_main;//返回主页按钮
    private TextView title1;  //页面标题
    protected  void onCreate(Bundle savedinstanceState){
        super.onCreate(savedinstanceState);
        setContentView(R.layout.previous_cost_layout);
        lv=(ListView)findViewById(R.id.cost_content_id);
        return_main=(ImageView)findViewById(R.id.return_main);
        title1=(TextView)findViewById(R.id.title1);
        title1.setText("预交费用");
        //为return_main图片按钮添加事件
        return_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(PreviousCost.this,MainActivity.class);
                startActivity(in);
            }
        });
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    l = LoginTool.get_cost();
                    simpleAdapter = new SimpleAdapter(PreviousCost.this, l, R.layout.previous_cost_content, new String[]{"0","1","2","3","4"}, new int[]{R.id.pc1, R.id.pc2,
                            R.id.pc3,R.id.pc4,R.id.pc5});
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
