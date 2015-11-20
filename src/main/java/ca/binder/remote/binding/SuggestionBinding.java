package ca.binder.remote.binding;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ca.binder.android.NullPhoto;
import ca.binder.domain.Course;
import ca.binder.domain.Suggestion;
import ca.binder.remote.Photo;

/**
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class SuggestionBinding implements JsonToModelBinding<Suggestion> {
    @Override
    public Suggestion model(JSONObject json) {
        try {
            String id = json.getString("id");
            String name = json.getString("name");
            String program = json.getString("program");
            String bio = json.getString("bio");
            String year = json.getString("year");
            String photo = json.getString("photo");

            JSONArray coursesArray = json.getJSONArray("courses");
            List<Course> courses = new ArrayList<>();
            for (int i = 0; i< coursesArray.length(); i++) {
                courses.add(new Course(coursesArray.get(i).toString()));
            }

            if (id == null || name == null || program == null || bio == null || year == null || photo == null) {
                Log.w("SuggestionBinding", "server didn't provide or returned null value");
                return null;
            }

            return new Suggestion(
                    id,
                    name, program,
                    bio, year, courses,
                    new Photo(photo)
            );
        } catch (JSONException e) {
            return null;
        }
    }
}
