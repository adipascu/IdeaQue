package ro.epb.ideaqueue.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AuthService extends Service {

	private final String TAG = "StubAuthService";
	private static Auth stubAuth;
	private static final Object lock = new Object();
	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate");
		super.onCreate();
		synchronized (lock) {
			if(stubAuth == null)
				stubAuth = new Auth(getApplicationContext());
		}
	}
	@Override
	public IBinder onBind(Intent intent) {
		Log.i(TAG, "onBind");
		return stubAuth.getIBinder();
	}



}
