package fax.play;

import java.util.HashMap;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.infinispan.client.hotrod.RemoteCache;
import org.jboss.logging.Logger;

import io.quarkus.infinispan.client.Remote;

@Path("/ciao")
public class CiaoResource {

   private static final Logger LOG = Logger.getLogger(CiaoResource.class);

   @Inject @Remote(Config.CACHE_NAME)
   RemoteCache<String, String> cache;

   @GET
   @Produces(MediaType.TEXT_PLAIN)
   public String ciao() {

      HashMap<String, String> spanContext = TelemetryHelper.getSpanContext();
      LOG.info(spanContext);

      cache.put("aaa", "bbb");

      return spanContext.toString();
   }

}
