package uk.gov.ons.rrm.kedsched.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hamcrest.Matchers;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import uk.gov.ons.rrm.kedsched.dao.exception.DAOException;
import uk.gov.ons.rrm.kedsched.entities.KeyEventDate;

@RunWith(MockitoJUnitRunner.class)
public class KeyEventDateDAOImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Session session;

    @Mock
    private Query query;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Captor
    private ArgumentCaptor<String> args;

    KeyEventDateDAOImpl dao;

    @Before
    public void setUp() throws Exception {
        dao = new KeyEventDateDAOImpl(entityManager);

        Mockito.when(entityManager.createNativeQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(Collections.emptyList());
    }

    @Test
    public void testGetKeyEventDateReturnsList() throws DAOException {
        List<KeyEventDate> myList = dao.getKeyEventDateList(false, 1);
        Assert.assertNotNull(myList);
    }

    @Ignore
    public void testGetKeyEventDateListThrowsException() throws DAOException {
        thrown.expect(DAOException.class);
        thrown.expectMessage("An error occurred retrieving list of key event dates");
        
        Mockito.doThrow(new HibernateException(Mockito.mock(Throwable.class))).when(query).getResultList();
        @SuppressWarnings("unused")
        List<KeyEventDate> keyEventDateList = dao.getKeyEventDateList(false, 1);
    }

    @Test
    public void testBuildQueryRestricstActiveResults() throws DAOException {
        dao.getKeyEventDateList(true, 1);

        assertSqlContains(
                "AND ((ckd.current_process_state_id in ('1','2')) OR (ckd.current_process_state_id in ('4','5'))) AND (ckd.attempt_count < cket.attempt_count_limit)");
    }

    @Test
    public void testBuildQueryOnlyReturnsDatesInThePastWhenDaysIsZero() throws DAOException {
        dao.getKeyEventDateList(false, 0);

        assertSqlContains("AND (ckd.event_date < NOW())");
    }

    /**
     * Helper method that gets the SQL that was created in
     * {@link KeyEventDateDAOImpl#buildQuery()} and asserts it contains some
     * string.
     * 
     * @param expectedSQL
     *            The sql string that was expected in the query.
     */
    private void assertSqlContains(final String expectedSQL) {
        Mockito.verify(entityManager).createNativeQuery(args.capture(), Mockito.any(Class.class));
        final String sql = args.getValue();

        Assert.assertNotNull(sql);
        Assert.assertThat(sql, Matchers.containsString(expectedSQL));
    }

}
