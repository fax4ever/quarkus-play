package fax.play.rest;

import java.util.List;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.Query;

import fax.play.model.Book;
import fax.play.model.BookGenerator;
import io.quarkus.infinispan.client.Remote;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

@Path("books")
public class BookResource {

   @Inject
   @Remote("books")
   RemoteCache<String, Book> booksCache;

   @PUT
   public void addBooks() {
      booksCache.clear();
      booksCache.putAll(BookGenerator.generateBooks());
   }

   @GET
   @Path("/description/{term}")
   @Produces("application/json")
   public List<Book> query(@PathParam("term") String term) {
      Query<Book> query = Search.getQueryFactory(booksCache)
            .create("from fax.play.book b where b.description : :description");
      query.setParameter("description", term);

      List<Book> books = query.execute().list();
      Log.info(books);
      return books;
   }
}
