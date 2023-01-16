package fax.play.search;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.query.dsl.QueryResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import fax.play.config.Config;
import fax.play.model.Book;
import fax.play.search.util.ModelGenerator;
import io.quarkus.infinispan.client.Remote;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IndexedQueriesTest {

   @Inject
   @Remote(Config.CACHE_NAME)
   RemoteCache<String, Object> cache;

   @BeforeAll
   public void beforeAll() {
      assertThat(cache).isNotNull(); // instance is injected
      cache.clear();
      cache.putAll(ModelGenerator.generateBooks());
   }

   @Test
   public void test() {
      QueryFactory queryFactory = Search.getQueryFactory(cache);

      Query<Book> query = queryFactory.create("from fax.play.book b");
      QueryResult<Book> result = query.execute();

      assertThat(result).isNotNull();
   }
}
