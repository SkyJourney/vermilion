package top.icdat.vermilion.core.session;

public enum DatabaseType {

    MYSQL("mysql",  "com.mysql.jdbc.Driver",",com.mysql.cj.jdbc.Driver");

    private String jdbcName;

    private String[] driverName;

    DatabaseType(String jdbcName, String...driverName) {
        this.jdbcName = jdbcName;
        this.driverName = driverName;
    }

    public String[] getDriverName() {
        return driverName;
    }

    public String getJdbcName() {
        return jdbcName;
    }
}
