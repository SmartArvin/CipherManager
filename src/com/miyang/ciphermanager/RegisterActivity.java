package com.miyang.ciphermanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	EditText register_et1,register_et2;
	Button register_ok;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);
		
//		把当前Activity加入到CloseApplication的Activitylist中
		CloseApplication.getInstance().addActivity(this);
		
//		获得控件的Id
		register_et1 = (EditText)findViewById(R.id.register_et1);
		register_et2 = (EditText)findViewById(R.id.register_et2);
		register_ok = (Button)findViewById(R.id.register_ok);
		register_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String et1 = register_et1.getText().toString();
				String et2 = register_et2.getText().toString();
				
				if(et1.length()==0){
					Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
				}else{
					if(et1.equals(et2)){
//						创建DBHelper的数据库工具类
						DBHelper regHelper = new DBHelper(getApplicationContext());
//						创建contentvalues对象，封装记录信息
						ContentValues cvs = new ContentValues();
						cvs.put("key1", et1);
//						插入新密码
						regHelper.insert(cvs, "login");
						Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
						RegisterActivity.this.startActivity(intent);
						finish();
						}else{
							Toast.makeText(RegisterActivity.this, "请确认密码", Toast.LENGTH_SHORT).show();
						}	
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
