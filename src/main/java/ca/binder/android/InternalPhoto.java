package ca.binder.android;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.File;

import ca.binder.domain.IPhoto;

/**
 * A photo which is stored on the current device as a file.
 *
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class InternalPhoto implements IPhoto {

    private final Bitmap bitmap;

    public InternalPhoto(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public Drawable getDrawable(Context context) {
        return new BitmapDrawable(context.getResources(), bitmap);
    }
}
