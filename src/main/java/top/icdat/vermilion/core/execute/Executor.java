package top.icdat.vermilion.core.execute;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface Executor {

    int insert(String sql) throws SQLException;

    int update(String sql) throws SQLException;

    int delete(String sql) throws SQLException;

    List<Map<String,Object>> select(String sql) throws SQLException;

}
