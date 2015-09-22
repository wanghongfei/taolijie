package com.fh.taolijie.test.other;

import com.fh.taolijie.domain.SysNotificationModel;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

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
