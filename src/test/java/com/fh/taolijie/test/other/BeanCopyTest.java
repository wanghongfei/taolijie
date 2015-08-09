package com.fh.taolijie.test.other;

import com.fh.taolijie.domain.SysNotificationModel;
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
