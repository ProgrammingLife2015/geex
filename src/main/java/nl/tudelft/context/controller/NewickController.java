package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.drawable.NewickLabel;
import nl.tudelft.context.newick.Tree;
import nl.tudelft.context.service.LoadNewickService;
import nl.tudelft.context.workspace.Workspace;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 8-5-2015
 */
public final class NewickController extends ViewController<ScrollPane> {

    /**
     * This is the x disposition of the load button.
     */
    public static final double LOAD_BUTTON_OFFSET = -50;
    /**
     * ProgressIndicator to show when the tree is loading.
     */
    @FXML
    ProgressIndicator progressIndicator;

    /**
     * The container of the newick tree.
     */
    @FXML
    Group newick;

    /**
     * The main controller used to set views.
     */
    MainController mainController;

    /**
     * The service used for loading the newick tree.
     */
    LoadNewickService loadNewickService;

    /**
     * Init a controller at newick.fxml.
     *
     * @param mainController   MainController for the application
     */
    public NewickController(final MainController mainController) {

        super(new ScrollPane());

        this.mainController = mainController;

        Workspace workspace = mainController.getWorkspace();
        this.loadNewickService = new LoadNewickService(workspace.getNwkFile());

        loadFXML("/application/newick.fxml");




    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

        progressIndicator.visibleProperty().bind(loadNewickService.runningProperty());

        loadTree();

    }

    /**
     * Load newick from source.
     */
    public void loadTree() {

        loadNewickService.setOnSucceeded(event -> {
            showTree(loadNewickService.getValue());
            mainController.displayMessage(MessageController.SUCCESS_LOAD_TREE);
        });
        loadNewickService.setOnFailed(event -> mainController.displayMessage(MessageController.FAIL_LOAD_TREE));
        loadNewickService.restart();


    }

    /**
     * Show the newick in console.
     *
     * @param tree newick to show
     */
    protected void showTree(final Tree tree) {

        // Bind edges
        List<DrawableEdge> edgeList = tree.edgeSet().stream()
                .map(edge -> new DrawableEdge(tree, edge))
                .collect(Collectors.toList());

        // Bind nodes
        List<Label> nodeList = tree.vertexSet().stream()
                .map(NewickLabel::new)
                .collect(Collectors.toList());

        newick.getChildren().addAll(edgeList);
        newick.getChildren().addAll(nodeList);

        Label button = new Label("Load");
        button.setTranslateX(LOAD_BUTTON_OFFSET);
        button.setOnMouseClicked(event -> loadGraph(tree));
        root.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                loadGraph(tree);
            }
        });

        newick.getChildren().add(button);
    }

    protected void loadGraph(Tree tree) {
        Set<String> sources = tree.getRoot().getSources();
        if (!sources.isEmpty()) {
            mainController.setView(new GraphController(mainController, sources));
        }
    }

    @Override
    public String getBreadcrumbName() {
        return "Phylogenetic tree";
    }

}
