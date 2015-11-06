package ca.binder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by SheldonCOMP4980 on 11/5/2015.
 */
public class FirstLaunchActivity extends Activity{

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_launch_activity_layout);
    }


    /**
     * Called when the user presses their START BINDING button to commence binding
     * @param v - The Button View that was pressed to call this method
     */
    private void onFinishSetup(View v){
        //TODO: Define methods: validateForms, createProfile
        //TODO: Only finish() if above methods return true
        if(validateForms()){
            if(createProfile()){
                //finish
            }
        }
        Intent returnIntent = new Intent();
        returnIntent.putExtra("finished", true);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private boolean validateForms(){
        return false;
    }

    private boolean createProfile(){
        return false;
    }
}
