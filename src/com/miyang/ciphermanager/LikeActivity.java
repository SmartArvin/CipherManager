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
		
//		把当前Activity加入到CloseApplication的Activitylist中
		CloseApplication.getInstance().addActivity(this);
		
//		获得控件的Id
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
		 * 将数据表中的数据绑定到listview中
		*/
//		创建DBHelper数据库工具类
		likeHelper = new DBHelper(getApplicationContext());
		Cursor cursor = likeHelper.rawQueryCount("like");
		int count = 0;
		while(cursor.moveToNext()){
			count = cursor.getInt(0);
		}
//		关闭游标
		cursor.close();
		
		if(count == 0){
			always_rl2.setVisibility(1);
			always_ls.setVisibility(0);
		}else{
			Cursor c = likeHelper.rawQueryInf("like");
			inflateList(c);
//			判断游标的位置是否指向数据表最后一项的后一项，如果是则关闭游标
			if(c.isAfterLast()){
				c.close();
			}
		}
		
		
		always_ls.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				View v = always_ls.getChildAt(position); 
//				获得listview中item的内部view
				txt1 = (TextView)v.findViewById(R.id.category_ls_content_txt1);
				txt2 = (TextView)v.findViewById(R.id.category_ls_content_txt2);
				txt3 = (TextView)v.findViewById(R.id.category_ls_content_txt3);
                txt4 = (TextView)v.findViewById(R.id.category_ls_content_txt4);
                txt5 = (TextView)v.findViewById(R.id.category_ls_content_txt5);
                
				//此处直接new一个Dialog对象出来，在实例化的时候传入主题
	            dialog_like = new Dialog(LikeActivity.this, R.style.MyDialog);
	            //设置它的ContentView
	            dialog_like.setContentView(R.layout.dialog_like);
	            dialog_like.show();
	            
//            得到dialog中查看和取消收藏的Id
			Button like_btn1 = (Button)dialog_like.findViewById(R.id.dialog_like_btn1);
			Button like_btn2 = (Button)dialog_like.findViewById(R.id.dialog_like_btn2);
//			为查看按键绑定监听器
			like_btn1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog_like.dismiss();
//					此处直接new一个Dialog对象出来，在实例化的时候传入主题
			        final Dialog dialog_like_check = new Dialog(LikeActivity.this, R.style.MyDialog);
			        //设置它的ContentView
			        dialog_like_check.setContentView(R.layout.dialog_list_check);
			        dialog_like_check.show();
			        
//			        得到dialog中三个TextView的Id
					TextView check_et1 = (TextView)dialog_like_check.findViewById(R.id.content_check_txt1);
					TextView check_et2 = (TextView)dialog_like_check.findViewById(R.id.content_check_txt2);
					TextView check_et3 = (TextView)dialog_like_check.findViewById(R.id.content_check_txt3);
			//将item中的数据插入dialog三个TextView控件中
					check_et1.setText(txt3.getText().toString()); 
					check_et2.setText(txt4.getText().toString());
					check_et3.setText(txt5.getText().toString());
			        
//			      获得系列控件的Id
			        Button content_check_ok = (Button)dialog_like_check.findViewById(R.id.content_check_ok);
//			        为确定按钮绑定监听器
			        content_check_ok.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog_like_check.dismiss();
						}
					});
				}
			});
			
			
			
//			为取消收藏绑定监听器
			like_btn2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog_like.dismiss();
//					此处直接new一个Dialog对象出来，在实例化的时候传入主题
			        final Dialog dialog_delete = new Dialog(LikeActivity.this, R.style.MyDialog);
			        //设置它的ContentView
			        dialog_delete.setContentView(R.layout.dialog_like_delete);
			        dialog_delete.show();
			        
//			        得到dialog中两个button的Id
					Button like_delete_cancle = (Button)dialog_delete.findViewById(R.id.like_delete_cancle);
					Button like_delete_ok = (Button)dialog_delete.findViewById(R.id.like_delete_ok);
//					为取消按键绑定监听器
					like_delete_cancle.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog_delete.dismiss();
						}
					});
//				为确定按键绑定监听器
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
//									判断游标的位置是否指向数据表最后一项的后一项，如果是则关闭游标
									if(cur.isAfterLast()){
										cur.close();
									}
									
//									判断like数据表是否为空
									Cursor cursor = likeHelper.rawQueryCount("like");
									int count = 0;
									while(cursor.moveToNext()){
										count = cursor.getInt(0);
									}
//									关闭游标
									cursor.close();
									if(count == 0){
										always_rl2.setVisibility(1);
										always_ls.setVisibility(0);
									}
								}
							}
//							关闭游标
							c.close();
							dialog_delete.dismiss();
						}
						
					});
				}
			});
			}
		});
	}
	
	
//	定义一个函数，实现把查询到的数据内容绑定到listview中
	private void inflateList(Cursor cursor){
		always_ls = (ListView)findViewById(R.id.always_ls);
		/*
		 * 填充SimpleCursorAdapter，使用SimpleCursorAdapter必须保证主键是_id,否则会运行出错
		*/
		SimpleCursorAdapter sc = new SimpleCursorAdapter(LikeActivity.this, R.layout.category_ls_item, cursor, 
				new String[]{"kind","date","user","password","remark"},
				new int[]{R.id.category_ls_content_txt1,R.id.category_ls_content_txt2,R.id.category_ls_content_txt3,R.id.category_ls_content_txt4,R.id.category_ls_content_txt5},
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		always_ls.setAdapter(sc);
	}
	

	//为返回键设置监听器，点击结束当前Activity
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
