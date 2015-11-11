package ca.binder;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * @author Mitchell Hentges
 * @since 11/6/2015
 */
public class BinderApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
