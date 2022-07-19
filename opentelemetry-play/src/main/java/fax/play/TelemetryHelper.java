package fax.play;

import java.util.HashMap;

import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.Context;

public class TelemetryHelper {

   public static HashMap<String, String> getSpanContext() {
      HashMap<String, String> map = new HashMap<>();

      // Inject the request with the *current* Context, which contains client current Span if exists.
      W3CTraceContextPropagator.getInstance().inject(Context.current(), map,
            (carrier, paramKey, paramValue) -> carrier.put(paramKey, paramValue)
      );

      return map;
   }
}
