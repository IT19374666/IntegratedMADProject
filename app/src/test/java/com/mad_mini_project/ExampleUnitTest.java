package com.mad_mini_project;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private MyAccount myAccount;

    @Before
    public void setMyAccount(){
        myAccount = new MyAccount();
    }

    @Test
    public void addition_isCorrect() {
        Double total = myAccount.calcTotal(200.00, 550.0);

        assertEquals(750, total, 0.001);
    }

}