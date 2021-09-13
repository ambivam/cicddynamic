package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class USID_011_TC034 {
    @Test
    public void statusPass(){
        Assert.assertTrue(true);
    }

    @Test
    public void statusFail(){
        Assert.assertFalse(true);
    }
}
