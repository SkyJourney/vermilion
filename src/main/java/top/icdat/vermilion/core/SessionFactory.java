package top.icdat.vermilion.core;

import java.sql.SQLException;

/**
 * @author SkyJourney
 * @author lyz-4dou
 */
public interface SessionFactory {

    Session getSession() throws SQLException;

}
