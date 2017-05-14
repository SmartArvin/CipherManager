package com.miyang.ciphermanager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class AboutActivity extends Activity{

	Button about_back,about_web;
	RelativeLayout about_rl3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);
		
//		把当前Activity加入到CloseApplication的Activitylist中
		CloseApplication.getInstance().addActivity(this);
		
//		获得控件的Id
		about_back = (Button)findViewById(R.id.about_back);
		about_web = (Button)findViewById(R.id.about_web);
		about_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(AboutActivity.this,HomeActivity.class);
				AboutActivity.this.startActivity(intent);
				finish();
			}
		});
		about_web.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Uri uri=Uri.parse("http://soft.leidian.com/detail/index/soft_id/2141072");
	            intent.setAction(Intent.ACTION_VIEW);
	            intent.setData(uri);
	            startActivity(intent);
			}
		});
	}
	//为返回键设置监听器，点击结束当前Activity
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK
		   && event.getAction() == KeyEvent.ACTION_DOWN){
			Intent intent = new Intent();
			intent.setClass(AboutActivity.this,HomeActivity.class);
			AboutActivity.this.startActivity(intent);
			finish();
		  }
		return true;
		} 
	

}
