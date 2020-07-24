START /I CMD /K docker run -it --rm --publish=7687:7687 --publish=7474:7474 --name neo4j --volume=%cd%/neo4j/plugins:/plugins --volume=%userprofile%/neo4j/data:/data --env NEO4J_dbms_connector_bolt_tls__level=DISABLED --env NEO4J_AUTH=neo4j/neo3j --env 'NEO4JLABS_PLUGINS=["apoc"]' neo4j:3.5.11
CALL gradlew.bat build
CALL docker build --tag coderadar:0.5 .
CALL docker run -it --rm -p 8080:8080 --name coderadar --network="bridge" coderadar:0.5