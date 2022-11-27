package fax.play;

import java.util.UUID;

import com.google.gson.JsonObject;

public class Event {

   public static final String FRUIT_TYPE = "fruit";

   public static final Event fruit(String name, String color) {
      Event event = new Event();

      String id = UUID.randomUUID().toString();
      event.id = id;
      event.type = FRUIT_TYPE;

      event.value = new JsonObject();
      event.value.addProperty("id", id);
      event.value.addProperty("name", name);
      event.value.addProperty("color", color);

      return event;
   }

   String id;
   String type;
   JsonObject value;

}
