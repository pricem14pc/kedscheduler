package uk.gov.ons.rrm.kedsched.services;

/**
 * @author deale
 */
public final class KeyEventDateEnums {

    /** The eventTypeID for MPS events. */
    public static final int MAIN_PRINT_SELECTION_EVENT_ID = 6;

    /** The eventTypeID for Go Live events. */    
    public static final int GO_LIVE_EVENT_ID = 1;

    /** The eventTypeID for Reminders events. */
    public static final int REMINDERS_EVENT_ID = 5;
    
    /** The currentEventProcessStateID for 'In Progress'. */
    public static final int PROCESS_STATE_IN_PROGRESS = 2;
    
    /** The survey service route for generating letters. */
    public static final String GENERATE_LETTERS_ROUTE = "/generateLetters";
    
    /** The survey service route for generating emails. */
    public static final String GENERATE_EMAILS_ROUTE = "/generateEmails";

    /** The survey service route for generating reminders. */
    public static final String GENERATE_REMINDERS_ROUTE = "/generateReminders";
    
    /** Event ID to be used when auditing an automated activity. */
    public static final int AUTOMATED_PROCEDURE = 9;
    
    /** Event ID to be used when auditing a user initiated activity. */
    public static final int USER_PROCEDURE = 8;
    
    /** Default constructor. */
    private KeyEventDateEnums() { }
}
