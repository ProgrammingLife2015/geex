package nl.tudelft.context.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import net.sourceforge.olduvai.treejuxtaposer.TreeParser;
import net.sourceforge.olduvai.treejuxtaposer.drawer.Tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 28-4-2015
 */
public class LoadNewickService extends Service<Tree> {

    protected File nwkFile;

    /**
     * Set nwk file.
     *
     * @param nwkFile nwk file location
     */
    public void setNwkFile(File nwkFile) {

        this.nwkFile = nwkFile;

    }

    /**
     * Create a task that loads the tree.
     *
     * @return the Task to execute
     */
    @Override
    protected Task<Tree> createTask() {

        return new Task<Tree>() {
            @Override
            protected Tree call() throws Exception {
                BufferedReader r = new BufferedReader(new FileReader(nwkFile));
                TreeParser tp = new TreeParser(r);
                return tp.tokenize(1, "ieuw, bacillen", null);
            }
        };

    }
}
