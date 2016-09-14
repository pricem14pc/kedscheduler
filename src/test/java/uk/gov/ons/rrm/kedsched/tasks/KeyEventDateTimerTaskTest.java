package uk.gov.ons.rrm.kedsched.tasks;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import uk.gov.ons.rrm.kedsched.services.KeyEventDateEnums;
import uk.gov.ons.rrm.kedsched.services.ScheduledTaskService;

/**
 * Unit tests for {@link KeyEventDateTimerTask}.
 * @author thomas3
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class KeyEventDateTimerTaskTest {
    
    /** The task service that is used to send notifications. */
    @Mock
    private ScheduledTaskService scheduledTaskService;

    /** The class under test. */
    private KeyEventDateTimerTask task;

    @Before
    public void setUp() throws Exception {
        task = new KeyEventDateTimerTask();
        
        Whitebox.setInternalState(task, "task", scheduledTaskService);
    }

    @Test
    public void testExecuteTaskShouldNotSendNotificationsWhenNotActive() {
        task.executeTask();
        
        Mockito.verify(scheduledTaskService, Mockito.never()).sendNotifications(KeyEventDateEnums.AUTOMATED_PROCEDURE);
    }

    
    @Test
    public void testExecuteTaskSendsNotificationsWhenActive() {
        Whitebox.setInternalState(task, "active", true);
        task.executeTask();
        
        Mockito.verify(scheduledTaskService).sendNotifications(KeyEventDateEnums.AUTOMATED_PROCEDURE);        
    }
}
