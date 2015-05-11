package nl.tudelft.context.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import nl.tudelft.context.newick.Tree;
import nl.tudelft.context.newick.TreeFactory;

import java.io.File;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 28-4-2015
 */
public class LoadNewickService extends Service<Tree> {

    protected File nwkFile;

    public LoadNewickService() {
    }

    /**
     * Set nwk file.
     *
     * @param nwkFile nwk file location
     */
    public LoadNewickService(File nwkFile) {

        this.nwkFile = nwkFile;

    }

    /**
     * Create a task that loads the newick.
     *
     * @return the Task to execute
     */
    @Override
    protected Task<Tree> createTask() {

        return new Task<Tree>() {
            @Override
            protected Tree call() throws Exception {
                TreeFactory treeFactory = new TreeFactory();
                return treeFactory.getTree(nwkFile);
            }
        };

    }
}
