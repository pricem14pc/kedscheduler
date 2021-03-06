package uk.gov.ons.rrm.kedsched.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import uk.gov.ons.rrm.kedsched.dao.exception.DAOException;
import uk.gov.ons.rrm.kedsched.services.KeyEventDateService;

/**
 * REST controller to expose the scheduler.
 * 
 * @author pricem
 * 
 */
@RestController
@Scope("singleton")
public class ScheduleControllerImpl {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = LogManager.getLogger(ScheduleControllerImpl.class);

    /** Number of days either side of today to display the schedule for. */
    @Value("${ked.schedule.list.days:5}")
    private Integer numDaysDisplay;

    /** Toggle to determine whether to return only entries which have outstanding actions. */
    @Value("${ked.schedule.list.active:false}")
    private Boolean onlyDisplayActive;
  
    /**
     * Service to access Key Event Dates.
     */
    @Autowired
    private KeyEventDateService keyEventDateService;

    /**
     * Constructor to provide autowired services.
     * 
     * @param keyEventDateService
     *            the key event date service
     */
    @Autowired
    public ScheduleControllerImpl(final KeyEventDateService keyEventDateService) {
        this.keyEventDateService = keyEventDateService;
        LOGGER.info("Creating ScheduleControllerImpl... ");
    }

    /**
     * This mapping returns a list of key event dates. This is now development
     * POC intended to hit db. This will be retired.
     * 
     * @return a list of the schedule
     */
    @RequestMapping(value = "/listSchedule", method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public final ModelAndView listSchedule() {
        LOGGER.entry();

        final ModelMap model = new ModelMap();

        try {
            model.put("keyEventDateList", keyEventDateService.getKeyEventDateList(onlyDisplayActive, numDaysDisplay));
        } catch (final DAOException e) {
            LOGGER.error("Error retrieving list of key event dates.", e);
        }

        return LOGGER.exit(new ModelAndView("listSchedule", model));
    }
    
   
}
