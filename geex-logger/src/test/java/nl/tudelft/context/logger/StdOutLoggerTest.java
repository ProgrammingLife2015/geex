package nl.tudelft.context.logger;

import nl.tudelft.context.logger.message.Message;
import nl.tudelft.context.logger.message.MessageType;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 14-6-2015
 */
public class StdOutLoggerTest {

    @Test
    public void testGetStream() throws Exception {
        StdOutLogger logger = new StdOutLogger(mock(ObservableLog.class));

        assertEquals(System.out, logger.getStream(MessageType.DEBUG));
        assertEquals(System.out, logger.getStream(MessageType.INFO));
        assertEquals(System.err, logger.getStream(MessageType.WARNING));
    }

    @Test
    public void testDebugLog() throws Exception {
        Logger logger = new StdOutLogger(mock(ObservableLog.class));

        logger.log(Message.MESSAGE_READY, MessageType.DEBUG);

        assertThat(outContent.toString(), CoreMatchers.containsString("[" + MessageType.DEBUG + "] " + Message.MESSAGE_READY));
    }

    @Test
    public void testWarningLog() throws Exception {
        Logger logger = new StdOutLogger(mock(ObservableLog.class));

        logger.log(Message.MESSAGE_READY, MessageType.WARNING);

        assertThat(errContent.toString(), CoreMatchers.containsString("[" + MessageType.WARNING + "] " + Message.MESSAGE_READY));
    }


    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

}