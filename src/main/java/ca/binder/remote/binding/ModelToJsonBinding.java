package ca.binder.remote.binding;

import org.json.JSONObject;

/**
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public interface ModelToJsonBinding<T> {
    /**
     * Returns json representation of object, or null if it fails to do so
     * @param object Java representation of object
     * @return json
     */
    JSONObject json(T object);
}
