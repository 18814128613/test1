package com.example.administrator.classdesign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.administrator.classdesign.mainFunctionModule.CheckAttendanceInfo;
import com.example.administrator.classdesign.mainFunctionModule.CheckOpenCourse;
import com.example.administrator.classdesign.mainFunctionModule.CourseTable;
import com.example.administrator.classdesign.mainFunctionModule.DegreeExamEnrol;
import com.example.administrator.classdesign.mainFunctionModule.ExamTimeTable;
import com.example.administrator.classdesign.mainFunctionModule.PersonInfo;
import com.example.administrator.classdesign.mainFunctionModule.PersonScore;
import com.example.administrator.classdesign.mainFunctionModule.PreviousCost;
import com.example.administrator.classdesign.mainFunctionModule.RewardRecord;
import com.example.administrator.classdesign.mainFunctionModule.ViolationRecord;
import com.example.administrator.classdesign.Adapter.pictureAdapter;
public class MainActivity extends AppCompatActivity {
    protected GridView mainInfo;            //创建GridView对象
    private String[] titles=new String[]{"个人信息","预交费用","考试时间表","考勤信息","个人成绩","奖赏记录","查看开设课程","违规记录","等级考试报名"};    //定义字符串数组，存储系统功能的文本
    private int[] images=new int[]{R.drawable.person_info,R.drawable.cost_icon,R.drawable.exam_time_table
    ,R.drawable.check_attendance_info,R.drawable.person_score,R.drawable.reward_record,R.drawable.check_open_course
    ,R.drawable.violation_record,R.drawable.degree_exam_enrol};//定义int数组，存储功能对应的图标
    private View course_table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainInfo=(GridView)findViewById(R.id.mainInfo);            //获取布局文件中的mainInfo组件
        course_table=(LinearLayout)findViewById(R.id.course_table); //获取课表的布局
        pictureAdapter adapter=new pictureAdapter(titles,images,this);     //创建pictureAAdapter对象
        mainInfo.setAdapter(adapter);             //为GridView设置数据源
        course_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=null;
                //使用CourseTable窗口初始化Intent
                intent1=new Intent(MainActivity.this, CourseTable.class);
                startActivity(intent1);                   //打开CourseTable
            }
        });
        mainInfo.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3){
                Intent intent=null;                 //创建Intent对象
                switch(arg2){
                    case 0:
                        //使用PersonInfo窗口初始化Intent
                        intent=new Intent(MainActivity.this, PersonInfo.class);
                        startActivity(intent);                //打开PersonInfo
                        break;
                    case 1:
                        //使用CourseTable窗口初始化Intent
                        intent=new Intent(MainActivity.this, PreviousCost.class);
                        startActivity(intent);                   //打开CourseTable
                        break;
                    case 2:
                        //使用ExamTimeTable窗口初始化Intent
                        intent=new Intent(MainActivity.this, ExamTimeTable.class);
                        startActivity(intent);                   //打开ExamTimeTable
                        break;
                    case 3:
                        //使用CheckAttendanceInfo窗口初始化Intent
                        intent=new Intent(MainActivity.this, CheckAttendanceInfo.class);
                        startActivity(intent);                   //打开CheckAttendanceInfo
                        break;
                    case 4:
                        //使用PersonScore窗口初始化Intent
                        intent=new Intent(MainActivity.this, PersonScore.class);
                        startActivity(intent);                   //打开PersonScore
                        break;
                    case 5:
                        //使用RewardRecord窗口初始化Intent
                        intent=new Intent(MainActivity.this,RewardRecord.class);
                        startActivity(intent);                   //打开RewardRecord
                        break;
                    case 6:
                        //使用CheckOpenCourse窗口初始化Intent
                        intent=new Intent(MainActivity.this, CheckOpenCourse.class);
                        startActivity(intent);                   //打开CheckOpenCourse
                        break;
                    case 7:
                        //使用ViolationRecord窗口初始化Intent
                        intent=new Intent(MainActivity.this, ViolationRecord.class);
                        startActivity(intent);                   //打开ViolationRecord
                        break;
                    case 8:
                        //使用DegreeExamEnrol窗口初始化Intent
                        intent=new Intent(MainActivity.this,DegreeExamEnrol.class);
                        startActivity(intent);                   //打开DegreeExamEnrol
                        break;
                }
            }
        });
    }
}
