package com.tequeno.test;

import com.tequeno.num.NumTransferHandler;

public class Decent {

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

    public void testNum() {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        Long h = 2L;

        System.out.println(c == d);//true
        System.out.println(e == f);//false
        System.out.println(c == (a + b));//true
        System.out.println(c.equals(a + b));//true
        System.out.println(g == (a + b));//true
        System.out.println(g.equals(a + b));//false
        System.out.println(g.equals(a + h));//true

        long l3 = System.currentTimeMillis();
        NumTransferHandler n = new NumTransferHandler();
        String in = "20190511";
        String result = n.transfer(in, NumTransferHandler.DecDispEnum.DEC, NumTransferHandler.DecDispEnum.BIN);
        System.out.println(result);
        long l4 = System.currentTimeMillis();
        System.out.println(l4-l3);
        result = n.transfer(in, NumTransferHandler.DecDispEnum.DEC, NumTransferHandler.DecDispEnum.OCT);
        System.out.println(result);
        result = n.transfer(in, NumTransferHandler.DecDispEnum.DEC, NumTransferHandler.DecDispEnum.HEX);
        System.out.println(result);
        result = n.transfer(in, NumTransferHandler.DecDispEnum.DEC, NumTransferHandler.DecDispEnum.DEC);
        System.out.println(result);

        String str = "54af";
        String transferRe = n.transfer(str, NumTransferHandler.DecDispEnum.HEX, NumTransferHandler.DecDispEnum.BIN);
        System.out.println(transferRe);

        long l1 = System.currentTimeMillis();
        int i = 20190511;
        String s = Integer.toBinaryString(i);
        System.out.println(s);
        long l2 = System.currentTimeMillis();
        System.out.println(l2-l1);
        String s1 = Integer.toOctalString(i);
        System.out.println(s1);
        String s2 = Integer.toHexString(i);
        System.out.println(s2);
    }
}
