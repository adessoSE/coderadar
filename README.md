# coderadar

[![Build Status](https://circleci.com/gh/reflectoring/coderadar.svg?style=shield&circle-token=:circle-token)](https://circleci.com/gh/reflectoring/coderadar) [![Gradle Status](https://gradleupdate.appspot.com/reflectoring/coderadar/status.svg)](https://gradleupdate.appspot.com/reflectoring/coderadar/status)

coderadar aims to be a continuous source code analysis tool that connects to your version control system (VCS) and automatically runs code analyzers that provide you with the metrics that are most important to you and your team. coderadar provides per-commit code metrics and even allows viewing the history of code metrics in legacy projects. Also, coderadar aims to provide metrics for each committer, thus enabling gamification features that add fun to creating quality code.

## Goals of coderadar
The overriding goal of coderadar is **to make it easy for you to improve the code quality** of your project. To achieve that, coderadar pursues the following goals:

* **Easy to use**
 * Find the data you want with as few clicks as possible
 * View only the data you need without being distracted by an overstuffed user interface
 * Minimal configuration neccessary to be able to get started quickly 
 * Support regular code quality meetings in a development team as good as possible

* **Flexible**
 * Measure your own definition of code quality
 * Customize your view on the data to fit your needs

* **Modular**
 * Low threshold for developing analyzer plugins to measure specific quality metrics
 * Low threshold for developing user interface plugins to visualize quality metrics
 * Easy installation of plugins

* **Fun**
 * Support gamification to make code quality fun
 
## Roadmap
The development of coderadar loosely follows a roadmap that serves as a guide when prioritizing issues and feature requests:

Milestone | Short Description | Status
----------|-------------------|-------
Proof of Concept | Build a server that supports the idea of analyzing a source code repository on-the-fly. Prove that it works with big code bases (>500.000 loc). | [ ]
Basic User Interface | Build a user interface that supports basic use cases like a comparing metrics between two commits. | [ ]
Minimum Viable Product | Add all features that are needed for coderadar to work in a real environment, like user management, security and administration | [ ] 

## Documentation
* [REST API documentation](http://reflectoring.github.io/coderadar/1.0.0-SNAPSHOT/docs/restapi.html).
* [Administration Guide](http://reflectoring.github.io/coderadar/1.0.0-SNAPSHOT/docs/admin.html)

## Want to contribute?
See the [contribution guide](https://github.com/reflectoring/coderadar/blob/master/CONTRIBUTING.md).
