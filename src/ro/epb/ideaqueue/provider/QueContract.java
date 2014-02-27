package ro.epb.ideaqueue.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class QueContract implements BaseColumns {

	final static String CONTENT_AUTHORITY = "ro.epb.ideaqueue.provider"; //also called provider name??
	
	static final String TABLE_NAME = "iqs";
	
	public static final String COLUMN_NAME_POSITION = "position";
	public static final String COLUMN_NAME_STRING = "string";
	
	
	private static final String PATH_IQS = "iqs";
	public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
	public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_IQS).build();

	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/ro.epb.ideaque";

}
