package top.icdat.vermilion.core.session;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceSessionFactory extends AbstractSessionFactory {

    private DataSource dataSource;

    public DataSourceSessionFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    protected void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Session getSession(boolean isAutoCommit) throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(isAutoCommit);
        if(!isAutoCommit) {
            return new DefaultTransactionSession(connection,getSqlGenerator());
        } else {
            return  new DefaultSession(connection,getSqlGenerator());
        }
    }

}
