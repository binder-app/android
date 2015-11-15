package ca.binder.remote.binding;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import ca.binder.android.NullPhoto;
import ca.binder.domain.Suggestion;

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

            if (id == null || name == null || bio == null) {
                Log.w("SuggestionBinding", "server didn't provide or returned null value");
                return null;
            }

            return new Suggestion(
                    id,
                    name, program,
                    bio, year,
                    new NullPhoto() //TODO
            );
        } catch (JSONException e) {
            return null;
        }
    }
}
