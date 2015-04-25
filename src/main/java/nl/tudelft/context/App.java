package nl.tudelft.context;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;
import nl.tudelft.context.window.Main;
import nl.tudelft.context.window.WindowFactory;

import java.io.FileNotFoundException;

/**
 * App
 *
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 23-4-2015
 */
public class App {

    static {
        GLProfile.initSingleton();
    }

    /**
     * @param args ignored
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {

        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);

        WindowFactory windowFactory = new WindowFactory();
        GLWindow window = windowFactory.getWindow(caps);

        window.addGLEventListener(new Main());

        FPSAnimator animator = new FPSAnimator(window, 30);
        animator.start();

    }

}
