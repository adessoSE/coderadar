#!/bin/bash

trap "exit_func" INT

if ! [ $(id -u) = 0 ]; then
   echo "Root permissions required!"
   exit 1
fi

exit_func() {
    docker kill neo4j &> /dev/null
    exit
}

docker pull neo4j:3.5.20

docker run -t --publish=7687:7687 \
            --publish=7474:7474 --name neo4j \
            --volume=$HOME/neo4j_docker/data:/data \
            --env NEO4J_dbms_connector_bolt_tls__level=OPTIONAL \
            --env NEO4J_AUTH=neo4j/neo3j \
            --env 'NEO4JLABS_PLUGINS=["apoc"]' neo4j:3.5.19 > /dev/null &

docker pull maxim615/coderadar


sleep 30
docker run -it --name coderadar --network="host" maxim615/coderadar
docker kill neo4j &> /dev/null
