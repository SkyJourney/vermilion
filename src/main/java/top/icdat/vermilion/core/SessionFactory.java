package top.icdat.vermilion.core;

import java.sql.SQLException;

/**
 * @author SkyJourney
 * @author lyz-4dou
 */
public interface SessionFactory {

    default Session getSession() throws SQLException {
        return getSession(true);
    }

    Session getSession(boolean isAutoCommit) throws SQLException;

}
