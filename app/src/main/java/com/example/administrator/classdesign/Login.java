package com.example.administrator.classdesign;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by Administrator on 2016/5/5.
 */
public class Login extends AppCompatActivity {
    private ClearEditText username;
    private ClearEditText password;
    private Button login1;
    private Toast mToast;
    private AppCompatCheckBox remember;
    private SharedPreferences sp;
    private LoginTool  lg;
    private Handler handler;
    private String userNameValue,passwordValue;
    private boolean isLogin;
    Dialog dialog;
   @Override
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.login_layout);
       //用户名称
       username=(ClearEditText)findViewById(R.id.username);
       //密码
       password=(ClearEditText)findViewById(R.id.password);
       //登录
       login1=(Button)findViewById(R.id.login);
       remember=(AppCompatCheckBox)findViewById(R.id.remember);
       sp = getSharedPreferences("userInfo", 0);
       String name=sp.getString("USER_NAME", "123");
       String pass =sp.getString("PASSWORD", "123");
       boolean choseRemember =sp.getBoolean("remember", false);
       isLogin=false;
       //如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
       if(choseRemember){
           username.setText(name);
           password.setText(pass);
           remember.setChecked(true);
       }

       login1.setOnClickListener(new View.OnClickListener(){

           // 默认可登录帐号tinyphp,密码123
           @Override
           public void onClick(View arg0) {
               userNameValue = username.getText().toString();
               passwordValue = password.getText().toString();
               SharedPreferences.Editor editor =sp.edit();
               if(TextUtils.isEmpty(userNameValue)){
                   //设置晃动
                   username.setShakeAnimation();
               //设置提示
               showToast("用户名不能为空");
               return;
           }

               if(TextUtils.isEmpty(password.getText())){
                   password.setShakeAnimation();
                   showToast("密码不能为空");
                   return;
               }
               // TODO Auto-generated method stub

                   //保存用户名和密码
                   editor.putString("USER_NAME", userNameValue);
                   editor.putString("PASSWORD", passwordValue);
                   //是否记住密码
                   if(remember.isChecked()){
                       editor.putBoolean("remember", true);
                   }else{
                       editor.putBoolean("remember", false);
                   }
                  editor.apply();
               createDialog(Login.this,"正在登录");

               new Thread() {
                   @Override
                   public void run() {
                       super.run();
                       try {
                           lg = new LoginTool(userNameValue, passwordValue);
                           isLogin = lg.dologin();
                            LoginTool.getInfor();
                           Message msg = new Message();
                           msg.what = 1;
                           handler.sendMessage(msg);
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }
               }.start();
               }

       });
       handler = new Handler() {
           @Override
           public void handleMessage(Message msg) {
               super.handleMessage(msg);
               if (msg.what == 1) {
                   if (isLogin) {
                       Log.d("first","6");
                       dialog.dismiss();
                       startActivity(new Intent(Login.this, MainActivity.class));
                   } else {
                       dialog.dismiss();
                       Toast.makeText(Login.this, "账号或密码错误", Toast.LENGTH_LONG).show();
                   }

               }
           }
       };

   }

    /**
     * 显示Toast消息
     * @param msg
     */
    private void showToast(String msg){
        if(mToast == null){
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(msg);
        }
        mToast.show();
    }

    public Dialog createDialog(Context context, String str) {
        // 加载样式
        dialog = new Dialog(this,R.style.dialog);
        LayoutInflater inflater = LayoutInflater.from(Login.this);
        View v = inflater.inflate(R.layout.dialog, null);// 得到加载view
        TextView tv = (TextView)v.findViewById(R.id.tv_dialog1);
        tv.setText(str);
        // dialog透明
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        ProgressBar pb = (ProgressBar)v.findViewById(R.id.dialog1);
        lp.alpha = 0.8f;
        dialog.getWindow().setAttributes(lp);
        dialog.setContentView(v);
        dialog.show();
        return dialog;
    }
}
