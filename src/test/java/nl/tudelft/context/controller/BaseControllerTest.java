package nl.tudelft.context.controller;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 8-5-2015
 */
public class BaseControllerTest {

    protected static BaseController baseController;

    /**
     * Setup Base Controller.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {

        baseController = new BaseController("ATCGATCGACG");

    }

    /**
     * Test RuntimeException on wrong FXML file.
     */
    @Test(expected = RuntimeException.class)
    public void testWrongFXMLFile() {

        baseController.loadFXML("");

    }

    /**
     * Test if base content is correct.
     */
    @Test
    public void testBaseContent() {

        assertEquals("ATCGATCGACG", baseController.base.getText());

    }

}
