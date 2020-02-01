package top.icdat.vermilion.core;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceSessionFactory implements SessionFactory {

    private DataSource dataSource;

    public DataSourceSessionFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Session getSession(boolean isAutoCommit) throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(isAutoCommit);
        if(!isAutoCommit) {
            return new DefaultTransactionSession(connection);
        } else {
            return new DefaultSession(connection);
        }
    }
}
