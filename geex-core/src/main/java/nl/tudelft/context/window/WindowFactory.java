package nl.tudelft.context.window;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Jasper Boot
 * @version 1.0
 * @since 23-06-2015
 */
public class WindowFactory {

    /**
     * Minimum width in pixels of window.
     */
    public static final int FRAME_WIDTH = 800;

    /**
     * Minimum height in pixels of window.
     */
    public static final int FRAME_HEIGHT = 600;

    /**
     * Scene background color.
     */
    public static final Color SCENE_BACKGROUND = new Color(0.1686, 0.1686, 0.1686, 1);

    /**
     * Different icon sizes for the logo.
     */
    private static final List<String> ICONS = Arrays.asList(
            "/application/images/icon_16.png",
            "/application/images/icon_32.png",
            "/application/images/icon_48.png",
            "/application/images/icon_64.png",
            "/application/images/icon_128.png",
            "/application/images/icon_256.png");

    /**
     * Private constructor because of static methods.
     */
    private WindowFactory() {
        // do nothing
    }

    /**
     * Creates a window with a specific background, attached to a given parent.
     *
     * @param title  The title for the window
     * @param parent The parent to attach to
     * @return       The newly created window
     */
    public static Stage create(final String title, final Parent parent) {

        Stage window = new Stage();
        addScene(window, parent, title);
        styleWindow(window);
        window.show();

        return window;

    }

    /**
     * Adds a scene to the window, with a given parent and window title.
     *
     * @param window The window to add the scene to
     * @param parent The parent to attach to the scene
     * @param title  The title to give the window
     */
    public static void addScene(final Stage window, final Parent parent, final String title) {
        Scene scene = new Scene(parent, SCENE_BACKGROUND);
        window.setTitle(title);
        scene.getStylesheets().add("/application/css/style.css");
        window.setScene(scene);
    }

    /**
     * Styles the window with icons and the right window size.
     *
     * @param window The window to style
     */
    public static void styleWindow(final Stage window) {
        window.getIcons().addAll(
                ICONS.stream()
                        .map(WindowFactory::getImage)
                        .collect(toList())
        );

        window.setMinWidth(FRAME_WIDTH);
        window.setMinHeight(FRAME_HEIGHT);
        window.setMaximized(true);
    }

    /**
     * Gets the image that belongs to the given source.
     *
     * @param source The source of the image
     * @return       The image
     */
    public static Image getImage(final String source) {
        return new Image(WindowFactory.class.getClass().getResourceAsStream(source));
    }
}
