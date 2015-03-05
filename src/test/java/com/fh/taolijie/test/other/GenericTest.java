package com.fh.taolijie.test.other;

/**
 * Created by wanghongfei on 15-3-4.
 */
public class GenericTest {

    public static void main(String[] args) {
        printType(new Pear());
    }

    private static <T> void printType(T obj) {
        if (obj instanceof Apple) {
            System.out.println("Apple");
        } else if (obj instanceof Pear) {
            System.out.println("Pear");
        }
    }
}

class Apple {

}

class Pear{

}

