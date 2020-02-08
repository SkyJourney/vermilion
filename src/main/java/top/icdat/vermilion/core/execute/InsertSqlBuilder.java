package top.icdat.vermilion.core.execute;

import top.icdat.vermilion.utils.reflect.FieldReflectUtils;

import java.util.Map;

public class InsertSqlBuilder implements SqlBuilder {

    public static final String INSERT = "INSERT INTO ";

    public static final String VALUES = " VALUES";

    private String sql;

    private String tableName;

    private String columns;

    private String values;

    public String getSql() {
        return sql;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumns() {
        return columns;
    }

    public String getValues() {
        return values;
    }

    private InsertSqlBuilder() {
    }

    public static InsertSqlBuilder start() {
        return new InsertSqlBuilder();
    }

    public InsertSqlBuilder table(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public InsertSqlBuilder sql(String sql) {
        this.sql = sql;
        return this;
    }

    public <T> InsertSqlBuilder values(T t) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        Map<String,String> fields = FieldReflectUtils.getNotNullFields(t,true);
        if (fields.size()>0) {
            for (String key: fields.keySet()) {
                columns.append(key).append(",");
                values.append(fields.get(key)).append(",");
            }
            this.columns = columns.deleteCharAt(columns.length()-1).toString();
            this.values = values.deleteCharAt(values.length()-1).toString();
        } else {
            throw new RuntimeException();
        }
        return this;
    }

    @Override
    public String build() {
        StringBuilder sql = new StringBuilder();
        if (getSql()!=null) {
            sql.append(getSql());
        } else {
            sql.append(INSERT).append(getTableName())
                    .append("(").append(getColumns()).append(")")
                    .append(VALUES).append("(").append(getValues()).append(")");
        }
        return sql.toString();
    }

}
