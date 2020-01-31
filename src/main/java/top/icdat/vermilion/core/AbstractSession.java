package top.icdat.vermilion.core;

import top.icdat.vermilion.data.Operator;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractSession implements Session {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public AbstractSession(Connection connection) {
        this.connection = connection;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public <T extends Operator> T getOperator(Class<T> operatorClass) {
        return null;
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
