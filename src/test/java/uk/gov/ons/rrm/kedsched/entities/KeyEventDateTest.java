package uk.gov.ons.rrm.kedsched.entities;

import java.math.BigInteger;
import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class KeyEventDateTest {

    private KeyEventDate keyEventDate;

    /** Test Data */
    private static final Short attemptCount = 1;
    private static final String collectionExerciseDisplayName = "Collection Exercise Display Name";
    private static final String collectionExerciseLabel = "CE Label";
    private static final BigInteger collectionExerciseSid = BigInteger.valueOf(2);
    private static final String collectionExerciseStateDescription = "Published";
    private static final Integer collectionExerciseStateId = 1;
    private static final Date stateChangedDate = new Date();
    private static final String currentProcessStateDescription = "Waiting";
    private static final Short currentProcessStateId = 1;
    private static final Date eventDate = new Date();
    private static final Long eventKeySid = 1L;
    private static final String eventTypeDescription = "Go Live";
    private static final Short eventTypeId = 1;
    private static final String surveyId = "001";
    private static final String surveyName = "Survey Name";
    private static final Long surveySid = 1L;

    @Before
    public void setUp() {
        this.keyEventDate = new KeyEventDate();

        keyEventDate.setAttemptCount(attemptCount);
        keyEventDate.setCollectionExerciseDisplayName(collectionExerciseDisplayName);
        keyEventDate.setCollectionExerciseLabel(collectionExerciseLabel);
        keyEventDate.setCollectionExerciseSid(collectionExerciseSid);
        keyEventDate.setCollectionExerciseStateDescription(collectionExerciseStateDescription);
        keyEventDate.setCollectionExerciseStateId(collectionExerciseStateId);
        keyEventDate.setCurrentProcessStateAsAt(stateChangedDate);
        keyEventDate.setCurrentProcessStateDescription(currentProcessStateDescription);
        keyEventDate.setCurrentProcessStateId(currentProcessStateId);
        keyEventDate.setEventDate(eventDate);
        keyEventDate.setEventKeySid(eventKeySid);
        keyEventDate.setEventTypeDescription(eventTypeDescription);
        keyEventDate.setEventTypeId(eventTypeId);
        keyEventDate.setSurveyId(surveyId);
        keyEventDate.setSurveyName(surveyName);
        keyEventDate.setSurveySid(surveySid);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void settersAndGettersBehaveAsExpected() {
        Assert.assertThat(keyEventDate,
                Matchers.allOf(Matchers.hasProperty("attemptCount", Matchers.equalTo(attemptCount)),
                        Matchers.hasProperty("collectionExerciseDisplayName",
                                Matchers.equalTo(collectionExerciseDisplayName)),
                        Matchers.hasProperty("collectionExerciseLabel", Matchers.equalTo(collectionExerciseLabel)),
                        Matchers.hasProperty("collectionExerciseSid", Matchers.equalTo(collectionExerciseSid)),
                        Matchers.hasProperty("collectionExerciseStateDescription",
                                Matchers.equalTo(collectionExerciseStateDescription)),
                        Matchers.hasProperty("collectionExerciseStateId", Matchers.equalTo(collectionExerciseStateId)),
                        Matchers.hasProperty("currentProcessStateAsAt", Matchers.equalTo(stateChangedDate)),
                        Matchers.hasProperty("currentProcessStateDescription",
                                Matchers.equalTo(currentProcessStateDescription)),
                        Matchers.hasProperty("currentProcessStateId", Matchers.equalTo(currentProcessStateId)),
                        Matchers.hasProperty("eventDate", Matchers.equalTo(eventDate)),
                        Matchers.hasProperty("eventKeySid", Matchers.equalTo(eventKeySid)),
                        Matchers.hasProperty("eventTypeDescription", Matchers.equalTo(eventTypeDescription)),
                        Matchers.hasProperty("eventTypeId", Matchers.equalTo(eventTypeId)),
                        Matchers.hasProperty("surveyId", Matchers.equalTo(surveyId)),
                        Matchers.hasProperty("surveyName", Matchers.equalTo(surveyName)),
                        Matchers.hasProperty("surveySid", Matchers.equalTo(surveySid))));
    }

    @Test
    public void toStringShouldDisplayUsefulInfo() {
        final String toString = keyEventDate.toString();
        
        Assert.assertTrue(toString.contains(attemptCount.toString()));
        Assert.assertTrue(toString.contains(collectionExerciseDisplayName.toString()));
        Assert.assertTrue(toString.contains(collectionExerciseLabel.toString()));
        Assert.assertTrue(toString.contains(collectionExerciseSid.toString()));
        Assert.assertTrue(toString.contains(collectionExerciseStateDescription.toString()));
        Assert.assertTrue(toString.contains(collectionExerciseStateId.toString()));
        Assert.assertTrue(toString.contains(stateChangedDate.toString()));
        Assert.assertTrue(toString.contains(currentProcessStateDescription.toString()));
        Assert.assertTrue(toString.contains(currentProcessStateId.toString()));
        Assert.assertTrue(toString.contains(eventDate.toString()));
        Assert.assertTrue(toString.contains(eventKeySid.toString()));
        Assert.assertTrue(toString.contains(eventTypeDescription.toString()));
        Assert.assertTrue(toString.contains(eventTypeId.toString()));
        Assert.assertTrue(toString.contains(surveyId.toString()));
        Assert.assertTrue(toString.contains(surveyName.toString()));
        Assert.assertTrue(toString.contains(surveySid.toString()));
        
    }

}
