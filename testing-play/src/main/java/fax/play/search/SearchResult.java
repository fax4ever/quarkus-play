package fax.play.search;

import java.util.List;

public interface SearchResult {

   long hitCount();

   boolean hitCountExact();

   List<SearchHit> hits();

   boolean hitsExact();

   int duration();

}
