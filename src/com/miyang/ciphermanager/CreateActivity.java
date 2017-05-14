package com.miyang.ciphermanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CreateActivity extends Activity{

	RelativeLayout create_rl3;
	TextView create_txt1;
	EditText create_name,create_password,create_password_enter,create_note;
	Button create_back,create_ok,k1,k2,k3,k4,k5,k6;
	String txt1,txt2,txt3,txt4;
	DBHelper helper;

	/*
	 * 获得系统时间，并设定时区，输出格式是24小时制
	*/
	public String now()  
    {  
        Time localTime = new Time("Asia/Shanghai");  
        localTime.setToNow();  
        return localTime.format("%Y/%m/%d   %H:%M:%S");  
    }  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_activity);
		
//		把当前Activity加入到CloseApplication的Activitylist中
		CloseApplication.getInstance().addActivity(this);
		
/*
 * 获得系统时间及时区，时间的输出格式是12时制
		*/
//		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd  hh:mm");       
//		final String date = sd.format(new java.util.Date());
		
		
		
		
//		获得控件的Id
		create_rl3 = (RelativeLayout)findViewById(R.id.create_rl3);
		create_txt1 = (TextView)findViewById(R.id.create_txt1);
		create_name = (EditText)findViewById(R.id.create_name);
		create_password = (EditText)findViewById(R.id.create_password);
		create_password_enter = (EditText)findViewById(R.id.create_password_enter);
		create_note = (EditText)findViewById(R.id.create_note);
		create_back = (Button)findViewById(R.id.create_back);
		create_ok = (Button)findViewById(R.id.create_ok);

//		为顶部返回按键绑定监听器
		create_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(CreateActivity.this,HomeActivity.class);
				CreateActivity.this.startActivity(intent);
				finish();
			}
		});
//		为选择分类按键绑定监听器
		create_rl3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//此处直接new一个Dialog对象出来，在实例化的时候传入主题
                final Dialog dialog = new Dialog(CreateActivity.this, R.style.MyDialog);
                //设置它的ContentView
                dialog.setContentView(R.layout.dialog_classify);
                dialog.show();
                
//              获得控件的Id
                Button k1 = (Button)dialog.findViewById(R.id.k1);
                Button k2 = (Button)dialog.findViewById(R.id.k2);
                Button k3 = (Button)dialog.findViewById(R.id.k3);
                Button k4 = (Button)dialog.findViewById(R.id.k4);
                Button k5 = (Button)dialog.findViewById(R.id.k5);
                Button k6 = (Button)dialog.findViewById(R.id.k6);
//                为网站论坛绑定监听器
                k1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						create_txt1.setText("--网站论坛--");
						dialog.dismiss();
//						为保存按键绑定监听器
						create_ok.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								editData("web","网站论坛");
								}
						});
					}
				});
//                为聊天社区绑定监听器
                k2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						create_txt1.setText("--聊天社区--");
						dialog.dismiss();
//						为保存按键绑定监听器
						create_ok.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								editData("chat","聊天社区");
								}
						});
					}
				});
//                为游戏账户绑定监听器
                k3.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						create_txt1.setText("--游戏账户--");
						dialog.dismiss();
//						为保存按键绑定监听器
						create_ok.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								editData("game","游戏账户");
							}
						});
					}
				});
//                为银行账户绑定监听器
                k4.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						create_txt1.setText("--银行账户--");
						dialog.dismiss();
//						为保存按键绑定监听器
						create_ok.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								editData("bank","银行账户");
							}
						});
					}
				});
//                为邮箱账户绑定监听器
                k5.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						create_txt1.setText("--邮箱账户--");
						dialog.dismiss();
//						为保存按键绑定监听器
						create_ok.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								editData("email","邮箱账户");
							}
						});
					}
				});
//                为其他绑定监听器
                k6.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						create_txt1.setText("--其他--");
						dialog.dismiss();
//						为保存按键绑定监听器
						create_ok.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								editData("others","其他");
							}
						});
					}
				});
			}
		});

	}
//	获得新建页面各编辑框内容，并对输入内容进行判断和处理
	public void editData(String tb_name,String title){
//		获得EditView的编辑内容
		txt1 = create_name.getText().toString();
		txt2 = create_password.getText().toString();
		txt3 = create_password_enter.getText().toString();
		txt4 = create_note.getText().toString();
//		判断账号是否为空，为空则提示
		if(txt1.isEmpty()){
			Toast.makeText(CreateActivity.this, "亲，账户信息不能为空！", Toast.LENGTH_SHORT).show();
		}else{
//			判断输入的密码和确认密码是否一致
			if(!txt2.isEmpty()&&txt2.equals(txt3)){
//				创建contentvalues对象，封装记录信息
				ContentValues c = new ContentValues();
				c.put("kind", title);
				c.put("date",now());
				c.put("user", txt1);
				c.put("password",txt2);
				c.put("remark",txt4);
//				创建数据库工具类DBHelper对象
				helper = new DBHelper(getApplicationContext());
//				插入数据
				helper.insert(c, tb_name);
//				跳转到主界面
				Intent intent = new Intent(CreateActivity.this,HomeActivity.class);
				CreateActivity.this.startActivity(intent);
				finish();
			}
//			如果两次密码不一致则提示
			else
				Toast.makeText(CreateActivity.this, "亲，请确认密码输入正确！", Toast.LENGTH_SHORT).show();
		}
	}
	
	//为返回键设置监听器，点击结束当前Activity
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if(keyCode == KeyEvent.KEYCODE_BACK
			   && event.getAction() == KeyEvent.ACTION_DOWN){
				Intent intent = new Intent();
				intent.setClass(CreateActivity.this,HomeActivity.class);
				CreateActivity.this.startActivity(intent);
				finish();
			  }
			return true;
			} 

}
