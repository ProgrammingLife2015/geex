package nl.tudelft.context;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import nl.tudelft.context.controller.MainController;

/**
 * Entry point of the App.
 *
 * @author René Vennik
 * @version 1.0
 * @since 23-4-2015
 */
public class App extends Application {

    /**
     * Minimum width in pixels of application.
     */
    protected static final int FRAME_WIDTH = 800;

    /**
     * Minimum height in pixels of application.
     */
    protected static final int FRAME_HEIGHT = 600;

    /**
     * @param args arguments
     */
    public static void main(final String... args) {

        launch(args);

    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param stage the primary stage for this application.
     */
    @Override
    public final void start(final Stage stage) {

        MainController controller = new MainController();
        Scene scene = new Scene(controller.getRoot());

        stage.setTitle("Geex");
        stage.getIcons().addAll(
                new Image(App.class.getResourceAsStream("/application/images/icon_16.png")),
                new Image(App.class.getResourceAsStream("/application/images/icon_32.png")),
                new Image(App.class.getResourceAsStream("/application/images/icon_48.png")),
                new Image(App.class.getResourceAsStream("/application/images/icon_64.png")),
                new Image(App.class.getResourceAsStream("/application/images/icon_128.png")),
                new Image(App.class.getResourceAsStream("/application/images/icon_256.png"))
        );
        stage.setScene(scene);
        stage.setMinHeight(FRAME_HEIGHT);
        stage.setMinWidth(FRAME_WIDTH);
        stage.setMaximized(true);
        stage.show();

    }

}
