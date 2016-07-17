/**
 * This is Spring Web services (not jax-ws)
 */
package ons.gov.uk.edc.rrm.kedsched.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SchedulerController {

	private static final Logger logger = LogManager.getLogger();

    public SchedulerController() {
    	logger.info("Creating SchedulerController()");
	}
    
    @RequestMapping(method = RequestMethod.GET)
    public String displaySchedule(ModelMap model) {
       model.addAttribute("message", "SchedulerController Spring MVC Framework!");
       return "scheduler";
    }
    
    
}
