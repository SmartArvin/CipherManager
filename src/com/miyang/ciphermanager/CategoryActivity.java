package com.miyang.ciphermanager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryActivity extends Activity{

	Button list_back;
	RelativeLayout list_rl2,list_rl3,list_rl4,list_rl5,list_rl6,list_rl7;
	TextView list_rl2_txt2,list_rl3_txt2,list_rl4_txt2,list_rl5_txt2,list_rl6_txt2,list_rl7_txt2;
	DBHelper cateHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_activity);
		
//		把当前Activity加入到CloseApplication的Activitylist中
		CloseApplication.getInstance().addActivity(this);
		
//		获得数据表中的数据条数并送显示
		dataCount();
//		获得控件的Id
		list_back = (Button)findViewById(R.id.list_back);
		list_rl2 = (RelativeLayout)findViewById(R.id.list_rl2);
		list_rl3 = (RelativeLayout)findViewById(R.id.list_rl3);
		list_rl4 = (RelativeLayout)findViewById(R.id.list_rl4);
		list_rl5 = (RelativeLayout)findViewById(R.id.list_rl5);
		list_rl6 = (RelativeLayout)findViewById(R.id.list_rl6);
		list_rl7 = (RelativeLayout)findViewById(R.id.list_rl7);
		
//		为每一行RelativeLayout绑定监听器
		list_rl2.setOnClickListener(new categoryListener());
		list_rl3.setOnClickListener(new categoryListener());
		list_rl4.setOnClickListener(new categoryListener());
		list_rl5.setOnClickListener(new categoryListener());
		list_rl6.setOnClickListener(new categoryListener());
		list_rl7.setOnClickListener(new categoryListener());
//		为顶部返回按钮绑定监听器
		list_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(CategoryActivity.this,HomeActivity.class);
				CategoryActivity.this.startActivity(intent);
				finish();
			}
		});
	}
//		创建监听器类
	class categoryListener implements OnClickListener{

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		Bundle mbundle = new Bundle();
		switch (v.getId()) {
		case R.id.list_rl2:
			intent.setClass(CategoryActivity.this,CategoryContent.class);
			mbundle.putString("key", "网站论坛");
			intent.putExtras(mbundle);
			CategoryActivity.this.startActivity(intent);
			finish();
			break;
		case R.id.list_rl3:
			intent.setClass(CategoryActivity.this,CategoryContent.class);
			mbundle.putString("key", "聊天社区");
			intent.putExtras(mbundle);
			CategoryActivity.this.startActivity(intent);
			finish();
			break;
		case R.id.list_rl4:
			intent.setClass(CategoryActivity.this,CategoryContent.class);
			mbundle.putString("key", "游戏账户");
			intent.putExtras(mbundle);
			CategoryActivity.this.startActivity(intent);
			finish();
			break;
		case R.id.list_rl5:
			intent.setClass(CategoryActivity.this,CategoryContent.class);
			mbundle.putString("key", "银行账户");
			intent.putExtras(mbundle);
			CategoryActivity.this.startActivity(intent);
			finish();
			break;
		case R.id.list_rl6:
			intent.setClass(CategoryActivity.this,CategoryContent.class);
			mbundle.putString("key", "邮箱账户");
			intent.putExtras(mbundle);
			CategoryActivity.this.startActivity(intent);
			finish();
			break;
		case R.id.list_rl7:
			intent.setClass(CategoryActivity.this,CategoryContent.class);
			mbundle.putString("key", "其他");
			intent.putExtras(mbundle);
			CategoryActivity.this.startActivity(intent);
			finish();
			break;

		default:
			break;
		}
	 }
	}
	/*
	 * 计算各数据表中数据的条数
	*/
	private void dataCount(){
		list_rl2_txt2 = (TextView)findViewById(R.id.list_rl2_txt2);
		list_rl3_txt2 = (TextView)findViewById(R.id.list_rl3_txt2);
		list_rl4_txt2 = (TextView)findViewById(R.id.list_rl4_txt2);
		list_rl5_txt2 = (TextView)findViewById(R.id.list_rl5_txt2);
		list_rl6_txt2 = (TextView)findViewById(R.id.list_rl6_txt2);
		list_rl7_txt2 = (TextView)findViewById(R.id.list_rl7_txt2);
		TextView[] txt_name = {list_rl2_txt2,list_rl3_txt2,list_rl4_txt2,list_rl5_txt2,list_rl6_txt2,list_rl7_txt2};
		String[] tb_name = {"web","chat","game","bank","email","others"}; 
		int count = 0;
//		创建数据库工具类DBHelper对象
		cateHelper = new DBHelper(getApplicationContext());
/*
 * 查询并统计数据表中的数据条数
*/
//		查询并统计系列数据表中的数据条数
		for(int i = 0; i<tb_name.length ;i++){
			Cursor cu = cateHelper.rawQueryCount(tb_name[i]);
		    while(cu.moveToNext())
		    {
		        count = cu.getInt(0);
		    }
		    txt_name[i].setText("共"+count+"个");
//		    关闭游标
		    cu.close();
		}
		
}
		

	//为返回键设置监听器，点击结束当前Activity
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if(keyCode == KeyEvent.KEYCODE_BACK
			   && event.getAction() == KeyEvent.ACTION_DOWN){
				Intent intent = new Intent();
				intent.setClass(CategoryActivity.this,HomeActivity.class);
				CategoryActivity.this.startActivity(intent);
				finish();
			  }
			return true;
			} 
}
