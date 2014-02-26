package ro.epb.ideaqueue.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class QueProvider extends ContentProvider {
	private static final String TAG = "ContentProvider";

	private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

	static final int IQS = 1;

	private SQLiteDatabase db;

	static{
		matcher.addURI(QueContract.CONTENT_AUTHORITY, "/iqs", IQS);		
	}
	@Override
	public boolean onCreate() {
		Log.i(TAG, "onCreate");
		db = new QueDatabaseHelper(getContext()).getWritableDatabase();
		return db!= null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Log.i(TAG, "query");
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();		
		switch (matcher.match(uri)) {
		case IQS:
			queryBuilder.setTables(QueContract.TABLE_NAME);
			break;

		default:
			throw new IllegalArgumentException("Unknown uri "+ uri);
		}
		Cursor ret = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		ret.setNotificationUri(getContext().getContentResolver(), uri);
		return ret;
	}

	@Override
	public String getType(Uri uri) {
		Log.i(TAG, "getType");
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Log.i(TAG, "insert");
		Uri result;//inserted uri
		switch (matcher.match(uri)) {
		case IQS:
			long id = db.insertOrThrow(QueContract.TABLE_NAME, null, values);
			result = Uri.parse(QueContract.CONTENT_URI + "/" + id); 		
			break;
		default:
			throw new IllegalArgumentException("Unknown uri "+ uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return result;
		//ctx.getContentResolver().notifyChange(uri, null, false);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
