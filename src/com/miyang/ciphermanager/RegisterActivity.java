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
		
//		�ѵ�ǰActivity���뵽CloseApplication��Activitylist��
		CloseApplication.getInstance().addActivity(this);
		
//		��ÿؼ���Id
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
					Toast.makeText(RegisterActivity.this, "����������", Toast.LENGTH_SHORT).show();
				}else{
					if(et1.equals(et2)){
//						����DBHelper�����ݿ⹤����
						DBHelper regHelper = new DBHelper(getApplicationContext());
//						����contentvalues���󣬷�װ��¼��Ϣ
						ContentValues cvs = new ContentValues();
						cvs.put("key1", et1);
//						����������
						regHelper.insert(cvs, "login");
						Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
						RegisterActivity.this.startActivity(intent);
						finish();
						}else{
							Toast.makeText(RegisterActivity.this, "��ȷ������", Toast.LENGTH_SHORT).show();
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
