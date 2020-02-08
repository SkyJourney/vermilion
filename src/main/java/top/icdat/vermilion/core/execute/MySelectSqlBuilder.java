package top.icdat.vermilion.core.execute;

import top.icdat.vermilion.core.data.Paging;

public class MySelectSqlBuilder extends SelectSqlBuilder {

    private MySelectSqlBuilder() {
    }

    public static final String LIMIT = " LIMIT ";

    private String paging;

    public String getPaging() {
        return paging;
    }

    public static SelectSqlBuilder start() {
        return new MySelectSqlBuilder();
    }

    @Override
    public String build() {
        StringBuilder sql = new StringBuilder();
        if (getSql()!=null) {
            sql.append(getSql());
            if (getGroup()!=null) {
                sql.append(GROUP).append(getGroup());
            }
        } else {
            sql.append(SELECT).append(getColumns()).append(FROM).append(getTableName());
            if (getCriteria()!=null) {
                sql.append(WHERE).append(getCriteria());
            }
        }
        if (getSort()!=null) {
            sql.append(SORT).append(getSort());
        }
        if (getPaging()!=null) {
            sql.append(LIMIT).append(getPaging());
        }
//            System.out.println(sql.toString());
        return sql.toString();
    }

    @Override
    SelectSqlBuilder paging(Paging paging) {
        this.paging = ((paging.getPageNum() - 1) * paging.getRecordAmount())
                + "," + paging.getRecordAmount();
        return this;
    }

}
