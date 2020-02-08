package top.icdat.vermilion.core.session;

import top.icdat.vermilion.core.execute.DatabaseTypeSqlGeneratorFactory;
import top.icdat.vermilion.core.execute.SqlGeneratorFactory;
import top.icdat.vermilion.utils.DatabaseTypeUtils;

import javax.sql.DataSource;

public class SessionFactoryBuilder {

    private SessionFactoryBuilder() {
    }

    public static SessionFactory build(Configuration configuration) {
        SessionFactory sessionFactory;
        DataSource dataSource;
        if ((dataSource = configuration.getDataSource())!=null) {
            sessionFactory = new DataSourceSessionFactory(dataSource);
        } else {
            sessionFactory = new DataSourceSessionFactory(
                    new org.apache.tomcat.jdbc.pool.DataSource(
                            configuration.getPoolProperties()
                    )
            );
        }
        SqlGeneratorFactory SqlGeneratorFactory =
                new DatabaseTypeSqlGeneratorFactory(configuration.getDatabaseType());
        sessionFactory.setSqlGenerator(SqlGeneratorFactory.getSqlGenerator());
        return sessionFactory;
    }

    public static SessionFactory build(DataSource dataSource) {
        SessionFactory sessionFactory = new DataSourceSessionFactory(dataSource);
        SqlGeneratorFactory SqlGeneratorFactory =
                new DatabaseTypeSqlGeneratorFactory(
                        DatabaseTypeUtils.getDatabaseTypeByDataSource(dataSource)
                );
        sessionFactory.setSqlGenerator(SqlGeneratorFactory.getSqlGenerator());
        return sessionFactory;
    }

}
