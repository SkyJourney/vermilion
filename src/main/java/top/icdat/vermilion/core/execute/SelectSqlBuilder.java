package top.icdat.vermilion.core.execute;

import com.google.common.base.CaseFormat;
import top.icdat.vermilion.annotation.Group;
import top.icdat.vermilion.annotation.Sort;
import top.icdat.vermilion.annotation.SortType;
import top.icdat.vermilion.core.data.Criteria;
import top.icdat.vermilion.core.data.Paging;
import top.icdat.vermilion.core.decode.DecoderExecutor;
import top.icdat.vermilion.utils.reflect.FieldReflectUtils;

import java.util.List;
import java.util.Map;

public abstract class SelectSqlBuilder implements SqlBuilder {


    public static final String AND = " AND ";

    public static final String SELECT = "SELECT ";

    public static final String FROM = " FROM ";

    public static final String WHERE = " WHERE ";

    public static final String SORT = " ORDER BY ";

    public static final String GROUP = " GROUP BY ";

    public static final String ASC = " ASC ";

    public static final String DESC = " DESC ";

    public static final String LIKE = " LIKE ";

    private String tableName;

    private String columns;

    private String criteria;

    private String group;

    private String sort;

    private String sql;

    public String getTableName() {
        return tableName;
    }

    public String getColumns() {
        return columns;
    }

    public String getCriteria() {
        return criteria;
    }

    public String getGroup() {
        return group;
    }

    public String getSort() {
        return sort;
    }

    public String getSql() {
        return sql;
    }

    public SelectSqlBuilder table(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public SelectSqlBuilder columns(List<String> fields) {
        StringBuilder columns = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            String column = fields.get(i)+(i==(fields.size()-1)?"":",");
            columns.append(column);
        }
        this.columns = columns.toString();
        return this;
    }

    public SelectSqlBuilder sql(String sql) {
        this.sql = sql;
        return this;
    }

    public <T> SelectSqlBuilder criteria(Criteria<T> criteria) {
        StringBuilder criteriaString = new StringBuilder();
        Map<String,String> fields = FieldReflectUtils.getNotNullFields(criteria.getInclusion());
        if (fields.size()>0) {
            for (String key: fields.keySet()) {
                criteriaString.append(key).append("=").append(fields.get(key)).append(AND);
            }
        }
        Map<String,Criteria.Data> criteriaData = criteria.getCriteriaData();
        if (criteriaData.size()>0) {
            for (String columnName: criteriaData.keySet()) {
                Criteria.Data data = criteriaData.get(columnName);
                switch (data.getCriteriaType()) {
                    case LIKE: {
                        criteriaString.append(columnName)
                                .append(LIKE)
                                .append("'")
                                .append(data.getFirst())
                                .append("'")
                                .append(AND);
                        break;
                    }
                    case OPEN_RANGE: {
                        criteriaString.append(columnName)
                                .append(">")
                                .append(DecoderExecutor.execute(data.getFirst()))
                                .append(AND)
                                .append(columnName)
                                .append("<")
                                .append(DecoderExecutor.execute(data.getFirst()))
                                .append(AND);
                        break;
                    }
                    case CLOSED_RANGE: {
                        criteriaString.append(columnName)
                                .append(">=")
                                .append(DecoderExecutor.execute(data.getFirst()))
                                .append(AND)
                                .append(columnName)
                                .append("<=")
                                .append(DecoderExecutor.execute(data.getFirst()))
                                .append(AND);
                        break;
                    }
                    case LEFT_OPEN_RANGE: {
                        criteriaString.append(columnName)
                                .append(">")
                                .append(DecoderExecutor.execute(data.getFirst()))
                                .append(AND)
                                .append(columnName)
                                .append("<=")
                                .append(DecoderExecutor.execute(data.getFirst()))
                                .append(AND);
                        break;
                    }
                    case RIGHT_OPEN_RANGE: {
                        criteriaString.append(columnName)
                                .append(">=")
                                .append(DecoderExecutor.execute(data.getFirst()))
                                .append(AND)
                                .append(columnName)
                                .append("<")
                                .append(DecoderExecutor.execute(data.getFirst()))
                                .append(AND);
                        break;
                    }
                    case GREATER: {
                        criteriaString.append(columnName)
                                .append(">")
                                .append(DecoderExecutor.execute(data.getFirst()))
                                .append(AND);
                        break;
                    }
                    case GREATER_AND_EQUAL: {
                        criteriaString.append(columnName)
                                .append(">=")
                                .append(DecoderExecutor.execute(data.getFirst()))
                                .append(AND);
                        break;
                    }
                    case LESS: {
                        criteriaString.append(columnName)
                                .append("<")
                                .append(DecoderExecutor.execute(data.getFirst()))
                                .append(AND);
                        break;
                    }
                    case LESS_AND_EQUAL: {
                        criteriaString.append(columnName)
                                .append("<=")
                                .append(DecoderExecutor.execute(data.getFirst()))
                                .append(AND);
                        break;
                    }
                }
            }
        }
        int length = criteriaString.length();
        if (length>0) {
            this.criteria = criteriaString.delete(length-5,length-1).toString();
        }
        return this;
    }

    public SelectSqlBuilder group(Group group) {
        this.group = getStringBuilderFromColumns(group.value()).toString();
        return this;
    }

    public SelectSqlBuilder sort(Sort sort) {
        StringBuilder sortString = getStringBuilderFromColumns(sort.value());
        if (sort.type()== SortType.ASC) {
            sortString.append(ASC);
        } else {
            sortString.append(DESC);
        }
        this.sort = sortString.toString();
        return this;
    }

    protected StringBuilder getStringBuilderFromColumns(String[] columns) {
        StringBuilder stringBuilder = new StringBuilder();
        if (columns.length==0) {
            throw new RuntimeException();
        }
        for (String column : columns) {
            if (getSql()!=null) {
                stringBuilder.append(column).append(",");
            } else {
                stringBuilder.append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,column))
                        .append(",");
            }
        }
        return stringBuilder.deleteCharAt(stringBuilder.length()-1);
    }

    abstract SelectSqlBuilder paging(Paging paging);

}
