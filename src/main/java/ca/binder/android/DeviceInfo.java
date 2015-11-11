package ca.binder.android;

import android.content.Context;
import android.provider.Settings;

/**
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class DeviceInfo {
    public static String deviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
