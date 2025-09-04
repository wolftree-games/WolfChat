package de.wolftree.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jetbrains.annotations.NotNull;


public class BaseConfig {
   protected static final ObjectMapper objectMapper = new ObjectMapper();
   protected ObjectNode data = objectMapper.createObjectNode();

   String getString(@NotNull String key, String alt){
      return this.data.has(key) ? this.data.get(key).asText() : alt;
   }

   long getLong(@NotNull String key, long alt){
      return this.data.has(key) ? this.data.get(key).asLong() : alt;
   }

   int getInt(@NotNull String key, int alt){
      return this.data.has(key) ? this.data.get(key).asInt() : alt;
   }
}
