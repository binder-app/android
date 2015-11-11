package ca.binder.domain;

import android.graphics.drawable.Drawable;

/**
 * TODO
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class RemotePhoto implements IPhoto {

    /**
     * Downloads image, blocks current thread
     */
    @Override
    public void download() {

    }

    @Override
    public Drawable getDrawable() {
        return null;
    }
}
