package uk.gov.ons.rrm.kedsched.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import uk.gov.ons.rrm.kedsched.dao.KeyEventDateDAO;
import uk.gov.ons.rrm.kedsched.dao.exception.DAOException;


@RunWith(MockitoJUnitRunner.class)
public class KeyEventDateServiceImplTest {

    @Mock
    private KeyEventDateDAO dao;
    
    @InjectMocks
    KeyEventDateService service = new KeyEventDateServiceImpl(dao);

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testKeyEventDateListNoException() throws DAOException {
        Mockito.when(dao.getKeyEventDateList
                (false, 1)).thenReturn(new ArrayList());
        List<?> keyEventDateList = service.getKeyEventDateList(false, 1);
        Assert.assertNotNull(keyEventDateList);
    }

}
