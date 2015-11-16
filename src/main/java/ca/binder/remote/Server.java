package ca.binder.remote;

import android.content.Context;
import ca.binder.android.DeviceInfo;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class Server {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String API_LOCATION = "http://getbinder.ddns.net/api/";

    private final OkHttpClient client;
    private final String apiLocation;
    private final String bearer;

    public Server(String apiLocation, String bearer) {
        this.apiLocation = apiLocation;
        this.bearer = bearer;
        this.client = new OkHttpClient();
    }

    public static Server standard(Context context) {
        return new Server(API_LOCATION, DeviceInfo.deviceId(context));
    }

    public Request.Builder request(String endpoint) {
        return new Request.Builder()
                .url(apiLocation + endpoint).header("Authorization", "Bearer " + bearer)
                .header("Content-Type", "application/json");
    }

    public Response execute(Request request) throws IOException {
        return client.newCall(request).execute();
    }
}
