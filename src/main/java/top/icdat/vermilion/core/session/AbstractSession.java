package top.icdat.vermilion.core.session;

import top.icdat.vermilion.core.execute.SqlGenerator;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractSession implements Session {

    private SqlGenerator sqlGenerator;

    private Connection connection;

    public synchronized Connection getConnection() {
        return connection;
    }

    @Override
    public SqlGenerator getSqlGenerator() {
        return sqlGenerator;
    }

    @Override
    public void setSqlGenerator(SqlGenerator sqlGenerator) {
        this.sqlGenerator = sqlGenerator;
    }

    public AbstractSession(Connection connection, SqlGenerator sqlGenerator) {
        this.connection = connection;
        this.sqlGenerator = sqlGenerator;
    }

    @Override
    public void close() throws SQLException {
        if (!getConnection().getAutoCommit()) {
            getConnection().setAutoCommit(true);
        }
        getConnection().close();
    }
}
