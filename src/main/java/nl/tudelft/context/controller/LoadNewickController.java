package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import net.sourceforge.olduvai.treejuxtaposer.drawer.Tree;
import net.sourceforge.olduvai.treejuxtaposer.drawer.TreeNode;
import nl.tudelft.context.service.LoadNewickService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 1-5-2015
 */
public class LoadNewickController extends DefaultController<GridPane> implements Initializable {

    @FXML
    protected Button
            loadNewick,
            load;

    @FXML
    protected TextField
            nwkSource;

    protected LoadNewickService loadNewickService;
    protected ProgressIndicator progressIndicator;

    /**
     * Init a controller at load_newick.fxml.
     *
     * @param progressIndicator progress indicator of Newick loading
     * @throws RuntimeException
     */
    public LoadNewickController(ProgressIndicator progressIndicator) {

        super(new GridPane());

        this.progressIndicator = progressIndicator;

        loadFXML("/application/load_newick.fxml");


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
    public void initialize(URL location, ResourceBundle resources) {

        loadNewickService = new LoadNewickService();
        loadNewickService.setOnSucceeded(event -> showTree(loadNewickService.getValue()));

        FileChooser nwkFileChooser = new FileChooser();
        nwkFileChooser.setTitle("Open Newick file");
        loadNewick.setOnAction(event -> loadNewickService.setNwkFile(loadFile(nwkFileChooser, nwkSource)));

        load.setOnAction(event -> loadTree());

    }

    /**
     * Load tree from source.
     */
    protected void loadTree() {

        loadNewickService.restart();

    }

    /**
     * Show the tree in console.
     *
     * @param tree tree to show
     */
    protected void showTree(Tree tree) {
        System.out.println("check: " + tree.getName());
        printTree(tree.getRoot(), .0);
    }

    /**
     * Print tree recursive to console.
     *
     * @param node   current node
     * @param prev_w previous position
     */
    protected void printTree(TreeNode node, double prev_w) {
        for (int i = 0; i < node.numberLeaves; i += 1) {
            if (node.getChild(i) != null) {
                double w = prev_w + node.getChild(i).getWeight() * .5e4;
                for (int h = 0; h < w; h += 1) {
                    System.out.print("  ");
                }
                System.out.println(node.getChild(i).getName());
                printTree(node.getChild(i), w);
            }
        }
    }

}
