package top.icdat.vermilion.core.session;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import top.icdat.vermilion.utils.DatabaseTypeUtils;

import javax.sql.DataSource;


@SuppressWarnings("FieldCanBeLocal")
public class ConfigurationBuilder {

    private DataSource dataSource;

    private DatabaseType databaseType;

    private PoolProperties poolProperties = new PoolProperties();

    public ConfigurationBuilder dataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        databaseType = DatabaseTypeUtils.getDatabaseTypeByDataSource(dataSource);
        return this;
    }

    public ConfigurationBuilder databaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
        return this;
    }

    public ConfigurationBuilder user(String user) {
        poolProperties.setUsername(user);
        return this;
    }

    public ConfigurationBuilder password(String password) {
        poolProperties.setPassword(password);
        return this;
    }

    public ConfigurationBuilder jdbcUrl(String jdbcUrl) {
        databaseType = DatabaseTypeUtils.getDatabaseTypeByJdbcUrl(jdbcUrl);
        poolProperties.setUrl(jdbcUrl);
        poolProperties.setDriverClassName(DatabaseTypeUtils.getDriverClass(databaseType));
        return this;
    }

    public ConfigurationBuilder poolProperties(PoolProperties poolProperties) {
        this.poolProperties = poolProperties;
        databaseType = DatabaseTypeUtils.getDatabaseTypeByJdbcUrl(poolProperties.getUrl());
        return this;
    }

    public Configuration build() {
        if (dataSource!=null) {
            return new Configuration(dataSource, databaseType);
        } else {
            return new Configuration(databaseType, poolProperties);
        }
    }

}
