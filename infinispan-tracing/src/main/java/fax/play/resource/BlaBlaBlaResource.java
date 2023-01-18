package fax.play.resource;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.inject.Inject;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.infinispan.client.hotrod.RemoteCache;

import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.infinispan.client.Remote;

@Path("/blablabla")
public class BlaBlaBlaResource {

   @Inject
   @Remote("blablabla")
   RemoteCache<Integer, String> cache;

   @PUT
   @Produces(MediaType.TEXT_PLAIN)
   @WithSpan(value = "name", kind = SpanKind.CLIENT)
   public Map<Integer, String> putAll() {
      Map<Integer, String> entries = IntStream.range(0, 10).boxed()
            .collect(Collectors.toMap(value -> value, value -> Character.toString('A' + value)));
//      cache.putAll(entries);
      for (Map.Entry<Integer, String> entry : entries.entrySet()) {
         cache.put(entry.getKey(), entry.getValue());
      }
      return entries;
   }
}
