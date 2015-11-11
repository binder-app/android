package ca.binder.domain;

import android.graphics.drawable.Drawable;

/**
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public interface IPhoto {
    /**
     * Downloads the photo from remote resource
     */
    void download();

    /**
     * Provides the {@link Drawable} format of the image
     * @return {@link Drawable}
     */
    Drawable getDrawable();
}
