package fax.play.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.commons.configuration.StringConfiguration;
import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;
import org.jboss.logging.Logger;

import fax.play.model.Author;
import fax.play.model.Book;
import fax.play.model.Review;
import fax.play.model.Shape;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class Config {

   public static final String CACHE_NAME = "shapes-and-books";

   private static final String INDEXED_CACHE_DEFINITION =
         "<local-cache name=\"shapes-and-books\" statistics=\"true\">" +
               "    <encoding media-type=\"application/x-protostream\"/>" +
               "    <indexing enabled=\"true\" storage=\"local-heap\">" +
               "        <indexed-entities>" +
               "            <indexed-entity>fax.play.book</indexed-entity>" +
               "            <indexed-entity>fax.play.shape</indexed-entity>" +
               "        </indexed-entities>" +
               "    </indexing>" +
               "</local-cache>";

   private static final Logger LOG = Logger.getLogger(Config.class);

   @Inject
   RemoteCacheManager remoteCacheManager;

   void onStart(@Observes StartupEvent event) {
      LOG.info("Get or create cache: " + CACHE_NAME);

      // Register proto schema on server side
      RemoteCache<String, String> metadataCache = remoteCacheManager.getCache(ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
      metadataCache.put(BooksSchema.INSTANCE.getProtoFileName(), BooksSchema.INSTANCE.getProtoFile());
      metadataCache.put(ShapesSchema.INSTANCE.getProtoFileName(), ShapesSchema.INSTANCE.getProtoFile());

      RemoteCache<Object, Object> cache = remoteCacheManager.administration()
            .getOrCreateCache(CACHE_NAME, new StringConfiguration(INDEXED_CACHE_DEFINITION));
      // clear at startup
      cache.clear();
   }

   @AutoProtoSchemaBuilder(includeClasses = {Book.class, Author.class, Review.class},
         schemaFileName = "books-schema.proto",
         schemaPackageName = "fax.play")
   public interface BooksSchema extends GeneratedSchema {
      BooksSchema INSTANCE = new BooksSchemaImpl();
   }

   @AutoProtoSchemaBuilder(includeClasses = {Shape.class},
         schemaFileName = "shapes-schema.proto",
         schemaPackageName = "fax.play")
   public interface ShapesSchema extends GeneratedSchema {
      ShapesSchema INSTANCE = new ShapesSchemaImpl();
   }
}
