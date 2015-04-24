package nl.tudelft.context.window;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.graph.GraphFactory;

import java.io.FileNotFoundException;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 24-4-2015
 */
public class Main implements GLEventListener {

    protected double theta = 0;

    public Main() throws FileNotFoundException {

        GraphFactory graphFactory = new GraphFactory();
        Graph graph = graphFactory.getGraph("/graph/10_strains_graph/simple_graph.node.graph", "/graph/10_strains_graph/simple_graph.edge.graph");

    }

    /**
     * Called on init of window.
     *
     * @param drawable drawable
     */
    public void init(GLAutoDrawable drawable) {

    }

    /**
     * Called on dispose and exits program.
     *
     * @param drawable drawable
     */
    public void dispose(GLAutoDrawable drawable) {

        System.exit(0);

    }

    /**
     * Render frame.
     *
     * @param drawable drawable
     */
    public void display(GLAutoDrawable drawable) {

        theta += 0.01;
        double s = Math.sin(theta);
        double c = Math.cos(theta);

        GL2 gl = drawable.getGL().getGL2();

        // Clear buffer
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        // Draw something
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glColor3f(1, 0, 0);
        gl.glVertex2d(-c, -c);
        gl.glColor3f(0, 1, 0);
        gl.glVertex2d(0, c);
        gl.glColor3f(0, 0, 1);
        gl.glVertex2d(s, -s);
        gl.glEnd();

    }

    /**
     * Called on resize of window.
     *
     * @param drawable drawable
     * @param x x coordinate of the viewport
     * @param y y coordinate of the viewport
     * @param width width of window
     * @param height height of window
     */
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    }

}
