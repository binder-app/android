package ca.binder;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by mitch4980 on 11/6/2015.
 */
public class BinderApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
