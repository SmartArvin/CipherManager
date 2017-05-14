package com.miyang.ciphermanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

//	����һ�����ݿ��ļ�
	private static final String DB_NAME = "my.db";
//	����һ����
	private static final String TB_LOGIN = "login";
	private static final String TB_WEB = "web";
	private static final String TB_CHAT = "chat";
	private static final String TB_GAME = "game";
	private static final String TB_BANK = "bank";
	private static final String TB_EMAIL = "email";
	private static final String TB_OTHERS = "others";
	private static final String TB_SEARCH = "search";
	private static final String TB_LIKE = "like";
	private static final String TB_BACKUP = "backup";
	private static final String TB_BACKUP_LIKE = "backup_like";
//	����һ�����ݿ����
	private SQLiteDatabase db;
	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		��ȡ���ݿ����
		this.db = db;
//		����һ����
		String CREATE_LOGIN = "CREATE TABLE login(_id INTEGER PRIMARY key autoincrement," + "key1 text)";
		String CREATE_WEB = "CREATE TABLE web(_id INTEGER PRIMARY key autoincrement," + "kind text,"+"date text," + "user text," + "password text," +" remark text)";
		String CREATE_CHAT = "CREATE TABLE chat(_id INTEGER PRIMARY key autoincrement," + "kind text,"+"date text," + "user text," + "password text," +" remark text)";
		String CREATE_GAME = "CREATE TABLE game(_id INTEGER PRIMARY key autoincrement," + "kind text,"+"date text," + "user text," + "password text," +" remark text)";
		String CREATE_BANK = "CREATE TABLE bank(_id INTEGER PRIMARY key autoincrement," + "kind text,"+"date text," + "user text," + "password text," +" remark text)";
		String CREATE_EMAIL = "CREATE TABLE email(_id INTEGER PRIMARY key autoincrement," + "kind text,"+"date text," + "user text," + "password text," +" remark text)";
		String CREATE_OTHERS = "CREATE TABLE others(_id INTEGER PRIMARY key autoincrement," + "kind text,"+"date text," + "user text," + "password text," +" remark text)";
		String CREATE_SEARCH = "CREATE TABLE search(_id INTEGER PRIMARY key autoincrement," + "kind text,"+"date text," + "user text," + "password text," +" remark text)";
		String CREATE_LIKE = "CREATE TABLE like(_id INTEGER PRIMARY key autoincrement," + "kind text,"+"date text," + "user text," + "password text," +" remark text)";
		String CREATE_BACKUP = "CREATE TABLE backup(_id INTEGER PRIMARY key autoincrement," + "kind text,"+"date text," + "user text," + "password text," +" remark text)";
		String CREATE_BACKUP_LIKE = "CREATE TABLE backup_like(_id INTEGER PRIMARY key autoincrement," + "kind text,"+"date text," + "user text," + "password text," +" remark text)";
//		ִ�д���������
		db.execSQL(CREATE_LOGIN);
		db.execSQL(CREATE_WEB);
		db.execSQL(CREATE_CHAT);
		db.execSQL(CREATE_GAME);
		db.execSQL(CREATE_BANK);
		db.execSQL(CREATE_EMAIL);
		db.execSQL(CREATE_OTHERS);
		db.execSQL(CREATE_SEARCH);
		db.execSQL(CREATE_LIKE);
		db.execSQL(CREATE_BACKUP);
		db.execSQL(CREATE_BACKUP_LIKE);
	}
	
//	���뺯��
	public void insert(ContentValues values,String tb_name){
		SQLiteDatabase db = getWritableDatabase();
		db.insert(tb_name, null, values);
		db.close();
	}
//	ȫ�ֶβ�ѯ����
	public Cursor query(String tb_name){
		SQLiteDatabase db = getWritableDatabase();
		Cursor c = db.query(tb_name, null, null, null, null, null, null);
		return c;
	}
//	��������
	public void update(ContentValues values,String tb_name){
//		������ݿ��������
		SQLiteDatabase db = getWritableDatabase();
		db.update(tb_name, values, null, null);
		db.close();
	}
//	��_idɾ������
	public void deleteRaw(String tb_name,int id){
		if(db==null)
			db = getWritableDatabase();
		db.delete(tb_name, "_id=?", new String[]{String.valueOf(id)});
	}
//	��dateɾ������
	public void deleteByDate(String tb_name,String tb_date){
		SQLiteDatabase db = getWritableDatabase();
		if(db==null)
			db = getWritableDatabase();
		db.delete(tb_name, "date=?", new String[]{String.valueOf(tb_date)});
	}
//	ɾ�����ݱ����������
	public void deleteTb(String tb_name){
		SQLiteDatabase db = getWritableDatabase();
//		db.execSQL("TRUNCATE TABLE"+tb_name);
//		db.execSQL("DROP TABLE "+tb_name);
		db.execSQL("delete from "+tb_name);
	}
//	��ѯ���ݱ��е�������Ϣ
	public Cursor rawQueryInf(String tb_name) {
		SQLiteDatabase db = getWritableDatabase();
		Cursor c = db.rawQuery("select * from " + tb_name, null);
		return c;
	}
//	ģ����ѯ����
	public Cursor rawQueryLike(String tb_name,String key) {
		SQLiteDatabase db = getWritableDatabase();
//		���ű�ͬʱ���ֶβ�ѯ
//		Cursor c = db.rawQuery("select * from web,chat " + " where web.user like ? or web.password like ? or web.remark like ? or chat.user like ? or chat.password like ? or chat.remark like ?" , new String[]{"%"+key+"%","%"+key+"%","%"+key+"%","%"+key+"%","%"+key+"%","%"+key+"%"});
//		���ű���ֶβ�ѯ
//		Cursor c = db.rawQuery("select * from web " + " where web.user like ? or web.password like ? or web.remark like ?" , new String[]{"%"+key+"%","%"+key+"%","%"+key+"%"});
		Cursor c = db.rawQuery("select * from "+tb_name+" where user like ? or remark like ?" , new String[]{"%"+key+"%","%"+key+"%"});
		return c;
	}
//	��ѯ���ݱ������ݵ�����
	public Cursor rawQueryCount(String tb_name) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.rawQuery("select count (_id) from " + tb_name, null);
		return c;
	}
//	������ʱ����¶�Ӧ�����ݱ���Ϣ
	public void updateByDate(String tb_name,ContentValues values,String date){
		SQLiteDatabase db = getWritableDatabase();
		db.update(tb_name, values, "date=?", new String[]{date});
	}
////	�ر����ݿ�
//	public void close(){
//		SQLiteDatabase db = getWritableDatabase();
//		if(db != null)
//			db.close();
//	}
//	�������ݿ�汾��Ϣ
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
