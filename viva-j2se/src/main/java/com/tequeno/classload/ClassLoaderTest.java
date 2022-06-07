package com.tequeno.classload;

public class ClassLoaderTest {
    public static void main(String[] args) {
//        System.out.println("爸爸的岁数:" + Son.factor);

//        Book.staticFunction();
//        Book.staticFunction();
//        Book b = new Book();
//        b.casualFunction();

//        Son son = new Son();
//        Son.run();

        TestClass testClass = new TestClass();
        testClass.run();

        TestClass.InnerClass ic = testClass.new InnerClass();
        ic.run();

        TestClass.InnerStaticClass c = new TestClass.InnerStaticClass();
        c.run();

    }
}