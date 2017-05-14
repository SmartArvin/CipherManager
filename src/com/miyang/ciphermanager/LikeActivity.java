package com.miyang.ciphermanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class LikeActivity extends Activity{

	RelativeLayout always_rl2;
	Button always_back;
	ListView always_ls;
	DBHelper likeHelper;
	TextView txt1,txt2,txt3,txt4,txt5;
	Dialog dialog_like;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.like_activity);
		
//		�ѵ�ǰActivity���뵽CloseApplication��Activitylist��
		CloseApplication.getInstance().addActivity(this);
		
//		��ÿؼ���Id
		always_rl2 = (RelativeLayout)findViewById(R.id.always_rl2);
		always_back = (Button)findViewById(R.id.always_back);
		always_ls = (ListView)findViewById(R.id.always_ls);
		always_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LikeActivity.this,HomeActivity.class);
				LikeActivity.this.startActivity(intent);
				finish();
			}
		});
		/*
		 * �����ݱ��е����ݰ󶨵�listview��
		*/
//		����DBHelper���ݿ⹤����
		likeHelper = new DBHelper(getApplicationContext());
		Cursor cursor = likeHelper.rawQueryCount("like");
		int count = 0;
		while(cursor.moveToNext()){
			count = cursor.getInt(0);
		}
//		�ر��α�
		cursor.close();
		
		if(count == 0){
			always_rl2.setVisibility(1);
			always_ls.setVisibility(0);
		}else{
			Cursor c = likeHelper.rawQueryInf("like");
			inflateList(c);
//			�ж��α��λ���Ƿ�ָ�����ݱ����һ��ĺ�һ��������ر��α�
			if(c.isAfterLast()){
				c.close();
			}
		}
		
		
		always_ls.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				View v = always_ls.getChildAt(position); 
//				���listview��item���ڲ�view
				txt1 = (TextView)v.findViewById(R.id.category_ls_content_txt1);
				txt2 = (TextView)v.findViewById(R.id.category_ls_content_txt2);
				txt3 = (TextView)v.findViewById(R.id.category_ls_content_txt3);
                txt4 = (TextView)v.findViewById(R.id.category_ls_content_txt4);
                txt5 = (TextView)v.findViewById(R.id.category_ls_content_txt5);
                
				//�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
	            dialog_like = new Dialog(LikeActivity.this, R.style.MyDialog);
	            //��������ContentView
	            dialog_like.setContentView(R.layout.dialog_like);
	            dialog_like.show();
	            
//            �õ�dialog�в鿴��ȡ���ղص�Id
			Button like_btn1 = (Button)dialog_like.findViewById(R.id.dialog_like_btn1);
			Button like_btn2 = (Button)dialog_like.findViewById(R.id.dialog_like_btn2);
//			Ϊ�鿴�����󶨼�����
			like_btn1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog_like.dismiss();
//					�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
			        final Dialog dialog_like_check = new Dialog(LikeActivity.this, R.style.MyDialog);
			        //��������ContentView
			        dialog_like_check.setContentView(R.layout.dialog_list_check);
			        dialog_like_check.show();
			        
//			        �õ�dialog������TextView��Id
					TextView check_et1 = (TextView)dialog_like_check.findViewById(R.id.content_check_txt1);
					TextView check_et2 = (TextView)dialog_like_check.findViewById(R.id.content_check_txt2);
					TextView check_et3 = (TextView)dialog_like_check.findViewById(R.id.content_check_txt3);
			//��item�е����ݲ���dialog����TextView�ؼ���
					check_et1.setText(txt3.getText().toString()); 
					check_et2.setText(txt4.getText().toString());
					check_et3.setText(txt5.getText().toString());
			        
//			      ���ϵ�пؼ���Id
			        Button content_check_ok = (Button)dialog_like_check.findViewById(R.id.content_check_ok);
//			        Ϊȷ����ť�󶨼�����
			        content_check_ok.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog_like_check.dismiss();
						}
					});
				}
			});
			
			
			
//			Ϊȡ���ղذ󶨼�����
			like_btn2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog_like.dismiss();
//					�˴�ֱ��newһ��Dialog�����������ʵ������ʱ��������
			        final Dialog dialog_delete = new Dialog(LikeActivity.this, R.style.MyDialog);
			        //��������ContentView
			        dialog_delete.setContentView(R.layout.dialog_like_delete);
			        dialog_delete.show();
			        
//			        �õ�dialog������button��Id
					Button like_delete_cancle = (Button)dialog_delete.findViewById(R.id.like_delete_cancle);
					Button like_delete_ok = (Button)dialog_delete.findViewById(R.id.like_delete_ok);
//					Ϊȡ�������󶨼�����
					like_delete_cancle.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog_delete.dismiss();
						}
					});
//				Ϊȷ�������󶨼�����
					like_delete_ok.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Cursor c = likeHelper.rawQueryInf("like");
							while(c.moveToNext()){
								int delete_txt1 = c.getInt(c.getColumnIndex("_id"));
								String delete_txt2 = c.getString(c.getColumnIndex("date"));
								if(txt2.getText().toString().equals(delete_txt2)){
									likeHelper.deleteRaw("like", delete_txt1);
									Cursor cur = likeHelper.rawQueryInf("like");
									inflateList(cur);
//									�ж��α��λ���Ƿ�ָ�����ݱ����һ��ĺ�һ��������ر��α�
									if(cur.isAfterLast()){
										cur.close();
									}
									
//									�ж�like���ݱ��Ƿ�Ϊ��
									Cursor cursor = likeHelper.rawQueryCount("like");
									int count = 0;
									while(cursor.moveToNext()){
										count = cursor.getInt(0);
									}
//									�ر��α�
									cursor.close();
									if(count == 0){
										always_rl2.setVisibility(1);
										always_ls.setVisibility(0);
									}
								}
							}
//							�ر��α�
							c.close();
							dialog_delete.dismiss();
						}
						
					});
				}
			});
			}
		});
	}
	
	
//	����һ��������ʵ�ְѲ�ѯ�����������ݰ󶨵�listview��
	private void inflateList(Cursor cursor){
		always_ls = (ListView)findViewById(R.id.always_ls);
		/*
		 * ���SimpleCursorAdapter��ʹ��SimpleCursorAdapter���뱣֤������_id,��������г���
		*/
		SimpleCursorAdapter sc = new SimpleCursorAdapter(LikeActivity.this, R.layout.category_ls_item, cursor, 
				new String[]{"kind","date","user","password","remark"},
				new int[]{R.id.category_ls_content_txt1,R.id.category_ls_content_txt2,R.id.category_ls_content_txt3,R.id.category_ls_content_txt4,R.id.category_ls_content_txt5},
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		always_ls.setAdapter(sc);
	}
	

	//Ϊ���ؼ����ü����������������ǰActivity
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if(keyCode == KeyEvent.KEYCODE_BACK
			   && event.getAction() == KeyEvent.ACTION_DOWN){
				Intent intent = new Intent();
				intent.setClass(LikeActivity.this,HomeActivity.class);
				LikeActivity.this.startActivity(intent);
				finish();
			  }
			return true;
			} 
}
