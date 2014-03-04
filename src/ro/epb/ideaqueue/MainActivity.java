package ro.epb.ideaqueue;

import ro.epb.ideaqueue.provider.QueContract;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		AccountManager accountManager = AccountManager.get(this);

		Account[] accounts = accountManager.getAccountsByType("ro.epb.ideaqueue.iqaccount");

		Bundle params = new Bundle();
	    params.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, false);
	    params.putBoolean(ContentResolver.SYNC_EXTRAS_DO_NOT_RETRY, false);
	    params.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, false);
	    ContentResolver.addPeriodicSync(accounts[0], QueContract.CONTENT_AUTHORITY, params, 60);
		ContentResolver.requestSync(accounts[0], QueContract.CONTENT_AUTHORITY, new Bundle());
	}

}
