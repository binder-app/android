package ca.binder.remote;

import android.content.Context;
import android.graphics.drawable.Drawable;

import ca.binder.domain.IPhoto;

/**
 * Represents an image which is downloaded from a remote server. Will block current thread on
 * {@link IPhoto#getDrawable(Context)} unless already {@link #downloaded()}
 *
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class RemotePhoto implements IPhoto {

    /**
     * Downloads image, blocks current thread
     */
    public void download() {
        //TODO
    }

    /**
     * @return true if image downloaded
     */
    public boolean downloaded() {
        //TODO
        return false;
    }

    @Override
    public Drawable getDrawable(Context context) {
        //TODO
        return null;
    }
}
