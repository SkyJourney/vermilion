package top.icdat.vermilion.core.execute;

import top.icdat.vermilion.core.session.DatabaseType;
import top.icdat.vermilion.utils.DatabaseTypeUtils;

public class DatabaseTypeSqlGeneratorFactory implements SqlGeneratorFactory {

    private SqlGenerator sqlGenerator;

    public DatabaseTypeSqlGeneratorFactory(DatabaseType databaseType) {
        sqlGenerator = DatabaseTypeUtils.getSqlGenerator(databaseType);
    }

    @Override
    public SqlGenerator getSqlGenerator() {
        return sqlGenerator;
    }
}
