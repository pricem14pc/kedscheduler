package uk.gov.ons.rrm.kedsched;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import uk.gov.ons.rrm.kedsched.dao.KeyEventDateDAO;
import uk.gov.ons.rrm.kedsched.dao.exception.DAOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestConfiguration.class)
@Ignore("Ignoring until db connection is correct in CI... works locally with settings.properties.")
public class KeyEventDateIntegrationTest {

    @Autowired
    private KeyEventDateDAO dao;

    @Test
    @Transactional
    public void aListIsReturned() throws DAOException {
        List<?> list = dao.getKeyEventDateList(false, 1);
       
        System.out.println(list.toString());
    }


}
