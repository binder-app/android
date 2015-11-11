package ca.binder.remote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ca.binder.domain.Course;
import ca.binder.domain.Profile;
import ca.binder.remote.binding.ModelToJsonBinding;

/**
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class ProfileBinding implements ModelToJsonBinding<Profile> {
    @Override
    public JSONObject json(Profile object) {
        JSONObject json = new JSONObject();
        try {
            json.put("name", object.getName());
            json.put("bio", object.getBio());
            JSONArray courseArray = new JSONArray();
            for (Course course : object.getCourses()) {
                courseArray.put(course.getName());
            }
            json.put("courses", courseArray);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
