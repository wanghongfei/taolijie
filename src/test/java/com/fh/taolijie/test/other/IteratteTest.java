package com.fh.taolijie.test.other;

import org.apache.commons.lang3.StringEscapeUtils;
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

    @Test
    public void escapeTest() {
        String str = "<script type=\"text/javascript\"> document.write(\"Hello World!\"); </script>";
        System.out.println("before = " + str);

        //str = StringEscapeUtils.escapeEcmaScript(str);
        str = StringEscapeUtils.escapeHtml4(str);
        System.out.println("after = " + str);
    }

}
