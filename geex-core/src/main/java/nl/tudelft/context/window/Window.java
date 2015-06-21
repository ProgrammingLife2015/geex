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
 * @author René Vennik
 * @version 1.0
 * @since 12-6-2015
 */
public class Window extends Stage {

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
     * Create a window with a view.
     *
     * @param title  Title of the window
     * @param parent Parent containing the view
     */
    public Window(final String title, final Parent parent) {


        final Scene scene = new Scene(parent, SCENE_BACKGROUND);
        scene.getStylesheets().add("/application/css/style.css");

        setTitle(title);
        getIcons().addAll(
                ICONS.stream()
                        .map(this::getImage)
                        .collect(toList())
        );

        setMinWidth(FRAME_WIDTH);
        setMinHeight(FRAME_HEIGHT);
        setMaximized(true);
        setScene(scene);
        show();

    }

    /**
     * Gets the image that belongs to the given source.
     *
     * @param source The source of the image
     * @return       The image
     */
    public Image getImage(final String source) {
        return new Image(getClass().getResourceAsStream(source));
    }

}
