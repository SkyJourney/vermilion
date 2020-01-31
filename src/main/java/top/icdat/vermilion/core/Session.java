package top.icdat.vermilion.core;

import top.icdat.vermilion.data.Operator;

import java.sql.SQLException;

/**
 * @author SkyJourney
 * @author lyz-4dou
 */
public interface Session {

    @SuppressWarnings("rawtypes")
    <T extends Operator> T getOperator(Class<T> clz);

    void close() throws SQLException;

}
