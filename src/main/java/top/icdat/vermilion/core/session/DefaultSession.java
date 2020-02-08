package top.icdat.vermilion.core.session;

import top.icdat.vermilion.core.data.Operator;
import top.icdat.vermilion.core.execute.SqlGenerator;
import top.icdat.vermilion.core.execute.OperatorExecutor;

import java.sql.Connection;

public class DefaultSession extends AbstractSession {

    public DefaultSession(Connection connection, SqlGenerator sqlGenerator) {
        super(connection, sqlGenerator);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public <T extends Operator> T getOperator(Class<T> clz) {
        return OperatorExecutor.getProxyOperator(clz, this, getSqlGenerator());
    }

    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

}
