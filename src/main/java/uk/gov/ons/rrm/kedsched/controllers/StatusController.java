package uk.gov.ons.rrm.kedsched.controllers;

import uk.gov.ons.rrm.kedsched.dto.ServiceStatus;

/**
 * An interface for the StatusControler.
 * 
 * @author pricem
 * 
 */
public interface StatusController {

    /**
     * Info about this service.
     * 
     * @return the service status.
     */
    ServiceStatus status();

}
