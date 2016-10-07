# coderadar

[![Build Status](https://circleci.com/gh/thombergs/coderadar.svg?style=shield&circle-token=:circle-token)](https://circleci.com/gh/thombergs/coderadar)

coderadar aims to be a continuous source code analysis tool that connects to your version control system (VCS) and automatically runs code analyzers that provide you with the metrics that are most important to you and your team. coderadar provides per-commit code metrics and even allows viewing the history of code metrics in legacy projects. Also, coderadar aims to provide metrics for each committer, thus enabling gamification features that add fun to creating quality code.

## coderadar server (work in progress)
The coderadar server is a web application that provides a REST API to interact with it. Via the REST API, you can configure your project and retrieve source code metrics for each commit in your version control system.

Snapshot version of the documentation:
* [REST API documentation](http://thombergs.github.io/coderadar/1.0.0-SNAPSHOT/docs/restapi.html).
* [Administration Guide](http://thombergs.github.io/coderadar/1.0.0-SNAPSHOT/docs/admin.html)

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


