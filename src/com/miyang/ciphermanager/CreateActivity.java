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
	 * ���ϵͳʱ�䣬���趨ʱ���������ʽ��24Сʱ��
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
		
//		�ѵ�ǰActivity���뵽CloseApplication��Activitylist��
		CloseApplication.getInstance().addActivity(this);
		
/*
 * ���ϵͳʱ�估ʱ����ʱ��������ʽ��12ʱ��
		*/
//		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd  hh:mm");       
//		final String date = sd.format(new java.util.Date());
		
		
		
		
//		��ÿؼ���Id
		create_rl3 = (RelativeLayout)findViewById(R.id.create_rl3);
		create_txt1 = (TextView)findViewById(R.id.create_txt1);
		create_name = (EditText)findViewById(R.id.create_name);
		create_password = (EditText)findViewById(R.id.create_password);
		create_password_enter = (EditText)findViewById(R.id.create_password_enter);
		create_note = (EditText)findViewById(R.id.create_note);
		create_back = (Button)findViewById(R.id.create_back);
		create_ok = (Button)findViewById(R.id.create_ok);

//		Ϊ�������ذ����󶨼�����
		create_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(CreateActivity.this,HomeActivity.class);
				CreateActivity.this.startActivity(intent);
				finish();
			}
		});
//		Ϊѡ����ఴ���󶨼�����
		create_rl3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
                final Dialog dialog = new Dialog(CreateActivity.this, R.style.MyDialog);
                //��������ContentView
                dialog.setContentView(R.layout.dialog_classify);
                dialog.show();
                
//              ��ÿؼ���Id
                Button k1 = (Button)dialog.findViewById(R.id.k1);
                Button k2 = (Button)dialog.findViewById(R.id.k2);
                Button k3 = (Button)dialog.findViewById(R.id.k3);
                Button k4 = (Button)dialog.findViewById(R.id.k4);
                Button k5 = (Button)dialog.findViewById(R.id.k5);
                Button k6 = (Button)dialog.findViewById(R.id.k6);
//                Ϊ��վ��̳�󶨼�����
                k1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						create_txt1.setText("--��վ��̳--");
						dialog.dismiss();
//						Ϊ���水���󶨼�����
						create_ok.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								editData("web","��վ��̳");
								}
						});
					}
				});
//                Ϊ���������󶨼�����
                k2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						create_txt1.setText("--��������--");
						dialog.dismiss();
//						Ϊ���水���󶨼�����
						create_ok.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								editData("chat","��������");
								}
						});
					}
				});
//                Ϊ��Ϸ�˻��󶨼�����
                k3.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						create_txt1.setText("--��Ϸ�˻�--");
						dialog.dismiss();
//						Ϊ���水���󶨼�����
						create_ok.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								editData("game","��Ϸ�˻�");
							}
						});
					}
				});
//                Ϊ�����˻��󶨼�����
                k4.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						create_txt1.setText("--�����˻�--");
						dialog.dismiss();
//						Ϊ���水���󶨼�����
						create_ok.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								editData("bank","�����˻�");
							}
						});
					}
				});
//                Ϊ�����˻��󶨼�����
                k5.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						create_txt1.setText("--�����˻�--");
						dialog.dismiss();
//						Ϊ���水���󶨼�����
						create_ok.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								editData("email","�����˻�");
							}
						});
					}
				});
//                Ϊ�����󶨼�����
                k6.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						create_txt1.setText("--����--");
						dialog.dismiss();
//						Ϊ���水���󶨼�����
						create_ok.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								editData("others","����");
							}
						});
					}
				});
			}
		});

	}
//	����½�ҳ����༭�����ݣ������������ݽ����жϺʹ���
	public void editData(String tb_name,String title){
//		���EditView�ı༭����
		txt1 = create_name.getText().toString();
		txt2 = create_password.getText().toString();
		txt3 = create_password_enter.getText().toString();
		txt4 = create_note.getText().toString();
//		�ж��˺��Ƿ�Ϊ�գ�Ϊ������ʾ
		if(txt1.isEmpty()){
			Toast.makeText(CreateActivity.this, "�ף��˻���Ϣ����Ϊ�գ�", Toast.LENGTH_SHORT).show();
		}else{
//			�ж�����������ȷ�������Ƿ�һ��
			if(!txt2.isEmpty()&&txt2.equals(txt3)){
//				����contentvalues���󣬷�װ��¼��Ϣ
				ContentValues c = new ContentValues();
				c.put("kind", title);
				c.put("date",now());
				c.put("user", txt1);
				c.put("password",txt2);
				c.put("remark",txt4);
//				�������ݿ⹤����DBHelper����
				helper = new DBHelper(getApplicationContext());
//				��������
				helper.insert(c, tb_name);
//				��ת��������
				Intent intent = new Intent(CreateActivity.this,HomeActivity.class);
				CreateActivity.this.startActivity(intent);
				finish();
			}
//			����������벻һ������ʾ
			else
				Toast.makeText(CreateActivity.this, "�ף���ȷ������������ȷ��", Toast.LENGTH_SHORT).show();
		}
	}
	
	//Ϊ���ؼ����ü����������������ǰActivity
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
