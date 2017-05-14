package com.miyang.ciphermanager;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SafeActivity extends Activity{

	Button safe_back,safe_delete,safe_backup,safe_return,safe_derive,safe_change;
	DBHelper safeHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.safe_activity);
		
//		�ѵ�ǰActivity���뵽CloseApplication��Activitylist��
		CloseApplication.getInstance().addActivity(this);
		
//		��ÿؼ���Id
		safe_back = (Button)findViewById(R.id.safe_back);
		safe_delete = (Button)findViewById(R.id.safe_delete);
		safe_backup = (Button)findViewById(R.id.safe_backup);
		safe_return = (Button)findViewById(R.id.safe_return);
		safe_derive = (Button)findViewById(R.id.safe_derive);
		safe_change = (Button)findViewById(R.id.safe_change);
//		Ϊ�ؼ��󶨼�����
		safe_back.setOnClickListener(new safeListener());
		safe_delete.setOnClickListener(new safeListener());
		safe_backup.setOnClickListener(new safeListener());
		safe_return.setOnClickListener(new safeListener());
		safe_derive.setOnClickListener(new safeListener());
		safe_change.setOnClickListener(new safeListener());
	}
//	������������
	class safeListener implements OnClickListener{
//		����DBHelper���ݿ⹤����
		DBHelper safeHelper = new DBHelper(getApplicationContext());
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.safe_back:
			intent.setClass(SafeActivity.this,HomeActivity.class);
			SafeActivity.this.startActivity(intent);
			finish();
			break;
		case R.id.safe_delete:
			//�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
            final Dialog dialog_delete = new Dialog(SafeActivity.this, R.style.MyDialog);
            //��������ContentView
            dialog_delete.setContentView(R.layout.dialog_safe_delete);
            dialog_delete.show();
            
//          ��ÿؼ���Id
            Button safe_delete_cancle = (Button)dialog_delete.findViewById(R.id.safe_delete_cancle);
            Button safe_delete_ok = (Button)dialog_delete.findViewById(R.id.safe_delete_ok);
//            Ϊȡ����ť�󶨼�����
            safe_delete_cancle.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog_delete.dismiss();
				}
			});
//            Ϊȷ��ɾ����ť�󶨼�����
            safe_delete_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
//					����һ���������ڴ��ϵ�����ݱ�
					String[] tb_name = {"web","chat","game","bank","email","others","search","like"}; 
//					ѭ����ѯ���ݱ�������ѯ������뵽search���ݱ���
					for(int i = 0;i < tb_name.length ; i++){
//						���ȫ�����ݱ�
						safeHelper.deleteTb(tb_name[i]);
					}
					dialog_delete.dismiss();
				}
			});
			break;
		case R.id.safe_backup:
//			����֮ǰɾ��ԭ���ı�������
			safeHelper.deleteTb("backup");
			safeHelper.deleteTb("backup_like");
			
			String[] tb_name = {"web","chat","game","bank","email","others"};
//			ѭ����ѯ���ݿ��е�ϵ�����ݱ�
			for(int i = 0; i<tb_name.length ; i++){
//					ѭ����ѯ���ݱ�
					Cursor cur = safeHelper.rawQueryInf(tb_name[i]);
					ContentValues cv = new ContentValues();
					while(cur.moveToNext()){
						cv.put("kind", cur.getString(cur.getColumnIndex("kind")));
						cv.put("date", cur.getString(cur.getColumnIndex("date")));
						cv.put("user", cur.getString(cur.getColumnIndex("user")));
						cv.put("password", cur.getString(cur.getColumnIndex("password")));
						cv.put("remark", cur.getString(cur.getColumnIndex("remark")));
						safeHelper.insert(cv, "backup");
					}
//					�ر��α�
					cur.close();
			}
		//			��ѯlike���ݱ�
					Cursor cu = safeHelper.rawQueryInf("like");
					ContentValues c = new ContentValues();
					while(cu.moveToNext()){
						c.put("kind", cu.getString(cu.getColumnIndex("kind")));
						c.put("date", cu.getString(cu.getColumnIndex("date")));
						c.put("user", cu.getString(cu.getColumnIndex("user")));
						c.put("password", cu.getString(cu.getColumnIndex("password")));
						c.put("remark", cu.getString(cu.getColumnIndex("remark")));
						safeHelper.insert(c, "backup_like");
					}
//					�ر��α�
					cu.close();
//					��ɱ��ݺ����ʾ��Ϣ
			Toast.makeText(SafeActivity.this, "���ݱ�����ɣ�", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.safe_return:
			//�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
            final Dialog dialog_return = new Dialog(SafeActivity.this, R.style.MyDialog);
            //��������ContentView
            dialog_return.setContentView(R.layout.dialog_safe_return);
            dialog_return.show();
            
//          ��ÿؼ���Id
            Button safe_return_cancle = (Button)dialog_return.findViewById(R.id.safe_return_cancle);
            Button safe_return_ok = (Button)dialog_return.findViewById(R.id.safe_return_ok);
//            Ϊȡ����ť�󶨼�����
            safe_return_cancle.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog_return.dismiss();
				}
			});
//            Ϊȷ����ԭ��ť�󶨼�����
            safe_return_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String[] title = {"��վ��̳","��������","��Ϸ�˻�","�����˻�","�����˻�","����"};
//					����һ���������ڴ��ϵ�����ݱ�
					String[] tb_name = {"web","chat","game","bank","email","others"}; 
//					��ԭ����֮ǰ����յ�ǰ���ݱ���Ϣ���������ݱ�����Ϣ�ظ�
					for(int j = 0;j<tb_name.length; j++){
						safeHelper.deleteTb(tb_name[j]);
					}
//					����ղ����ݱ����Ϣ
					safeHelper.deleteTb("like");
//						��ѯbackup���ݱ�
						Cursor c = safeHelper.rawQueryInf("backup");
						while(c.moveToNext()){
							String txt = c.getString(c.getColumnIndex("kind"));
							for(int i = 0;i < title.length ; i++){
//								ѭ����ѯ���ݱ�������ѯ������뵽��Ӧ�����ݱ���
								if(txt.equals(title[i])){
									ContentValues cv = new ContentValues();
									cv.put("kind", c.getString(c.getColumnIndex("kind")));
									cv.put("date", c.getString(c.getColumnIndex("date")));
									cv.put("user", c.getString(c.getColumnIndex("user")));
									cv.put("password", c.getString(c.getColumnIndex("password")));
									cv.put("remark", c.getString(c.getColumnIndex("remark")));
									safeHelper.insert(cv, tb_name[i]);
								}
							}
						}
//						�ر��α�
						c.close();
						
//					��ѯbackup_like���ݱ�����back_like���ݱ��е����ݲ��뵽like����
					Cursor cu = safeHelper.rawQueryInf("backup_like");
					ContentValues cvl = new ContentValues();
					while(cu.moveToNext()){
						cvl.put("kind", cu.getString(cu.getColumnIndex("kind")));
						cvl.put("date", cu.getString(cu.getColumnIndex("date")));
						cvl.put("user", cu.getString(cu.getColumnIndex("user")));
						cvl.put("password", cu.getString(cu.getColumnIndex("password")));
						cvl.put("remark", cu.getString(cu.getColumnIndex("remark")));
//						��like���ݱ��в�������
						safeHelper.insert(cvl, "like");
					}
//					�ر��α�
					cu.close();
					
//					����dialog
					dialog_return.dismiss();
					Toast.makeText(SafeActivity.this, "�����ѳɹ���ԭ��", Toast.LENGTH_SHORT).show();
				}
			});
			break;
		case R.id.safe_derive:
			try{
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				File dirpath = Environment.getExternalStorageDirectory();
				File file = new File(dirpath,"password.txt");
				FileOutputStream fos = new FileOutputStream(file);
				String[] tbName = {"web","chat","game","bank","email","others"};
				for(int i = 0;i<tbName.length;i++){
					Cursor cursor = safeHelper.rawQueryInf(tbName[i]);
					while(cursor.moveToNext()){
						String txt1 = cursor.getString(cursor.getColumnIndex("kind"));
						String txt2 = cursor.getString(cursor.getColumnIndex("date"));
						String txt3 = cursor.getString(cursor.getColumnIndex("user"));
						String txt4 = cursor.getString(cursor.getColumnIndex("password"));
						String txt5 = cursor.getString(cursor.getColumnIndex("remark"));
						String txt = txt1+"\t"+"����:"+txt2+"\t"+"�û���:"+txt3+"\t"+"���룺"+txt4+"\t"+"��ע��"+txt5+"\r\n";
						fos.write(txt.getBytes("UTF-8"));
					}
					if(cursor.isAfterLast()){
						cursor.close();
					}
				}
				fos.close();
				Toast.makeText(SafeActivity.this, "�����ļ�ΪSdCardĿ¼password.txt!", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(SafeActivity.this, "SD������!", Toast.LENGTH_SHORT).show();
				}
			}catch(Exception ex){
				Toast.makeText(SafeActivity.this, "����ʧ�ܣ�", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.safe_change:
			//�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
            final Dialog dialog_change = new Dialog(SafeActivity.this, R.style.MyDialog);
            //��������ContentView
            dialog_change.setContentView(R.layout.dialog_safe_change);
            dialog_change.show();
            
            Cursor curs = safeHelper.rawQueryCount("login");
			int cn = 0;
			while(curs.moveToNext()){
				cn = curs.getInt(0);
			}
//			�ر��α�
			curs.close();
			
//			���login���ݱ�Ϊ�գ�����ζ���û�ѡ���������ģʽ
			if(cn == 0){
				dialog_change.dismiss();
				Toast.makeText(SafeActivity.this, "��������Ϣ����ѡ�����ģʽ��", Toast.LENGTH_SHORT).show();
			}
//			���login���ݱ�Ϊ�գ�����ζ���û��Ѿ�ѡ���˼���ģʽ�������޸ĵ�½����
			else{
//	          ��ÿؼ���Id
	            Button safe_change_cancle = (Button)dialog_change.findViewById(R.id.safe_change_cancle);
	            Button safe_change_ok = (Button)dialog_change.findViewById(R.id.safe_change_ok);
//	            Ϊȡ����ť�󶨼�����
	            safe_change_cancle.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog_change.dismiss();
					}
				});
//	            Ϊȷ���޸İ�ť�󶨼�����
	            safe_change_ok.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
//						�õ�dialog������EditText�༭���Id
						EditText safe_change_et1 = (EditText)dialog_change.findViewById(R.id.safe_change_et1);
						EditText safe_change_et2 = (EditText)dialog_change.findViewById(R.id.safe_change_et2);
						EditText safe_change_et3 = (EditText)dialog_change.findViewById(R.id.safe_change_et3);
//						������������༭�������
						String safe_change_e1 = safe_change_et1.getText().toString();
						String safe_change_e2 = safe_change_et2.getText().toString();
						String safe_change_e3 = safe_change_et3.getText().toString();
						Cursor c = safeHelper.rawQueryInf("login");
						while(c.moveToNext()){
							String temp = c.getString(c.getColumnIndex("key1"));
							if(safe_change_e1.equals(temp)){
								if(safe_change_e2.isEmpty()&&safe_change_e3.isEmpty()){
//									����޸���Ϊ�������login���ݱ������,�������봦��
									safeHelper.deleteTb("login");
									dialog_change.dismiss();
									Toast.makeText(SafeActivity.this, "��������գ�", Toast.LENGTH_SHORT).show();
								}else{
									if(safe_change_e2.equals(safe_change_e3)){
//										��������֮ǰ�����login���ݱ������
										safeHelper.deleteTb("login");
//										����contentvalues���󣬷�װ��¼��Ϣ
										ContentValues cv = new ContentValues();
										cv.put("key1", safe_change_e2);
//										����������
										safeHelper.insert(cv, "login");
										dialog_change.dismiss();
										Toast.makeText(SafeActivity.this, "�޸ĳɹ���", Toast.LENGTH_SHORT).show();
									}else{
										Toast.makeText(SafeActivity.this, "��ȷ������������ȷ��", Toast.LENGTH_SHORT).show();
									}
								}
							}else{
								dialog_change.dismiss();
								Toast.makeText(SafeActivity.this, "�Բ�����û���޸�Ȩ�ޣ�", Toast.LENGTH_SHORT).show();
							}
						}
//						�ر��α�
						c.close();
					}
				});
			}
			break;

		default:
			break;
		}
	  }
	}
	//Ϊ���ؼ����ü����������������ǰActivity
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK
		   && event.getAction() == KeyEvent.ACTION_DOWN){
			Intent intent = new Intent();
			intent.setClass(SafeActivity.this,HomeActivity.class);
			SafeActivity.this.startActivity(intent);
			finish();
		  }
		return true;
	}
}


