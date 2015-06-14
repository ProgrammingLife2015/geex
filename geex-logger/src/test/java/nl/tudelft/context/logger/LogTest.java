package nl.tudelft.context.logger;

import nl.tudelft.context.logger.message.Message;
import nl.tudelft.context.logger.message.MessageType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 14-6-2015
 */
public class LogTest {
    @Test
    public void testInstance() {
        assertEquals(Log.instance(), Log.instance());
    }

    @Test
     public void testDebugLogger() {
        Logger logger = mock(Logger.class);

        when(logger.getLevel()).thenReturn(MessageType.DEBUG);

        Log.instance().addLogger(logger);

        Log.info(Message.MESSAGE_READY);
        verify(logger).log(Message.MESSAGE_READY, MessageType.INFO);

        Log.debug(Message.MESSAGE_READY);
        verify(logger).log(Message.MESSAGE_READY, MessageType.DEBUG);

        Log.warning(Message.MESSAGE_READY);
        verify(logger).log(Message.MESSAGE_READY, MessageType.WARNING);
    }

    @Test
    public void testInfoLogger() {
        Logger logger = mock(Logger.class);

        when(logger.getLevel()).thenReturn(MessageType.INFO);

        Log.instance().addLogger(logger);

        Log.info(Message.MESSAGE_READY);
        verify(logger).log(Message.MESSAGE_READY, MessageType.INFO);

        Log.debug(Message.MESSAGE_READY);
        verify(logger, never()).log(Message.MESSAGE_READY, MessageType.DEBUG);

        Log.warning(Message.MESSAGE_READY);
        verify(logger).log(Message.MESSAGE_READY, MessageType.WARNING);
    }

    @Test
    public void testWarningLogger() {
        Logger logger = mock(Logger.class);

        when(logger.getLevel()).thenReturn(MessageType.WARNING);

        Log.instance().addLogger(logger);

        Log.info(Message.MESSAGE_READY);
        verify(logger, never()).log(Message.MESSAGE_READY, MessageType.INFO);

        Log.debug(Message.MESSAGE_READY);
        verify(logger, never()).log(Message.MESSAGE_READY, MessageType.DEBUG);

        Log.warning(Message.MESSAGE_READY);
        verify(logger).log(Message.MESSAGE_READY, MessageType.WARNING);
    }

    @Test
    public void testRemoveLogger() {
        Logger logger = mock(Logger.class);

        when(logger.getLevel()).thenReturn(MessageType.DEBUG);

        Log.instance().addLogger(logger);

        Log.info(Message.MESSAGE_READY);
        verify(logger).log(Message.MESSAGE_READY, MessageType.INFO);

        Log.instance().removeLogger(logger);

        Log.debug(Message.MESSAGE_READY);
        verify(logger, never()).log(Message.MESSAGE_READY, MessageType.DEBUG);
    }
}
