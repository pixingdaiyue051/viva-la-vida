package com.tequeno.collect;

public class CompareTest {
	public static void main(String[] args) {
		String[] str = { "10", "11ggg", "12" };
		System.out.println(findMax(str));
	}

	public static Comparable findMax(Comparable[] arr) {
		int maxIndex = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].compareTo(arr[maxIndex]) > 0) {
				maxIndex = i;
			}
		}
		return arr[maxIndex];
	}

}
