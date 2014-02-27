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
	static final int IQ_ITEM = 2;

	private SQLiteDatabase db;

	static{
		matcher.addURI(QueContract.CONTENT_AUTHORITY, "/iqs", IQS);		
		matcher.addURI(QueContract.CONTENT_AUTHORITY, "/iqs/#", IQ_ITEM);
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
		case IQ_ITEM:
			queryBuilder.setTables(QueContract.TABLE_NAME);
			queryBuilder.appendWhere(QueContract._ID + "=" + uri.getPathSegments().get(1));
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
		switch (matcher.match(uri)) {
		case IQS:
			return QueContract.CONTENT_TYPE;
		case IQ_ITEM:
			return QueContract.CONTENT_ITEM_TYPE;


		default:
			throw new IllegalArgumentException("unknown uri " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, final ContentValues initialValues) {
		Log.i(TAG, "insert");
		Uri result;//inserted uri
		ContentValues values;
		if(initialValues == null)
		{
			values = new ContentValues();
		}
		else
		{
			values = new ContentValues(initialValues);
		}
		switch (matcher.match(uri)) {
		case IQS:
			long id = db.insertOrThrow(QueContract.TABLE_NAME, QueContract.COLUMN_NAME_STRING, values);
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
		int ret;
		switch (matcher.match(uri)) {
		case IQ_ITEM:
			ret = db.delete(QueContract.TABLE_NAME, QueContract._ID + "=?", new String[]{uri.getPathSegments().get(1)});
			break;
		default:
			ret = 0;
			break;
		}
		if(ret!=0)
			getContext().getContentResolver().notifyChange(uri, null);
		return ret;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		switch (matcher.match(uri)) {
		case IQ_ITEM:
			getContext().getContentResolver().notifyChange(uri, null);
			return db.update(QueContract.TABLE_NAME, values, QueContract._ID + "=?", new String[]{uri.getPathSegments().get(1)});

		default:
			return 0;
		}


	}

}
