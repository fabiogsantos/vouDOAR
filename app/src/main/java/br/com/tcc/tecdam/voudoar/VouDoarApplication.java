package br.com.tcc.tecdam.voudoar;

import android.app.Application;
import android.widget.Toast;

/**
 * Created by fabio.goncalves on 13/05/2018.
 */

public class VouDoarApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Toast.makeText(getApplicationContext(),"APP Modo Debug",Toast.LENGTH_SHORT).show();
        }
    }
}
