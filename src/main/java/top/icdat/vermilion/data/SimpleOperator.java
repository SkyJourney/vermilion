package top.icdat.vermilion.data;

import top.icdat.vermilion.annotation.Delete;
import top.icdat.vermilion.annotation.Select;

public interface SimpleOperator<T,R> extends Operator<T> {

    @Select
    T selectByPrimaryKey(R primaryKey);

    @Delete
    boolean deleteByPrimaryKey(R primaryKey);

}
