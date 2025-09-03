package de.wolftree.wolfchat.command;

import de.wolftree.wolfchat.Member;
import de.wolftree.wolfchat.interfaces.ICommand;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Commands {
   private static final Logger logger = LoggerFactory.getLogger(Commands.class);
   private final Map<String, ICommand> commands = new ConcurrentHashMap<>();

   public Commands register(String name, ICommand command) {
      if (name == null || name.isBlank()){
         throw new IllegalArgumentException("name must not be null");
      }
      if (command == null){
         throw new IllegalArgumentException("command must not be null");
      }
      if (commands.containsKey(name)){
         throw new IllegalArgumentException("command name is already in use");
      }
      commands.put(name, command);
      return this;
   }

   public boolean unregister(String name){
      if (name == null || name.isBlank()){
         return false;
      }
      return commands.remove(name) != null;
   }

   public boolean hasCommand(String name){
      if (name == null || name.isBlank()){
         return false;
      }
      return commands.containsKey(name);
   }

   public String execute(Member member, String input) {
      if (member == null){
         throw new IllegalArgumentException("Member must not be null");
      }
      if (input == null || input.isBlank()){
         return null;
      }
      if (!input.startsWith("/")) {
         return null;
      }
      String[] parts = input.substring(1).split("\\s+");
      if (parts.length == 0 || parts[0].isBlank()) {
         return "No command specified";
      }
      String cmd = parts[0];
      String[] args = Arrays.copyOfRange(parts, 1, parts.length);

      ICommand command = commands.get(cmd);
      if (command != null) {
         return command.execute(member, args);
      } else {
         return "Unknown command: " + cmd;
      }
   }
}

