package fax.play.search;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Condition;
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
   public void test() throws Exception {
      assertThat(searchBackend).isNotNull();

      // mapping
      String response = searchBackend.mapping("developers").toCompletableFuture().join();
      LOG.info(response);
      assertThat(response).contains("\"index\":\"developers\"");

      // put
      Json json = Json.object("surname", "Ercoli", "name", "Fabio Massimo", "nick", "fax4ever");
      response = searchBackend.put("developers", "fax4ever", json).toCompletableFuture().join();
      LOG.info(response);

      Condition<String> created = new Condition<>(r -> r.contains("\"result\":\"created\""), "created");
      Condition<String> updated = new Condition<>(r -> r.contains("\"result\":\"updated\""), "updated");
      assertThat(response).is(anyOf(created, updated));

      // TODO find a better way to wait for the put
      Thread.sleep(1000);

      // search
      Json queryResponse = searchBackend.query("select * from developers").toCompletableFuture().join();
      LOG.info(queryResponse);
      assertThat(queryResponse).isNotNull();

      List<Json> hits = queryResponse.at("hits").at("hits").asJsonList();
      LOG.info(hits);
      assertThat(hits).hasSize(1);

      // remove
      response = searchBackend.remove("developers", "fax4ever").toCompletableFuture().join();
      LOG.info(response);

      assertThat(response).contains("\"result\":\"deleted\"");

      // TODO find a better way to wait for the remove
      Thread.sleep(1000);

      // search again
      queryResponse = searchBackend.query("select * from developers").toCompletableFuture().join();
      LOG.info(queryResponse);
      assertThat(queryResponse).isNotNull();

      hits = queryResponse.at("hits").at("hits").asJsonList();
      LOG.info(hits);
      assertThat(hits).isEmpty();
   }

}
