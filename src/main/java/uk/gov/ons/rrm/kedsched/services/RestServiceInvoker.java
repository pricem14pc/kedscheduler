package uk.gov.ons.rrm.kedsched.services;

/**
 * Interface with useful methods to invoke REST service endpoints.
 * @author thomas3
 *
 */
public interface RestServiceInvoker {
    
    /**
     * Issues a POST request to a URL and returns an object of type T.
     * @param url           The URL of the rest endpoint.
     * @param request       The request params.
     * @param responseType  The expected type of the response object.
     * @param <T>           The response object type.
     * @return  A response object of type T.
     */
    <T> T postForObject(final String url, final Object request, Class<T> responseType);

}
