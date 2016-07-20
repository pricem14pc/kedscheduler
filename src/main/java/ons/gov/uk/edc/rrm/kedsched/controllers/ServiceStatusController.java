/**
 * This is Spring Web services (not jax-ws)
 */
package ons.gov.uk.edc.rrm.kedsched.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceStatusController {

	private static final Logger logger = LogManager.getLogger();

    public ServiceStatusController() {
    	logger.info("Creating StatusController()");
	}
    
    /**
     * This simple mapping returns a string to report the service is available
     * @return 
     * @return
     */
    @RequestMapping(value="/showStatus", method = RequestMethod.GET)
    public @ResponseBody ServiceStatus status() {
       return logger.exit(ServiceStatus.getInstance());
    }
}
