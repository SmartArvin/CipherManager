package com.miyang.ciphermanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class HomeActivity extends Activity implements OnTouchListener{

	RelativeLayout home_rl1,home_rl2,home_rl3,home_rl4,home_rl5,home_rl6;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		
//		把当前Activity加入到CloseApplication的Activitylist中
		CloseApplication.getInstance().addActivity(this);
				
//		获得控件的Id
		home_rl1 = (RelativeLayout)findViewById(R.id.home_rl1);
		home_rl2 = (RelativeLayout)findViewById(R.id.home_rl2);
		home_rl3 = (RelativeLayout)findViewById(R.id.home_rl3);
		home_rl4 = (RelativeLayout)findViewById(R.id.home_rl4);
		home_rl5 = (RelativeLayout)findViewById(R.id.home_rl5);
		home_rl6 = (RelativeLayout)findViewById(R.id.home_rl6);
//		为各控件绑定监听器
		home_rl1.setOnTouchListener(this);
		home_rl2.setOnTouchListener(this);
		home_rl3.setOnTouchListener(this);
		home_rl4.setOnTouchListener(this);
		home_rl5.setOnTouchListener(this);
		home_rl6.setOnTouchListener(this);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
//		设置Animation  
        Animation animDwon = AnimationUtils.loadAnimation(this, R.anim.show_down);  
        Animation animUp = AnimationUtils.loadAnimation(this, R.anim.show_up); 
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.home_rl1:
			 switch (event.getAction()) { 
//			 按键按下时的动作
		        case MotionEvent.ACTION_DOWN:  
		        	home_rl1.startAnimation(animDwon);  
		            animDwon.setFillAfter(true);  
		            break;  
//		             按键抬起时的动作
		        case MotionEvent.ACTION_UP:  
		        	home_rl1.startAnimation(animUp);  
		            animUp.setFillAfter(true); 
		            new Handler().postDelayed(new Runnable(){    
		                public void run() {    
		                //execute the task
		                	Intent intent = new Intent(HomeActivity.this,CreateActivity.class);
		        			HomeActivity.this.startActivity(intent);
		        			finish();
		                }    
		             }, 200); 
		            break;  
		        } 
     			break;
		case R.id.home_rl2:
			 switch (event.getAction()) { 
//			 按键按下时的动作
		        case MotionEvent.ACTION_DOWN:  
		        	home_rl2.startAnimation(animDwon);  
		            animDwon.setFillAfter(true);  
		            break;  
//		             按键抬起时的动作
		        case MotionEvent.ACTION_UP:  
		        	home_rl2.startAnimation(animUp);  
		            animUp.setFillAfter(true); 
		            new Handler().postDelayed(new Runnable(){    
		                public void run() {    
		                //execute the task
		                	Intent intent = new Intent(HomeActivity.this,SearchActivity.class);
		        			HomeActivity.this.startActivity(intent);
		        			finish();
		                }    
		             }, 200); 
		            break;  
		        } 
     			break;
		case R.id.home_rl3:
			switch (event.getAction()) {  
	        case MotionEvent.ACTION_DOWN:  
	        	home_rl3.startAnimation(animDwon);  
	            animDwon.setFillAfter(true);  
	            break;  
	  
	        case MotionEvent.ACTION_UP:  
	        	home_rl3.startAnimation(animUp);  
	            animUp.setFillAfter(true); 
	            new Handler().postDelayed(new Runnable(){    
	                public void run() {    
	                //execute the task
	                	Intent intent = new Intent(HomeActivity.this,CategoryActivity.class);
	        			HomeActivity.this.startActivity(intent);
	        			finish();
	                }    
	             }, 200); 
	            break;  
	        } 
 			break;
		case R.id.home_rl4:
			switch (event.getAction()) {  
	        case MotionEvent.ACTION_DOWN:  
	        	home_rl4.startAnimation(animDwon);  
	            animDwon.setFillAfter(true);  
	            break;  
	  
	        case MotionEvent.ACTION_UP:  
	        	home_rl4.startAnimation(animUp);  
	            animUp.setFillAfter(true); 
	            new Handler().postDelayed(new Runnable(){    
	                public void run() {    
	                //execute the task
	                	Intent intent = new Intent(HomeActivity.this,LikeActivity.class);
	        			HomeActivity.this.startActivity(intent);
	        			finish();
	                }    
	             }, 200); 
	            break;  
	        } 
 			break;
		case R.id.home_rl5:
			switch (event.getAction()) {  
	        case MotionEvent.ACTION_DOWN:  
	        	home_rl5.startAnimation(animDwon);  
	            animDwon.setFillAfter(true);  
	            break;  
	  
	        case MotionEvent.ACTION_UP:  
	        	home_rl5.startAnimation(animUp);  
	            animUp.setFillAfter(true); 
	            new Handler().postDelayed(new Runnable(){    
	                public void run() {    
	                //execute the task
	                	Intent intent = new Intent(HomeActivity.this,SafeActivity.class);
	        			HomeActivity.this.startActivity(intent);
	        			finish();
	                }    
	             }, 200); 
	            break;  
	        } 
 			break;
		case R.id.home_rl6:
			switch (event.getAction()) {  
	        case MotionEvent.ACTION_DOWN:  
	        	home_rl6.startAnimation(animDwon);  
	            animDwon.setFillAfter(true);  
	            break;  
	  
	        case MotionEvent.ACTION_UP:  
	        	home_rl6.startAnimation(animUp);  
	            animUp.setFillAfter(true); 
	            new Handler().postDelayed(new Runnable(){    
	                public void run() {    
	                //execute the task
	                	Intent intent = new Intent(HomeActivity.this,AboutActivity.class);
	        			HomeActivity.this.startActivity(intent);
	        			finish();
	                }    
	             }, 200); 
	            break;  
	        } 
 			break;
		default:
			break;
		}
		return true;
	} 
	
	//设置硬件返回按钮的监听器，实现再按一次退出程序的功能
			private long exitTime = 0;

			@Override
			public boolean onKeyDown(int keyCode, KeyEvent event) {
			if(keyCode == KeyEvent.KEYCODE_BACK
			   && event.getAction() == KeyEvent.ACTION_DOWN){
			     if((System.currentTimeMillis()-exitTime) > 2000){
			         Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
			         exitTime = System.currentTimeMillis();
			  } else {
				  CloseApplication.getInstance().exit();
			  }
			     return true;
			    }
			return super.onKeyDown(keyCode, event);
			}

}
