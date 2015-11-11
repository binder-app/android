package ca.binder.remote.binding;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import ca.binder.domain.Match;

/**
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class MatchBinding implements JsonToModelBinding<Match> {
    @Override
    public Match model(JSONObject json) {
        try {
            String name = json.getString("name");
            String phone = json.getString("phone");

            if (name == null || phone == null) {
                Log.w("MatchBinding", "server didn't provide or returned null value");
                return null;
            }

            return new Match(
                    name,
                    phone
            );
        } catch (JSONException e) {
            return null;
        }
    }
}
