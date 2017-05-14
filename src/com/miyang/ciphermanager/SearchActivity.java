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
//		����DBHelper���ݿ⹤����
		searchHelper = new DBHelper(getApplicationContext());
//		�������֮ǰ���search���ݱ�
		searchHelper.deleteTb("search");
//		�ѵ�ǰActivity���뵽CloseApplication��Activitylist��
		CloseApplication.getInstance().addActivity(this);
		
//		��ÿؼ���Id
		search_txt = (EditText)findViewById(R.id.search_txt);
		search_back = (Button)findViewById(R.id.search_back);
		search_ls = (ListView)findViewById(R.id.search_ls);
		search_ok = (Button)findViewById(R.id.search_ok);
//		�󶨼�����
		search_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String txt = search_txt.getText().toString();
//				��������֮ǰ������ݱ�
				searchHelper.deleteTb("search");
//				�ж�txt�Ƿ�Ϊ�գ������Ϊ����ִ����ز��������Ϊ���򵯳��Ի�����ʾ����ؼ���
				if(!txt.isEmpty()){
//					����һ���������ڴ��ϵ�����ݱ�
					String[] tb_name = {"web","chat","game","bank","email","others"}; 
//					ѭ����ѯ���ݱ�������ѯ������뵽search���ݱ���
					for(int i = 0;i < tb_name.length ; i++){
//						ѭ����ѯ���ݱ�
						Cursor cu = searchHelper.rawQueryLike(tb_name[i],txt);
//						����contentvalues���󣬷�װ��¼��Ϣ
						ContentValues c = new ContentValues();
						while(cu.moveToNext()){
							c.put("kind", cu.getString(cu.getColumnIndex("kind")));
							c.put("date",cu.getString(cu.getColumnIndex("date")));
							c.put("user", cu.getString(cu.getColumnIndex("user")));
							c.put("password",cu.getString(cu.getColumnIndex("password")));
							c.put("remark",cu.getString(cu.getColumnIndex("remark")));
//							��search���ݱ��в�������
							searchHelper.insert(c, "search");
						}
//						�ر��α�
						cu.close();
					}
					
					Cursor cu = searchHelper.rawQueryCount("search");
					int count = 0;
				    while(cu.moveToNext())
				    {
				        count = cu.getInt(0);
				    }
				    if(count == 0){
				    	Toast.makeText(SearchActivity.this, "��ؼ�¼�����ڣ�", Toast.LENGTH_SHORT).show();
				    }else{
//						��ѯsearch���ݱ������ݱ����ݲ��뵽listview��
						Cursor cur = searchHelper.rawQueryInf("search");
							inflateList(cur);
				    }
//				    �ر��α�
				    cu.close();
				}else{
					search_ls.setVisibility(View.GONE);
					Toast.makeText(SearchActivity.this, "�������ѯ�ؼ��֣�", Toast.LENGTH_SHORT).show();
				}
			}

		});
//		Ϊ�������ذ�ť�󶨼�����
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
//	����һ��������������ѯ�������ݰ󶨵�listview��
	private void inflateList(Cursor cursor){
		search_ls = (ListView)findViewById(R.id.search_ls);
		/*
		 * ���SimpleCursorAdapter��ʹ��SimpleCursorAdapter���뱣֤������_id,��������г���
		*/
		SimpleCursorAdapter sc = new SimpleCursorAdapter(SearchActivity.this, R.layout.category_ls_item, cursor, 
				new String[]{"kind","date","user","password","remark"},
				new int[]{R.id.category_ls_content_txt1,R.id.category_ls_content_txt2,R.id.category_ls_content_txt3,R.id.category_ls_content_txt4,R.id.category_ls_content_txt5},
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		search_ls.setAdapter(sc);
	}
	
	//Ϊ���ؼ����ü����������������ǰActivity
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
