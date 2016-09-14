package uk.gov.ons.rrm.kedsched.services;

import java.util.List;

import uk.gov.ons.rrm.kedsched.dao.exception.DAOException;
import uk.gov.ons.rrm.kedsched.entities.KeyEventDate;

/**
 * Interface for the ce service.
 * @author pricem
 *
 */
public interface KeyEventDateService {

    /**
     * Retrieves the list of key event dates.
     * 
     * @param onlyActive - only return key events which have not completed or exceeded their retries
     * @param days - number of days +- to return events for. 0 returns all past
     * @return A List of key event dates.
     * @throws DAOException 
     */
    List<KeyEventDate> getKeyEventDateList(boolean onlyActive, int days) throws DAOException;

}
