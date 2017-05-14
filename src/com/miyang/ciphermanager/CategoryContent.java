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
		
//		把当前Activity加入到CloseApplication的Activitylist中
		CloseApplication.getInstance().addActivity(this);
		
		layout = this.getLayoutInflater().inflate(R.layout.category_ls_item, null);
		category_content_ls = (ListView)findViewById(R.id.category_content_ls);
//		获得控件的Id
		category_content_title = (TextView)findViewById(R.id.category_content_title);
		category_content_rl2 = (RelativeLayout)findViewById(R.id.category_content_rl2);
		category_content_back = (Button)findViewById(R.id.category_content_back);
		
//		得到传入的数据对象，将传入的数据设为标题
		bundle = getIntent().getExtras();
		String content_title = bundle.getString("key");
		category_content_title.setText(content_title);
//		为返回按钮绑定监听器
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
		 * 判断从CategoryActivity传入的对象名称
		*/
		 
		String[] title = {"网站论坛","聊天社区","游戏账户","银行账户","邮箱账户","其他"};
		String[] tb_name = {"web","chat","game","bank","email","others"};
//		通过判断bundle传入的数据，循环查询数据库中的系列数据表
		for(int i = 0; i<title.length ; i++){
//			创建数据库工具类DBHelper对象
			contentHelper = new DBHelper(getApplicationContext());
//			对比传入的对象和title类别数组
			if(bundle.getString("key").equals(title[i])){
				Cursor cursor = contentHelper.rawQueryCount(tb_name[i]);
				int count = 0 ;
				while(cursor.moveToNext()){
					count = cursor.getInt(0);
				}
//				关闭游标
				cursor.close();
				contentHelper.close();
				if(count==0){
					category_content_rl2.setVisibility(View.VISIBLE);
					category_content_ls.setVisibility(View.GONE);
				}else{
					try{
//						循环查询数据表
						Cursor cur = contentHelper.rawQueryInf(tb_name[i]);
//						调用函数将查询结果绑定到listview中
						inflateList(cur);
//						判断游标的位置是否指向数据表最后一项的后一项，如果是则关闭游标
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
//		为listview的item绑定监听器
		category_content_ls.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
//				获得当前item的内容布局
				View v = category_content_ls.getChildAt(position); 
//				获得item的内部view
				txt1 = (TextView)v.findViewById(R.id.category_ls_content_txt1);
				txt2 = (TextView)v.findViewById(R.id.category_ls_content_txt2);
				txt3 = (TextView)v.findViewById(R.id.category_ls_content_txt3);
                txt4 = (TextView)v.findViewById(R.id.category_ls_content_txt4);
                txt5 = (TextView)v.findViewById(R.id.category_ls_content_txt5);
                
				//此处直接new一个Dialog对象出来，在实例化的时候传入主题
	            final Dialog dialog_list = new Dialog(CategoryContent.this, R.style.MyDialog);
	            //设置它的ContentView
	            dialog_list.setContentView(R.layout.dialog_content_list);
	            dialog_list.show();
	            
//            得到dialog中查看、修改、移动、收藏、删除的Id
			Button list_btn1 = (Button)dialog_list.findViewById(R.id.content_list_btn1);
			Button list_btn2 = (Button)dialog_list.findViewById(R.id.content_list_btn2);
			Button list_btn3 = (Button)dialog_list.findViewById(R.id.content_list_btn3);
			Button list_btn4 = (Button)dialog_list.findViewById(R.id.content_list_btn4);
			Button list_btn5 = (Button)dialog_list.findViewById(R.id.content_list_btn5);
			
//			为查看按键绑定监听器
			list_btn1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog_list.dismiss();
//					调用list_btn1的监听器函数
					list_btn1();
				}
			});
			
//			为修改按键绑定监听器
			list_btn2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog_list.dismiss();
//					调用list_btn2的监听器函数
					list_btn2();
				}
			});
			
//			为移动按键绑定监听器
			list_btn3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog_list.dismiss();
//					调用list_btn3的监听器函数
					list_btn3();
				}
			});
			
//			为收藏按键绑定监听器,需要判断是否已经收藏过
			list_btn4.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					int count = 0;
					ContentValues cv = new ContentValues();
//					查询数据表的全部信息
					Cursor cu = contentHelper.rawQueryInf("like");
//					查询like数据表的数据条数
					Cursor c = contentHelper.rawQueryCount("like");
					while(c.moveToNext()){
						count = c.getInt(0);
				/*
				 * 判断like数据表是否是空表，为空则执行数据插入操作
				 * 不为空，则检索数据表
				*/
						if(count==0){
								cv.put("kind", txt1.getText().toString());
								cv.put("date", txt2.getText().toString());
								cv.put("user", txt3.getText().toString());
								cv.put("password", txt4.getText().toString());
								cv.put("remark", txt5.getText().toString());
								contentHelper.insert(cv, "like");
//								隐藏dialog窗口
									dialog_list.dismiss();
									Toast.makeText(CategoryContent.this, "完成收藏！", Toast.LENGTH_SHORT).show();
						}
						else{
							ArrayList<String> al = new ArrayList<String>();
							String like_txt2 = null;
//							检索like数据表元素
							while(cu.moveToNext()){
								like_txt2 = cu.getString(cu.getColumnIndex("date"));
//								将like数据表中的date字段数据全部检索并存放在al数组中
								al.add(like_txt2);
							}
							int j = 0 ;
//							循环检索al数组元素
							while(j<al.size()){
//								如果数组元素与item中的txt2内容一致，则提示该账号已经收藏过，不进行数据插入
									if(txt2.getText().toString().equals(al.get(j))){
										dialog_list.dismiss();
										Toast.makeText(CategoryContent.this, "已收藏", Toast.LENGTH_SHORT).show();
										break;
									}
//									如果数组元素与item中的txt2内容不一致，则将数组下标加1继续检索数组元素
									else{
										j++;
									}
								}
//							判断数组元素是否全部检索完毕，如果检索完毕（即数组所有元素与item中的txt2内容不一致）
//							，此时的j=al.size(),然后执行插入数据
							if(j>=al.size()){
								cv.put("kind", txt1.getText().toString());
								cv.put("date", txt2.getText().toString());
								cv.put("user", txt3.getText().toString());
								cv.put("password", txt4.getText().toString());
								cv.put("remark", txt5.getText().toString());
								contentHelper.insert(cv, "like");
							
//						隐藏dialog窗口
							dialog_list.dismiss();
							Toast.makeText(CategoryContent.this, "完成收藏！", Toast.LENGTH_SHORT).show();
							}
						}
								
					 }	
//					关闭游标
					cu.close();
					c.close();
				 }
			});
//			为删除按键绑定监听器
			list_btn5.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog_list.dismiss();
//					调用list_btn5的监听器函数
					list_btn5();
				}
			});
			}
		});
		contentHelper.close();
	}
	/*
	 * 创建以上四个Button的监听器类
	 * list_btn1()
	 * list_btn2()
	 * list_btn3()
	 * list_btn4()
	 * list_btn5()
	 * 
	*/
	
//	这个是查看账号信息
	private void list_btn1(){
//		此处直接new一个Dialog对象出来，在实例化的时候传入主题
        final Dialog dialog_check = new Dialog(CategoryContent.this, R.style.MyDialog);
        //设置它的ContentView
        dialog_check.setContentView(R.layout.dialog_list_check);
        dialog_check.show();
        
//        得到dialog中三个TextView的Id
		TextView check_et1 = (TextView)dialog_check.findViewById(R.id.content_check_txt1);
		TextView check_et2 = (TextView)dialog_check.findViewById(R.id.content_check_txt2);
		TextView check_et3 = (TextView)dialog_check.findViewById(R.id.content_check_txt3);
//将item中的数据插入dialog三个TextView控件中
		check_et1.setText(txt3.getText().toString()); 
		check_et2.setText(txt4.getText().toString());
		check_et3.setText(txt5.getText().toString());
        
//      获得系列控件的Id
        Button content_check_ok = (Button)dialog_check.findViewById(R.id.content_check_ok);
//        为确定按钮绑定监听器
        content_check_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog_check.dismiss();
			}
		});
	}
//	这个是修改账号信息
	private void list_btn2(){
//		此处直接new一个Dialog对象出来，在实例化的时候传入主题
        final Dialog dialog_change = new Dialog(CategoryContent.this, R.style.MyDialog);
        //设置它的ContentView
        dialog_change.setContentView(R.layout.dialog_list_change);
        dialog_change.show();
        
//        得到dialog中四个TextView的Id
        final EditText list_change_et1 = (EditText)dialog_change.findViewById(R.id.list_change_et1);
        final EditText list_change_et2 = (EditText)dialog_change.findViewById(R.id.list_change_et2);
        final EditText list_change_et3 = (EditText)dialog_change.findViewById(R.id.list_change_et3);
        final EditText list_change_et4 = (EditText)dialog_change.findViewById(R.id.list_change_et4);
//将item中的数据插入dialog三个TextView控件中
        list_change_et1.setText(txt3.getText().toString()); 
        list_change_et2.setText(txt4.getText().toString());
        list_change_et4.setText(txt5.getText().toString());
        
//      获得系列控件的Id
        Button list_change_cancle = (Button)dialog_change.findViewById(R.id.list_change_cancle);
        Button list_change_ok = (Button)dialog_change.findViewById(R.id.list_change_ok);
//        为取消修改按钮绑定监听器
        list_change_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog_change.dismiss();
			}
		});
//      为确定修改按钮绑定监听器
      list_change_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*
				 * 执行更新数据表的操作
				*/
				String[] title = {"网站论坛","聊天社区","游戏账户","银行账户","邮箱账户","其他"};
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
//							循环查询数据表
							Cursor cur = contentHelper.rawQueryInf(tb_name[i]);
//							调用函数将查询结果绑定到listview中
							inflateList(cur);
//							判断游标的位置是否指向数据表最后一项的后一项，如果是则关闭游标
							if(cur.isAfterLast()){
								cur.close();
							}
							dialog_change.dismiss();
						}else{
							Toast.makeText(CategoryContent.this, "请确认密码！", Toast.LENGTH_SHORT).show();
						}

					}
				}
			}
		});
	}
//	这个是移动账号信息，dialog_classify对话框
	private void list_btn3(){
//		此处直接new一个Dialog对象出来，在实例化的时候传入主题
        dialog_move = new Dialog(CategoryContent.this, R.style.MyDialog);
        //设置它的ContentView
        dialog_move.setContentView(R.layout.dialog_classify);
        dialog_move.show();
        
//        得到dialog中六个button的Id
		Button k1 = (Button)dialog_move.findViewById(R.id.k1);//web
		Button k2 = (Button)dialog_move.findViewById(R.id.k2);//chat
		Button k3 = (Button)dialog_move.findViewById(R.id.k3);//game
		Button k4 = (Button)dialog_move.findViewById(R.id.k4);//bank
		Button k5 = (Button)dialog_move.findViewById(R.id.k5);//email
		Button k6 = (Button)dialog_move.findViewById(R.id.k6);//others
		
//		为网站论坛web绑定监听器
		k1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(txt1.getText().toString().equals("网站论坛")){
					dialog_move.dismiss();
					Toast.makeText(CategoryContent.this, "已在当前类别！", Toast.LENGTH_SHORT).show();
				}else{
//					定义两个字符串数组，用来存放除本类别名称和当前类别对应的数据表之外的类别名称和数据表名称
					String[] str1 = {"聊天社区","游戏账户","银行账户","邮箱账户","其他"};
					String[] str2 = {"chat","game","bank","email","others"};
//					调用movelistener()函数，对数据表进行查询修改等操作
					moveListener("网站论坛", str1, str2, "web");
//					隐藏对话框
					dialog_move.dismiss();
				}

			}
		});
	
//		为聊天社区chat绑定监听器
		k2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(txt1.getText().toString().equals("聊天社区")){
					dialog_move.dismiss();
					Toast.makeText(CategoryContent.this, "已在当前类别！", Toast.LENGTH_SHORT).show();
				}else{
//					定义两个字符串数组，用来存放除本类别名称和当前类别对应的数据表之外的类别名称和数据表名称
					String[] str1 = {"网站论坛","游戏账户","银行账户","邮箱账户","其他"};
					String[] str2 = {"web","game","bank","email","others"};
//					调用movelistener()函数，对数据表进行查询修改等操作
					moveListener("聊天社区", str1, str2, "chat");
//					隐藏对话框
					dialog_move.dismiss();
				}

			}
		});
//		为游戏账户game绑定监听器
		k3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(txt1.getText().toString().equals("游戏账户")){
					dialog_move.dismiss();
					Toast.makeText(CategoryContent.this, "已在当前类别！", Toast.LENGTH_SHORT).show();
				}else{
//					定义两个字符串数组，用来存放除本类别名称和当前类别对应的数据表之外的类别名称和数据表名称
					String[] str1 = {"网站论坛","聊天社区","银行账户","邮箱账户","其他"};
					String[] str2 = {"web","chat","bank","email","others"};
//					调用movelistener()函数，对数据表进行查询修改等操作
					moveListener("游戏账户", str1, str2, "game");
//					隐藏对话框
					dialog_move.dismiss();
				}

			}
		});
//		为银行账户bank绑定监听器
		k4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(txt1.getText().toString().equals("银行账户")){
					dialog_move.dismiss();
					Toast.makeText(CategoryContent.this, "已在当前类别！", Toast.LENGTH_SHORT).show();
				}else{
//					定义两个字符串数组，用来存放除本类别名称和当前类别对应的数据表之外的类别名称和数据表名称
					String[] str1 = {"网站论坛","聊天社区","游戏账户","邮箱账户","其他"};
					String[] str2 = {"web","chat","game","email","others"};
//					调用movelistener()函数，对数据表进行查询修改等操作
					moveListener("银行账户", str1, str2, "bank");
//					隐藏对话框
					dialog_move.dismiss();
				}

			}
		});
//		为邮箱账户email绑定监听器
		k5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(txt1.getText().toString().equals("邮箱账户")){
					dialog_move.dismiss();
					Toast.makeText(CategoryContent.this, "已在当前类别！", Toast.LENGTH_SHORT).show();
				}else{
//					定义两个字符串数组，用来存放除本类别名称和当前类别对应的数据表之外的类别名称和数据表名称
					String[] str1 = {"网站论坛","聊天社区","游戏账户","银行账户","其他"};
					String[] str2 = {"web","chat","game","bank","others"};
//					调用movelistener()函数，对数据表进行查询修改等操作
					moveListener("邮箱账户", str1, str2, "email");
//					隐藏对话框
					dialog_move.dismiss();
				}

			}
		});
//		为其他others绑定监听器
		k6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(txt1.getText().toString().equals("其他")){
					dialog_move.dismiss();
					Toast.makeText(CategoryContent.this, "已在当前类别！", Toast.LENGTH_SHORT).show();
				}else{
//					定义两个字符串数组，用来存放除本类别名称和当前类别对应的数据表之外的类别名称和数据表名称
					String[] str1 = {"网站论坛","聊天社区","游戏账户","邮箱账户","银行账户"};
					String[] str2 = {"web","chat","game","email","bank"};
//					调用movelistener()函数，对数据表进行查询修改等操作
					moveListener("其他", str1, str2, "others");
//					隐藏对话框
					dialog_move.dismiss();
				}

			}
		});
	}
//	这个是删除账号信息，加确认对话框
	private void list_btn5(){
//		此处直接new一个Dialog对象出来，在实例化的时候传入主题
        final Dialog dialog_delete = new Dialog(CategoryContent.this, R.style.MyDialog);
        //设置它的ContentView
        dialog_delete.setContentView(R.layout.dialog_list_delete);
        dialog_delete.show();
        
//        得到dialog中三个TextView的Id
		Button list_delete_cancle = (Button)dialog_delete.findViewById(R.id.list_delete_cancle);
		Button list_delete_ok = (Button)dialog_delete.findViewById(R.id.list_delete_ok);
//		为取消删除绑定监听器
		list_delete_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog_delete.dismiss();
			}
		});
//        为确定删除按钮绑定监听器
		list_delete_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] title = {"网站论坛","聊天社区","游戏账户","银行账户","邮箱账户","其他"};
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
//							判断该数据表是否为空	
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
//									判断游标的位置是否指向数据表最后一项的后一项，如果是则关闭游标
									if(cu.isAfterLast()){
										cu.close();
									}
//									关闭游标
									cursor.close();
								}
							}
						}
//						关闭游标
						c.close();
						dialog_delete.dismiss();
					}
				}
				
				
			}
		});
	}
	/*
	 * 此处定义一个函数
	 * 功能：提供给dialog_move中各键调用
	 * 参数：1、当前按键的名称（"网站论坛"等）2、字符串数组str1 3、字符串数组str2 4、当前按键对应的数据表名称
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
//				检索like数据表，判断是否存在该条数据，如果存在则更新kind字段
				Cursor c = contentHelper.rawQueryInf("like");
				while(c.moveToNext()){
					if(txt2.getText().toString().equals(c.getString(c.getColumnIndex("date")))){
						ContentValues cvl = new ContentValues();
						cvl.put("kind", kind);
						contentHelper.updateByDate("like", cvl, c.getString(c.getColumnIndex("date")));
					}
				}
//				关闭游标
				c.close();
				
//				删除原表数据
				Cursor cu = contentHelper.rawQueryInf(str2[i]);
				while(cu.moveToNext()){
					if(txt2.getText().toString().equals(cu.getString(cu.getColumnIndex("date")))){
						contentHelper.deleteByDate(str2[i],cu.getString(cu.getColumnIndex("date")));
					}
				}
//				关闭游标
				cu.close();
				
				Cursor curs = contentHelper.rawQueryInf(str2[i]);
				inflateList(curs);
//				判断游标的位置是否指向数据表最后一项的后一项，如果是则关闭游标
				if(curs.isAfterLast()){
					curs.close();
				}
//				判断该数据表是否为空	
					Cursor cursor = contentHelper.rawQueryCount(str2[i]);
					int count = 0;
					while(cursor.moveToNext()){
						count = cursor.getInt(0);
					}
//					关闭游标
					cursor.close();
					if(count == 0){
						category_content_rl2.setVisibility(View.VISIBLE);
						category_content_ls.setVisibility(View.GONE);
					}
			}
		}
	}
	
	
//	定义一个函数，实现把查询到的数据内容绑定到listview中
	private void inflateList(Cursor cursor){
		category_content_ls = (ListView)findViewById(R.id.category_content_ls);
		/*
		 * 填充SimpleCursorAdapter，使用SimpleCursorAdapter必须保证主键是_id,否则会运行出错
		*/
		SimpleCursorAdapter sc = new SimpleCursorAdapter(CategoryContent.this, R.layout.category_ls_item, cursor, 
				new String[]{"kind","date","user","password","remark"},
				new int[]{R.id.category_ls_content_txt1,R.id.category_ls_content_txt2,R.id.category_ls_content_txt3,R.id.category_ls_content_txt4,R.id.category_ls_content_txt5},
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		category_content_ls.setAdapter(sc);
	}
	//为返回键设置监听器，点击结束当前Activity
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
