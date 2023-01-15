package fax.play.search;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Date;

import javax.inject.Inject;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.query.dsl.QueryResult;
import org.junit.jupiter.api.Test;

import fax.play.config.Config;
import fax.play.model.Author;
import fax.play.model.Book;
import fax.play.model.Review;
import io.quarkus.infinispan.client.Remote;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ProtoAutoQueryTest {

   @Inject
   @Remote(Config.CACHE_NAME)
   RemoteCache<String, Object> cache;

   @Test
   public void test() {
      assertThat(cache).isNotNull(); // instance is injected

      Author author1 = new Author("Joshua", "Bloch", 25);
      Review review11 = new Review(new Date(), "This is a very great book", 9.3f);
      Review review12 = new Review(new Date(), "Very useful and nice book", 8.5f);
      Review review13 = new Review(new Date(), "Great to have in your library", 9.0f);
      Book book1 = new Book("Effective Java - Third Edition", 2017,
            "Java has changed dramatically since the previous edition of Effective Java was published shortly after the release of Java",
            40.07f, author1, Arrays.asList(review11, review12, review13));

      cache.put("effective-java", book1);

      Book book = (Book) cache.get("effective-java");
      assertThat(book).isNotNull();
      assertThat(book.getAuthor().getSurname()).isEqualTo("Bloch");
      assertThat(book.getReviews()).extracting("score").containsExactly(9.3f, 8.5f, 9.0f);
      assertThat(book.getTitle()).contains("Java");

      QueryFactory queryFactory = Search.getQueryFactory(cache);

      Query<Book> query = queryFactory.create("from fax.play.book b");
      QueryResult<Book> result = query.execute();
      assertThat(result.hitCount()).hasValue(1L);
      book = result.list().get(0);
      assertThat(book).isNotNull();
   }
}
