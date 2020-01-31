package top.icdat.vermilion.core;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceSessionFactory implements SessionFactory {

    private DataSource dataSource;
    private boolean isAutoCommit = true;

    public DataSourceSessionFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public boolean isAutoCommit() {
        return isAutoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        isAutoCommit = autoCommit;
    }

    @Override
    public Session getSession() throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(isAutoCommit);
        if(!isAutoCommit) {
            return new DefaultTransactionSession(connection);
        } else {
            return new DefaultSession(connection);
        }
    }
}
