package com.tequeno.superchild;

public abstract class Person<T> {
	private T storedVal;

	public T read() {
		return storedVal;
	}

	public void write(T val) {
		storedVal = val;
	}

	public abstract int getHashCode(T t);
}
