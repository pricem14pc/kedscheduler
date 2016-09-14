package uk.gov.ons.rrm.kedsched.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

/**
 * An implementation of {@link RestServiceInvoker} that wraps around Spring's RestTemplate.
 * @author thomas3
 *
 */
@Service
public final class RestServiceInvokerImpl implements RestServiceInvoker {
    
    /** Logger instance. */
    private static final Logger LOG = LogManager.getLogger(RestServiceInvokerImpl.class);
    
    /** Spring RestTemplate. */
    @Autowired
    private RestOperations restTemplate;
    
    /**
     * {@inheritDoc}
     */
    public <T> T postForObject(final String url, final Object request, final Class<T> responseType) {
        LOG.entry(url, request, responseType);
        return LOG.exit(restTemplate.postForObject(url, request, responseType));
    }

}
