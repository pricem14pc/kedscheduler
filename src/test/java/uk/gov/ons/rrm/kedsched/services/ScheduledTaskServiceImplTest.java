package uk.gov.ons.rrm.kedsched.services;

import static uk.gov.ons.rrm.kedsched.services.KeyEventDateEnums.GO_LIVE_EVENT_ID;
import static uk.gov.ons.rrm.kedsched.services.KeyEventDateEnums.MAIN_PRINT_SELECTION_EVENT_ID;
import static uk.gov.ons.rrm.kedsched.services.KeyEventDateEnums.PROCESS_STATE_IN_PROGRESS;
import static uk.gov.ons.rrm.kedsched.services.KeyEventDateEnums.REMINDERS_EVENT_ID;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.web.client.RestClientException;

import uk.gov.ons.rrm.kedsched.dao.AuditLogDAO;
import uk.gov.ons.rrm.kedsched.dao.exception.DAOException;
import uk.gov.ons.rrm.kedsched.entities.KeyEventDate;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
public class ScheduledTaskServiceImplTest {

    private static final long COLLECTION_EXERCISE_SID = 1L;

    private static final int WAITING = 1;

    @Mock
    private AuditLogDAO auditLogDao;
    
    @Mock
    private RestServiceInvoker restClient;

    @Mock
    private KeyEventDateService keyEventDateService;
    
    @Mock
    private Logger logger;

    ScheduledTaskServiceImpl service;

    @Before
    public void setUp() {
        service = new ScheduledTaskServiceImpl(keyEventDateService);

        Whitebox.setInternalState(service, "audit", auditLogDao);
        Whitebox.setInternalState(service, "client", restClient);
    }

    @Test
    public void testSendNotificationOnMpsDateShouldInvokeRestEndpoint() throws DAOException, IOException {
        final KeyEventDate ked = createKeyEventDate(MAIN_PRINT_SELECTION_EVENT_ID, WAITING, COLLECTION_EXERCISE_SID);
        Mockito.when(keyEventDateService.getKeyEventDateList(true, 0)).thenReturn(Arrays.asList(ked));

        service.sendNotifications(1);

        assertCorrectEndpointInvoked("/generateLetters", COLLECTION_EXERCISE_SID);
    }

    @Test
    public void testSendNotificationOnGoLiveShouldInvokeRestEndpoint() throws DAOException, IOException {
        final KeyEventDate ked = createKeyEventDate(GO_LIVE_EVENT_ID, WAITING, COLLECTION_EXERCISE_SID);
        Mockito.when(keyEventDateService.getKeyEventDateList(true, 0)).thenReturn(Arrays.asList(ked));

        service.sendNotifications(1);

        assertCorrectEndpointInvoked("/generateEmails", COLLECTION_EXERCISE_SID);
    }

    @Test
    public void testSendNotificationOnReminderShouldInvokeRestEndpoint() throws DAOException, IOException {
        final KeyEventDate ked = createKeyEventDate(REMINDERS_EVENT_ID, WAITING, COLLECTION_EXERCISE_SID);
        Mockito.when(keyEventDateService.getKeyEventDateList(true, 0)).thenReturn(Arrays.asList(ked));

        service.sendNotifications(1);

        assertCorrectEndpointInvoked("/generateReminders", COLLECTION_EXERCISE_SID);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testSendNotificationWritesToAuditLogWhenKeyEventDateInProgress() throws DAOException, IOException {
        final KeyEventDate ked = createKeyEventDate(REMINDERS_EVENT_ID, PROCESS_STATE_IN_PROGRESS, COLLECTION_EXERCISE_SID);
        Mockito.when(keyEventDateService.getKeyEventDateList(true, 0)).thenReturn(Arrays.asList(ked));

        service.sendNotifications(1);
        
        // Should not invoke rest client if event is In Progress...
        Mockito.verify(restClient, Mockito.never()).postForObject(Mockito.anyString(), Mockito.any(Object.class), Mockito.any(Class.class));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @PrepareForTest({ScheduledTaskServiceImpl.class})
    public void testSendNotificationLogsErrorWhenProblemInvokingRestClient() throws DAOException {
        final KeyEventDate ked = createKeyEventDate(REMINDERS_EVENT_ID, WAITING, COLLECTION_EXERCISE_SID);
        Mockito.when(keyEventDateService.getKeyEventDateList(true, 0)).thenReturn(Arrays.asList(ked));
        final RestClientException exception = new RestClientException("Error invoking REST endpoint.");
        Mockito.when(restClient.postForObject(Mockito.anyString(), Mockito.any(Object.class), Mockito.any(Class.class))).thenThrow(exception);
        Whitebox.setInternalState(ScheduledTaskServiceImpl.class, "LOGGER", logger);
        
        service.sendNotifications(1);
        
        final ArgumentCaptor<Throwable> arg = ArgumentCaptor.forClass(Throwable.class);
        Mockito.verify(logger).error(Mockito.anyString(), arg.capture());
        Assert.assertEquals(exception, arg.getValue());
    }
    

    @SuppressWarnings("unchecked")
    private void assertCorrectEndpointInvoked(final String expectedRestEndpoint, final long expectedCollectionExercise)
            throws IOException {
        final ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
        Mockito.verify(restClient).postForObject(arg.capture(), Mockito.any(Object.class), Mockito.any(Class.class));
        Assert.assertNotNull(arg.getValue());
        final String url = arg.getValue();

        
        // TODO Verify that correct endpoint was invoked.
        Assert.assertThat(url, Matchers.endsWith(expectedRestEndpoint + "/" + expectedCollectionExercise + "/12/123"));
    }

    private KeyEventDate createKeyEventDate(final int eventTypeId, final int keyEventDateState,
            final long collectionExerciseSid) {
        final KeyEventDate ked = new KeyEventDate();

        ked.setEventTypeId((short) eventTypeId);
        ked.setCurrentProcessStateId((short) keyEventDateState); // Waiting
        ked.setCollectionExerciseSid(BigInteger.valueOf(collectionExerciseSid));

        return ked;
    }

}
