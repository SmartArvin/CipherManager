package com.miyang.ciphermanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class SearchActivity extends Activity{

	EditText search_txt;
	Button search_back,search_ok;
	ListView search_ls;
	DBHelper searchHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_activity);
//		创建DBHelper数据库工具类
		searchHelper = new DBHelper(getApplicationContext());
//		点击搜索之前清空search数据表
		searchHelper.deleteTb("search");
//		把当前Activity加入到CloseApplication的Activitylist中
		CloseApplication.getInstance().addActivity(this);
		
//		获得控件的Id
		search_txt = (EditText)findViewById(R.id.search_txt);
		search_back = (Button)findViewById(R.id.search_back);
		search_ls = (ListView)findViewById(R.id.search_ls);
		search_ok = (Button)findViewById(R.id.search_ok);
//		绑定监听器
		search_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String txt = search_txt.getText().toString();
//				插入数据之前清空数据表
				searchHelper.deleteTb("search");
//				判断txt是否为空，如果不为空则执行相关操作，如果为空则弹出对话框提示输入关键字
				if(!txt.isEmpty()){
//					定义一个数组用于存放系列数据表
					String[] tb_name = {"web","chat","game","bank","email","others"}; 
//					循环查询数据表，并将查询结果插入到search数据表中
					for(int i = 0;i < tb_name.length ; i++){
//						循环查询数据表
						Cursor cu = searchHelper.rawQueryLike(tb_name[i],txt);
//						创建contentvalues对象，封装记录信息
						ContentValues c = new ContentValues();
						while(cu.moveToNext()){
							c.put("kind", cu.getString(cu.getColumnIndex("kind")));
							c.put("date",cu.getString(cu.getColumnIndex("date")));
							c.put("user", cu.getString(cu.getColumnIndex("user")));
							c.put("password",cu.getString(cu.getColumnIndex("password")));
							c.put("remark",cu.getString(cu.getColumnIndex("remark")));
//							向search数据表中插入数据
							searchHelper.insert(c, "search");
						}
//						关闭游标
						cu.close();
					}
					
					Cursor cu = searchHelper.rawQueryCount("search");
					int count = 0;
				    while(cu.moveToNext())
				    {
				        count = cu.getInt(0);
				    }
				    if(count == 0){
				    	Toast.makeText(SearchActivity.this, "相关记录不存在！", Toast.LENGTH_SHORT).show();
				    }else{
//						查询search数据表并将数据表内容插入到listview中
						Cursor cur = searchHelper.rawQueryInf("search");
							inflateList(cur);
				    }
//				    关闭游标
				    cu.close();
				}else{
					search_ls.setVisibility(View.GONE);
					Toast.makeText(SearchActivity.this, "请输入查询关键字！", Toast.LENGTH_SHORT).show();
				}
			}

		});
//		为顶部返回按钮绑定监听器
		search_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SearchActivity.this,HomeActivity.class);
				SearchActivity.this.startActivity(intent);
				finish();
			}
		});
	}
//	定义一个函数用来将查询到的数据绑定到listview中
	private void inflateList(Cursor cursor){
		search_ls = (ListView)findViewById(R.id.search_ls);
		/*
		 * 填充SimpleCursorAdapter，使用SimpleCursorAdapter必须保证主键是_id,否则会运行出错
		*/
		SimpleCursorAdapter sc = new SimpleCursorAdapter(SearchActivity.this, R.layout.category_ls_item, cursor, 
				new String[]{"kind","date","user","password","remark"},
				new int[]{R.id.category_ls_content_txt1,R.id.category_ls_content_txt2,R.id.category_ls_content_txt3,R.id.category_ls_content_txt4,R.id.category_ls_content_txt5},
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		search_ls.setAdapter(sc);
	}
	
	//为返回键设置监听器，点击结束当前Activity
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK
		   && event.getAction() == KeyEvent.ACTION_DOWN){
			Intent intent = new Intent();
			intent.setClass(SearchActivity.this,HomeActivity.class);
			SearchActivity.this.startActivity(intent);
			finish();
		  }
		return true;
		} 

}
