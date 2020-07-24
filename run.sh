#!/bin/bash

trap "exit_func" INT

exit_func() {
    kill -9 $PID1
    kill -9 $PID2
    exit
}

docker run --rm -t --publish=7687:7687 \
            --publish=7474:7474 --name neo4j \
            --volume=$HOME/neo4j/data:/data \
            --volume=$PWD/neo4j/plugins:/plugins \
            --env NEO4J_dbms_connector_bolt_tls__level=DISABLED \
            --env NEO4J_AUTH=neo4j/neo3j \
            --env 'NEO4JLABS_PLUGINS=["apoc"]' neo4j:3.5.11 &

PID1=$!

./gradlew build
docker build --tag coderadar:0.5 .
docker run -it --rm -p 8080:8080 --name coderadar --network="bridge" coderadar:0.5
PID2=$!

