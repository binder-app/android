package ca.binder;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.hardware.camera2.CaptureRequest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SheldonCOMP4980 on 11/5/2015.
 */
public class FirstLaunchActivity extends Activity {

    final String LOG_HEADER = "MYLOG###";

    ImageView uploadImageView;
    private Uri photopath;
    private final int IMAGE_CAPTURE_REQUEST_CODE = 9999;

    File photo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_launch_activity_layout);

        uploadImageView = (ImageView)findViewById(R.id.add_user_image);
        uploadImageView.setImageDrawable(getDrawable(R.drawable.add_user));
        setupOnClickListenerForImage();

        fillYearSpinner();
    }


    /**
     * Create list of years available and populate the spinner with those options
     */
    private void fillYearSpinner() {
        //Create array of options
        List<String> array = new ArrayList<>();
        array.add("1st");
        array.add("2nd");
        array.add("3rd");
        array.add("4th");
        array.add("5th");
        array.add("6th");

        //Create adapter using above array
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Use adapter to fill Spinner View
        Spinner spinner = (Spinner)findViewById(R.id.year_input);
        spinner.setAdapter(adapter);
    }


    private void setupOnClickListenerForImage() {
        uploadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                File file = new File(Environment.getExternalStorageDirectory(), "okokok.jpg");
                photopath = Uri.fromFile(file);
                Log.v(LOG_HEADER, photopath.toString());
                if(photopath != null){
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photopath);
                }
                startActivityForResult(intent, IMAGE_CAPTURE_REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == IMAGE_CAPTURE_REQUEST_CODE) {
            if(resultCode == RESULT_OK && data != null) {
                photo = BinderUtilities.findImageFileFromUri(photopath, this);

                Bitmap bitmap = BinderUtilities.convertFileToBitmap(photo);
                uploadImageView.setImageBitmap(bitmap);
                //TODO: Turn photo into image that uploadImageView can use
            }
            else {
                Toast.makeText(this, "Photo was not taken", Toast.LENGTH_SHORT);
            }
        }
    }


    /**
     * Called when the user presses their START BINDING button to commence binding
     * @param v - The Button View that was pressed to call this method
     */
    public void onFinishSetup(View v) {
        //TODO: Define methods: validateForms, createProfile
        //TODO: Only finish() if above methods return true
//        if(validateForms()) {
//            if(createProfile()) {
//                //finish
//            }
//        }
        Intent returnIntent = new Intent();
        returnIntent.putExtra("finished", true);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }


    /**
     * Check each form to ensure data meets requirements
     * @return
     */
    private boolean validateForms() {
        EditText checkText;

        //Check photo
        if(photo == null){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "A picture must be taken!", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        //Check name
        checkText = (EditText)findViewById(R.id.name_input);
        if(checkText.getText().length() == 0) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Name cannot be empty!", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        //Check phone number
        checkText = (EditText)findViewById(R.id.phone_no_input);
        if(checkText.getText().length() == 0) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Phone Number cannot be empty!", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        //Check degree
        checkText = (EditText)findViewById(R.id.degree_input);
        if(checkText.getText().length() == 0) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Degree Program cannot be empty!", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        //Check class list
        checkText = (EditText)findViewById(R.id.class_list);
        if(checkText.getText().length() == 0) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Class List cannot be empty!", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        return true;
    }


    private boolean createProfile() {

        return false;
    }

    //Ensure back does not navigate away from profile creation page
    @Override
    public void onBackPressed() {
    }
}
