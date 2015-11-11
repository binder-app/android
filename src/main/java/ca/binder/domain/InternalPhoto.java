package ca.binder.domain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.File;

/**
 * A photo which is stored on the current device as a file.
 *
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class InternalPhoto implements IPhoto {

    private final File file;
    private final Context context;

    public InternalPhoto(File file, Context context) {
        this.file = file;
        this.context = context;
    }

    @Override
    public Drawable getDrawable(Context context) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        return new BitmapDrawable(this.context.getResources(), bitmap);
    }
}
