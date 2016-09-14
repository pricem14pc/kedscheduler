package uk.gov.ons.rrm.kedsched.entities;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Entity class that stores the result of the query in KeyEventDateDAOImpl.
 * 
 * @author deale
 */
@Entity
public class KeyEventDate {

    /** Attempt count for processing the key event date. */
    @Column(name = "attempt_count")
    private Short attemptCount;

    /** The display name of the collection exercise. */
    @Column(name = "display_name")
    private String collectionExerciseDisplayName;

    /** The collection exercise label. */
    @Column(name = "collection_exercise_label")
    private String collectionExerciseLabel;

    /** Collection Exercise SID. */
    @Column(name = "collection_exercise_sid")
    private BigInteger collectionExerciseSid;

    /** The name of the current state. */
    @Column(name = "current_state_desc")
    private String collectionExerciseStateDescription;

    /** The Id of the current state. */
    @Column(name = "current_state_id")
    private Integer collectionExerciseStateId;

    /** The date when the state was last changed. */
    @Column(name = "current_process_state_as_at")
    private Date currentProcessStateAsAt;

    /** The key event date state description. */
    @Column(name = "current_process_state_desc")
    private String currentProcessStateDescription;

    /** The key event current state Id. */
    @Column(name = "current_process_state_id")
    private Short currentProcessStateId;

    /** The key event date. */
    @Column(name = "event_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventDate;

    /** The key event Sid. */
    @Id
    @Column(name = "event_key_sid")
    private Long eventKeySid;

    /** The key event description. */
    @Column(name = "event_type_description")
    private String eventTypeDescription;

    /** Event Type ID. */
    @Column(name = "event_type_id")
    private Short eventTypeId;

    /** The user entered survey Id. */
    @Column(name = "survey_id")
    private String surveyId;

    /** The name of the survey. */
    @Column(name = "surveyname")
    private String surveyName;

    /** The survey Sid. */
    @Column(name = "survey_sid")
    private Long surveySid;

    /**
     * Gets the attempt count.
     * @return  The attempt count.
     */
    public final Short getAttemptCount() {
        return this.attemptCount;
    }

    /**
     * Gets the collection exercise display name.
     * @return  The collection exercise display name.
     */
    public final String getCollectionExerciseDisplayName() {
        return collectionExerciseDisplayName;
    }

    /**
     * Gets the collection exercise label.
     * @return  The collection exercise label.
     */
    public final String getCollectionExerciseLabel() {
        return collectionExerciseLabel;
    }

    /**
     * Gets the collection exercise Sid.
     * @return  The collection exercise Sid.
     */
    public final BigInteger getCollectionExerciseSid() {
        return this.collectionExerciseSid;
    }

    /**
     * Gets the collection exercise state description.
     * @return  The collection exercise state description.
     */
    public final String getCollectionExerciseStateDescription() {
        return collectionExerciseStateDescription;
    }

    /**
     * Gets the collection exercise state Id.
     * @return the collection exercise state Id
     */
    public final Integer getCollectionExerciseStateId() {
        return collectionExerciseStateId;
    }

    /**
     * Gets the date when the key event date state was last changed.
     * @return  the {@link Date} when the key event date state was last changed
     */
    public final Date getCurrentProcessStateAsAt() {
        return currentProcessStateAsAt;
    }

    /**
     * Gets the key event date state description.
     * @return the key event date state description
     */
    public final String getCurrentProcessStateDescription() {
        return currentProcessStateDescription;
    }

    /**
     * Gets the key event date state Id.
     * @return the key event date state Id.
     */
    public final Short getCurrentProcessStateId() {
        return this.currentProcessStateId;
    }
    
    /**
     * Gets the key event date.
     * @return The {@link Date} of the key event.
     */
    public final Date getEventDate() {
        return this.eventDate;
    }

    /**
     * Gets the Sid of the key event date.
     * @return The key event Sid.
     */
    public final Long getEventKeySid() {
        return eventKeySid;
    }

    /**
     * Gets the key event type description.
     * @return The key event type description.
     */
    public final String getEventTypeDescription() {
        return eventTypeDescription;
    }

    /**
     * Get the event type ID of the key event date.
     * @return The event type Id.
     */
    public final Short getEventTypeId() {
        return this.eventTypeId;
    }

    /**
     * Gets the survey Id.
     * @return The survey Id.
     */
    public final String getSurveyId() {
        return surveyId;
    }

    /**
     * Gets the name of the survey.
     * @return  The survey name.
     */
    public final String getSurveyName() {
        return surveyName;
    }

    /**
     * Gets the survey Sid.
     * @return  The survey Sid.
     */
    public final Long getSurveySid() {
        return surveySid;
    }

    
    /**
     * Sets the attempt count.
     * @param attemptCount  The attempt count.
     */
    public final void setAttemptCount(final Short attemptCount) {
        this.attemptCount = attemptCount;
    }

    /**
     * Sets the collection exercise display name.
     * @param displayName The collection exercise display name.
     */
    public final void setCollectionExerciseDisplayName(final String displayName) {
        this.collectionExerciseDisplayName = displayName;
    }

    /**
     * Sets the collection exercise label.
     * @param collectionExerciseLabel   The collection exercise label.
     */
    public final void setCollectionExerciseLabel(final String collectionExerciseLabel) {
        this.collectionExerciseLabel = collectionExerciseLabel;
    }

    /**
     * Set the collection exercise ID of the key event date.
     * 
     * @param id
     *            - collection exercise ID from db
     */
    public final void setCollectionExerciseSid(final BigInteger id) {
        this.collectionExerciseSid = id;
    }

    /**
     * Sets the collection exercise state description.
     * @param stateDescription    The collection exercise state description.
     */
    public final void setCollectionExerciseStateDescription(final String stateDescription) {
        this.collectionExerciseStateDescription = stateDescription;
    }

    /**
     * Sets the collection exercise state Id.
     * @param stateId     The collection exercise state Id.
     */
    public final void setCollectionExerciseStateId(final Integer stateId) {
        this.collectionExerciseStateId = stateId;
    }

    /**
     * Sets the current key event date state last changed date.
     * @param stateChangedDate   The date when the state was last changed.
     */
    public final void setCurrentProcessStateAsAt(final Date stateChangedDate) {
        this.currentProcessStateAsAt = stateChangedDate;
    }

    /**
     * Sets the state change description.
     * @param stateDescription    The key event date state description.
     */
    public final void setCurrentProcessStateDescription(final String stateDescription) {
        this.currentProcessStateDescription = stateDescription;
    }

    /**
     * Set the current process state ID of the key event date.
     * 
     * @param id
     *            - current process state ID from db
     */
    public final void setCurrentProcessStateId(final Short id) {
        this.currentProcessStateId = id;
    }

    /**
     * Set the key event date.
     * 
     * @param eventDate
     *            - key event date timestamp from db
     */
    public final void setEventDate(final Date eventDate) {
        this.eventDate = eventDate;
    }

    /**
     * Sets the key event date Sid.
     * @param eventKeySid   The key event date Sid.
     */
    public final void setEventKeySid(final Long eventKeySid) {
        this.eventKeySid = eventKeySid;
    }

    /**
     * Sets the key event type description.
     * @param eventTypeDescription  The key event type description.
     */
    public final void setEventTypeDescription(final String eventTypeDescription) {
        this.eventTypeDescription = eventTypeDescription;
    }

    /**
     * Set the event type ID of the key event date.
     * 
     * @param id
     *            - event type ID from db
     */
    public final void setEventTypeId(final Short id) {
        this.eventTypeId = id;
    }

    /**
     * Sets the survey Id.
     * @param surveyId The survey Id.
     */
    public final void setSurveyId(final String surveyId) {
        this.surveyId = surveyId;
    }

    /**
     * Sets the survey name.
     * @param surveyName    The survey name.
     */
    public final void setSurveyName(final String surveyName) {
        this.surveyName = surveyName;
    }

    /**
     * Sets the survey Sid.
     * @param surveySid The survey Sid.
     */
    public final void setSurveySid(final Long surveySid) {
        this.surveySid = surveySid;
    }

    /**
     * String values of each field of the object.
     * 
     * @return string contents of class
     */
    public final String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
