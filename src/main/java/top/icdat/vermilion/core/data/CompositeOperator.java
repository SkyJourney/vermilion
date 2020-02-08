package top.icdat.vermilion.core.data;

import top.icdat.vermilion.annotation.Delete;
import top.icdat.vermilion.annotation.Select;

public interface CompositeOperator<T> extends Operator<T> {

    @Select
    T selectByPrimaryKey(Primary<T> primaryKey);

    @Delete
    boolean deleteByPrimaryKey(Primary<T> primaryKey);

}
