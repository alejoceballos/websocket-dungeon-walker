# Dungeon Walker

Under development!

The goal is to create a RPG-like game where one ore more users can walk through different dungeon scenarios
facing a different set of adversities.

Currently, it's just a dream.

## Current state

<img src="README.files/v0.0.2-preview.gif" title="Version 0.0.2 Preview" alt="Version 0.0.2 Preview" height="50%" width="50%">


## Motivation

Use a game scenario to study a set of technologies, such as:

- New Java features since 8 (until 17)
- Spring Boot (Web MVC, Web Sockets, Security, Test, ...)
- Common used Java libraries (lombok, mapstruct, ...)
- Common used unit testing libraries (mockito, hamcrest, ...)
- REST APIs
- Web Sockets
- JQuery
- Docker (TBD)
- Apache Kafka (TBD)
- Some Relational/NoSQL database (TBD)
- Microservices (TBD)
- Kubernetes (TBD)
- Some cloud provider (AWS? GCP?)

## Running the game

### 1. Run using maven

```shell
mvn spring-boot:run
```

### 2. Open your browser

1. Go to `http://localhost:8080/`
2. Log in with user `test` and password `test`.

## Versions

#### Version 0.0.2 [99373f2](https://github.com/alejoceballos/websocket-dungeon-walker/commit/99373f23b9d49f2bcecc605c83d07e247477146f)

The map and its inhabitants can be dynamically loaded based on configuration files. This enables a different
set of maps allowing more complex scenarios.

<img src="README.files/v0.0.2-preview.gif" title="Version 0.0.2 Preview" alt="Version 0.0.2 Preview" height="50%" width="50%">

#### Version 0.0.1 [11731ba2](https://github.com/alejoceballos/websocket-dungeon-walker/commit/11731ba280bde152cee1f3f223758ee9bd16814a)

Nothing but a lot of letters wandering in a walled squared field, bumping and bouncing each other. But
their movement and positioning is controlled by the backend and dynamically updated using websockets.

<img src="README.files/v0.0.1-preview.gif" title="Version 0.0.1 Preview" alt="Version 0.0.1 Preview" height="50%" width="50%">

## Basic Architecture

The game consists in a map where its "walkers" can navigate in 8 cardinal directions. A map is made by
"cells" that can be empty or contain blocking elements, like walls (and the walkers themselves). Auto
walkers may have different movement strategies.

![Class Diagram](README.files/class-diagram.png)

### Loading maps and dungeon elements

Since version 0.0.2 it is possible to "draw" a map identifying its components and where they are placed
and set a json file with their characteristics.

![Map Load Sequence Diagram](README.files/map-load-sequence-diagram.png)

