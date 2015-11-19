package ca.binder.remote;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import ca.binder.domain.IPhoto;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Represents an image which is downloaded from a remote server. Will block current thread on
 * {@link IPhoto#getDrawable(Context)} unless already {@link #downloaded()}
 *
 * To explicitly handle download exceptions, invoke {@link #download(Context)} manually. The implicit
 * download on {@link #getDrawable(Context)} will eat such an exception and possibly return null
 *
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class RemotePhoto implements IPhoto {

    private final String url;
    private Drawable drawable = null;

    public RemotePhoto(String url) {
        this.url = url;
    }

    /**
     * Downloads image, blocks current thread
     */
    public void download(Context context) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();
        drawable = new BitmapDrawable(context.getResources(), BitmapFactory.decodeStream(input));

    }

    /**
     * @return true if image downloaded
     */
    public boolean downloaded() {
        return drawable != null;
    }

    @Override
    public Drawable getDrawable(Context context) {
        if (!downloaded()) {
            try {
                download(context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return drawable;
    }
}
