package nl.tudelft.context.window;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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

    public Window(String title, Parent parent) {

        Scene scene = new Scene(parent);
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
