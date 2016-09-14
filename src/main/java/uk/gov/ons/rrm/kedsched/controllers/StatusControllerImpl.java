package uk.gov.ons.rrm.kedsched.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.gov.ons.rrm.kedsched.dto.ServiceStatus;

/**
 * REST controller to expose the status of this service.
 * 
 * @author pricem
 * 
 */
@RestController
@Scope("singleton")
public final class StatusControllerImpl implements StatusController {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = LogManager.getLogger(StatusControllerImpl.class);

    /**
     * The service status.
     */
    private ServiceStatus serviceStatus;

    /**
     * Constructor to provide autowired services.
     * 
     * @param serviceStatus
     *            the service status
     */
    @Autowired
    public StatusControllerImpl(final ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
        LOGGER.info("Creating StatusControllerImpl " + this.toString() + " :: " + this.serviceStatus);
    }

    /**
     * This simple mapping returns a string to report the service is available.
     * 
     * @return the service staus.
     */
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @ResponseBody
    public ServiceStatus status() {
        return LOGGER.exit(this.serviceStatus);
    }
}
