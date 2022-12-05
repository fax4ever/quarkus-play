package fax.play;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(SearchTestResource.class)
public class OpenSearchBackendIT {

   private static final Logger LOG = Logger.getLogger(OpenSearchBackendIT.class);

   @Inject
   OpenSearchBackend openSearchBackend;

   @Test
   public void testGreetingService() {
      assertThat(openSearchBackend).isNotNull();
   }
}
