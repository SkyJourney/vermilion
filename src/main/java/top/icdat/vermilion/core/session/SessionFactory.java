package top.icdat.vermilion.core.session;

import top.icdat.vermilion.core.execute.SqlGeneratorIncludable;

import java.sql.SQLException;

/**
 * @author SkyJourney
 * @author lyz-4dou
 */
public interface SessionFactory extends SqlGeneratorIncludable {

    default Session getSession() throws SQLException {
        return getSession(true);
    }

    Session getSession(boolean isAutoCommit) throws SQLException;

}
