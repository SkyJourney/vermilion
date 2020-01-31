package top.icdat.vermilion.core;

import java.sql.Connection;

public class DefaultSession extends AbstractSession {

    public DefaultSession(Connection connection) {
        super(connection);
    }

}
