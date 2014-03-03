package ro.epb.ideaqueue;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class AccountActivity extends AccountAuthenticatorActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);

		AccountManager accountManager = AccountManager.get(this);
		Account account = new Account("Username", "ro.epb.ideaqueue.iqaccount");
		accountManager.addAccountExplicitly(account, "pass", null);

		final Intent intent = new Intent();  
		intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, "Username");  
		intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, "ro.epb.ideaqueue.iqaccount");  
		intent.putExtra(AccountManager.KEY_AUTHTOKEN, "pass");  
		this.setAccountAuthenticatorResult(intent.getExtras());  
		this.setResult(RESULT_OK, intent);  
		this.finish();  
		return;
		//		new Handler().postDelayed(new Runnable() {
		//			
		//			@Override
		//			public void run() {
		//				final Intent intent = new Intent();
		//				intent.putExtra(AccountManager.KEY_BOOLEAN_RESULT, true);
		//				setAccountAuthenticatorResult(intent.getExtras());
		//				setResult(RESULT_OK, intent);
		//				finish();
		//			}
		//		}, 3000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account, menu);
		return true;
	}

}
