package fax.play.model;

import java.util.Date;

import org.infinispan.api.annotations.indexing.Basic;
import org.infinispan.api.annotations.indexing.Indexed;
import org.infinispan.api.annotations.indexing.Text;
import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoName;

@Indexed
@ProtoName("review")
public class Review {

   private Date date;
   private String content;
   private Float score;

   @ProtoFactory
   public Review(Date date, String content, Float score) {
      this.date = date;
      this.content = content;
      this.score = score;
   }

   @Basic
   @ProtoField(value = 1)
   public Date getDate() {
      return date;
   }

   @Text
   @ProtoField(value = 2)
   public String getContent() {
      return content;
   }

   @Basic
   @ProtoField(value = 3)
   public Float getScore() {
      return score;
   }
}
