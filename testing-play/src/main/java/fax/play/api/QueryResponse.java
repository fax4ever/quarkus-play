package fax.play.api;

import java.util.List;

import org.infinispan.commons.dataconversion.internal.Json;

public interface QueryResponse {

   long hitCount();

   boolean hitCountExact();

   List<Json> hits();

   boolean hitsExact();

   long searchDuration();

}
