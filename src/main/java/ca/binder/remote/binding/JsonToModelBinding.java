package ca.binder.remote.binding;

import org.json.JSONObject;

/**
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public interface JsonToModelBinding<T> {
    /**
     * Returns Java object, once converted from json
     * @param json json representation of object
     * @return Java object
     */
    T model(JSONObject json);
}
