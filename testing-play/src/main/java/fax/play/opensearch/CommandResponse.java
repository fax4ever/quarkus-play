package fax.play.opensearch;

final class CommandResponse {

   final boolean success;
   String entity;
   Exception exception;

   public CommandResponse(boolean success) {
      this.success = success;
   }

   public void entity(String entity) {
      this.entity = entity;
   }

   public void exception(Exception exception) {
      this.exception = exception;
   }

   @Override
   public String toString() {
      if (entity != null) {
         return entity;
      }

      return exception.getMessage();
   }
}
