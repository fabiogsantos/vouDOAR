package br.com.tcc.tecdam.voudoar;

import android.app.Application;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;

/**
 * Created by fabio.goncalves on 13/05/2018.
 */

public class VouDoarApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(),"Facebook modo Debug",Toast.LENGTH_SHORT).show();
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
    }
}
