package fax.play;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.jboss.resteasy.reactive.RestQuery;

@Path("/fruits")
public class FruitResource {

   @Inject
   FruitService fruitService;

   @POST
   public void index(Fruit fruit) throws IOException {
      if (fruit.id == null) {
         fruit.id = UUID.randomUUID().toString();
      }
      fruitService.index(fruit);
      fruitService.index(fruit);
   }

   @GET
   @Path("/{id}")
   public Fruit get(String id) throws IOException {
      return fruitService.get(id);
   }

   @GET
   @Path("/search")
   public List<Fruit> search(@RestQuery String name, @RestQuery String color) throws IOException {
      if (name != null) {
         return fruitService.searchByName(name);
      } else if (color != null) {
         return fruitService.searchByColor(color);
      } else {
         throw new BadRequestException("Should provide name or color query parameter");
      }
   }
}
