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
		
//		�ѵ�ǰActivity���뵽CloseApplication��Activitylist��
		CloseApplication.getInstance().addActivity(this);
		
//		������ݱ��е���������������ʾ
		dataCount();
//		��ÿؼ���Id
		list_back = (Button)findViewById(R.id.list_back);
		list_rl2 = (RelativeLayout)findViewById(R.id.list_rl2);
		list_rl3 = (RelativeLayout)findViewById(R.id.list_rl3);
		list_rl4 = (RelativeLayout)findViewById(R.id.list_rl4);
		list_rl5 = (RelativeLayout)findViewById(R.id.list_rl5);
		list_rl6 = (RelativeLayout)findViewById(R.id.list_rl6);
		list_rl7 = (RelativeLayout)findViewById(R.id.list_rl7);
		
//		Ϊÿһ��RelativeLayout�󶨼�����
		list_rl2.setOnClickListener(new categoryListener());
		list_rl3.setOnClickListener(new categoryListener());
		list_rl4.setOnClickListener(new categoryListener());
		list_rl5.setOnClickListener(new categoryListener());
		list_rl6.setOnClickListener(new categoryListener());
		list_rl7.setOnClickListener(new categoryListener());
//		Ϊ�������ذ�ť�󶨼�����
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
//		������������
	class categoryListener implements OnClickListener{

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		Bundle mbundle = new Bundle();
		switch (v.getId()) {
		case R.id.list_rl2:
			intent.setClass(CategoryActivity.this,CategoryContent.class);
			mbundle.putString("key", "��վ��̳");
			intent.putExtras(mbundle);
			CategoryActivity.this.startActivity(intent);
			finish();
			break;
		case R.id.list_rl3:
			intent.setClass(CategoryActivity.this,CategoryContent.class);
			mbundle.putString("key", "��������");
			intent.putExtras(mbundle);
			CategoryActivity.this.startActivity(intent);
			finish();
			break;
		case R.id.list_rl4:
			intent.setClass(CategoryActivity.this,CategoryContent.class);
			mbundle.putString("key", "��Ϸ�˻�");
			intent.putExtras(mbundle);
			CategoryActivity.this.startActivity(intent);
			finish();
			break;
		case R.id.list_rl5:
			intent.setClass(CategoryActivity.this,CategoryContent.class);
			mbundle.putString("key", "�����˻�");
			intent.putExtras(mbundle);
			CategoryActivity.this.startActivity(intent);
			finish();
			break;
		case R.id.list_rl6:
			intent.setClass(CategoryActivity.this,CategoryContent.class);
			mbundle.putString("key", "�����˻�");
			intent.putExtras(mbundle);
			CategoryActivity.this.startActivity(intent);
			finish();
			break;
		case R.id.list_rl7:
			intent.setClass(CategoryActivity.this,CategoryContent.class);
			mbundle.putString("key", "����");
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
	 * ��������ݱ������ݵ�����
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
//		�������ݿ⹤����DBHelper����
		cateHelper = new DBHelper(getApplicationContext());
/*
 * ��ѯ��ͳ�����ݱ��е���������
*/
//		��ѯ��ͳ��ϵ�����ݱ��е���������
		for(int i = 0; i<tb_name.length ;i++){
			Cursor cu = cateHelper.rawQueryCount(tb_name[i]);
		    while(cu.moveToNext())
		    {
		        count = cu.getInt(0);
		    }
		    txt_name[i].setText("��"+count+"��");
//		    �ر��α�
		    cu.close();
		}
		
}
		

	//Ϊ���ؼ����ü����������������ǰActivity
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
