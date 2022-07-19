# OpenTelemetry Infinispan 

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## External services

We need a Jaeger server instance:

``` shell script
jaeger> ./jaeger-all-in-one --collector.otlp.enabled
```

And an Infinispan server listening on 11222 (default Hot Rod port)
Furthermore we need to create the admin user and to configure the tracing on Infinispan server instance:

``` shell script
infinispan-server/bin>./cli.sh user create -g admin -p pass user
export JAVA_OPTS="-Dinfinispan.tracing.enabled=true -Dotel.service.name=infinispan-server-service -Dotel.exporter.otlp.endpoint=http://localhost:4317 -Dotel.metrics.exporter=none"
infinispan-server/bin>./server.sh
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Call the rest resource
```shell script
curl http://localhost:8080/ciao
```

The output should be similar to
> {traceparent=00-f9ee6cdba83bad86352b73d0fe187707-17b45466258cb6a1-01}

## Look the result

Go to Jaeger console: http://localhost:16686/

## Related Guides

- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive)): A JAX-RS implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- OpenTelemetry exporter: OTLP ([guide](https://quarkus.io/guides/opentelemetry)): Enable OTLP Exporter for OpenTelemetry
