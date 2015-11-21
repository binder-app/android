package ca.binder;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.binder.domain.Match;
import ca.binder.domain.Profile;
import ca.binder.remote.Photo;

/**
 * Created by SheldonCOMP4980 on 11/18/2015.
 */
public class ViewMatchesActivity extends Activity {

    private Button addContactButton;
    private Button reviewLater;
    private ImageView userImage;
    private TextView userName;
    private ImageView matchImage;
    private TextView matchName;

    private ArrayList<Match> matches;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_matches_activity_layout);

        addContactButton = (Button) findViewById(R.id.addContactButton);
        reviewLater = (Button)findViewById(R.id.reviewLaterButton);
        userImage = (ImageView)findViewById(R.id.currentUserImageView);
        userName = (TextView)findViewById(R.id.currentUserNameLabel);
        matchImage = (ImageView)findViewById(R.id.matchedUserImageView);
        matchName = (TextView)findViewById(R.id.matchedUserNameLabel);

        //onClick for SEND SMS button
        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch SMS app with number set to match's phone number
                Match contact = matches.get(0);
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, contact.getPhone()).putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).putExtra(ContactsContract.Intents.Insert.NAME, contact.getName()).putExtra(ContactsContract.Intents.Insert.NOTES, "Matched by Binder. " + contact.getYear() + " " + contact.getProgram());
                startActivity(intent);
            }
        });

        //onClick for REVIEW LATER button
        reviewLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Remove the first (current) Match from the list
                //showNewMatch will always show first Match
                matches.remove(0);
                showNewMatch();
            }
        });

        Profile userProfile = Profile.load(this);
        if(userProfile != null) {
            userName.setText(userProfile.getName());
            userImage.setImageDrawable(userProfile.getPhoto().drawable(this));
        } else {
            Log.e("ViewMatchesActivity", "Local user profile is null!");
        }

        //Get list of matches
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        matches = extras.getParcelable("matches");
        showNewMatch();
    }


    /**
     * Shows the person in the first position in the list of matches
     */
    private void showNewMatch() {
        if(matches.size() > 0) {
            matchName.setText(matches.get(0).getName());
            matchImage.setImageDrawable(matches.get(0).getPhoto().drawable(this));
            addContactButton.setText("Contact " + matches.get(0).getName());
        } else {
            onBackPressed();
        }
    }


    /**
     * Launch SuggestionViewActivity rather than returning to empty main activity
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SuggestionViewActivity.class);
        startActivity(intent);
    }

}
