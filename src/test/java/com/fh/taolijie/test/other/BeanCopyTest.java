package com.fh.taolijie.test.other;

import net.sf.cglib.beans.BeanCopier;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by whf on 8/8/15.
 */
public class BeanCopyTest {
    @Test
    public void testCopy() {
        MyName myName = new MyName();
        myName.setName("wanghognfei");

        YourName yourName = new YourName();
        BeanCopier bc = BeanCopier.create(MyName.class, YourName.class, false);

        bc.copy(myName, yourName, null);
        Assert.assertEquals(myName.getName(), yourName.getName());

        System.out.println("LV0".compareTo("VIP1"));
    }

    @Test
    public void binTest() throws Exception {
/*        System.out.println("a".getBytes().length);
        System.out.println("中".getBytes().length);

        String str = Integer.toBinaryString(2);
        System.out.println(str);

        int y = 0x7fffffff;
        int ry = 0xffffffff;
        System.out.println("oy = " + y);
        System.out.println("ry = " + Integer.toBinaryString(ry));*/

        byte[] buf = "\n".getBytes();

        for (byte b : buf) {
            int ib = (int)b;
            System.out.println(ib);
        }

        //Character.
    }

}

class MyName {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class YourName {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
