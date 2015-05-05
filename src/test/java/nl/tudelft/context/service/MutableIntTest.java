package nl.tudelft.context.service;

import org.junit.Test; 
import org.junit.Before;

import static org.junit.Assert.assertEquals;

/** 
* MutableInt Tester. 
*
* @author Jasper Nieuwdorp <jaspernieuwdorp@hotmail.com>
* @version  1.0
* @since 04-05-2015
*/ 
public class MutableIntTest {
    protected static MutableInt mutableint1, mutableint2, mutableint3, mutableint4;

@Before
public void before() throws Exception {
    mutableint1 = new MutableInt();
    mutableint2 = new MutableInt();
    mutableint3 = new MutableInt();
    mutableint4 = new MutableInt();
}

/** 
* 
* Method: get() 
* 
*/ 
@Test
public void testGet() throws Exception {
    assertEquals(0,mutableint1.get());
} 

/** 
* 
* Method: increment() 
* 
*/ 
@Test
public void testIncrement() throws Exception {
    assertEquals(0,mutableint2.get());
    mutableint2.increment();
    assertEquals(1,mutableint2.get());
    mutableint2.increment();
    mutableint2.increment();
    assertEquals(3,mutableint2.get());
} 

/** 
* 
* Method: decrement() 
* 
*/ 
@Test
public void testDecrement() throws Exception {
    assertEquals(0, mutableint3.get());
    mutableint3.decrement();
    assertEquals(-1, mutableint3.get());
    mutableint3.decrement();
    mutableint3.decrement();
    assertEquals(-3, mutableint3.get());
} 

/** 
* 
* Method: reset() 
* 
*/ 
@Test
public void testReset() throws Exception {
    assertEquals(0, mutableint4.get());
    mutableint4.increment();
    assertEquals(1, mutableint4.get());
    mutableint4.reset();
    assertEquals(0, mutableint4.get());

} 

/** 
* 
* Method: toString() 
* 
*/ 
@Test
public void testToString() throws Exception {
    assertEquals(0, mutableint1.get());
    assertEquals(0, mutableint2.get());
    mutableint2.increment();
    assertEquals("0", mutableint1.toString());
    assertEquals("1", mutableint2.toString());

} 


} 
