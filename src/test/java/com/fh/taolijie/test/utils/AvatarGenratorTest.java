package com.fh.taolijie.test.utils;

import com.fh.taolijie.utils.DefaultAvatarGenerator;
import org.junit.Test;

/**
 * Created by wynfrith on 15-3-29.
 */
public class AvatarGenratorTest {

    @Test
    public void testGetAvatar(){
        int N = 100;
        for(int i = 0; i<N; i++){
            System.out.println(DefaultAvatarGenerator.getRandomAvatar());
        }
    }
}
