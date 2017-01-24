# coderadar-plugin-api

This component contains the API for creating analyzer plugins.

## What is an analyzer?
An analyzer is a plugin that creates or imports code metrics into coderadar.
These metrics are then accessible via the coderadar API so that users can
view the metrics.

The following types of plugins can be created by implementing an interface:
* [**SourceCodeAnalyzerPlugins**](https://github.com/reflectoring/coderadar/blob/master/coderadar-plugin-api/src/main/java/org/wickedsource/coderadar/analyzer/api/SourceCodeFileAnalyzerPlugin.java): 
  a plugin that takes a source code file and calculates metrics from it.
* [**AdapterPlugins**](https://github.com/reflectoring/coderadar/blob/master/coderadar-plugin-api/src/main/java/org/wickedsource/coderadar/analyzer/api/AdapterPlugin.java):
  a plugin that takes a report file from some external source and maps it into 
  coderadar metrics.
  
## How do I create an analyzer plugin?
Plugins are registered using the Java [ServiceLoader](http://docs.oracle.com/javase/8/docs/api/java/util/ServiceLoader.html)
mechanism. In short, you have to do the following:
 
* Create a class that implements one of the plugin interfaces
* Create a jar containing the the following
  * the implementing class 
  * a folder named META-INF.services 
  * a file named like the full qualified classname of the implemented interface in the services folder
  * the file contains only the full qualified classname of the implementing
    class
* Put the jar into the classpath of coderadar

## How do I activate a plugin?
Plugins are activated for a project via the [REST API](http://www.reflectoring.io/coderadar/1.0.0-SNAPSHOT/docs/restapi.html#_analyzer_configuration).