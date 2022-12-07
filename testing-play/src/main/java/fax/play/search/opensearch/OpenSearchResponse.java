package fax.play.search.opensearch;

import java.util.List;
import java.util.stream.Collectors;

import org.infinispan.commons.dataconversion.internal.Json;

import fax.play.search.SearchHit;
import fax.play.search.SearchResponse;

public class OpenSearchResponse implements SearchResponse {

   private final long hitCount;
   private final boolean hitCountExact;
   private final List<SearchHit> hits;
   private final boolean hitsExacts;
   private final int duration;

   public OpenSearchResponse(Json queryResponse) {
      duration = queryResponse.at("took").asInteger();
      hitsExacts = queryResponse.at("timed_out").asBoolean();
      Json hits = queryResponse.at("hits");
      Json total = hits.at("total");
      hitCount = total.at("value").asLong();
      String countRelation = total.at("relation").asString();
      hitCountExact = "EQ".equalsIgnoreCase(countRelation);
      this.hits = hits.at("hits").asJsonList().stream().map((hit) -> {
         String indexName = hit.at("_index").asString();
         String documentId = hit.at("_id").asString();
         double score = hit.at("_score").asDouble();
         return new SearchHit(indexName, documentId, score);
      }).collect(Collectors.toList());
   }

   @Override
   public long hitCount() {
      return hitCount;
   }

   @Override
   public boolean hitCountExact() {
      return hitCountExact;
   }

   @Override
   public List<SearchHit> hits() {
      return hits;
   }

   @Override
   public boolean hitsExact() {
      return hitsExacts;
   }

   @Override
   public int duration() {
      return duration;
   }
}
