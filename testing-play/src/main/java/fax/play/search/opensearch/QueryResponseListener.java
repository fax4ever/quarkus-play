package fax.play.search.opensearch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.infinispan.commons.dataconversion.internal.Json;

class QueryResponseListener implements ResponseListener {

   final CompletableFuture<Json> future = new CompletableFuture<>();

   @Override
   public void onSuccess(Response response) {
      try {
         String jsonList = EntityUtils.toString(response.getEntity());
         future.complete(Json.read(jsonList));
      } catch (Throwable throwable) {
         future.completeExceptionally(throwable);
      }
   }

   @Override
   public void onFailure(Exception exception) {
      future.completeExceptionally(exception);
   }

   public CompletionStage<Json> completionStage() {
      return future;
   }
}
