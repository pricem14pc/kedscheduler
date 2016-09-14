package uk.gov.ons.rrm.kedsched.dao;

import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.Date;

import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import uk.gov.ons.rrm.kedsched.entities.KeyEventDate;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
public class AuditLogDAOImplTest {
    
    @Mock
    private JdbcTemplate jdbcTemplate;
    
    @Captor
    private ArgumentCaptor<String> query;
    
    private AuditLogDAOImpl dao;

    private static final String message = "This is the audit log message.";
    private static final String endpoint = "/generateLetters";
    private static final int userID = 99;
    private static final int eventID = 100;
    
    private KeyEventDate keyEventDate;
    
    @Before
    public void setUp() {
        dao = new AuditLogDAOImpl();
        
        Whitebox.setInternalState(dao, "jdbcTemplate", jdbcTemplate);
        
        keyEventDate = new KeyEventDate();
        keyEventDate.setCollectionExerciseSid(BigInteger.valueOf(1));
        keyEventDate.setEventKeySid(1L);
        keyEventDate.setEventDate(new Date());
        keyEventDate.setCurrentProcessStateDescription("WAITING");
    }

    @Test
    public void testCreateAuditRecordInsertsRowIntoDatabase() {
        
        dao.createAuditLogRecord(message, endpoint, keyEventDate, userID, eventID);
        
        Mockito.verify(jdbcTemplate).update(query.capture(), Mockito.anyVararg());
        
        assertIsInsertQuery(query.getValue());
        
    }
    
    @SuppressWarnings("unchecked")
    @Test(expected = DataAccessException.class)
    public void testCreateAuditRecordThrowsExceptionWhenIssueLoggingAuditRecord() {
        Mockito.when(jdbcTemplate.update(Mockito.anyString(), Mockito.anyVararg())).thenThrow(RecoverableDataAccessException.class);
        
        dao.createAuditLogRecord(message, endpoint, keyEventDate, userID, eventID);
        
    }
    
    @Test
    @PrepareForTest({AuditLogDAOImpl.class})
    public void testCreateAuditRecordLogsErrorWhenNoRecordsUpdated() {
        final Logger logger = PowerMockito.mock(Logger.class);
        Whitebox.setInternalState(AuditLogDAOImpl.class, "LOGGER", logger);
        
        dao.createAuditLogRecord(message, endpoint, keyEventDate, userID, eventID);
        
        Mockito.verify(logger).error(Mockito.any(Throwable.class));
        
    }
    
    @Test
    @PrepareForTest({AuditLogDAOImpl.class})
    public void testCreateAuditRecordDoesNotLogErrorWhenRowUpdated() {
        when(jdbcTemplate.update(Mockito.anyString(), Mockito.anyVararg())).thenReturn(1);
        final Logger logger = PowerMockito.mock(Logger.class);
        Whitebox.setInternalState(AuditLogDAOImpl.class, "LOGGER", logger);
        
        dao.createAuditLogRecord(message, endpoint, keyEventDate, userID, eventID);
        
        Mockito.verify(logger, Mockito.never()).error(Mockito.any(Throwable.class));
    }

    private void assertIsInsertQuery(final String sql) {
        Assert.assertNotNull(sql);
        Assert.assertTrue(sql.toUpperCase().startsWith("INSERT"));
    }
    
}
