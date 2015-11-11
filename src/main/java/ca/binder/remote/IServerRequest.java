package ca.binder.remote;

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
    T request(Server server);
}
