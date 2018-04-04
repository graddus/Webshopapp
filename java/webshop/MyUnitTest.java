package webshop;
import org.junit.Test;
import junit.framework.*;
import org.junit.*;

import static org.hamcrest.CoreMatchers.equalTo;

public class MyUnitTest extends TestCase{
    

    
	@Test
	public void testConcatenate() {
//        MyUnit myUnit = new MyUnit();

        String result = ("onetwo");

       assertEquals("oneetwo", result);

    }

}
