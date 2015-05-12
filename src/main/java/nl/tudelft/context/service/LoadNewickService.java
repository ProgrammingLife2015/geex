package nl.tudelft.context.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import nl.tudelft.context.newick.Tree;
import nl.tudelft.context.newick.TreeFactory;

import java.io.File;

/**
 * Service for loading a Newick tree.
 *
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 28-4-2015
 */
public final class LoadNewickService extends Service<Tree> {

    /**
     * The file containing the newick tree.
     */
    File nwkFile;

    /**
     * Set nwk file.
     *
     * @param nwkFile nwk file location
     */
    public LoadNewickService(final File nwkFile) {

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
