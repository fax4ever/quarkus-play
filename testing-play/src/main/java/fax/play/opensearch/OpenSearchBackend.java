package fax.play.opensearch;

import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.elasticsearch.client.Request;
import org.elasticsearch.client.RestClient;
import org.infinispan.commons.dataconversion.internal.Json;

import fax.play.SearchBackend;

@ApplicationScoped
public class OpenSearchBackend implements SearchBackend {

   @Inject
   RestClient restClient;

   @Override
   public CompletionStage<String> mapping(String indexName) {
      Request request = new Request("PUT", "/" + indexName);

      Json mappings = Json.object("mappings",
            Json.object("_source",
                  Json.object("enabled", false)));
      request.setJsonEntity(mappings.toString());

      return commandSubmit(request);
   }

   @Override
   public CompletionStage<String> put(String indexName, String key, Json value) {
      Request request = new Request("PUT", "/" + indexName + "/_doc/" + key);
      request.setJsonEntity(value.toString());

      return commandSubmit(request);
   }

   private CompletionStage<String> commandSubmit(Request request) {
      CommandResponseListener responseListener = new CommandResponseListener();
      restClient.performRequestAsync(request, responseListener);
      return responseListener.completionStage().thenApply(CommandResponse::toString);
   }
}
