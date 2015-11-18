package ca.binder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.binder.android.InternalPhoto;
import ca.binder.domain.Course;
import ca.binder.domain.CourseListManager;
import ca.binder.domain.ProfileBuilder;
import ca.binder.remote.Callback;
import ca.binder.remote.Server;
import ca.binder.remote.request.AsyncServerRequest;
import ca.binder.remote.request.UpdateProfileRequest;

/**
 * Created by SheldonCOMP4980 on 11/5/2015.
 */
public class ProfileCreationActivity extends Activity {

    private final int IMAGE_CAPTURE_REQUEST_CODE = 1;
    private ImageView uploadImageView;
    private InternalPhoto photo;
    private boolean photoTaken = false;
    private Dialog currentDialog;

    private CourseListManager courseListManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseListManager = CourseListManager.getInstance(this);

        setContentView(R.layout.profile_creation_activity_layout);

        uploadImageView = (ImageView)findViewById(R.id.add_user_image);
        uploadImageView.setImageDrawable(getResources().getDrawable(R.drawable.add_user));
        fillYearSpinner();

        uploadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, IMAGE_CAPTURE_REQUEST_CODE);
            }
        });
    }


    /**
     * Create list of years available and populate the spinner with those options
     */
    private void fillYearSpinner() {
        //Create array of options
        List<String> array = new ArrayList<>();
        array.add("Year 1");
        array.add("Year 2");
        array.add("Year 3");
        array.add("Year 4");
        array.add("Year 5");
        array.add("Year 6");

        //Create adapter using above array
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Use adapter to fill Spinner View
        Spinner spinner = (Spinner)findViewById(R.id.year_input);
        spinner.setAdapter(adapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_CAPTURE_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            photo = new InternalPhoto((Bitmap) extras.get("data"));
            uploadImageView.setImageDrawable(photo.getDrawable(this));

            photoTaken = true;
        }
    }


    /**
     * Called when the user presses their START BINDING button to commence binding
     * @param v - The Button View that was pressed to call this method
     */
    public void onFinishSetup(View v) {
        if (formValid()) {
            createProfile();
        }
    }

    /**
     * Check each input in form to ensure data meets requirements
     * @return true if form is valid and ready for submission
     */
    private boolean formValid() {
        if(!photoTaken){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "A profile picture must be taken!", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        Map<Integer, String> inputToError = new HashMap<>();
        inputToError.put(R.id.name_input, "Name cannot be empty!");
        inputToError.put(R.id.phone_no_input, "Phone Number cannot be empty!");
        inputToError.put(R.id.program_input, "Degree Program cannot be empty!");
        inputToError.put(R.id.course_list, "Class List cannot be empty!");

        for (Map.Entry<Integer, String> check : inputToError.entrySet()) {
            if (failsValidation(check.getKey(), check.getValue())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Creates Profile object using form data, begins API request
     */
    private void createProfile() {

        final ProfileBuilder builder = ProfileBuilder.start(this)
                .name(text(R.id.name_input))
                .phone(text(R.id.phone_no_input))
                .program(text(R.id.program_input))
                .year(text(R.id.year_input))
                .name(text(R.id.name_input))
                .bio(text(R.id.user_bio))
                .photo(photo);

        for (String course : text(R.id.course_list).split("\n")) {
            builder.course(new Course(course));
        }

        Server server = Server.standard(this);
        new AsyncServerRequest<>(this, server, new UpdateProfileRequest(builder.build()), new Callback() {
            @Override
            public void use(Object success) {
                //Executed after request finishes
                if ((Boolean) success) {
                    onProfileCreateSuccess();
                } else {
                    onProfileCreateFailure();
                }
            }
        }).run();
    }

    /**
     * Checks a textview to see if it is empty. If so, displays a brief {@link Toast}
     * @param textView input to validate
     * @param error string to show user if validation fails
     * @return true if validation failed for input afff
     */
    private boolean failsValidation(int textView, String error) {
        EditText toCheck = (EditText)findViewById(textView);
        if(toCheck.getText().length() == 0) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    error, Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }
        return false;
    }

    private String text(int inputId) {
        if (inputId == R.id.year_input) {
            return ((Spinner) findViewById(inputId)).getSelectedItem().toString();
        }
        return ((EditText)(findViewById(inputId))).getText().toString();
    }


    private void onProfileCreateFailure() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        currentDialog = builder.setMessage("Couldn't save profile. Are you connected to the Internet?")
                .setCancelable(false)
                .setPositiveButton("Let me check", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        currentDialog.cancel();
                    }
                })
                .create();
        currentDialog.show();
    }

    private void onProfileCreateSuccess() {
        Log.d("CreateProfileTask", "Profile created successfully!");
        Intent returnIntent = new Intent();
        returnIntent.putExtra("finished", true);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
