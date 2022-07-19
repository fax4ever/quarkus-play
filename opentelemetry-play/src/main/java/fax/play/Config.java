package fax.play;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.commons.configuration.XMLStringConfiguration;
import org.jboss.logging.Logger;

import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class Config {

   public static final String CACHE_NAME = "blablabla";

   private static final String CACHE_DEFINITION = "<local-cache name=\"blablabla\"></local-cache>";

   private static final Logger LOG = Logger.getLogger(Config.class);

   @Inject
   private RemoteCacheManager remoteCacheManager;

   void onStart(@Observes StartupEvent ev) {
      LOG.info("Get or create cache: " + CACHE_NAME);

      remoteCacheManager.administration()
            .getOrCreateCache(CACHE_NAME, new XMLStringConfiguration(CACHE_DEFINITION));
   }
}
