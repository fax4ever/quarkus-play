package fax.play.search;

import java.util.concurrent.CompletionStage;

import org.infinispan.commons.dataconversion.internal.Json;

public interface SearchBackend {

   CompletionStage<String> mapping(String indexName);

   CompletionStage<String> put(String indexName, String documentId, Json value);

   CompletionStage<String> remove(String indexName, String documentId);

   CompletionStage<Json> query(String sql);

}
