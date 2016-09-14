package uk.gov.ons.rrm.kedsched.controllers;

import java.util.List;

import uk.gov.ons.rrm.kedsched.entities.KeyEventDate;

/**
 * Interface for the schedule controller.
 * 
 * @author pricem
 * 
 */
public interface ScheduleController {

    /**
     * Return a list of schedule.
     * 
     * @return list of schedules
     */
    List<KeyEventDate> listSchedule();

}
