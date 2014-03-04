package ro.epb.ideaqueue;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.auth.AuthenticationException;
import org.json.JSONException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

public class SyncAdapter extends AbstractThreadedSyncAdapter {
	private static final String TAG = "SyncAdapter";
	private ContentResolver contentResolver;
	private AccountManager accountManager;
	public SyncAdapter(Context context, boolean autoInitialize) {		
		this(context, autoInitialize, false);
	}	

	public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
		super(context, autoInitialize, allowParallelSyncs);
		contentResolver = context.getContentResolver();
		accountManager = AccountManager.get(context);
		Log.i(TAG, "SyncAdapter");
	}

	@Override
	public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
		Log.i(TAG, "onPerformSync");
		try{
			final String authtoken = accountManager.blockingGetAuthToken(account, "com.example.android.samplesync.xxx", true);
		} catch (final AuthenticatorException e) {
			Log.e(TAG, "AuthenticatorException", e);
			syncResult.stats.numParseExceptions++;
		} catch (final OperationCanceledException e) {
			Log.e(TAG, "OperationCanceledExcetpion", e);
		} catch (final IOException e) {
			Log.e(TAG, "IOException", e);
			syncResult.stats.numIoExceptions++;
		}
//		 catch (final AuthenticationException e) {
//			Log.e(TAG, "AuthenticationException", e);
//			syncResult.stats.numAuthExceptions++;
//		}
		catch (final ParseException e) {
			Log.e(TAG, "ParseException", e);
			syncResult.stats.numParseExceptions++;
		}
//		catch (final JSONException e) {
//			Log.e(TAG, "JSONException", e);
//			syncResult.stats.numParseExceptions++;
//		}


		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i(TAG, "sync end");

	}

}
