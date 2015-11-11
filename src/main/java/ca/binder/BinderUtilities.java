package ca.binder;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by SheldonCOMP4980 on 11/11/2015.
 */
public class BinderUtilities {

    /**
     * Will allow the user to derive a File object from a URI location
     * @param uri       - URI of the file, showing storage location, etc.
     * @param context   - Context of the calling activity
     * @return
     */
    public static File findImageFileFromUri(Uri uri, Context context) {
        Cursor cursor = null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(
                    uri, projection, null, null, null);

            if(cursor.moveToFirst()){
                int col_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                File file = new File(cursor.getString(col_index));
                return file;
            }
            return null;
        } finally {
            if(cursor != null){
                cursor.close();
            }
        }

    }

    /**
     *
     * @param file  - The file we will convert to a bitmap object
     * @return
     */
    public static Bitmap convertFileToBitmap(File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

        return bitmap;
    }
}
