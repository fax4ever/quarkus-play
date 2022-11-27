package fax.play;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseException;
import org.elasticsearch.client.RestClient;

import com.google.gson.JsonObject;

@ApplicationScoped
public class FruitService {

   @Inject
   RestClient restClient;

   public String init() throws IOException {
      Request request = new Request(
            "PUT",
            "/" +Event.FRUIT_TYPE);

      JsonObject enabled = new JsonObject();
      enabled.addProperty("enabled", false);
      JsonObject source = new JsonObject();
      source.add("_source", enabled);
      JsonObject mappings = new JsonObject();
      mappings.add("mappings", source);

      request.setJsonEntity(mappings.toString());

      try {
         Response response = restClient.performRequest(request);
         return EntityUtils.toString(response.getEntity());
      } catch (ResponseException ex) {
         // probably => resource_already_exists_exception
         return ex.getMessage();
      }
   }

   public String index(Event event) throws IOException {
      Request request = new Request(
            "PUT",
            "/" + event.type + "/_doc/" + event.id);
      request.setJsonEntity(event.value.toString());

      Response response = restClient.performRequest(request);
      return EntityUtils.toString(response.getEntity());
   }

   public String searchByColor(String color) throws IOException {
      return search(Event.FRUIT_TYPE, "color", color);
   }

   private String search(String type, String term, String match) throws IOException {
      Request request = new Request(
            "GET",
            "/" + type + "/_search");

      // construct a JSON query like {"query": {"match": {"<term>": "<match>"}}
      JsonObject termJson = new JsonObject();
      termJson.addProperty(term, match);
      JsonObject matchJson = new JsonObject();
      matchJson.add("match", termJson);
      JsonObject queryJson = new JsonObject();
      queryJson.add("query", matchJson);

      request.setJsonEntity(queryJson.toString());

      Response response = restClient.performRequest(request);
      return EntityUtils.toString(response.getEntity());
   }

}
