package top.icdat.vermilion.core;

import java.sql.Connection;
import java.sql.SQLException;

public class DefaultTransactionSession extends DefaultSession implements TransactionSession {

    public DefaultTransactionSession(Connection connection) {
        super(connection);
    }

    @Override
    public void commit() throws SQLException {
        getConnection().commit();
    }

    @Override
    public void rollback() throws SQLException {
        getConnection().rollback();
    }

    @Override
    public void close() throws SQLException {
        getConnection().setAutoCommit(true);
        super.close();
    }
}
