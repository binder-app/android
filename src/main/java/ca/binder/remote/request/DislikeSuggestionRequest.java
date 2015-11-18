package ca.binder.remote.request;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;

import ca.binder.remote.Server;

/**
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class DislikeSuggestionRequest implements IServerRequest<Boolean> {

    private final String dislikedId;

    public DislikeSuggestionRequest(String dislikedId) {
        this.dislikedId = dislikedId;
    }

    @Override
    public Object request(Server server) {
        try {
            RequestBody body = RequestBody.create(Server.JSON, "{\"to\": \"" + dislikedId + "\"}");
            Request request = server.request("dislikes")
                    .post(body)
                    .build();
            return server.execute(request).code() == 200;
        } catch (IOException e) {
            return false;
        }
    }
}
