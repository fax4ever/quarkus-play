package fax.play.search;

import java.util.List;

public interface SearchResponse {

   long hitCount();

   boolean hitCountExact();

   List<SearchHit> hits();

   boolean hitsExact();

   int duration();

}
