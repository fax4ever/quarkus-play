package fax.play.opensearch;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseException;
import org.elasticsearch.client.ResponseListener;

class CommandResponseListener implements ResponseListener {

   CompletableFuture<CommandResponse> future = new CompletableFuture<>();

   @Override
   public void onSuccess(Response response) {
      CommandResponse result = new CommandResponse(true);

      try {
         String entity = EntityUtils.toString(response.getEntity());
         result.entity(entity);
      } catch (IOException exception) {
         result.exception(exception);
      }

      future.complete(result);
   }

   @Override
   public void onFailure(Exception exception) {
      if (exception instanceof ResponseException) {
         /*
          * The client tries to guess what's an error and what's not, but it's too naive.
          * A 404 on DELETE should be accepted for instance to support idempotency.
          */
         CommandResponse result = new CommandResponse(true);
         result.exception(exception);
         future.complete(result);
      }
      future.completeExceptionally(exception);
   }

   public CompletionStage<CommandResponse> completionStage() {
      return future;
   }
}
