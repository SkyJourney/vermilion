package top.icdat.vermilion.core.session;

import top.icdat.vermilion.core.data.Operator;
import top.icdat.vermilion.core.execute.SqlGeneratorIncludable;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author SkyJourney
 * @author lyz-4dou
 */
public interface Session extends AutoCloseable, SqlGeneratorIncludable {

    Connection getConnection();

    @SuppressWarnings("rawtypes")
    <T extends Operator> T getOperator(Class<T> clz);

    void commit() throws SQLException;

    void rollback() throws SQLException;

}
