package uk.gov.ons.rrm.kedsched.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import uk.gov.ons.rrm.kedsched.controllers.ScheduleControllerImpl;
import uk.gov.ons.rrm.kedsched.dao.AuditLogDAO;
import uk.gov.ons.rrm.kedsched.dao.exception.DAOException;
import uk.gov.ons.rrm.kedsched.entities.KeyEventDate;

/**
 * @author deale
 */
@Service
public class ScheduledTaskServiceImpl implements ScheduledTaskService {

    /** The logger for this class. */
    private static final Logger LOGGER = LogManager.getLogger(ScheduleControllerImpl.class);
    
    /** Format for the survey service URL. */
    private static final String SURVEY_SERVICE_URL_TEMPLATE = "%s%s/%s/%s/12/123";
    
    /** DAO for creating and storing audit events in db table. */
    @Autowired
    private AuditLogDAO audit;

    /** Host URL for the survey service. */
    @Value("${ked.survey.url}")
    private String surveyServiceUrlHost;

    /** Endpoint for the survey service. */
    @Value("${ked.survey.url.context}")
    private String surveyServiceUrlEndpoint;
    
    /** Service to access Key Event Dates. */
    @Autowired
    private KeyEventDateService keyEventDateService;

    /** Service that invokes the survey service endpoint. */
    @Autowired
    private RestServiceInvoker client;
    
    /** Constructor with service to access Key Event Dates.
     * @param keyEventDateService - autowired service for accessing KeyEventDates
     */
    @Autowired
    public ScheduledTaskServiceImpl(final KeyEventDateService keyEventDateService) {
        this.keyEventDateService = keyEventDateService;
    }
 

    /** 
     * Checks the database for the next chronological trigger and decides what action to take.
     * 
     * @param eventID - ID for type of event trigger.
     */
    public final void sendNotifications(final int eventID) {
        LOGGER.debug("Beginning scheduled task - checking for key event dates requiring action");
        final KeyEventDate ked = retrieveKeyEventDate(eventID);
        if (ked == null) {
            return;
        }

        final String endpoint = determineEndpoint(ked.getEventTypeId());
        audit.createAuditLogRecord("Making request to survey service with params: ", endpoint, ked, 0, eventID);

        final String url = String.format(SURVEY_SERVICE_URL_TEMPLATE, surveyServiceUrlHost, surveyServiceUrlEndpoint,
                endpoint, ked.getCollectionExerciseSid());

        String result = null;
        try {
            LOGGER.info("Calling survey service with url: {}", url);
            result = client.postForObject(url, null, String.class);
        } catch (final RestClientException ex) {
            LOGGER.error(String.format("Error invoking REST endpoint. url: %s", url), ex);
            result = ex.getMessage();
        } finally {
            LOGGER.info("result: {}", result);
        }

    }
    
    /** Get any key event dates which have outstanding actions and
     *  either log when nothing to do or return the KeyEventDate.
     *  
     *  @param eventID - ID for type of event trigger.
     *  @return KeyEventDate
     */
    private KeyEventDate retrieveKeyEventDate(final int eventID) {
        KeyEventDate ked = null;
        List<KeyEventDate> list = null;
        try {
            list = keyEventDateService.getKeyEventDateList(true, 0);
        } catch (final DAOException de) {
            LOGGER.error(de);
            return null;
        }
        
        if (list.isEmpty()) {
            LOGGER.debug("Done - No KEDs waiting to be processed");
            return null;
        }
        
        ked = list.get(0);
        LOGGER.debug("Retrieved KED: " + ked.toString());
        
        //new query for safety
        if (ked.getCurrentProcessStateId() == KeyEventDateEnums.PROCESS_STATE_IN_PROGRESS) {
            LOGGER.debug("Done - KED for collection exercise [" + ked.getCollectionExerciseSid() + "] already in progress.");
            audit.createAuditLogRecord("KED already in progress", null, ked, 0, eventID);
            return null;
        }

        return ked;
    }

    /** Determine which endpoint on the survey service is required to fulfil the key event date action.
     * 
     * @param eventType - an ID for a key event type
     * @return route to be accessed on survey service
     */
    private String determineEndpoint(final int eventType) {
        String endpoint = null;
        switch (eventType) {
            case KeyEventDateEnums.MAIN_PRINT_SELECTION_EVENT_ID : 
                endpoint = KeyEventDateEnums.GENERATE_LETTERS_ROUTE;
                break;
            case KeyEventDateEnums.GO_LIVE_EVENT_ID:
                endpoint = KeyEventDateEnums.GENERATE_EMAILS_ROUTE;
                break;
            case KeyEventDateEnums.REMINDERS_EVENT_ID:
                endpoint = KeyEventDateEnums.GENERATE_REMINDERS_ROUTE;
                break;
            default:
                LOGGER.error("Invalid key event type ID");
        }
 
        LOGGER.debug("Will call [" + endpoint + "] route on survey service.");
        return endpoint;
    }
}
