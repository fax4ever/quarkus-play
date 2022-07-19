package fax.play;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

@Path("/ciao")
public class CiaoResource {

   private static final Logger LOG = Logger.getLogger(CiaoResource.class);

   @GET
   @Produces(MediaType.TEXT_PLAIN)
   public String ciao() {

      HashMap<String, String> spanContext = TelemetryHelper.getSpanContext();
      LOG.info(spanContext);

      return spanContext.toString();
   }

}
