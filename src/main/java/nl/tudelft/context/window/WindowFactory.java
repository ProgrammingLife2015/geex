package nl.tudelft.context.window;

import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 24-4-2015
 */
public class WindowFactory {

    public GLWindow getWindow(GLCapabilities caps) {

        GLWindow window = GLWindow.create(caps);

        window.setSize(800, 600);
        window.setTitle("Programming Life");
        window.setDefaultCloseOperation(WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE);
        window.setVisible(true);

        return window;

    }

}
