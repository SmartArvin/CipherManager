package com.miyang.ciphermanager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class WelcomePage extends Activity{

	DBHelper welcomeHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_page);
		
//		把当前Activity加入到CloseApplication的Activitylist中
		CloseApplication.getInstance().addActivity(this);
		
//		创建DBHelper的数据库工具类
		welcomeHelper = new DBHelper(getApplicationContext());
		try{
			Cursor cursor = welcomeHelper.rawQueryCount("login");
			while(cursor.moveToFirst()){
				int count = cursor.getInt(0);
				if(count==0){
					new Handler().postDelayed(new Runnable(){    
						   public void run() {
							   Intent intent = new Intent();
							   intent.setClass(WelcomePage.this, RegisterActivity.class);
								WelcomePage.this.startActivity(intent);
								finish();
						    }    
						 }, 2000);
					
				}else{
					new Handler().postDelayed(new Runnable(){    
						   public void run() {
							   Intent intent = new Intent();
							   intent.setClass(WelcomePage.this, LoginActivity.class);
								WelcomePage.this.startActivity(intent);
								finish();
						    }    
						 }, 2000);
				}
					break;
			}
//			关闭游标
			cursor.close();
			welcomeHelper.close();
		}catch(SQLiteDatabaseLockedException sl){
			System.out.println(sl);
		}

	}
	

}
