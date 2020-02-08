package top.icdat.vermilion.core.session;

import top.icdat.vermilion.core.execute.SqlGenerator;
import top.icdat.vermilion.exception.SessionFactoryInitializationException;

import javax.sql.DataSource;

public class SessionFactoryBean {

    private SessionFactory sessionFactory;

    private DataSource dataSource;

    private SqlGenerator sqlGenerator;

    public void init() {
        if (dataSource!=null) {
            sessionFactory = new DataSourceSessionFactory(dataSource);
        }
        throw new SessionFactoryInitializationException("The SessionFactory configuration is incorrect. Please check the parameters loaded.");
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public SqlGenerator getSqlGenerator() {
        return sqlGenerator;
    }

    public void setSqlGenerator(SqlGenerator sqlGenerator) {
        this.sqlGenerator = sqlGenerator;
    }
}
