package ca.binder.remote.binding;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ca.binder.domain.Course;
import ca.binder.domain.Match;
import ca.binder.remote.Photo;

/**
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class MatchBinding implements JsonToModelBinding<Match> {
    @Override
    public Match model(JSONObject json) {
        try {
            String name = json.getString("name");
			String bio = json.getString("bio");
			String phone = json.getString("phone");
			String program = json.getString("program");
			String year = json.getString("year");
            String photo = json.getString("photo");

			JSONArray courseArray = json.getJSONArray("courses");
			List<Course> coursesList = new ArrayList<>();
			for (int i = 0; i < courseArray.length(); i++) {
				coursesList.add(new Course(courseArray.get(i).toString()));
			}

            if (name == null || bio == null || phone == null || program == null || year == null || photo == null) {
                Log.w("MatchBinding", "server didn't provide or returned null value");
                return null;
            }

            return new Match(
                    name, bio, phone, program, year, new Photo(photo), coursesList);
        } catch (JSONException e) {
            return null;
        }
    }
}
