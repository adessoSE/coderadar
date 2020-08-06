# Contributing to coderadar

Thanks for donating your time to developing coderadar! This is the most
valuable contribution you can make.

This guide gives an overview of coderadar and some development guidelines
to make it easier for new contributors to get up and running. If you have
any questions, feel free to get in touch.

## Overview of coderadar components
Major components are documented via a README file in the folder of the component.
The following list links to those READMEs:

* [coderadar-app](https://github.com/adessoAG/coderadar/tree/master/coderadar-app): Contains the coderadar application and has a dependency on every other module.
* [coderadar-core](https://github.com/adessoAG/coderadar/tree/master/coderadar-core): Contains the core logic of coderadar. Has a dependency on coderadar-plugin-api.
* [coderadar-graph](https://github.com/adessoAG/coderadar/tree/master/coderadar-graph): Contains database logic and connects to Neo4j.
* [coderadar-plugin-api](https://github.com/adessoAG/coderadar/tree/master/coderadar-plugin-api): API classes for creating analyzers.
* [coderadar-plugins](https://github.com/adessoAG/coderadar/tree/master/coderadar-plugins): Analyzer plugins that are by default contained in the coderadar server.
* [coderadar-vcs](https://github.com/adessoAG/coderadar/tree/master/coderadar-vcs): Contains logic for working with git repositories.
* [coderadar-rest](https://github.com/adessoAG/coderadar/tree/master/coderadar-rest): Contains all REST-endpoints.
* [coderadar-dependency-map](https://github.com/adessoAG/coderadar/tree/master/coderadar-dependency-map): Contains logic for calculating dependency maps.
* [coderadar-test](https://github.com/adessoAG/coderadar/tree/master/coderadar-test): Contains integration tests.
* [coderadar-ui](https://github.com/adessoAG/coderadar/tree/master/coderadar-ui): Contains the coderadar UI.

## How can I contribute?

Any kind of contribution is welcome as a pull request.
If you are unsure on how to fork a repository and then create a pull 
request from your fork, please read [this blog post](https://reflectoring.io/github-fork-and-pull/)
for a quick guide.

### Pick an Issue
You can pick any issue from the [issue tracker](https://github.com/adessoAG/coderadar/issues). 
We try to document all feature ideas, tasks and bugs as issues in the tracker.
Issues marked with the tag ["up-for-grabs"](https://github.com/adessoAG/coderadar/issues?q=is%3Aissue+is%3Aopen+label%3Aup-for-grabs) are especially documented so that 
new contributors should get an idea of what is to do. You might want to
start with one of those tasks. You can just as well pick any other task, though.
If you have any questions, get in touch.

### Submit an Idea
If you have an idea, submit it to the [issue tracker](https://github.com/adessoAG/coderadar/issues)
for discussion. I will let you know my thoughts. If you want to develop your idea
yourself, you can do so and submit a pull request.

## Coding Conventions
Below are some hints on conventions used in this project. If you are unsure about
any, just get in touch. During a pull request review, we will also check these
conventions. Fear not, the worst thing that may happen if you do not follow them
is that we might propose some changes to a pull request you submitted.

### Styleguide
For java code style, the gradle plugin ["spotless"](https://github.com/diffplug/spotless) has been included in the build to enforce the
[Google Java Code style](https://google.github.io/styleguide/javaguide.html). Builds will fail if 
the code does not comply to the style guide. To apply the style guide simply call 
`gradlew spotlessApply`. Only after applying the style guide should you push your changes.

**Hint:** in order for automatic style guide enforcement to work, you have to **disable**
automatic code formatting on check-in in your IDE!

### Documentation
Please keep documentation up-to-date when changing the code. Documentation
is made up of the following elements:

* Documentation of the REST API. This documentation is made with [AsciiDoctor](http://asciidoctor.org/) and
  [Spring RestDocs](https://projects.spring.io/spring-restdocs/). Example requests
  and responses are generated automatically from the integration tests covering
  the REST controllers. The documentation files are [here](https://github.com/adessoAG/coderadar/tree/master/coderadar-test/coderadar-core/src/main/asciidoc).
* README.md files in the folders of all main components
* Javadoc: please provide sensible javadoc of at least public API
* This contribution guide: this guide is not carved in stone, so when things change,
  change this guide. 
  
## Getting Started

### Building coderadar
Simply run `gradlew build`.

### Starting coderadar
* install Neo4j
* copy the file `coderadar-app/src/main/resources/local.application.properties.template` into a file named `local.application.properties`
  in the same folder and edit the configuration parameters in that file for your local environment and database
* run `gradle build bootRun` from the project directory
* coderadar will be accessible on `localhost:8080` by default
* alternatively, if you have Docker installed, running `./run.sh` or `run.bat` will start coderadar and Neo4j in docker images.

### Accessing the coderadar REST API
* start coderadar
* install a REST client like DHC
* refer to the [REST API documentation](https://adessoag.github.io/coderadar/current/docs/restapi.html)
  to see what calls you can make and submit them using your REST client
 
