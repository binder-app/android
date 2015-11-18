package ca.binder.remote.request;

import android.app.Activity;

import ca.binder.remote.Callback;
import ca.binder.remote.Server;

/**
 * Created by mitch on 16/11/15.
 */
public class AsyncServerRequest<T> {

    private final Activity activity;
    private final Server server;
    private final IServerRequest<T> request;
    private final Callback callback;

    public AsyncServerRequest(Activity activity, Server server, IServerRequest<T> request, Callback callback) {
        this.activity = activity;
        this.server = server;
        this.request = request;
        this.callback = callback;
    }

    public void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Object obj = request.request(server);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.use(obj);
                    }
                });
            }
        }).start();
    }
}
