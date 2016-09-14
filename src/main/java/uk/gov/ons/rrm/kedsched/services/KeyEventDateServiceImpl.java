package uk.gov.ons.rrm.kedsched.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.gov.ons.rrm.kedsched.dao.KeyEventDateDAO;
import uk.gov.ons.rrm.kedsched.dao.exception.DAOException;
import uk.gov.ons.rrm.kedsched.entities.KeyEventDate;

/**
 * Implementation of the KeyEventDateService that uses Spring.
 * 
 * @author price
 *
 */
@Service
public final class KeyEventDateServiceImpl implements KeyEventDateService {

    /** Logger instance. */
    private static final Logger LOGGER = LogManager.getLogger();

    /** Data access object for the keyEventDateDAO repository. **/
    @Autowired
    private KeyEventDateDAO keyEventDateDAO;

    /**
     * Constructs the service with the data access object.
     * @param dao 
     */
    @Autowired
    public KeyEventDateServiceImpl(final KeyEventDateDAO dao) {
        this.keyEventDateDAO = dao;
    }
    
    /**
     * {@inheritDoc}
     * @throws DAOException 
     */
    @Transactional(rollbackFor = DAOException.class)
    public List<KeyEventDate> getKeyEventDateList(final boolean onlyActive, final int days) throws DAOException {
        LOGGER.entry(onlyActive);
        return LOGGER.exit(keyEventDateDAO.getKeyEventDateList(onlyActive, days));
    }
}
