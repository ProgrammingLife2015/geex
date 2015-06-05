package nl.tudelft.context.controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 5-6-2015
 */
public class SelectNewickController extends DefaultNewickController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public String getBreadcrumbName() {
        return "Select strains ( " + 0 + ")";
    }

}
