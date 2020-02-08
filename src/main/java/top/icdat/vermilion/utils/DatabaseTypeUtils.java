package top.icdat.vermilion.utils;

import top.icdat.vermilion.core.execute.MySqlGenerator;
import top.icdat.vermilion.core.execute.SqlGenerator;
import top.icdat.vermilion.core.session.DatabaseType;
import top.icdat.vermilion.exception.DriverNotFoundException;
import top.icdat.vermilion.exception.UnsupportedDatabaseException;
import top.icdat.vermilion.utils.reflect.ClassReflectUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseTypeUtils {

    public static DatabaseType getDatabaseTypeByJdbcUrl(String jdbcUrl) {
        String jdbcName = jdbcUrl.substring(5, jdbcUrl.indexOf(":", 5));
        return searchDatabaseTypeByJdbcName(jdbcName);
    }

    public static DatabaseType getDatabaseTypeByDriverName(String driverName) {
        return searchDatabaseTypeByDriverName(driverName);
    }

    public static DatabaseType getDatabaseTypeByDataSource(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            return DatabaseTypeUtils.getDatabaseTypeByDriverName(
                    connection.getMetaData().getDriverName()
            );

        } catch (SQLException e) {
            throw new UnsupportedDatabaseException("Currently does not support this database");
        }
    }

    private static DatabaseType searchDatabaseTypeByJdbcName(String jdbcName) {
        for (DatabaseType databaseType : DatabaseType.values()) {
            if (databaseType.getJdbcName().equals(jdbcName)) {
                return databaseType;
            }
        }
        throw new UnsupportedDatabaseException("Currently does not support this database");
    }

    private static DatabaseType searchDatabaseTypeByDriverName(String driverName) {
        for (DatabaseType databaseType : DatabaseType.values()) {
            for (String driverClassName : databaseType.getDriverName()) {
                if (driverClassName.equals(driverName)) {
                    return databaseType;
                }
            }
        }
        throw new UnsupportedDatabaseException("Currently does not support this database");
    }

    public static String getDriverClass(DatabaseType databaseType) {
        String[] driverClasses = databaseType.getDriverName();
        for (String driverClass : driverClasses) {
            if (ClassReflectUtils.doesClassExist(driverClass)) {
                return driverClass;
            }
        }
        throw new DriverNotFoundException("Cannot find the jdbc driver for ["+ databaseType.getJdbcName().toUpperCase()+"] database.");
    }

    public static SqlGenerator getSqlGenerator(DatabaseType databaseType) {
        switch (databaseType) {
            case MYSQL:return new MySqlGenerator();
            default:return null;
        }
    }

}
