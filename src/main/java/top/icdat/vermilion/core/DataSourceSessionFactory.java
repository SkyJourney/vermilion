package top.icdat.vermilion.core;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceSessionFactory implements SessionFactory {

    private DataSource dataSource;
    private boolean isAutoCommit;

    public DataSourceSessionFactory(DataSource dataSource) {
        this(dataSource,false);
    }

    public DataSourceSessionFactory(DataSource dataSource, boolean isAutoCommit) {
        this.dataSource = dataSource;
        this.isAutoCommit = isAutoCommit;
    }

    @Override
    public Session getSession() throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(isAutoCommit);
        if(isAutoCommit) {
            return new DefaultTransactionSession(connection);
        } else {
            return new DefaultSession(connection);
        }
    }
}
