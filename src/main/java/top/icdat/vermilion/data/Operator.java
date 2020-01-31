package top.icdat.vermilion.data;

import java.util.List;

public interface Operator<T> {

    T insert(T t);

    int delete(Criteria<T> criteria);

    boolean deleteOne(T t);

    int update(Criteria<T> criteria,T t);

    T updateOne(T t);

    List<T> select(T t);

    T selectOne(T t);

}
