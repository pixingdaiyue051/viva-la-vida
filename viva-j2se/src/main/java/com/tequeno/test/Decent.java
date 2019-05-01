package com.tequeno.test;

public class Decent {
    public static void main(String[] args) {
        Decent de = new Decent();
        // for (int i = 0; i < 1000; i++) {
        // System.out.println(de.sumReciprocal(i + 1));
        // }
        // System.out.println(de.sumEqualDifference(100));
        // System.out.println(de.sumEqualRatio(10));
        de.sumFibonacci(10);
    }

    // reciprocal倒数
    public double sumReciprocal(int n) {
        if (n == 1) {
            return 1.0d;
        }
        return sumReciprocal(n - 1) + 1.0d / n;
    }

    // equalDifference等差
    // a1=1,d=1
    private int equalDifference(int n) {
        if (n == 1) {
            return 1;
        }
        return equalDifference(n - 1) + 1;
    }

    public int sumEqualDifference(int n) {
        int sum = 0;
        while (n > 0) {
            sum += equalDifference(n--);
        }
        return sum;
    }

    // equalDifference等比
    // a1=1,q=2
    private int equalRatio(int n) {
        if (n == 0) {
            return 1;
        }
        return equalRatio(n - 1) * 2;
    }

    public int sumEqualRatio(int n) {
        int sum = 0;
        while (n > -1) {
            sum += equalRatio(n--);
        }
        return sum;
    }

    // Fibonacci
    private int fibonacci(int n) {
        if (n < 3) {
            return 1;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    // 1+1+2+3+5+8+13+21+34+55+....=...
    public int sumFibonacci(int n) {
        int sum = 0;
        int tmp = 0;
        StringBuffer buf = new StringBuffer();
        while (n > 0) {
            tmp = fibonacci(n--);
            buf.insert(0, tmp + "+");
            sum += tmp;
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
            buf.append("=" + sum);
            System.out.println(buf.toString());
        }
        return sum;
    }

    //
    public int sumFibonacci(int n, int n1) {
        int sum = 0;
        int tmp = 0;
        StringBuffer buf = new StringBuffer();
        while (n > 0) {
            tmp = fibonacci(n--);
            buf.insert(0, tmp + "+");
            sum += tmp;
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
            buf.append("=" + sum);
            System.out.println(buf.toString());
        }
        return sum;
    }
}
