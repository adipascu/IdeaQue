package ro.epb.ideaqueue.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QueDatabaseHelper extends SQLiteOpenHelper {

	static final int DATABASE_VERSION = 9;

	static final String DATABSE_NAME = "main.db";
	public QueDatabaseHelper(Context context) {
		super(context, DATABSE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		final String CREATE_QUERY = 
				"CREATE TABLE " + QueContract.TABLE_NAME + " ("+
						QueContract._ID + " INTEGER PRIMARY KEY, " + 
						QueContract.COLUMN_NAME_POSITION + " INTEGER, " + 
						QueContract.COLUMN_NAME_STRING + " TEXT);";
		db.execSQL(CREATE_QUERY);
		db.execSQL("INSERT INTO " + QueContract.TABLE_NAME + " VALUES(NULL, '2', 'da')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " +  QueContract.TABLE_NAME);
		onCreate(db);
	}

}
