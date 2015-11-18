package ca.binder.remote.request;

import ca.binder.remote.Server;

/**
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public interface IServerRequest<T> {
    /**
     * Makes a request to the server
     * @param server makes physical request
     * @return response
     */
    Object request(Server server);
}
