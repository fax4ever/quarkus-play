package fax.play.api;

import java.util.List;

import org.infinispan.commons.dataconversion.internal.Json;

public interface QueryResult {

   long hitCount();

   boolean hitCountExact();

   List<Json> hits();

   boolean hitsExact();

   long searchDuration();

}
