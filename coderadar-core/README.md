# coderadar-core

This module contains the core logic of coderadar, including creating and analyzing projects.
The core provides ports for different adapters to implement and does not itself have any means of
manipulating the database or interacting with local git repositories. Such functionality is provided
by the graph and VCS adapters respectively.
