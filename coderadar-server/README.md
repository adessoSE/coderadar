# server
This component contains the coderadar server which is a
web application running on Spring Boot. 

The coderadar server contains components that do the following
* checkout remote git repositories
* run analyzer plugins on source code to create metrics
* provide a [REST API](http://www.reflectoring.io/coderadar/1.0.0-SNAPSHOT/docs/restapi.html)
  to create projects and access metrics data