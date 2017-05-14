package com.miyang.ciphermanager;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryContent extends Activity{

	RelativeLayout category_content_rl2;
	Button category_content_back;
	ListView category_content_ls;
	TextView category_content_title,category_ls_content_txt1,category_ls_content_txt2,txt1,txt2,txt3,txt4,txt5;
	View layout;
	Dialog dialog_list,dialog_move;
	DBHelper contentHelper;
	String like_txt1,like_txt2;
	Bundle bundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_content_activity);
		
//		�ѵ�ǰActivity���뵽CloseApplication��Activitylist��
		CloseApplication.getInstance().addActivity(this);
		
		layout = this.getLayoutInflater().inflate(R.layout.category_ls_item, null);
		category_content_ls = (ListView)findViewById(R.id.category_content_ls);
//		��ÿؼ���Id
		category_content_title = (TextView)findViewById(R.id.category_content_title);
		category_content_rl2 = (RelativeLayout)findViewById(R.id.category_content_rl2);
		category_content_back = (Button)findViewById(R.id.category_content_back);
		
//		�õ���������ݶ��󣬽������������Ϊ����
		bundle = getIntent().getExtras();
		String content_title = bundle.getString("key");
		category_content_title.setText(content_title);
//		Ϊ���ذ�ť�󶨼�����
		category_content_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(CategoryContent.this,CategoryActivity.class);
				CategoryContent.this.startActivity(intent);
				finish();
			}
		});
		/*
		 * �жϴ�CategoryActivity����Ķ�������
		*/
		 
		String[] title = {"��վ��̳","��������","��Ϸ�˻�","�����˻�","�����˻�","����"};
		String[] tb_name = {"web","chat","game","bank","email","others"};
//		ͨ���ж�bundle��������ݣ�ѭ����ѯ���ݿ��е�ϵ�����ݱ�
		for(int i = 0; i<title.length ; i++){
//			�������ݿ⹤����DBHelper����
			contentHelper = new DBHelper(getApplicationContext());
//			�Աȴ���Ķ����title�������
			if(bundle.getString("key").equals(title[i])){
				Cursor cursor = contentHelper.rawQueryCount(tb_name[i]);
				int count = 0 ;
				while(cursor.moveToNext()){
					count = cursor.getInt(0);
				}
//				�ر��α�
				cursor.close();
				contentHelper.close();
				if(count==0){
					category_content_rl2.setVisibility(View.VISIBLE);
					category_content_ls.setVisibility(View.GONE);
				}else{
					try{
//						ѭ����ѯ���ݱ�
						Cursor cur = contentHelper.rawQueryInf(tb_name[i]);
//						���ú�������ѯ����󶨵�listview��
						inflateList(cur);
//						�ж��α��λ���Ƿ�ָ�����ݱ����һ��ĺ�һ��������ر��α�
						if(cur.isAfterLast()){
							cur.close();
						}
					}catch(SQLiteDatabaseLockedException sl){
						System.out.println(sl);
					}

				}
			}
		}
		contentHelper.close();
//		Ϊlistview��item�󶨼�����
		category_content_ls.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
//				��õ�ǰitem�����ݲ���
				View v = category_content_ls.getChildAt(position); 
//				���item���ڲ�view
				txt1 = (TextView)v.findViewById(R.id.category_ls_content_txt1);
				txt2 = (TextView)v.findViewById(R.id.category_ls_content_txt2);
				txt3 = (TextView)v.findViewById(R.id.category_ls_content_txt3);
                txt4 = (TextView)v.findViewById(R.id.category_ls_content_txt4);
                txt5 = (TextView)v.findViewById(R.id.category_ls_content_txt5);
                
				//�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
	            final Dialog dialog_list = new Dialog(CategoryContent.this, R.style.MyDialog);
	            //��������ContentView
	            dialog_list.setContentView(R.layout.dialog_content_list);
	            dialog_list.show();
	            
//            �õ�dialog�в鿴���޸ġ��ƶ����ղء�ɾ����Id
			Button list_btn1 = (Button)dialog_list.findViewById(R.id.content_list_btn1);
			Button list_btn2 = (Button)dialog_list.findViewById(R.id.content_list_btn2);
			Button list_btn3 = (Button)dialog_list.findViewById(R.id.content_list_btn3);
			Button list_btn4 = (Button)dialog_list.findViewById(R.id.content_list_btn4);
			Button list_btn5 = (Button)dialog_list.findViewById(R.id.content_list_btn5);
			
//			Ϊ�鿴�����󶨼�����
			list_btn1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog_list.dismiss();
//					����list_btn1�ļ���������
					list_btn1();
				}
			});
			
//			Ϊ�޸İ����󶨼�����
			list_btn2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog_list.dismiss();
//					����list_btn2�ļ���������
					list_btn2();
				}
			});
			
//			Ϊ�ƶ������󶨼�����
			list_btn3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog_list.dismiss();
//					����list_btn3�ļ���������
					list_btn3();
				}
			});
			
//			Ϊ�ղذ����󶨼�����,��Ҫ�ж��Ƿ��Ѿ��ղع�
			list_btn4.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					int count = 0;
					ContentValues cv = new ContentValues();
//					��ѯ���ݱ��ȫ����Ϣ
					Cursor cu = contentHelper.rawQueryInf("like");
//					��ѯlike���ݱ����������
					Cursor c = contentHelper.rawQueryCount("like");
					while(c.moveToNext()){
						count = c.getInt(0);
				/*
				 * �ж�like���ݱ��Ƿ��ǿձ�Ϊ����ִ�����ݲ������
				 * ��Ϊ�գ���������ݱ�
				*/
						if(count==0){
								cv.put("kind", txt1.getText().toString());
								cv.put("date", txt2.getText().toString());
								cv.put("user", txt3.getText().toString());
								cv.put("password", txt4.getText().toString());
								cv.put("remark", txt5.getText().toString());
								contentHelper.insert(cv, "like");
//								����dialog����
									dialog_list.dismiss();
									Toast.makeText(CategoryContent.this, "����ղأ�", Toast.LENGTH_SHORT).show();
						}
						else{
							ArrayList<String> al = new ArrayList<String>();
							String like_txt2 = null;
//							����like���ݱ�Ԫ��
							while(cu.moveToNext()){
								like_txt2 = cu.getString(cu.getColumnIndex("date"));
//								��like���ݱ��е�date�ֶ�����ȫ�������������al������
								al.add(like_txt2);
							}
							int j = 0 ;
//							ѭ������al����Ԫ��
							while(j<al.size()){
//								�������Ԫ����item�е�txt2����һ�£�����ʾ���˺��Ѿ��ղع������������ݲ���
									if(txt2.getText().toString().equals(al.get(j))){
										dialog_list.dismiss();
										Toast.makeText(CategoryContent.this, "���ղ�", Toast.LENGTH_SHORT).show();
										break;
									}
//									�������Ԫ����item�е�txt2���ݲ�һ�£��������±��1������������Ԫ��
									else{
										j++;
									}
								}
//							�ж�����Ԫ���Ƿ�ȫ��������ϣ����������ϣ�����������Ԫ����item�е�txt2���ݲ�һ�£�
//							����ʱ��j=al.size(),Ȼ��ִ�в�������
							if(j>=al.size()){
								cv.put("kind", txt1.getText().toString());
								cv.put("date", txt2.getText().toString());
								cv.put("user", txt3.getText().toString());
								cv.put("password", txt4.getText().toString());
								cv.put("remark", txt5.getText().toString());
								contentHelper.insert(cv, "like");
							
//						����dialog����
							dialog_list.dismiss();
							Toast.makeText(CategoryContent.this, "����ղأ�", Toast.LENGTH_SHORT).show();
							}
						}
								
					 }	
//					�ر��α�
					cu.close();
					c.close();
				 }
			});
//			Ϊɾ�������󶨼�����
			list_btn5.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog_list.dismiss();
//					����list_btn5�ļ���������
					list_btn5();
				}
			});
			}
		});
		contentHelper.close();
	}
	/*
	 * ���������ĸ�Button�ļ�������
	 * list_btn1()
	 * list_btn2()
	 * list_btn3()
	 * list_btn4()
	 * list_btn5()
	 * 
	*/
	
//	����ǲ鿴�˺���Ϣ
	private void list_btn1(){
//		�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
        final Dialog dialog_check = new Dialog(CategoryContent.this, R.style.MyDialog);
        //��������ContentView
        dialog_check.setContentView(R.layout.dialog_list_check);
        dialog_check.show();
        
//        �õ�dialog������TextView��Id
		TextView check_et1 = (TextView)dialog_check.findViewById(R.id.content_check_txt1);
		TextView check_et2 = (TextView)dialog_check.findViewById(R.id.content_check_txt2);
		TextView check_et3 = (TextView)dialog_check.findViewById(R.id.content_check_txt3);
//��item�е����ݲ���dialog����TextView�ؼ���
		check_et1.setText(txt3.getText().toString()); 
		check_et2.setText(txt4.getText().toString());
		check_et3.setText(txt5.getText().toString());
        
//      ���ϵ�пؼ���Id
        Button content_check_ok = (Button)dialog_check.findViewById(R.id.content_check_ok);
//        Ϊȷ����ť�󶨼�����
        content_check_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog_check.dismiss();
			}
		});
	}
//	������޸��˺���Ϣ
	private void list_btn2(){
//		�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
        final Dialog dialog_change = new Dialog(CategoryContent.this, R.style.MyDialog);
        //��������ContentView
        dialog_change.setContentView(R.layout.dialog_list_change);
        dialog_change.show();
        
//        �õ�dialog���ĸ�TextView��Id
        final EditText list_change_et1 = (EditText)dialog_change.findViewById(R.id.list_change_et1);
        final EditText list_change_et2 = (EditText)dialog_change.findViewById(R.id.list_change_et2);
        final EditText list_change_et3 = (EditText)dialog_change.findViewById(R.id.list_change_et3);
        final EditText list_change_et4 = (EditText)dialog_change.findViewById(R.id.list_change_et4);
//��item�е����ݲ���dialog����TextView�ؼ���
        list_change_et1.setText(txt3.getText().toString()); 
        list_change_et2.setText(txt4.getText().toString());
        list_change_et4.setText(txt5.getText().toString());
        
//      ���ϵ�пؼ���Id
        Button list_change_cancle = (Button)dialog_change.findViewById(R.id.list_change_cancle);
        Button list_change_ok = (Button)dialog_change.findViewById(R.id.list_change_ok);
//        Ϊȡ���޸İ�ť�󶨼�����
        list_change_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog_change.dismiss();
			}
		});
//      Ϊȷ���޸İ�ť�󶨼�����
      list_change_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*
				 * ִ�и������ݱ�Ĳ���
				*/
				String[] title = {"��վ��̳","��������","��Ϸ�˻�","�����˻�","�����˻�","����"};
				String[] tb_name = {"web","chat","game","bank","email","others"};
				for(int i = 0;i<title.length;i++){
					if(txt1.getText().toString().equals(title[i])){
						String date = txt2.getText().toString();
						ContentValues cv = new ContentValues();
						cv.put("kind",title[i]);
						cv.put("date",txt2.getText().toString());
						cv.put("user",list_change_et1.getText().toString());
						cv.put("password",list_change_et2.getText().toString());
						cv.put("remark",list_change_et4.getText().toString());
						if(list_change_et2.getText().toString().equals(list_change_et3.getText().toString())){
							contentHelper.updateByDate(tb_name[i], cv, date);
							contentHelper.updateByDate("like", cv, date);
//							ѭ����ѯ���ݱ�
							Cursor cur = contentHelper.rawQueryInf(tb_name[i]);
//							���ú�������ѯ����󶨵�listview��
							inflateList(cur);
//							�ж��α��λ���Ƿ�ָ�����ݱ����һ��ĺ�һ��������ر��α�
							if(cur.isAfterLast()){
								cur.close();
							}
							dialog_change.dismiss();
						}else{
							Toast.makeText(CategoryContent.this, "��ȷ�����룡", Toast.LENGTH_SHORT).show();
						}

					}
				}
			}
		});
	}
//	������ƶ��˺���Ϣ��dialog_classify�Ի���
	private void list_btn3(){
//		�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
        dialog_move = new Dialog(CategoryContent.this, R.style.MyDialog);
        //��������ContentView
        dialog_move.setContentView(R.layout.dialog_classify);
        dialog_move.show();
        
//        �õ�dialog������button��Id
		Button k1 = (Button)dialog_move.findViewById(R.id.k1);//web
		Button k2 = (Button)dialog_move.findViewById(R.id.k2);//chat
		Button k3 = (Button)dialog_move.findViewById(R.id.k3);//game
		Button k4 = (Button)dialog_move.findViewById(R.id.k4);//bank
		Button k5 = (Button)dialog_move.findViewById(R.id.k5);//email
		Button k6 = (Button)dialog_move.findViewById(R.id.k6);//others
		
//		Ϊ��վ��̳web�󶨼�����
		k1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(txt1.getText().toString().equals("��վ��̳")){
					dialog_move.dismiss();
					Toast.makeText(CategoryContent.this, "���ڵ�ǰ���", Toast.LENGTH_SHORT).show();
				}else{
//					���������ַ������飬������ų���������ƺ͵�ǰ����Ӧ�����ݱ�֮���������ƺ����ݱ�����
					String[] str1 = {"��������","��Ϸ�˻�","�����˻�","�����˻�","����"};
					String[] str2 = {"chat","game","bank","email","others"};
//					����movelistener()�����������ݱ���в�ѯ�޸ĵȲ���
					moveListener("��վ��̳", str1, str2, "web");
//					���ضԻ���
					dialog_move.dismiss();
				}

			}
		});
	
//		Ϊ��������chat�󶨼�����
		k2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(txt1.getText().toString().equals("��������")){
					dialog_move.dismiss();
					Toast.makeText(CategoryContent.this, "���ڵ�ǰ���", Toast.LENGTH_SHORT).show();
				}else{
//					���������ַ������飬������ų���������ƺ͵�ǰ����Ӧ�����ݱ�֮���������ƺ����ݱ�����
					String[] str1 = {"��վ��̳","��Ϸ�˻�","�����˻�","�����˻�","����"};
					String[] str2 = {"web","game","bank","email","others"};
//					����movelistener()�����������ݱ���в�ѯ�޸ĵȲ���
					moveListener("��������", str1, str2, "chat");
//					���ضԻ���
					dialog_move.dismiss();
				}

			}
		});
//		Ϊ��Ϸ�˻�game�󶨼�����
		k3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(txt1.getText().toString().equals("��Ϸ�˻�")){
					dialog_move.dismiss();
					Toast.makeText(CategoryContent.this, "���ڵ�ǰ���", Toast.LENGTH_SHORT).show();
				}else{
//					���������ַ������飬������ų���������ƺ͵�ǰ����Ӧ�����ݱ�֮���������ƺ����ݱ�����
					String[] str1 = {"��վ��̳","��������","�����˻�","�����˻�","����"};
					String[] str2 = {"web","chat","bank","email","others"};
//					����movelistener()�����������ݱ���в�ѯ�޸ĵȲ���
					moveListener("��Ϸ�˻�", str1, str2, "game");
//					���ضԻ���
					dialog_move.dismiss();
				}

			}
		});
//		Ϊ�����˻�bank�󶨼�����
		k4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(txt1.getText().toString().equals("�����˻�")){
					dialog_move.dismiss();
					Toast.makeText(CategoryContent.this, "���ڵ�ǰ���", Toast.LENGTH_SHORT).show();
				}else{
//					���������ַ������飬������ų���������ƺ͵�ǰ����Ӧ�����ݱ�֮���������ƺ����ݱ�����
					String[] str1 = {"��վ��̳","��������","��Ϸ�˻�","�����˻�","����"};
					String[] str2 = {"web","chat","game","email","others"};
//					����movelistener()�����������ݱ���в�ѯ�޸ĵȲ���
					moveListener("�����˻�", str1, str2, "bank");
//					���ضԻ���
					dialog_move.dismiss();
				}

			}
		});
//		Ϊ�����˻�email�󶨼�����
		k5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(txt1.getText().toString().equals("�����˻�")){
					dialog_move.dismiss();
					Toast.makeText(CategoryContent.this, "���ڵ�ǰ���", Toast.LENGTH_SHORT).show();
				}else{
//					���������ַ������飬������ų���������ƺ͵�ǰ����Ӧ�����ݱ�֮���������ƺ����ݱ�����
					String[] str1 = {"��վ��̳","��������","��Ϸ�˻�","�����˻�","����"};
					String[] str2 = {"web","chat","game","bank","others"};
//					����movelistener()�����������ݱ���в�ѯ�޸ĵȲ���
					moveListener("�����˻�", str1, str2, "email");
//					���ضԻ���
					dialog_move.dismiss();
				}

			}
		});
//		Ϊ����others�󶨼�����
		k6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(txt1.getText().toString().equals("����")){
					dialog_move.dismiss();
					Toast.makeText(CategoryContent.this, "���ڵ�ǰ���", Toast.LENGTH_SHORT).show();
				}else{
//					���������ַ������飬������ų���������ƺ͵�ǰ����Ӧ�����ݱ�֮���������ƺ����ݱ�����
					String[] str1 = {"��վ��̳","��������","��Ϸ�˻�","�����˻�","�����˻�"};
					String[] str2 = {"web","chat","game","email","bank"};
//					����movelistener()�����������ݱ���в�ѯ�޸ĵȲ���
					moveListener("����", str1, str2, "others");
//					���ضԻ���
					dialog_move.dismiss();
				}

			}
		});
	}
//	�����ɾ���˺���Ϣ����ȷ�϶Ի���
	private void list_btn5(){
//		�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
        final Dialog dialog_delete = new Dialog(CategoryContent.this, R.style.MyDialog);
        //��������ContentView
        dialog_delete.setContentView(R.layout.dialog_list_delete);
        dialog_delete.show();
        
//        �õ�dialog������TextView��Id
		Button list_delete_cancle = (Button)dialog_delete.findViewById(R.id.list_delete_cancle);
		Button list_delete_ok = (Button)dialog_delete.findViewById(R.id.list_delete_ok);
//		Ϊȡ��ɾ���󶨼�����
		list_delete_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog_delete.dismiss();
			}
		});
//        Ϊȷ��ɾ����ť�󶨼�����
		list_delete_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] title = {"��վ��̳","��������","��Ϸ�˻�","�����˻�","�����˻�","����"};
				String[] tb_name = {"web","chat","game","bank","email","others"};
				for(int i = 0;i<title.length;i++){
					if(txt1.getText().toString().equals(title[i])){
						Cursor c = contentHelper.rawQueryInf(tb_name[i]);
						while(c.moveToNext()){
							int delete_txt1 = c.getInt(c.getColumnIndex("_id"));
							String delete_txt2 = c.getString(c.getColumnIndex("user"));
							String delete_txt3 = c.getString(c.getColumnIndex("remark"));
							if(txt3.getText().toString().equals(delete_txt2)&&txt5.getText().toString().equals(delete_txt3)){
								contentHelper.deleteRaw(tb_name[i], delete_txt1);
//							�жϸ����ݱ��Ƿ�Ϊ��	
								Cursor cursor = contentHelper.rawQueryCount(tb_name[i]);
								int count = 0;
								while(cursor.moveToNext()){
									count = cursor.getInt(0);
								}
								if(count == 0){
									category_content_rl2.setVisibility(View.VISIBLE);
									category_content_ls.setVisibility(View.GONE);
								}else{
									Cursor cu = contentHelper.rawQueryInf(tb_name[i]);
									inflateList(cu);
//									�ж��α��λ���Ƿ�ָ�����ݱ����һ��ĺ�һ��������ر��α�
									if(cu.isAfterLast()){
										cu.close();
									}
//									�ر��α�
									cursor.close();
								}
							}
						}
//						�ر��α�
						c.close();
						dialog_delete.dismiss();
					}
				}
				
				
			}
		});
	}
	/*
	 * �˴�����һ������
	 * ���ܣ��ṩ��dialog_move�и�������
	 * ������1����ǰ���������ƣ�"��վ��̳"�ȣ�2���ַ�������str1 3���ַ�������str2 4����ǰ������Ӧ�����ݱ�����
	*/
	private void moveListener(String kind,String[] str1,String[] str2,String tb_name){
		for(int i = 0;i<str1.length;i++){
			if(txt1.getText().toString().equals(str1[i])){
				ContentValues cv = new ContentValues();
				cv.put("kind", kind);
				cv.put("date", txt2.getText().toString());
				cv.put("user", txt3.getText().toString());
				cv.put("password", txt4.getText().toString());
				cv.put("remark", txt5.getText().toString());
				contentHelper.insert(cv, tb_name);
//				����like���ݱ��ж��Ƿ���ڸ������ݣ�������������kind�ֶ�
				Cursor c = contentHelper.rawQueryInf("like");
				while(c.moveToNext()){
					if(txt2.getText().toString().equals(c.getString(c.getColumnIndex("date")))){
						ContentValues cvl = new ContentValues();
						cvl.put("kind", kind);
						contentHelper.updateByDate("like", cvl, c.getString(c.getColumnIndex("date")));
					}
				}
//				�ر��α�
				c.close();
				
//				ɾ��ԭ������
				Cursor cu = contentHelper.rawQueryInf(str2[i]);
				while(cu.moveToNext()){
					if(txt2.getText().toString().equals(cu.getString(cu.getColumnIndex("date")))){
						contentHelper.deleteByDate(str2[i],cu.getString(cu.getColumnIndex("date")));
					}
				}
//				�ر��α�
				cu.close();
				
				Cursor curs = contentHelper.rawQueryInf(str2[i]);
				inflateList(curs);
//				�ж��α��λ���Ƿ�ָ�����ݱ����һ��ĺ�һ��������ر��α�
				if(curs.isAfterLast()){
					curs.close();
				}
//				�жϸ����ݱ��Ƿ�Ϊ��	
					Cursor cursor = contentHelper.rawQueryCount(str2[i]);
					int count = 0;
					while(cursor.moveToNext()){
						count = cursor.getInt(0);
					}
//					�ر��α�
					cursor.close();
					if(count == 0){
						category_content_rl2.setVisibility(View.VISIBLE);
						category_content_ls.setVisibility(View.GONE);
					}
			}
		}
	}
	
	
//	����һ��������ʵ�ְѲ�ѯ�����������ݰ󶨵�listview��
	private void inflateList(Cursor cursor){
		category_content_ls = (ListView)findViewById(R.id.category_content_ls);
		/*
		 * ���SimpleCursorAdapter��ʹ��SimpleCursorAdapter���뱣֤������_id,��������г���
		*/
		SimpleCursorAdapter sc = new SimpleCursorAdapter(CategoryContent.this, R.layout.category_ls_item, cursor, 
				new String[]{"kind","date","user","password","remark"},
				new int[]{R.id.category_ls_content_txt1,R.id.category_ls_content_txt2,R.id.category_ls_content_txt3,R.id.category_ls_content_txt4,R.id.category_ls_content_txt5},
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		category_content_ls.setAdapter(sc);
	}
	//Ϊ���ؼ����ü����������������ǰActivity
			public boolean onKeyDown(int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_BACK
				   && event.getAction() == KeyEvent.ACTION_DOWN){
					Intent intent = new Intent();
					intent.setClass(CategoryContent.this,CategoryActivity.class);
					CategoryContent.this.startActivity(intent);
					finish();
				  }
				return true;
				}
}
