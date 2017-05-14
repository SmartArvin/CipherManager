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
		
//		把当前Activity加入到CloseApplication的Activitylist中
		CloseApplication.getInstance().addActivity(this);
		
//		获得控件的Id
		safe_back = (Button)findViewById(R.id.safe_back);
		safe_delete = (Button)findViewById(R.id.safe_delete);
		safe_backup = (Button)findViewById(R.id.safe_backup);
		safe_return = (Button)findViewById(R.id.safe_return);
		safe_derive = (Button)findViewById(R.id.safe_derive);
		safe_change = (Button)findViewById(R.id.safe_change);
//		为控件绑定监听器
		safe_back.setOnClickListener(new safeListener());
		safe_delete.setOnClickListener(new safeListener());
		safe_backup.setOnClickListener(new safeListener());
		safe_return.setOnClickListener(new safeListener());
		safe_derive.setOnClickListener(new safeListener());
		safe_change.setOnClickListener(new safeListener());
	}
//	创建监听器类
	class safeListener implements OnClickListener{
//		创建DBHelper数据库工具类
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
			//此处直接new一个Dialog对象出来，在实例化的时候传入主题
            final Dialog dialog_delete = new Dialog(SafeActivity.this, R.style.MyDialog);
            //设置它的ContentView
            dialog_delete.setContentView(R.layout.dialog_safe_delete);
            dialog_delete.show();
            
//          获得控件的Id
            Button safe_delete_cancle = (Button)dialog_delete.findViewById(R.id.safe_delete_cancle);
            Button safe_delete_ok = (Button)dialog_delete.findViewById(R.id.safe_delete_ok);
//            为取消按钮绑定监听器
            safe_delete_cancle.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog_delete.dismiss();
				}
			});
//            为确定删除按钮绑定监听器
            safe_delete_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
//					定义一个数组用于存放系列数据表
					String[] tb_name = {"web","chat","game","bank","email","others","search","like"}; 
//					循环查询数据表，并将查询结果插入到search数据表中
					for(int i = 0;i < tb_name.length ; i++){
//						清空全部数据表
						safeHelper.deleteTb(tb_name[i]);
					}
					dialog_delete.dismiss();
				}
			});
			break;
		case R.id.safe_backup:
//			备份之前删除原来的备份数据
			safeHelper.deleteTb("backup");
			safeHelper.deleteTb("backup_like");
			
			String[] tb_name = {"web","chat","game","bank","email","others"};
//			循环查询数据库中的系列数据表
			for(int i = 0; i<tb_name.length ; i++){
//					循环查询数据表
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
//					关闭游标
					cur.close();
			}
		//			查询like数据表
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
//					关闭游标
					cu.close();
//					完成备份后的提示信息
			Toast.makeText(SafeActivity.this, "数据备份完成！", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.safe_return:
			//此处直接new一个Dialog对象出来，在实例化的时候传入主题
            final Dialog dialog_return = new Dialog(SafeActivity.this, R.style.MyDialog);
            //设置它的ContentView
            dialog_return.setContentView(R.layout.dialog_safe_return);
            dialog_return.show();
            
//          获得控件的Id
            Button safe_return_cancle = (Button)dialog_return.findViewById(R.id.safe_return_cancle);
            Button safe_return_ok = (Button)dialog_return.findViewById(R.id.safe_return_ok);
//            为取消按钮绑定监听器
            safe_return_cancle.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog_return.dismiss();
				}
			});
//            为确定还原按钮绑定监听器
            safe_return_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String[] title = {"网站论坛","聊天社区","游戏账户","银行账户","邮箱账户","其他"};
//					定义一个数组用于存放系列数据表
					String[] tb_name = {"web","chat","game","bank","email","others"}; 
//					还原数据之前先清空当前数据表信息，避免数据表中信息重复
					for(int j = 0;j<tb_name.length; j++){
						safeHelper.deleteTb(tb_name[j]);
					}
//					清空收藏数据表的信息
					safeHelper.deleteTb("like");
//						查询backup数据表
						Cursor c = safeHelper.rawQueryInf("backup");
						while(c.moveToNext()){
							String txt = c.getString(c.getColumnIndex("kind"));
							for(int i = 0;i < title.length ; i++){
//								循环查询数据表，并将查询结果插入到对应的数据表中
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
//						关闭游标
						c.close();
						
//					查询backup_like数据表，并把back_like数据表中的数据插入到like表中
					Cursor cu = safeHelper.rawQueryInf("backup_like");
					ContentValues cvl = new ContentValues();
					while(cu.moveToNext()){
						cvl.put("kind", cu.getString(cu.getColumnIndex("kind")));
						cvl.put("date", cu.getString(cu.getColumnIndex("date")));
						cvl.put("user", cu.getString(cu.getColumnIndex("user")));
						cvl.put("password", cu.getString(cu.getColumnIndex("password")));
						cvl.put("remark", cu.getString(cu.getColumnIndex("remark")));
//						向like数据表中插入数据
						safeHelper.insert(cvl, "like");
					}
//					关闭游标
					cu.close();
					
//					隐藏dialog
					dialog_return.dismiss();
					Toast.makeText(SafeActivity.this, "数据已成功还原！", Toast.LENGTH_SHORT).show();
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
						String txt = txt1+"\t"+"日期:"+txt2+"\t"+"用户名:"+txt3+"\t"+"密码："+txt4+"\t"+"备注："+txt5+"\r\n";
						fos.write(txt.getBytes("UTF-8"));
					}
					if(cursor.isAfterLast()){
						cursor.close();
					}
				}
				fos.close();
				Toast.makeText(SafeActivity.this, "导出文件为SdCard目录password.txt!", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(SafeActivity.this, "SD卡错误!", Toast.LENGTH_SHORT).show();
				}
			}catch(Exception ex){
				Toast.makeText(SafeActivity.this, "导出失败！", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.safe_change:
			//此处直接new一个Dialog对象出来，在实例化的时候传入主题
            final Dialog dialog_change = new Dialog(SafeActivity.this, R.style.MyDialog);
            //设置它的ContentView
            dialog_change.setContentView(R.layout.dialog_safe_change);
            dialog_change.show();
            
            Cursor curs = safeHelper.rawQueryCount("login");
			int cn = 0;
			while(curs.moveToNext()){
				cn = curs.getInt(0);
			}
//			关闭游标
			curs.close();
			
//			如果login数据表为空，则意味着用户选择的是无密模式
			if(cn == 0){
				dialog_change.dismiss();
				Toast.makeText(SafeActivity.this, "无密码信息，请选择加密模式！", Toast.LENGTH_SHORT).show();
			}
//			如果login数据表不为空，则意味着用户已经选择了加密模式，允许修改登陆密码
			else{
//	          获得控件的Id
	            Button safe_change_cancle = (Button)dialog_change.findViewById(R.id.safe_change_cancle);
	            Button safe_change_ok = (Button)dialog_change.findViewById(R.id.safe_change_ok);
//	            为取消按钮绑定监听器
	            safe_change_cancle.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog_change.dismiss();
					}
				});
//	            为确定修改按钮绑定监听器
	            safe_change_ok.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
//						得到dialog中三个EditText编辑框的Id
						EditText safe_change_et1 = (EditText)dialog_change.findViewById(R.id.safe_change_et1);
						EditText safe_change_et2 = (EditText)dialog_change.findViewById(R.id.safe_change_et2);
						EditText safe_change_et3 = (EditText)dialog_change.findViewById(R.id.safe_change_et3);
//						获得以上三个编辑框的内容
						String safe_change_e1 = safe_change_et1.getText().toString();
						String safe_change_e2 = safe_change_et2.getText().toString();
						String safe_change_e3 = safe_change_et3.getText().toString();
						Cursor c = safeHelper.rawQueryInf("login");
						while(c.moveToNext()){
							String temp = c.getString(c.getColumnIndex("key1"));
							if(safe_change_e1.equals(temp)){
								if(safe_change_e2.isEmpty()&&safe_change_e3.isEmpty()){
//									如果修改项为空则清除login数据表的数据,作无密码处理
									safeHelper.deleteTb("login");
									dialog_change.dismiss();
									Toast.makeText(SafeActivity.this, "密码已清空！", Toast.LENGTH_SHORT).show();
								}else{
									if(safe_change_e2.equals(safe_change_e3)){
//										更新密码之前先清除login数据表的数据
										safeHelper.deleteTb("login");
//										创建contentvalues对象，封装记录信息
										ContentValues cv = new ContentValues();
										cv.put("key1", safe_change_e2);
//										插入新密码
										safeHelper.insert(cv, "login");
										dialog_change.dismiss();
										Toast.makeText(SafeActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
									}else{
										Toast.makeText(SafeActivity.this, "请确认输入密码正确！", Toast.LENGTH_SHORT).show();
									}
								}
							}else{
								dialog_change.dismiss();
								Toast.makeText(SafeActivity.this, "对不起，你没有修改权限！", Toast.LENGTH_SHORT).show();
							}
						}
//						关闭游标
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
	//为返回键设置监听器，点击结束当前Activity
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


