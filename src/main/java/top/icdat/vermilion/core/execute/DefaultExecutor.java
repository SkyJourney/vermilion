package top.icdat.vermilion.core.execute;

import top.icdat.vermilion.core.session.Session;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultExecutor implements Executor {

    Session session;

    SqlGenerator sqlGenerator;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public SqlGenerator getSqlGenerator() {
        return sqlGenerator;
    }

    public void setSqlGenerator(SqlGenerator sqlGenerator) {
        this.sqlGenerator = sqlGenerator;
    }

    public DefaultExecutor(Session session, SqlGenerator sqlGenerator) {
        this.session = session;
        this.sqlGenerator = sqlGenerator;
    }

    @Override
    public synchronized int insert(String sql) throws SQLException {
        try (Statement statement = session.getConnection().createStatement()) {
            return statement.executeUpdate(sql);
        }
    }

    @Override
    public synchronized int update(String sql) throws SQLException {
        try (Statement statement = session.getConnection().createStatement()) {
            return statement.executeUpdate(sql);
        }
    }

    @Override
    public synchronized int delete(String sql) throws SQLException {
        try (Statement statement = session.getConnection().createStatement()) {
            return statement.executeUpdate(sql);
        }
    }

    public synchronized Object lastId() throws SQLException {
        return ((BigInteger)select("SELECT LAST_INSERT_ID() _last_id").get(0).get("_last_id")).intValueExact();
    }

    @Override
    public synchronized List<Map<String, Object>> select(String sql) throws SQLException {
        try(Statement statement = session.getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                return convertResultSet2List(resultSet);
            }
        }
    }

    private List<Map<String,Object>> convertResultSet2List(ResultSet resultSet) throws SQLException{
        List<Map<String,Object>> resultGroup = new ArrayList<>();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        while(resultSet.next()) {
            Map<String,Object> singleRow = new HashMap<>();
            for(int i = 1;i<=resultSetMetaData.getColumnCount();i++) {
                singleRow.put(resultSetMetaData.getColumnLabel(i), resultSet.getObject(i));
            }
            resultGroup.add(singleRow);
        }
        return resultGroup;
    }

}
