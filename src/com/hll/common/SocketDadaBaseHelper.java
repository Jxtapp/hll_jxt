package com.hll.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * websocket 数据库相关
 * @author liaoyun 2016-8-21
 */
public class SocketDadaBaseHelper extends SQLiteOpenHelper{
	
	private static final String SOCKET_CHAT = "create table chat_t(" //对话表 的 创表语句
			+ "id integer primary key autoincrement,"                //主键
			+ "placeid text,"                                        //场地id 
			+ "account text,"                                        //发信人账号
			+ "name text,"                                           //发信人名字
			+ "nickName text,"                                       //发信人昵称
			+ "content text,"                                        //信息内容
			+ "sendTime text)";                                      //发送信息的时间，服务器转发的时间  yyyy-MM-dd HH:mm:ss

	public SocketDadaBaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SOCKET_CHAT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists chat_t");
		onCreate(db);
	}
}
