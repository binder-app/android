package ca.binder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.binder.domain.Match;

/**
 * Created by SheldonCOMP4980 on 11/18/2015.
 */
public class ViewMatchesActivity extends Activity {

    private Button sendSMS;
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

        sendSMS = (Button)findViewById(R.id.sendSMSButton);
        reviewLater = (Button)findViewById(R.id.reviewLaterButton);
        userImage = (ImageView)findViewById(R.id.currentUserImageView);
        userName = (TextView)findViewById(R.id.currentUserNameLabel);
        matchImage = (ImageView)findViewById(R.id.matchedUserImageView);
        matchName = (TextView)findViewById(R.id.matchedUserNameLabel);

        //onClick for SEND SMS button
        sendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch SMS app with number set to match's phone number
                Match contact = matches.get(0);
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.fromParts("sms:", contact.getPhone(), null));
                startActivity(intent);
                matches.remove(0);
                showNewMatch();
            }
        });

        //onClick for REVIEW LATER button
        reviewLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Remove the first (current) Match from the list
                //showNewMatch will always show first Mathc
                matches.remove(0);
                showNewMatch();
            }
        });

        //TODO: Set user photo and name

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        matches = (ArrayList<Match>) extras.getSerializable("matches");
    }


    /**
     * Shows the person in the first position in the list of matches
     */
    private void showNewMatch() {
        matchName.setText(matches.get(0).getName());
        //matchImage.setImageDrawable(matches.get(0).getPhoto().getDrawable());
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
