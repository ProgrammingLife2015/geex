package nl.tudelft.context.model;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import static org.mockito.Mockito.mock;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 24-5-2015
 */
public class ParserTest extends TestCase {
    public static String MYSTRING = "my_test_string";

    class MyParser extends Parser<Object> {

        @Override
        protected Object parse(BufferedReader... file) {
            return MYSTRING;
        }
    }

    public void testSetReader() throws Exception {
        MyParser p = new MyParser();

        File f1 = new File(getClass().getResource("/newick/10strains.nwk").getPath()),
                f2 = new File(getClass().getResource("/newick/10strains.nwk").getPath());

        assertEquals(null, p.readerList);

        p.setReader(f1, f2);

        assertEquals(2, p.readerList.length);

    }

    public void testParse() throws Exception {
        MyParser p = new MyParser();
        assertEquals(MYSTRING, p.parse());
    }
}