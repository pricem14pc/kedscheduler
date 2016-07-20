/**
 * This is Spring Web services (not jax-ws)
 */
package ons.gov.uk.edc.rrm.kedsched.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SchedulerController {

	private static final Logger logger = LogManager.getLogger();

    public SchedulerController() {
    	logger.info("Creating SchedulerController()");
	}
    
    @RequestMapping(value="/listSchedule", method = RequestMethod.POST)
    public String displaySchedule(ModelMap model) {
       model.addAttribute("message", "SchedulerController::String displaySchedule()");
       return "scheduler";
    }
    
	@RequestMapping(value="/listSchedule", method = RequestMethod.GET)
    public ModelAndView displaySchedule(HttpServletRequest request, HttpServletResponse response) {
       ModelAndView modelAndView = 
    		   new ModelAndView("scheduler","message","SchedulerController::ModelAndView displaySchedule()"); 
       return modelAndView;
    }    
}
