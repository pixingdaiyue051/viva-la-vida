package com.tequeno.superchild;

public class Employee extends AbstractPerson<Integer> {

	@Override
	public int getHashCode(Integer t) {
		return t.hashCode();
	}

}
