package com.nepshirts.android;

import android.content.Intent;

import com.nepshirts.android.home.HomeFragment;
import com.nepshirts.android.models.UserModel;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void userModel(){
        UserModel user = new UserModel("anil poudyal",
                "anil@anil.com",
                "123456",
                "Male",
                "2020/05/15",
                "KTM",
                "Thankot",
                "Baje Ko chiya Pasal");

        if(user.getFullName().equals("anil poudyal")){
            assertTrue(true);
        }else{
            fail();
        }
    }
    @Test
    public  void  loginInput(){

    }



}