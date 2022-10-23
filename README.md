![Context Mapper](https://raw.githubusercontent.com/wiki/ContextMapper/context-mapper-dsl/logo/cm-logo-github-small.png)
# Context Mapper Web App

Checkout the website [https://contextmapper.org/](https://contextmapper.org/) to get started.

## Features
* Create Context Maps with the help of the Context Mapper Generator and DSL
  * Write context maps with bounded contexts and their relationships
  * Find examples in the [examples repository](https://github.com/ContextMapper/context-mapper-examples)
  * Consult the [online documentation](https://contextmapper.org/docs/) to get detailed language documentation, manuals and how to get started.
* Export diagrams in *.png, *.svg or *.gv format

## System Requirements
To use the ContextMapper Web App you need the following tools:
* [Docker](https://www.docker.com/) 
  * [Docker Compose 2.0.0+](https://github.com/docker/compose)

To use the application without docker you need the following tools: 
* [Java JDK](https://www.oracle.com/java/technologies/downloads/#java17) (JDK 17 or newer)
* [Graphviz](https://www.graphviz.org/) for diagram generation
* [Gradle](https://gradle.org/) version 7.5.1 was used 
* [npm](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm) (8.19.1+) or [yarn](https://classic.yarnpkg.com/lang/en/docs/install/) (1.22.19+)
  * Node Version 18.9.0 or higher

## Getting Started for docker
1. Execute start.bat or start.sh depending on the system
2. Open localhost in the web browser

## Getting Started locally
1. Execute ```start.bat local``` or ```start.sh local``` depending on the system to start the backend
2. Run ```npm install``` or ```yarn install``` in the /gui subfolder
3. Run ```npm start``` or ```yarn start``` to start the gui
4. Open ```localhost:4200``` in the web browser

## Licence
ContextMapper is released under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
