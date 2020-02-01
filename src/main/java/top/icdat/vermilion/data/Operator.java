package top.icdat.vermilion.data;

import top.icdat.vermilion.annotation.Delete;
import top.icdat.vermilion.annotation.Insert;
import top.icdat.vermilion.annotation.Select;
import top.icdat.vermilion.annotation.Update;

import java.util.List;

public interface Operator<T> {

    @Insert
    T insert(T item);

    @Insert
    List<T> insert(List<T> tList);

    @Delete
    int delete(Criteria<T> criteria);

    @Delete
    int delete(T criteria);

    @Delete
    boolean deleteOne(T item);

    @Update
    int update(Criteria<T> criteria,T item);

    @Update
    int update(T criteria, T item);

    @Update
    int update(T item);

    @Update
    boolean updateOne(T item);

    @Select
    List<T> select(Criteria<T> criteria);

    @Select
    List<T> select(T item);

    @Select
    T selectOne(T item);

}
