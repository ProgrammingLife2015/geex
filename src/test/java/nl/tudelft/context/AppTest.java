package nl.tudelft.context;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 26-4-2015
 */
public class AppTest {

    @Test
    public void testLaunch() throws InterruptedException {

        new Thread(App::main).start();

        App.started.await();

        assertEquals(0L, App.started.getCount());

    }

}
