@ECHO OFF
CALL docker pull neo4j:3.5.20
START /I CMD /C docker run -it --publish=7687:7687 --publish=7474:7474 --name neo4j --volume=%userprofile%/neo4j_docker/data:/data --env NEO4J_dbms_connector_bolt_tls__level=DISABLED --env NEO4J_AUTH=neo4j/neo3j --env NEO4JLABS_PLUGINS=[\"apoc\"] neo4j:3.5.19
CALL docker pull maxim615/coderadar

::Wait for neo4j to start
waitfor /T 30 pause 2>NUL
CALL docker run -it --rm -p 8080:8080 --name coderadar --network="bridge" --env spring.data.neo4j.uri=bolt://host.docker.internal:7687 maxim615/coderadar:latest
