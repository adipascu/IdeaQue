package ro.epb.ideaqueue;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class AccountActivity extends AccountAuthenticatorActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);

		AccountManager accountManager = AccountManager.get(this);

		Account[] accounts = accountManager.getAccountsByType("ro.epb.ideaqueue.iqaccount");
		if(accounts.length >= 1)
		{
			Toast.makeText(this, "You may have only one account!", Toast.LENGTH_LONG).show();
			Bundle bundle = new Bundle();
			bundle.putInt(AccountManager.KEY_ERROR_CODE, AccountManager.ERROR_CODE_UNSUPPORTED_OPERATION);
			bundle.putString(AccountManager.KEY_ERROR_MESSAGE, "only one account is allowed");
			setAccountAuthenticatorResult(bundle);
			finish();
		}
		else
		{
			Account account = new Account("Username", "ro.epb.ideaqueue.iqaccount");
			accountManager.addAccountExplicitly(account, "pass", null);
			Bundle bundle = new Bundle();
			bundle.putString(AccountManager.KEY_ACCOUNT_NAME, "Username");  
			bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, "ro.epb.ideaqueue.iqaccount");  
			bundle.putString(AccountManager.KEY_AUTHTOKEN, "pass");  
			this.setAccountAuthenticatorResult(bundle);
			//	this.finish();  
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account, menu);
		return true;
	}

}
