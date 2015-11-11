package ca.binder.domain;

import android.graphics.drawable.Drawable;

/**
 * Represents an image which is downloaded from a remote server. Will block current thread on
 * {@link #getDrawable()} unless already {@link #downloaded()}
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
    public Drawable getDrawable() {
        //TODO
        return null;
    }
}
