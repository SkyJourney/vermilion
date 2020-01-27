package top.icdat.vermilion.data;

public interface Operator<T> {
    T insert(T t);
    T deleteByPrimaryKey(T t);
    T updateByPrimaryKey(T t);
    T[] selectByPrimaryKey(T t);
}
