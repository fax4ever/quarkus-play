package fax.play;

import java.util.concurrent.CompletionStage;

import org.infinispan.commons.dataconversion.internal.Json;

public interface SearchBackend {

   CompletionStage<String> mapping(String indexName);

   CompletionStage<String> put(String indexName, String key, Json value);

}
