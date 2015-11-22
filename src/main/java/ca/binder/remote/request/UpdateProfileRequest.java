package ca.binder.remote.request;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import org.json.JSONObject;

import java.io.IOException;

import ca.binder.domain.Profile;
import ca.binder.remote.Server;
import ca.binder.remote.binding.ProfileBinding;

/**
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class UpdateProfileRequest implements IServerRequest<Boolean> {

    private final Profile profile;

    public UpdateProfileRequest(Profile profile) {
        this.profile = profile;
    }

    @Override
    public Object request(Server server) {
        JSONObject json = new ProfileBinding().json(profile);
        try {
            RequestBody body = RequestBody.create(Server.JSON, json.toString());
            Request request = server.request("profiles/" + profile.getId())
                    .put(body)
                    .build();
            return server.execute(request).code() == 200;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
