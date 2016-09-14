package uk.gov.ons.rrm.kedsched.dao.work;

import static org.apache.commons.dbutils.DbUtils.closeQuietly;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.jdbc.Work;
/**
 * Unit to fetch the Key Event Dates from the DB.
 * @author pricem
 *
 */
public class FetchKeyEventDatesWork implements Work {
    /** The logger instance. **/
    private static final Logger LOGGER = LogManager.getLogger(FetchKeyEventDatesWork.class);

    /** The database query which gets executed by this work. **/
    private static final String SQL_GET_KEY_EVENT_DATES = "{ }";

    public final void execute(final Connection connection) throws SQLException {

        LOGGER.debug("Fetching key event dates...");

        CallableStatement statement = null;
        ResultSet rs = null;

        try {
            statement = connection.prepareCall(SQL_GET_KEY_EVENT_DATES);

            statement.execute();

            rs = (ResultSet) statement.getResultSet();

            while (rs.next()) {
                LOGGER.debug(rs.toString());
            }

        } catch (final SQLException se) {
            LOGGER.error("Error fetching key event dates...", se);
            throw new HibernateException(se);
        } finally {
            closeQuietly(rs);
            closeQuietly(statement);
        }

        LOGGER.debug("Completed fetching key event dates...");

    }

}
