package ca.binder.android;

import android.content.Context;
import android.graphics.drawable.Drawable;

import ca.binder.R;
import ca.binder.domain.IPhoto;

/**
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class NullPhoto implements IPhoto {
    @Override
    public Drawable getDrawable(Context context) {
        return context.getDrawable(R.drawable.default_photo);
    }
}
