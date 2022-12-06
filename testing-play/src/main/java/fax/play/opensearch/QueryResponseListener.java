package fax.play.opensearch;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.infinispan.commons.dataconversion.internal.Json;

class QueryResponseListener implements ResponseListener {

   final CompletableFuture<List<Json>> future = new CompletableFuture<>();

   @Override
   public void onSuccess(Response response) {
      try {
         String jsonList = EntityUtils.toString(response.getEntity());
         Json read = Json.read(jsonList);
         future.complete(read.asJsonList());
      } catch (Throwable throwable) {
         future.completeExceptionally(throwable);
      }
   }

   @Override
   public void onFailure(Exception exception) {
      future.completeExceptionally(exception);
   }

   public CompletionStage<List<Json>> completionStage() {
      return future;
   }
}
