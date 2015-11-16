package ca.binder.remote.request;

import ca.binder.remote.Callback;
import ca.binder.remote.Server;

/**
 * Created by mitch on 16/11/15.
 */
public class AsyncServerRequest<T> {

    private final Server server;
    private final IServerRequest<T> request;
    private final Callback<T> callback;

    public AsyncServerRequest(Server server, IServerRequest<T> request, Callback<T> callback) {
        this.server = server;
        this.request = request;
        this.callback = callback;
    }

    public void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                callback.use(request.request(server));
            }
        }).start();
    }
}
