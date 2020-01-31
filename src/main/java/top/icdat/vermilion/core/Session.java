package top.icdat.vermilion.core;

import top.icdat.vermilion.data.Operator;

/**
 * @author SkyJourney
 * @author lyz-4dou
 */
public interface Session {

    @SuppressWarnings("rawtypes")
    <T extends Operator> T getOperator(Class<T> clz);

}
