package nl.tudelft.context;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.tudelft.context.controller.MainController;

import java.io.IOException;

/**
 * App
 *
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 23-4-2015
 */
public class App extends Application {

    /**
     * @param args arguments
     */
    public static void main(String[] args) {

        launch(args);

    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * <p>
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param stage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage stage) throws IOException {

        MainController controller = new MainController("/graph/10_strains_graph/simple_graph.node.graph", "/graph/10_strains_graph/simple_graph.edge.graph");
        Scene scene = new Scene(controller);

        stage.setTitle("Programming Life");
        stage.setScene(scene);
        stage.setMinHeight(600);
        stage.setMinWidth(800);
        stage.setMaximized(true);
        stage.show();

    }

}
