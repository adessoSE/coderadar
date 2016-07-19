# coderadar

coderadar aims to be a continuous source code analysis tool. Point coderadar at your version control system and it analyzes your code for you.

## coderadar server (work in progress)
The coderadar server is a web application that provides a REST API to interact with it. Via the REST API, you can configure your project and retrieve source code metrics for each commit in your version control system.

Browse the current snapshot version of the REST API documentation [here](http://thombergs.github.io/coderadar/1.0.0-SNAPSHOT/docs/restapi/index.html).

## coderadar app (planned)
The coderadar app connects to the coderadar server REST API and provides a user interface to configure your project and view the source code metrics via web browser. Features planned for the app include:
* comparison of source code metrics between two commits
* charting source code metrics history over time
* defining your own quality profiles by combining metrics of your choice
* gamification features like highscores where each committer can view his or her source code quality score
* ...

## coderadar analyzer plugins (planned)
coderadar is meant to be extensible in that you can implement your own analyzer plugins that provide the metrics you need for your own definition of quality. Plugins that are planned to be bundled with the coderadar server are:
* PMD plugin
* Checkstyle plugin
* Findbugs plugin
* Java metrics (LOC, #classes, ...)
* ...
* 


