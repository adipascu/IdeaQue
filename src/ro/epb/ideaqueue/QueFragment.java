package ro.epb.ideaqueue;

import ro.epb.ideaqueue.provider.QueContract;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;

public class QueFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

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
	}

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
		Log.wtf("da", "nu");
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		
	}



}
