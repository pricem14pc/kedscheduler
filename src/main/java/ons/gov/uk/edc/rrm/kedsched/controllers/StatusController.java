/**
 * This is Spring Web services (not jax-ws)
 */
package ons.gov.uk.edc.rrm.kedsched.controllers;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

	private static final Logger logger = LogManager.getLogger();

    private final AtomicLong counter = new AtomicLong();
    private final Date upDate = new Date();

    public StatusController() {
    	logger.info("Creating StatusController()");
	}
    
    /**
     * This simple mapping returns a string to report the service is available
     * @param msg
     * @return
     */
    @RequestMapping("/basic")
    public String status(@RequestParam(value="msg", defaultValue="foo") String msg) {
       return logger.exit("RRM Key Even Date Scheduler Running... No. status requests: "
        		.concat(Long.toString(counter.incrementAndGet())
        		.concat(" msg: ").concat(msg)
        		.concat(" up since: ")
        		.concat(upDate.toString())));
    }
	
}
