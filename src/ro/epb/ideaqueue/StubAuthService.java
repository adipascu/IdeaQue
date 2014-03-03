package ro.epb.ideaqueue;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class StubAuthService extends Service {

	private final String TAG = "StubAuthService";
	private static StubAuth stubAuth;
	private static final Object lock = new Object();
	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate");
		super.onCreate();
		synchronized (lock) {
			if(stubAuth == null)
				stubAuth = new StubAuth(getApplicationContext());
		}
	}
	@Override
	public IBinder onBind(Intent intent) {
		Log.i(TAG, "onBind");
		return stubAuth.getIBinder();
	}



}
