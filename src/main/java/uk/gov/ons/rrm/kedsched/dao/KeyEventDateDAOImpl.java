package uk.gov.ons.rrm.kedsched.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import uk.gov.ons.rrm.kedsched.dao.exception.DAOException;
import uk.gov.ons.rrm.kedsched.entities.KeyEventDate;

/**
 * @author pricem
 * 
 */
@Repository
public final class KeyEventDateDAOImpl implements KeyEventDateDAO {

    /** The logger instance. **/
    private static final Logger LOGGER = LogManager.getLogger();

    /** Results set of key event dates. */
    private static final String BASE_SQL = "SELECT "                
        + "sur.survey_id, "
        + "sur.surveyname, "
        + "ckd.collection_exercise_sid, "
        + "coe.collection_exercise_label, "
        + "coe.display_name, "
        + "coe.current_state_id, "
        + "coe.current_state_desc, "
        + "sur.survey_sid, "
        + "ckd.event_key_sid, "
        + "ckd.event_type_id, "
        + "cket.event_type_description, "
        + "ckd.event_date, "
        + "ckd.current_process_state_id, "
        + "ckd.current_process_state_desc, "
        + "ckd.current_process_state_as_at, "
        + "ckd.attempt_count "
        + "FROM edc_collection_key_date as ckd "
        + "JOIN edc_collection_key_event_type as cket ON cket.event_type_id = ckd.event_type_id "
        + "JOIN edc_collection_exercise coe ON coe.collection_exercise_sid = ckd.collection_exercise_sid "
        + "JOIN edc_survey sur ON sur.survey_sid = coe.survey_sid ";
    
    /** The persistence context instance. **/
    @PersistenceContext
    private final EntityManager entityManager;
    
    /**
     * Constructs the DAO with a persistence context.
     * 
     * @param entityManager
     *            The entity manager.
     */
    @Autowired
    public KeyEventDateDAOImpl(final EntityManager entityManager) {
        LOGGER.debug("Constructing KeyEventDateDAOImpl, about to set entityManager");
        this.entityManager = entityManager;
    }

    /**
     * {@inheritDoc}
     * @throws DAOException 
     */
    @SuppressWarnings("unchecked")
    public List<KeyEventDate> getKeyEventDateList(final boolean onlyActive, final int days) throws DAOException {
        LOGGER.debug("Entering getKeyEventDateList");

        List<KeyEventDate> data = new ArrayList<KeyEventDate>();

        try {
            final String sql = buildQuery(onlyActive, days);

            final Query query = entityManager.createNativeQuery(sql, KeyEventDate.class);
            data = query.getResultList();

        } catch (final HibernateException he) {
            LOGGER.error("Error in getKeyEventDateList.", he);
            throw new DAOException("An error occurred retrieving list of key event dates", he);
        }

        return LOGGER.exit(data);
    }
    
    /**
     * Add any additional constraints for SQL query.
     * @param onlyActive - filter on entries which may need to be processed
     * @param days - number of days' worth to return in the query
     * @return - string containing full SQL query to be performed
     */
    private String buildQuery(final boolean onlyActive, final int days) {
        LOGGER.entry(onlyActive, days);
        LOGGER.debug("Building SQL query now - excluding completed items: [" + onlyActive + "] list spanning [" + days + "] days");
        String options = "WHERE (ckd.event_type_id in ('1','5','6')) "; //only MPS, Go Live and Reminders

        if (onlyActive) { //only return rows which can still have an action performed on them
            options += "AND ((ckd.current_process_state_id in ('1','2')) "; //Waiting or In progress
            options += "OR ";
            options += "(ckd.current_process_state_id in ('4','5'))) "; //Completed with failures or Failed with remaining retries
            options += "AND ";
            options += "(ckd.attempt_count < cket.attempt_count_limit)"; //Have not exceeded retries
        }

        if (days == 0) { //only return events in the past
            options += "AND (ckd.event_date < NOW()) ";
        } else { //return a range of several days around current
            options += "AND ((ckd.event_date < (NOW() + INTERVAL '" + days + " DAY')) ";
            options += "AND (ckd.event_date > (NOW() - INTERVAL '" + days + " DAY'))) ";
        }
         
        options += "ORDER BY ckd.event_date ";
        options += ", ckd.collection_exercise_sid";

        return LOGGER.exit(BASE_SQL + options);
    }

}
