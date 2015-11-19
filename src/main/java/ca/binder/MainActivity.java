package ca.binder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ca.binder.domain.Match;
import ca.binder.domain.Suggestion;
import ca.binder.domain.SuggestionReaction;
import ca.binder.remote.Callback;
import ca.binder.remote.Server;
import ca.binder.remote.request.AsyncServerRequest;
import ca.binder.remote.request.GetMatchesRequest;

/**
 * Created by SheldonCOMP4980 on 11/5/2015.
 */
public class MainActivity extends Activity {

    final String LOG_HEADER = "MYLOG###";

    private final int FIRST_LAUNCH_REQUEST = 12345;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        maybeLaunchFirstLaunchLaunch();
        checkForMatches();
    }

    /**
     * Check to see if it is the first launch
     */
    private void maybeLaunchFirstLaunchLaunch() {

        //Check to see if the ProfileCreationActivity has ever completed
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = sharedPreferences.getBoolean(getString(R.string.previously_started), false);

        if (!previouslyStarted) {
            // first launch / profile creation
            Intent intent = new Intent(this, ProfileCreationActivity.class);
            startActivityForResult(intent, FIRST_LAUNCH_REQUEST);
        } else {
            // start viewing suggestions
            startActivity(new Intent(this, SuggestionViewActivity.class));
            finish();
        }

    }

    /**
     *
     * @param requestCode   - Code used to identify where the startActivityForResult is called from
     * @param resultCode    - Identifies if the activity completed (RESULT_OK, _CANCELLED, etc)
     * @param data          - Data passed back from the activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == FIRST_LAUNCH_REQUEST && data != null){   //Handle ProfileCreationActivity result
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            SharedPreferences.Editor edit = sharedPreferences.edit();

            //Update PREVIOUSLY_STARTED field in SharedPrefs, ProfileCreationActivity should not start anymore
            edit.putBoolean(getString(R.string.previously_started), data.getBooleanExtra("finished", false));
            edit.commit();
            Intent intent = new Intent(this, SuggestionViewActivity.class);
            startActivity(intent);
            finish();
        }
    }

    //Ensure back does not navigate back to other pages, not necessary for functionality
    @Override
    public void onBackPressed() {
    }


    /**
     * Check to see if the user has any matches
     */
    private void checkForMatches() {
        Server server = Server.standard(this);
        new AsyncServerRequest<>(this, server, new GetMatchesRequest(), new Callback() {
            //Called after request finishes
            @Override
            public void use(Object success) {
                if(!((ArrayList<Match>)success).isEmpty()) {
                    ArrayList<Match> matchList = (ArrayList<Match>) success;
                    Intent intent = new Intent(getBaseContext(), ViewMatchesActivity.class);
                    intent.putExtra("matches", matchList);
                    startActivity(intent);
                }
                else {
                    if(!((Boolean)success)) {
                        onGetMatchesFailure();
                    }
                    Intent intent = new Intent(getBaseContext(), SuggestionViewActivity.class);
                    startActivity(intent);
                }
            }
        }).run();
    }


    /**
     * Handler when a get matches request fails
     */
    private void onGetMatchesFailure() {
        AlertDialog alertDialog;
        Log.e("Main Activity", "Get Matches request failed");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        alertDialog = builder.setMessage("Couldn't retrieve matches. Are you connected to the Internet?").setCancelable(false).setPositiveButton("Let me check", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        }).create();
        alertDialog.show();
    }
}
