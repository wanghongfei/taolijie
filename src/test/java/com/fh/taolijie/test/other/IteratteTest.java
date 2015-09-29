package com.fh.taolijie.test.other;

import org.junit.Test;

/**
 * Created by whf on 8/16/15.
 */
public class IteratteTest {
    @Test
    public void test() {
        MyList m = new MyList();
        for (String s : m.split()) {

        }
    }

    class MyList {
        public String[] split() {
            System.out.println("split called");

            return new String[] {
                    "", "", ""
            };
        }

    }

}
