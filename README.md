[![Build Status](https://travis-ci.org/AbeelLab/geex.svg?branch=master)](https://travis-ci.org/AbeelLab/geex)

![Geex](SE%20deliverables/logo.png)

## Programming Life (group 2)
For more details about this project, the team and the application, visit the [Wiki pages](https://github.com/AbeelLab/geex/wiki).

## Sites
* [Maven Site](http://geex.hup.blue/) (Updated manually)
* [Sonarqube](http://sonar.hup.blue/) (Updated manually)

## Maven

### Install
Before using any of the MVN lifecycle aspects for the first time or after an update, run `mvn install`

### Site
To generate a site run `mvn site:site site:stage`. The staging site will be in `target/staging`. Just running `mvn site` will not create proper links between modules.

### Package
To create a runnable jar run `mvn package`, the runnable jar is created in `geex-core/target/geex-core-<VERSION>-jar-with-dependencies.jar`.

## Diagrams
The diagrams are based on [PlantUML](http://plantuml.sourceforge.net/). Install the PlantUML Integration plugin from the IntelliJ plugin browser. The diagrams are also available in the Maven site.

In order for the rendering of the graphs to work [GraphViz](http://www.graphviz.org/Download..php) is needed. This is an external dependeny which cannot be installed with maven.
