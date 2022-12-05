package fax.play;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.infinispan.commons.dataconversion.internal.Json;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(SearchTestResource.class)
public class SearchBackendTest {

   private static final Logger LOG = Logger.getLogger(SearchBackendTest.class);

   @Inject
   SearchBackend searchBackend;

   @Test
   public void test() {
      assertThat(searchBackend).isNotNull();

      Json json = Json.object("surname", "Ercoli", "name", "Fabio Massimo", "nick", "fax4ever");
      String response = searchBackend.put("developers", "fax4ever", json).toCompletableFuture().join();
      LOG.info(response);

      assertThat(response).isNotBlank();
   }

}
