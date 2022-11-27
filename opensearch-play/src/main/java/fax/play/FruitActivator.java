package fax.play;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import io.quarkus.runtime.Startup;

@ApplicationScoped
@Startup
public class FruitActivator {

   private static final Logger LOG = Logger.getLogger(FruitActivator.class);

   @Inject
   FruitService fruitService;

   @PostConstruct
   void startup() throws Exception {
      String response = fruitService.init();
      LOG.info("config index --> " + response);

      Event strawberry = Event.fruit("strawberry", "red");
      response = fruitService.index(strawberry);
      LOG.info("index 1 --> " + response);

      Event blueberry = Event.fruit("blueberry", "blue");
      response = fruitService.index(blueberry);
      LOG.info("index 2 --> " + response);

      Thread.sleep(1000);

      response = fruitService.searchByColor("red");
      LOG.info("reds --> " + response);
   }
}
