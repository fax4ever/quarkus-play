package fax.play.model;

import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;

@AutoProtoSchemaBuilder(includeClasses = {Book.class, Author.class, Review.class},
      schemaPackageName = "fax.play",
      schemaFilePath = "proto",
      schemaFileName = "book-schema.proto")
interface BookSchema extends GeneratedSchema {

}
