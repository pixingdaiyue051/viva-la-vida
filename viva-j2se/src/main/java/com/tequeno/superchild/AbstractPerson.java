package com.tequeno.superchild;

public abstract class AbstractPerson<T> {
    private T storedVal;

    public T read() {
        return storedVal;
    }

    public void write(T val) {
        storedVal = val;
    }

    /**
     * 使用的子类的hashCode方法
     *
     * @param t
     * @return
     */
    public abstract int getHashCode(T t);
}
