package com.miyang.ciphermanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

//	定义一个数据库文件
	private static final String DB_NAME = "my.db";
//	定义一个表
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
//	声明一个数据库对象
	private SQLiteDatabase db;
	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		获取数据库对象
		this.db = db;
//		创建一个表
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
//		执行创建表的语句
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
	
//	插入函数
	public void insert(ContentValues values,String tb_name){
		SQLiteDatabase db = getWritableDatabase();
		db.insert(tb_name, null, values);
		db.close();
	}
//	全字段查询函数
	public Cursor query(String tb_name){
		SQLiteDatabase db = getWritableDatabase();
		Cursor c = db.query(tb_name, null, null, null, null, null, null);
		return c;
	}
//	更新数据
	public void update(ContentValues values,String tb_name){
//		获得数据库操作对象
		SQLiteDatabase db = getWritableDatabase();
		db.update(tb_name, values, null, null);
		db.close();
	}
//	按_id删除数据
	public void deleteRaw(String tb_name,int id){
		if(db==null)
			db = getWritableDatabase();
		db.delete(tb_name, "_id=?", new String[]{String.valueOf(id)});
	}
//	按date删除数据
	public void deleteByDate(String tb_name,String tb_date){
		SQLiteDatabase db = getWritableDatabase();
		if(db==null)
			db = getWritableDatabase();
		db.delete(tb_name, "date=?", new String[]{String.valueOf(tb_date)});
	}
//	删除数据表的所有内容
	public void deleteTb(String tb_name){
		SQLiteDatabase db = getWritableDatabase();
//		db.execSQL("TRUNCATE TABLE"+tb_name);
//		db.execSQL("DROP TABLE "+tb_name);
		db.execSQL("delete from "+tb_name);
	}
//	查询数据表中的数据信息
	public Cursor rawQueryInf(String tb_name) {
		SQLiteDatabase db = getWritableDatabase();
		Cursor c = db.rawQuery("select * from " + tb_name, null);
		return c;
	}
//	模糊查询数据
	public Cursor rawQueryLike(String tb_name,String key) {
		SQLiteDatabase db = getWritableDatabase();
//		两张表同时多字段查询
//		Cursor c = db.rawQuery("select * from web,chat " + " where web.user like ? or web.password like ? or web.remark like ? or chat.user like ? or chat.password like ? or chat.remark like ?" , new String[]{"%"+key+"%","%"+key+"%","%"+key+"%","%"+key+"%","%"+key+"%","%"+key+"%"});
//		单张表多字段查询
//		Cursor c = db.rawQuery("select * from web " + " where web.user like ? or web.password like ? or web.remark like ?" , new String[]{"%"+key+"%","%"+key+"%","%"+key+"%"});
		Cursor c = db.rawQuery("select * from "+tb_name+" where user like ? or remark like ?" , new String[]{"%"+key+"%","%"+key+"%"});
		return c;
	}
//	查询数据表中数据的条数
	public Cursor rawQueryCount(String tb_name) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.rawQuery("select count (_id) from " + tb_name, null);
		return c;
	}
//	按日期时间更新对应的数据表信息
	public void updateByDate(String tb_name,ContentValues values,String date){
		SQLiteDatabase db = getWritableDatabase();
		db.update(tb_name, values, "date=?", new String[]{date});
	}
////	关闭数据库
//	public void close(){
//		SQLiteDatabase db = getWritableDatabase();
//		if(db != null)
//			db.close();
//	}
//	更新数据库版本信息
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
