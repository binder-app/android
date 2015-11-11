package ca.binder.remote.request;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.binder.domain.Match;
import ca.binder.domain.Suggestion;
import ca.binder.remote.IServerRequest;
import ca.binder.remote.Server;
import ca.binder.remote.binding.MatchBinding;
import ca.binder.remote.binding.SuggestionBinding;

/**
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class GetMatchesRequest implements IServerRequest<List<Match>> {
    @Override
    public List<Match> request(Server server) {
        try {
            Request request = server.request("suggestions")
                    .get()
                    .build();
            Response response = server.execute(request);
            if (response.code() != 200) {
                return new ArrayList<>();
            }

            MatchBinding binding = new MatchBinding();
            List<Match> toReturn = new ArrayList<>();
            JSONArray json = new JSONArray(response.body().string());
            for (int i = 0; i < json.length(); i++) {
                Match match = binding.model(json.getJSONObject(i));
                if (match == null) {
                    continue;
                }
                toReturn.add(match);
            }
            return toReturn;
        } catch (IOException e) {
            return new ArrayList<>();
        } catch (JSONException e) {
            return new ArrayList<>();
        }
    }
}
