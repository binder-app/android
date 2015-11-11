package ca.binder.domain;

import android.graphics.drawable.Drawable;

/**
 * A photo which is stored on the current device as a file.
 *
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class InternalPhoto implements IPhoto {
    @Override
    public Drawable getDrawable() {
        return null;
    }
}
