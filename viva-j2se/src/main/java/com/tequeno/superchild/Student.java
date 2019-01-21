package com.tequeno.superchild;

public class Student extends Person<String> {

	@Override
	public int getHashCode(String t) {
		return t.hashCode();
	}

}
