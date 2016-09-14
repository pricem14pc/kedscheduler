package uk.gov.ons.rrm.kedsched.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.Logger;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.test.util.ReflectionTestUtils;

import uk.gov.ons.rrm.kedsched.dao.KeyEventDateDAO;
import uk.gov.ons.rrm.kedsched.dao.KeyEventDateDAOImpl;
import uk.gov.ons.rrm.kedsched.dao.exception.DAOException;
import uk.gov.ons.rrm.kedsched.entities.KeyEventDate;
import uk.gov.ons.rrm.kedsched.services.KeyEventDateServiceImpl;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*"})
public class ScheduleControllerImplTest {
   
	@Mock
	private KeyEventDateDAO daom;
	   
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private EntityManager em;
    
	@InjectMocks
	private KeyEventDateDAOImpl dao = new KeyEventDateDAOImpl(em);
    
    @InjectMocks
    private KeyEventDateServiceImpl service = new KeyEventDateServiceImpl(dao);
    
    @InjectMocks
    ScheduleControllerImpl scheduleController = new ScheduleControllerImpl(service);
    
    private final String DAO_EXCEPTION_MSG = "An error occurred retrieving list of key event dates";
    
	@SuppressWarnings({ "unchecked" })
    @Test
    public void testGetKeyEventDateListIsntNull() throws DAOException {
        
        final List<KeyEventDate> list = new ArrayList<KeyEventDate>();
        list.add(createKeyEventDate(1L, (short) 1, "20160817", "Waiting"));
        list.add(createKeyEventDate(2L, (short) 2, "20160817", "In Progress"));
        list.add(createKeyEventDate(3L, (short) 3, "20160817", "Completed"));
        
        Mockito.doReturn(list).when(daom).getKeyEventDateList(false, 5);
        
        Mockito.when(service.getKeyEventDateList
                (false, 5)).thenReturn(list);
        
        ReflectionTestUtils.setField(scheduleController, "onlyDisplayActive", false);
        ReflectionTestUtils.setField(scheduleController, "numDaysDisplay", 5);
        
        assertTrue(scheduleController.listSchedule().getModel().containsKey("keyEventDateList"));
        assertEquals(3, ((List<KeyEventDate>) scheduleController.listSchedule().getModel().get("keyEventDateList")).size());
       
    }

    private KeyEventDate createKeyEventDate(final long collectionExerciseSid, final short currentProcessStateId, final String collectionExerciseLabel, final String currentProcessStateDescription) {
        final KeyEventDate keyEventDate = new KeyEventDate();
        
        keyEventDate.setCollectionExerciseSid(BigInteger.valueOf(collectionExerciseSid));
        keyEventDate.setCurrentProcessStateId(currentProcessStateId);
        keyEventDate.setCollectionExerciseLabel(collectionExerciseLabel);
        keyEventDate.setCurrentProcessStateDescription(currentProcessStateDescription);
        
        return keyEventDate;
    }

    @Test
    @PrepareForTest({ScheduleControllerImpl.class})
    public void testGetKeyEventDateLogsException() throws DAOException {
 
        Exception nestedEx = new DAOException(DAO_EXCEPTION_MSG, new Exception());
        Exception serviceEx = new DAOException(nestedEx);
        Mockito.when(service.getKeyEventDateList(false, 5))
        	.thenThrow(serviceEx);

        Logger mockLogger = mock(Logger.class);

        Whitebox.setInternalState(ScheduleControllerImpl.class, "LOGGER", mockLogger);

        ReflectionTestUtils.setField(scheduleController, "onlyDisplayActive", false);
        ReflectionTestUtils.setField(scheduleController, "numDaysDisplay", 5);
       
        scheduleController.listSchedule();

        verify(mockLogger).error(Mockito.anyString(), Mockito.eq(serviceEx));
    }
}
