package de.wolftree.config;

import com.fasterxml.jackson.databind.node.ObjectNode;
import de.wolftree.utils.Utils;
import de.wolftree.wolfchat.ChatService;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config extends BaseConfig {
   private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

   private final String env;

   public Config(@NotNull String env){
      this.env = env;
   }

   public Config load() throws IOException {
      String jsonStr = Utils.readResourceFile("config/" + env + "/config/default/config.json");
      this.data = (ObjectNode) objectMapper.readTree(jsonStr);
      return this;
   }

   public String hostname(){
      return getString("hostname","");
   }





}















