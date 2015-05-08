package nl.tudelft.context;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.tudelft.context.controller.MainController;

import java.util.Locale;

/**
 * Entry point of the App.
 *
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 23-4-2015
 */
public class App extends Application {

    protected static final int FRAME_WIDTH = 800;
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
    final public void start(final Stage stage) {

        MainController controller = new MainController();
        Scene scene = new Scene(controller.getRoot());

        stage.setTitle("Programming Life");
        stage.setScene(scene);
        stage.setMinHeight(FRAME_HEIGHT);
        stage.setMinWidth(FRAME_WIDTH);
        stage.setMaximized(true);
        stage.show();

    }

}
