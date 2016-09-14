package uk.gov.ons.rrm.kedsched.dao;

import java.util.List;

import uk.gov.ons.rrm.kedsched.dao.exception.DAOException;
import uk.gov.ons.rrm.kedsched.entities.KeyEventDate;

/**
 * @author pricem.
 *
 */
public interface KeyEventDateDAO {

    /**
     * Retrieves a list of key event dates.
     * 
     * @param onlyActive - only return key events which have not completed or exceeded their retries
     * @param days - number of days +- to return events for. 0 returns all future
     * 
     * @return a list of key event dates
     * @throws DAOException 
     */
    List<KeyEventDate> getKeyEventDateList(boolean onlyActive, int days) throws DAOException;
}
