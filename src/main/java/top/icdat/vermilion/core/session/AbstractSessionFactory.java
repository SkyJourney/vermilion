package top.icdat.vermilion.core.session;

import top.icdat.vermilion.core.execute.SqlGenerator;

public abstract class AbstractSessionFactory implements SessionFactory {

    private SqlGenerator sqlGenerator;

    @Override
    public void setSqlGenerator(SqlGenerator sqlGenerator) {
        this.sqlGenerator = sqlGenerator;
    }

    @Override
    public SqlGenerator getSqlGenerator() {
        return sqlGenerator;
    }
}
