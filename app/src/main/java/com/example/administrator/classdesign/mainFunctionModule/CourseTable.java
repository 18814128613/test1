package com.example.administrator.classdesign.mainFunctionModule;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.classdesign.LoginTool;
import com.example.administrator.classdesign.MainActivity;
import com.example.administrator.classdesign.R;



/**
 * Created by Administrator on 2016/5/10.
 */
public class CourseTable extends Activity {
    private List<Map<String,Object>> l;
    private View v;
    private Handler handler;
    //返回主页面图片按钮
        private ImageView back_form_table;
        private ImageView arrow;
        private boolean isOpenPop = false;
        private PopupWindow window;
        private ListView list;
        private LinearLayout title_layout;
        public static final String KEY = "key";
        ArrayList<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
    //课程页面的button引用，8行7列
    private int[][] lessons={
            {R.id.lesson11,R.id.lesson12,R.id.lesson13,R.id.lesson14,R.id.lesson15,R.id.lesson16,R.id.lesson17},
            {R.id.lesson21,R.id.lesson22,R.id.lesson23,R.id.lesson24,R.id.lesson25,R.id.lesson26,R.id.lesson27},
            {R.id.lesson31,R.id.lesson32,R.id.lesson33,R.id.lesson34,R.id.lesson35,R.id.lesson36,R.id.lesson37},
            {R.id.lesson41,R.id.lesson42,R.id.lesson43,R.id.lesson44,R.id.lesson45,R.id.lesson46,R.id.lesson47},
            {R.id.lesson51,R.id.lesson52,R.id.lesson53,R.id.lesson54,R.id.lesson55,R.id.lesson56,R.id.lesson57},
            {R.id.lesson61,R.id.lesson62,R.id.lesson63,R.id.lesson64,R.id.lesson65,R.id.lesson66,R.id.lesson67},
            {R.id.lesson71,R.id.lesson72,R.id.lesson73,R.id.lesson74,R.id.lesson75,R.id.lesson76,R.id.lesson77},
            {R.id.lesson81,R.id.lesson82,R.id.lesson83,R.id.lesson84,R.id.lesson85,R.id.lesson86,R.id.lesson87},
    };
    //某节课的背景图,用于随机获取
    private int[] bg={R.drawable.kb1,R.drawable.kb2,R.drawable.kb3,R.drawable.kb4,R.drawable.kb5,R.drawable.kb6,R.drawable.kb7};
    //在校期间的周数
        private String week[]=new String[]{"第1周","第2周","第3周","第4周","第5周","第6周","第7周","第8周","第9周","第10周","第11周","第12周"};
        Context mContext;
        private TextView weekTitle;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_course);
            mContext=this;
            arrow = (ImageView) findViewById(R.id.arrow);
            //初始化返回主页面图片按钮
            back_form_table=(ImageView)findViewById(R.id.back_from_table);
            title_layout= (LinearLayout) findViewById(R.id.table_title_layout);
            weekTitle=(TextView)findViewById(R.id.set_week);
            title_layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    changPopState(v);

                }
            });
            //返回主页面图片按钮添加监听
            back_form_table.setOnClickListener(new OnClickListener(){
                public void onClick(View args){
                    Intent in=new Intent(CourseTable.this, MainActivity.class);
                    startActivity(in);
                    finish();
                }
            });
            initView();

        }


    /**
     * 初始化视图
     */
    private void initView() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    l = LoginTool.getSchedular();
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
        //这里有逻辑问题，只是简单的显示了下数据，数据并不一定是显示在正确位置
        //课程可能有重叠
        //课程可能有1节课的，2节课的，3节课的，因此这里应该改成在自定义View上显示更合理
        //循环遍历
        for (int i = 1; i < l.size(); i++) {
            Map map=(Map)l.get(i);
           // System.out.println("map:"+map.toString());
            for (int j=0;j<map.size()-1;j++)
            {   String value= (String) map.get((int)(j+2)+"");
                value=value.trim();
                System.out.println("value:"+value);
                System.out.println("boolean10:"+value.length());
                if(value != null && value.length() > 1&&!value.equals("")){
                    Button lesson = (Button) findViewById(lessons[i - 1][j]);//获得该节课的button
                    int bgRes = bg[CommonUtil.getRandom(bg.length - 1)];//随机获取背景色
                    lesson.setBackgroundResource(bgRes);//设置背景
                    lesson.setText(value);//设置文本为课程名+“@”+教室
                }
            }
        }
                }
            }
        };
    }
    //判断value不为空的方法
    public static boolean isEmpty( String input )
    {
        if ( input == null || "".equals( input ) )
            return true;

        for ( int i = 0; i < input.length(); i++ )
        {
            char c = input.charAt( i );
            if ( c != ' ' && c != '\t' && c != '\r' && c != '\n' )
            {
                return false;
            }
        }
        return true;
    }
        /**
         * 更改Pop状态
         * */

        public void changPopState(View v) {

            isOpenPop = !isOpenPop;
            if (isOpenPop) {
                arrow.setBackgroundResource(R.drawable.icon_arrow_up);
                popAwindow(v);

            } else {
                arrow.setBackgroundResource(R.drawable.icon_arrow_down);
                if (window != null) {
                    window.dismiss();

                }
            }
        }

        private void popAwindow(View parent) {
            if (window == null) {
                LayoutInflater lay = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = lay.inflate(R.layout.week_time_pop, null);
                list = (ListView) v.findViewById(R.id.pop_list);

                SimpleAdapter adapter = new SimpleAdapter(this, CreateData(),
                        R.layout.week_time_pop_list_item, new String[] { KEY },
                        new int[] { R.id.week_time_title});

                list.setAdapter(adapter);
                list.setItemsCanFocus(false);
                list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                list.setOnItemClickListener(listClickListener);
                // window = new PopupWindow(v, 260, 300);
                int x = (int) getResources().getDimension(R.dimen.pop_x);
                int y = (int) getResources().getDimension(R.dimen.pop_y);
                window = new PopupWindow(v, x, y);
            }
            window.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.pop_bg));
            window.setFocusable(true);
            window.setOutsideTouchable(false);
            window.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss() {
                    // TODO Auto-generated method stub
                    isOpenPop = false;
                    arrow.setBackgroundResource(R.drawable.icon_arrow_down);
                }
            });
            window.update();
            window.showAtLocation(parent, Gravity.CENTER_HORIZONTAL | Gravity.TOP,
                    0, (int) getResources().getDimension(R.dimen.pop_layout_y));

        }

        OnItemClickListener listClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Map<String, Object> map=(Map<String, Object>) parent.getItemAtPosition(position);
                weekTitle.setText((CharSequence) map.get(KEY));
                if (window != null) {
                    window.dismiss();

                }

            }
        };

        public ArrayList<Map<String, Object>> CreateData() {

            Map<String, Object> map;
            for(int i=0;i<week.length;i++){
                map = new HashMap<String, Object>();
                map.put(KEY, week[i]);
                items.add(map);
            }
            return items;

        }


}
