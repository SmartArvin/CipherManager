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
		
//		�ѵ�ǰActivity���뵽CloseApplication��Activitylist��
		CloseApplication.getInstance().addActivity(this);
		
		login_et = (EditText)findViewById(R.id.login_et);
		login_ok = (Button)findViewById(R.id.login_ok);
		login_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String et = login_et.getText().toString();
//				����DBHelper���ݿ⹤����
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
							Toast.makeText(LoginActivity.this, "�Բ�����û�з���Ȩ�ޣ�", Toast.LENGTH_SHORT).show();
						}
					}
//					�ر��α�
					cursor.close();
//					�ر����ݿ�
					loginHelper.close();
				}catch(SQLiteDatabaseLockedException sl){
					System.out.println(sl);
				}
			}
		});

	}

}
