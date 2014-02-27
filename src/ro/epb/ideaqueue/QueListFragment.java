package ro.epb.ideaqueue;

import ro.epb.ideaqueue.provider.QueContract;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class QueListFragment extends ListFragment implements LoaderCallbacks<Cursor> {

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		startActivity(new Intent(Intent.ACTION_EDIT, Uri.parse(QueContract.CONTENT_URI + "/" + id)));
		super.onListItemClick(l, v, position, id);
	}

	private static final String TAG = "QueFragment";
	private SimpleCursorAdapter cursorAdapter;


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setEmptyText("This is empty");
		cursorAdapter = new SimpleCursorAdapter(
				getActivity(),       // Current context
				android.R.layout.simple_list_item_2,  // Layout for individual rows
				null,                // Cursor
				new String[]{QueContract.COLUMN_NAME_STRING, QueContract.COLUMN_NAME_POSITION},        // Cursor columns to use
				new int[]{android.R.id.text1, android.R.id.text2},           // Layout fields to use
				0                    // No flags
				);
		setListAdapter(cursorAdapter);
		getLoaderManager().initLoader(0, null, this);
		setHasOptionsMenu(true);
		//loopDaLoop();
	}

	//	private void loopDaLoop(){
	//		new Handler().postDelayed(new Runnable() {
	//
	//			@Override
	//			public void run() {
	//				ContentValues values = new ContentValues();
	//				values.put(QueContract.COLUMN_NAME_POSITION, 1133);
	//				values.put(QueContract.COLUMN_NAME_STRING, "mmyeha");
	//				getActivity().getContentResolver().insert(QueContract.CONTENT_URI, values);
	//				loopDaLoop();
	//			}
	//		}, 1000);
	//	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = new String[]{QueContract._ID, QueContract.COLUMN_NAME_STRING, QueContract.COLUMN_NAME_POSITION};
		return new CursorLoader(getActivity(),  // Context
				QueContract.CONTENT_URI, // URI
				projection,                // Projection
				null,                           // Selection
				null,                           // Selection args
				null);							//sort
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		cursorAdapter.swapCursor(data);
		Log.i(TAG, "onLoadFinished");
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		cursorAdapter.swapCursor(null);

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.list, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add:
			startActivity(new Intent(Intent.ACTION_INSERT, QueContract.CONTENT_URI));
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}



}
