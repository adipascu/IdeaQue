package ro.epb.ideaqueue;

import ro.epb.ideaqueue.provider.QueContract;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class QueEditor extends FragmentActivity implements LoaderCallbacks<Cursor> {

	private EditText editText;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_que_editor);
		editText = (EditText)findViewById(R.id.editText);
		intent = getIntent();
		if(intent.getAction().equals(Intent.ACTION_INSERT))
		{
			Uri uri = getContentResolver().insert(intent.getData(), null);
			if(uri == null)
				finish();
			else{
				intent.setData(uri);
				intent.setAction(Intent.ACTION_EDIT);
			}
		}
		if(intent.getAction().equals(Intent.ACTION_EDIT))
		{
			getSupportLoaderManager().initLoader(0, null, this);
		}


		//getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder)
	}


	@Override
	protected void onPause() {
		AsyncQueryHandler queryHandler = new AsyncQueryHandler(getContentResolver()) {
		};
		ContentValues contentValues = new ContentValues();
		contentValues.put(QueContract.COLUMN_NAME_STRING, editText.getText().toString());
		queryHandler.startUpdate(0, null, intent.getData(),contentValues , null, null);
		super.onPause();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.que_editor, menu);
		return true;
	}


	@Override
	public void finish() {
		super.finish();
		setResult(RESULT_OK, (new Intent()).setData(intent.getData()));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete:
			AsyncQueryHandler queryHandler = new AsyncQueryHandler(getContentResolver()) {
			};
			queryHandler.startDelete(0, null, intent.getData(),null , null);
			finish();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	};
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = new String[]{QueContract._ID, QueContract.COLUMN_NAME_STRING, QueContract.COLUMN_NAME_POSITION};
		return new CursorLoader(this,  // Context
				intent.getData(), // URI
				projection,                // Projection
				null,                           // Selection
				null,                           // Selection args
				null);							//sort
	}


	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		if(data.moveToFirst())
			editText.setText(data.getString(data.getColumnIndex(QueContract.COLUMN_NAME_STRING)));
		else
			editText.setText("<missing item>");

	}


	@Override
	public void onLoaderReset(Loader<Cursor> loader) {

	}

}
