package com.atguigu.testDebug;

public class A {

    public A(){
        System.out.println("this is A's Constructor");
    }

    public void  a1(){
        int i = 5;
        int j = 6;
        System.out.println("this is a1 ");
        a2();
        System.out.println("invoke over a2");
    }

    public void a2(){
        System.out.println("this is a2");
    }

    public static void main(String[] args) {
        System.out.println("New A object.....");
        A a = new A();
        System.out.println("invoke start a1");
        a.a1();
    }
}
