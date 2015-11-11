package ca.binder.domain;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public interface IPhoto {
    /**
     * Provides the {@link Drawable} format of the image
     * @return {@link Drawable}
     * @param context
     */
    Drawable getDrawable(Context context);
}
