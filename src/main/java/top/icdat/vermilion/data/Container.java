package top.icdat.vermilion.data;

public abstract class Container<T> implements Includable<T> {

    private T t;

    public Container(T t) {
        this.t = t;
    }

    @Override
    public T getInclusion() {
        return t;
    }
}
