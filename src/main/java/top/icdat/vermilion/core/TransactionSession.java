package top.icdat.vermilion.core;

import java.sql.SQLException;

public interface TransactionSession extends Session {

    void commit() throws SQLException;

    void rollback() throws SQLException;

}
