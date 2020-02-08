package top.icdat.vermilion.core.session;

import org.apache.tomcat.jdbc.pool.PoolProperties;

import javax.sql.DataSource;

public class Configuration {

    private DataSource dataSource;

    private DatabaseType databaseType;

    private PoolProperties poolProperties;

    public Configuration(DataSource dataSource, DatabaseType databaseType) {
        this.dataSource = dataSource;
        this.databaseType = databaseType;
    }

    public Configuration(DatabaseType databaseType, PoolProperties poolProperties) {
        this.databaseType = databaseType;
        this.poolProperties = poolProperties;
    }

    public Configuration() {
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

    public PoolProperties getPoolProperties() {
        return poolProperties;
    }

    public void setPoolProperties(PoolProperties poolProperties) {
        this.poolProperties = poolProperties;
    }

    public static ConfigurationBuilder builder() {
        return new ConfigurationBuilder();
    }

}
