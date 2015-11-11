package ca.binder.remote.binding;

import org.json.JSONException;
import org.json.JSONObject;

import ca.binder.domain.NullPhoto;
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
            String bio = json.getString("bio");

            if (id == null || name == null || bio == null) {
                return null;
            }

            return new Suggestion(
                    id,
                    name,
                    "TODO", //TODO
                    bio,
                    0, //TODO
                    new NullPhoto() //TODO
            );
        } catch (JSONException e) {
            return null;
        }
    }
}
