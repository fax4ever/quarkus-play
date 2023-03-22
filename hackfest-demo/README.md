# HackFest demo

0. Go to the project directory

``` shell
cd /Users/fabio/code/quarkus-play/hackfest-demo
```

1. Let's start a Jeager server

``` shell
docker run --name jaeger \
  -e COLLECTOR_OTLP_ENABLED=true \
  -p 16686:16686 \
  -p 4317:4317 \
  --rm \
  jaegertracing/all-in-one:1.36
```

``` shell
docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' jaeger
```

=> in case we need to change the `application.properties`

2. Run Quarkus dev mode

``` shell
./mvnw compile quarkus:dev
```

Type 'd' to open the developer page

3. Infinispan web console

* Use admin / password credentials
* See the schema

4. Open the RESTEasy list of entry point

5. Create the books

``` shell
http PUT localhost:8080/books
``` 

Back to the Infinispan web console

* See the cache configuration
* See the data

6. Open the Jeager server

> http://localhost:16686/search

Show the tracing

7. Play with queries

``` shell
http GET localhost:8080/books/description/java
``` 

Use the console to play with queries

```
from fax.play.book b where b.description : 'java'
```