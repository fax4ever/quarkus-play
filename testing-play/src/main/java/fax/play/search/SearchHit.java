package fax.play.search;

public final class SearchHit {

   private final String indexName;
   private final String documentId;
   private final Double score;

   public SearchHit(String indexName, String documentId, Double score) {
      this.indexName = indexName;
      this.documentId = documentId;
      this.score = score;
   }

   public String indexName() {
      return indexName;
   }

   public String documentId() {
      return documentId;
   }

   public Double score() {
      return score;
   }

   @Override
   public String toString() {
      return "SearchHit{" +
            "indexName='" + indexName + '\'' +
            ", documentId='" + documentId + '\'' +
            ", score=" + score +
            '}';
   }
}
