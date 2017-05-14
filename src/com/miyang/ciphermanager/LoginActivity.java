package com.miyang.ciphermanager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity{

	EditText login_et;
	Button login_ok;
	DBHelper loginHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
//		把当前Activity加入到CloseApplication的Activitylist中
		CloseApplication.getInstance().addActivity(this);
		
		login_et = (EditText)findViewById(R.id.login_et);
		login_ok = (Button)findViewById(R.id.login_ok);
		login_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String et = login_et.getText().toString();
//				创建DBHelper数据库工具类
				loginHelper = new DBHelper(getApplicationContext());
				try{
					Cursor cursor = loginHelper.rawQueryInf("login");
					while(cursor.moveToNext()){
						String temp = cursor.getString(cursor.getColumnIndex("key1"));
						if(et.equals(temp)){
							Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
							LoginActivity.this.startActivity(intent);
							finish();
						}
						else{
							Toast.makeText(LoginActivity.this, "对不起，你没有访问权限！", Toast.LENGTH_SHORT).show();
						}
					}
//					关闭游标
					cursor.close();
//					关闭数据库
					loginHelper.close();
				}catch(SQLiteDatabaseLockedException sl){
					System.out.println(sl);
				}
			}
		});

	}

}
