package top.icdat.vermilion.core;

import top.icdat.vermilion.data.Operator;

import java.sql.Connection;

public class DefaultSession implements Session {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public DefaultSession(Connection connection) {
        this.connection = connection;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public <T extends Operator> T getOperator(Class<T> clz) {
        return null;
    }
}
