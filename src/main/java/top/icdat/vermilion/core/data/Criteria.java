package top.icdat.vermilion.core.data;


import top.icdat.vermilion.exception.InstantiatedException;
import top.icdat.vermilion.utils.reflect.FieldReflectUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Criteria<T> extends Container<T> {

    private Map<String, Data> criteriaData = new HashMap<>();

    private Criteria(T t) {
        super(t);
    }

    public Map<String, Data> getCriteriaData() {
        return criteriaData;
    }

    public static <T> Criteria<T> of(Class<T> tableModel) {
        try {
            return new Criteria<T>(tableModel.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new InstantiatedException("Cannot create Criteria instance for ["+tableModel.getName()+"] class.");
        }
    }

    public static <T> Criteria<T> of(T t) {
        return new Criteria<>(t);
    }

    public Criteria<T> fixedValue(String field, Object value) {
        Field setField = FieldReflectUtils.requiredExists(getT().getClass(), field);
        setFieldValue(setField, value);
        return this;
    }

    public Criteria<T> like(String field, String likeString) {
        FieldReflectUtils.requiredExists(getT().getClass(), field);
        criteriaData.put(
                FieldReflectUtils.getColumnSqlName(FieldReflectUtils.requiredExists(getT().getClass(), field)),
                new Data(
                        CriteriaType.LIKE,
                        FieldReflectUtils.requiredExists(getT().getClass(), field).getType(),
                        likeString
                )
        );
        return this;
    }

    public Criteria<T> openRange(String field, Object floor, Object ceiling) {
        FieldReflectUtils.requiredExists(getT().getClass(), field);
        criteriaData.put(
                FieldReflectUtils.getColumnSqlName(FieldReflectUtils.requiredExists(getT().getClass(), field)),
                new Data(
                        CriteriaType.OPEN_RANGE,
                        FieldReflectUtils.requiredExists(getT().getClass(), field).getType(),
                        floor,
                        ceiling
                )
        );
        return this;
    }

    public Criteria<T> closedRange(String field, Object floor, Object ceiling) {
        FieldReflectUtils.requiredExists(getT().getClass(), field);
        criteriaData.put(
                FieldReflectUtils.getColumnSqlName(FieldReflectUtils.requiredExists(getT().getClass(), field)),
                new Data(
                        CriteriaType.CLOSED_RANGE,
                        FieldReflectUtils.requiredExists(getT().getClass(), field).getType(),
                        floor,
                        ceiling
        ));
        return this;
    }

    public Criteria<T> leftOpenRange(String field, Object floor, Object ceiling) {
        FieldReflectUtils.requiredExists(getT().getClass(), field);
        criteriaData.put(
                FieldReflectUtils.getColumnSqlName(FieldReflectUtils.requiredExists(getT().getClass(), field)),
                new Data(
                        CriteriaType.LEFT_OPEN_RANGE,
                        FieldReflectUtils.requiredExists(getT().getClass(), field).getType(),
                        floor,
                        ceiling
                )
        );
        return this;
    }

    public Criteria<T> rightOpenRange(String field, Object floor, Object ceiling) {
        FieldReflectUtils.requiredExists(getT().getClass(), field);
        criteriaData.put(
                FieldReflectUtils.getColumnSqlName(FieldReflectUtils.requiredExists(getT().getClass(), field)),
                new Data(
                        CriteriaType.RIGHT_OPEN_RANGE,
                        FieldReflectUtils.requiredExists(getT().getClass(), field).getType(),
                        floor,
                        ceiling
                )
        );
        return this;
    }

    public Criteria<T> greater(String field, Object floor) {
        FieldReflectUtils.requiredExists(getT().getClass(), field);
        criteriaData.put(
                FieldReflectUtils.getColumnSqlName(FieldReflectUtils.requiredExists(getT().getClass(), field)),
                new Data(
                        CriteriaType.GREATER,
                        FieldReflectUtils.requiredExists(getT().getClass(), field).getType(),
                        floor
                )
        );
        return this;
    }

    public Criteria<T> less(String field, Object ceiling) {
        criteriaData.put(
                FieldReflectUtils.getColumnSqlName(FieldReflectUtils.requiredExists(getT().getClass(), field)),
                new Data(
                        CriteriaType.LESS,
                        FieldReflectUtils.requiredExists(getT().getClass(), field).getType(),
                        ceiling
                )
        );
        return this;
    }

    public Criteria<T> lessAndEqual(String field, Object ceiling) {
        criteriaData.put(
                FieldReflectUtils.getColumnSqlName(FieldReflectUtils.requiredExists(getT().getClass(), field)),
                new Data(
                        CriteriaType.LESS_AND_EQUAL,
                        FieldReflectUtils.requiredExists(getT().getClass(), field).getType(),
                        ceiling
                )
        );
        return this;
    }

    public Criteria<T> greaterAndEqual(String field, Object floor) {
        criteriaData.put(
                FieldReflectUtils.getColumnSqlName(FieldReflectUtils.requiredExists(getT().getClass(), field)),
                new Data(
                        CriteriaType.GREATER_AND_EQUAL,
                        FieldReflectUtils.requiredExists(getT().getClass(), field).getType(),
                        floor
                )
        );
        return this;
    }

    public enum CriteriaType {
        OPEN_RANGE,
        LEFT_OPEN_RANGE,
        RIGHT_OPEN_RANGE,
        CLOSED_RANGE,
        GREATER,
        GREATER_AND_EQUAL,
        LESS,
        LESS_AND_EQUAL,
        LIKE;
    }

    public static class Data {
        private CriteriaType criteriaType;
        private Object first;
        private Object second;
        private Class<?> clz;

        public CriteriaType getCriteriaType() {
            return criteriaType;
        }

        public Object getFirst() {
            return first;
        }

        public Object getSecond() {
            return second;
        }

        public Class<?> getClz() {
            return clz;
        }

        public Data(CriteriaType criteriaType, Class<?> clz, Object first) {
            this.criteriaType = criteriaType;
            this.clz = clz;
            this.first = first;
        }

        public Data(CriteriaType criteriaType, Class<?> clz, Object first, Object second) {
            this.criteriaType = criteriaType;
            this.clz = clz;
            this.first = first;
            this.second = second;
        }
    }

}
