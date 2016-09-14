package uk.gov.ons.rrm.kedsched.dao;

import uk.gov.ons.rrm.kedsched.entities.KeyEventDate;

/**
 * @author deale
 */
public interface AuditLogDAO {

    /**
     * Write to the Audit Log table.
     * 
     * @param message - the message to be logged in audit table
     * @param endpoint - the endpoint being call by the event being audited
     * @param entity - the KeyEventDate entity relating to the event
     * @param userID - ID for user performing the event being audited
     * @param eventID - reference for the type of event being audited
     */
    void createAuditLogRecord(final String message, final String endpoint, final KeyEventDate entity, final int userID, final int eventID);
}
