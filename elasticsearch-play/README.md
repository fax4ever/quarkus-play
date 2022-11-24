1. Run OpenSearch

``` bash
docker run --name elasticsearch -e "discovery.type=single-node" -e "plugins.security.ssl.http.enabled=false" \
    --rm -p 9200:9200 opensearchproject/opensearch:2.4.0
```

2. Build Quarkus client application

> mvn clean install -DskipTests

3. Run Quarkus client application (in dev mode)

> mvn quarkus:dev -Ddebug

4. Add a fruit

> curl localhost:8080/fruits -d '{"name": "bananas", "color": "yellow"}' -H "Content-Type: application/json"

5. Search fruit

> curl localhost:8080/fruits/search\?color=yellow