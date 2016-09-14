package uk.gov.ons.rrm.kedsched.services;

/**
 * @author deale
 */
public interface ScheduledTaskService {

    /** Retrieve records from db and send any requests warranted to the survey-service.
     * 
     * @param eventID - ID for type of scheduling event for audit purposes.
     */
    void sendNotifications(int eventID);

}
