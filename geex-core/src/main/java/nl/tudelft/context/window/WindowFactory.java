package nl.tudelft.context.window;

import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * @author Jasper Boot
 * @version 1.0
 * @since 23-06-2015
 */
public class WindowFactory {
    /**
     * Private constructor because of static methods.
     */
    private WindowFactory() {
        // do nothing
    }

    /**
     * Creates a window with a specific background, attached to a given parent.
     *
     * @param parent The parent to attach to
     * @return       The newly created window
     */
    public static Window create(final String name, final Parent parent) {

        Window window = new Window(name);
        Scene scene = new Scene(parent, Window.SCENE_BACKGROUND);
        scene.getStylesheets().add("/application/css/style.css");
        window.setScene(scene);
        window.show();

        return window;

    }
}
