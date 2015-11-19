package ca.binder.remote;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.util.Base64;
import ca.binder.domain.IPhoto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Represents an image. Includes utilities for converting to/from a Base64 String.
 *
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class Photo implements IPhoto {

    private final Bitmap bitmap;

    public Photo(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Photo(String base64) {
        byte[] decodedByte = Base64.decode(base64, 0);
        bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    @Override
    public String base64() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    @Override
    public Drawable drawable(Context context) {

        return new BitmapDrawable(context.getResources(), bitmap);
    }
}
