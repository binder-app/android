package ca.binder.remote.request;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;

import ca.binder.remote.IServerRequest;
import ca.binder.remote.Server;

/**
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class LikeSuggestionRequest implements IServerRequest<Boolean> {

    private final String likedId;

    public LikeSuggestionRequest(String likedId) {
        this.likedId = likedId;
    }

    @Override
    public Boolean request(Server server) {
        try {
            RequestBody body = RequestBody.create(Server.JSON, "{\"to\": \"" + likedId + "\"}");
            Request request = server.request("likes")
                    .post(body)
                    .build();
            return server.execute(request).code() == 200;
        } catch (IOException e) {
            return false;
        }
    }
}
