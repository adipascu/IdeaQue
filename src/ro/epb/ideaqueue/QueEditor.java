package ro.epb.ideaqueue;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;

public class QueEditor extends Activity implements LoaderCallbacks<Cursor> {

	private EditText editText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_que_editor);
		editText = (EditText)findViewById(R.id.editText);
		final Intent intent = getIntent();

		Uri uri = getContentResolver().insert(intent.getData(), null);
		if(uri == null)
		{
			finish();
		}
		setResult(RESULT_OK, (new Intent()).setAction(uri.toString()));
		getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder)
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.que_editor, menu);
		return true;
	}


	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		
	}

}
