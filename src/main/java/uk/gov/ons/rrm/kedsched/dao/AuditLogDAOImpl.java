package uk.gov.ons.rrm.kedsched.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import uk.gov.ons.rrm.kedsched.entities.KeyEventDate;
// import uk.gov.ons.rrm.services.exception.RRMServiceGeneralException;

/**
 * @author deale
 */
@Repository
public class AuditLogDAOImpl implements AuditLogDAO {

    /** The logger instance. **/
    private static final Logger LOGGER = LogManager.getLogger();

    /** Audit Log insert SQL. */
    private static final String INSERT_INTO_AUDIT_LOG_SQL = "INSERT INTO edc_audit_log(userid, timedatestamp, "
            + "ipaddress, eventdetail, event_id, severity_id, codeaddress, "
            + "version) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    /** Template for executing JDBC queries. */
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * Write to the Audit Log table.
     * 
     * @param message - the message to be logged in audit table
     * @param endpoint - the endpoint being called by the action
     * @param ked - the KeyEventDate entity relating to the event
     * @param userID - ID for user performing the event being audited
     * @param eventID - reference for the type of event being audited
     */
    public final void createAuditLogRecord(final String message, final String endpoint, final KeyEventDate ked, final int userID, final int eventID) {
        LOGGER.entry(message, endpoint, ked, userID, eventID);

        int rowsUpdated = 0;
        final String details = buildDetails(ked, endpoint);

        try {
            LOGGER.debug("Writing to audit log table with data: " + details);

            final String msg = message + details;
            rowsUpdated = jdbcTemplate.update(INSERT_INTO_AUDIT_LOG_SQL,
                    new Object[] { userID, new Date(), 0,
                                   msg, eventID, 1, 
                                   0, 1, });
        } catch (final DataAccessException e) {
            LOGGER.error("Exception writing to audit table: ", e);
            throw e;
        }

        if (rowsUpdated == 0) {
            // final RRMServiceGeneralException ge = new RRMServiceGeneralException(
        	final Exception ge = new Exception(
                    "No records written to audit log");
            LOGGER.error(ge);
            return;
        }

        LOGGER.exit();
    }
    
    /**
     * Build a map of the details relevant for auditing to form part of the stored message.
     * @param ked - Key Event Date details for the event being recorded
     * @param endpoint - optional endpoint if this event is going to call the survey service.
     * @return details - a stringed key-value map of relevant event details for auditing.
     */
    private String buildDetails(final KeyEventDate ked, final String endpoint) {
        final Map<String, String> details = new HashMap<String, String>();
        details.put("ceid", ked.getCollectionExerciseSid().toString());
        details.put("eventKeySid", ked.getEventKeySid().toString());
        details.put("eventDate", ked.getEventDate().toString());
        details.put("status", ked.getCurrentProcessStateDescription());

        if (endpoint != null) {
            details.put("endpoint", endpoint);
        }
        
        return details.toString();
    }
}
