package top.icdat.vermilion.data;

import java.util.List;

public interface Operator<T> {
    T insert(T t);
    T deleteByPrimaryKey(T t);
    T updateByPrimaryKey(T t);
    List<T> selectByPrimaryKey(T t);
}
