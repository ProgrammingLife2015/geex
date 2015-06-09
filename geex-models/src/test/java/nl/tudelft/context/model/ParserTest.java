package nl.tudelft.context.model;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 24-5-2015
 */
public class ParserTest {
    public static String MYSTRING = "my_test_string";

    class MyParser extends Parser<Object> {

        @Override
        protected Object parse(BufferedReader... file) {
            return MYSTRING;
        }
    }

    @Test
    public void testSetReader() throws Exception {
        MyParser p = new MyParser();

        File f1 = new File(getClass().getResource("/newick/10strains.nwk").getPath()),
                f2 = new File(getClass().getResource("/newick/10strains.nwk").getPath());

        assertNull(p.readerList);

        p.setReader(f1, f2);

        assertEquals(2, p.readerList.length);

    }

    @Test
    public void testParse() throws Exception {
        MyParser p = new MyParser();
        assertEquals(MYSTRING, p.parse());
    }
}