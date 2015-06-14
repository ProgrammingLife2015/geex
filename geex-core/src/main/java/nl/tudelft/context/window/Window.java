package nl.tudelft.context.window;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nl.tudelft.context.App;

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
                new Image(App.class.getResourceAsStream("/application/images/icon_16.png")),
                new Image(App.class.getResourceAsStream("/application/images/icon_32.png")),
                new Image(App.class.getResourceAsStream("/application/images/icon_48.png")),
                new Image(App.class.getResourceAsStream("/application/images/icon_64.png")),
                new Image(App.class.getResourceAsStream("/application/images/icon_128.png")),
                new Image(App.class.getResourceAsStream("/application/images/icon_256.png"))
        );

        setMinWidth(FRAME_WIDTH);
        setMinHeight(FRAME_HEIGHT);
        setMaximized(true);
        setScene(scene);
        show();

    }

}
