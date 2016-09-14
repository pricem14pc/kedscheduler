package uk.gov.ons.rrm.kedsched.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import uk.gov.ons.rrm.kedsched.services.KeyEventDateEnums;
import uk.gov.ons.rrm.kedsched.services.ScheduledTaskService;

/**
 * This class is invoked periodically by spring.
 * @author pricem
 *
 */
@Component
public class KeyEventDateTimerTask {

    /** The logger for this class. */
    private static final Logger LOGGER = LogManager.getLogger(KeyEventDateTimerTask.class);
    
    /** Injected with node active. */
    @Value("${ked.schedule.active:false}")
    private boolean active;
    
    /** ID for auditing what type of event happened. */
    private int eventID = KeyEventDateEnums.AUTOMATED_PROCEDURE;
    
    /** Service for handling the task executed by this scheduler. */
    @Autowired
    private ScheduledTaskService task;
    
    /** The executable method for this task. */
    @Scheduled(fixedRateString = "${ked.schedule.fixedRate.in.milliseconds}")
    public final void executeTask() {
        if (active) {
            LOGGER.info("Task is executing...");
            task.sendNotifications(eventID);
        }
    }
    
    /**
     * Setter for eventID, allows injecting of event type for auditing.
     * @param eventID - ID of event type.
     */
    public final void setEventID(final int eventID) {
        this.eventID = eventID;
    }

}
