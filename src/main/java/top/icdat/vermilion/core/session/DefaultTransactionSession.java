package top.icdat.vermilion.core.session;

import top.icdat.vermilion.core.data.Operator;
import top.icdat.vermilion.core.execute.OperatorExecutor;
import top.icdat.vermilion.core.execute.SqlGenerator;

import java.sql.Connection;
import java.sql.SQLException;

public class DefaultTransactionSession extends AbstractSession {

    public DefaultTransactionSession(Connection connection, SqlGenerator sqlGenerator) {
        super(connection, sqlGenerator);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public <T extends Operator> T getOperator(Class<T> clz) {
        return OperatorExecutor.getProxyOperator(clz, this, getSqlGenerator());
    }

    @Override
    public void close() throws SQLException {
        getConnection().setAutoCommit(true);
        super.close();
    }

    @Override
    public void commit() throws SQLException {
        getConnection().commit();
    }

    @Override
    public void rollback() throws SQLException {
        getConnection().rollback();
    }

}
