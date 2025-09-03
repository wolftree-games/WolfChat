package de.wolftree.wolfchat.command;

import de.wolftree.wolfchat.ChatService;
import de.wolftree.wolfchat.Member;
import de.wolftree.wolfchat.Room;
import de.wolftree.wolfchat.enums.OpMode;
import de.wolftree.wolfchat.interfaces.ICommand;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListCommand implements ICommand {
   private final ChatService chatService;

   public ListCommand(ChatService chatService) {
      this.chatService = chatService;
   }

   @Override
   public String execute(Member member, String[] args) {
      if (args.length < 1)
         return "Usage: /list <roomTitle> [password]";

      String roomTitle = args[0];
      String password = args.length > 1 ? args[1] : null;

      Room room = chatService.rooms().getRoomByTitle(roomTitle);
      if (room != null){
         if (!room.checkPassword(password)){
            return "Password incorrect!";
         }

         HashMap<Member, OpMode> roomMembers = chatService.rooms().getRoomMembers(room);
         List<String> list = new ArrayList<>();
         for (Map.Entry<Member, OpMode> entry : roomMembers.entrySet()) {
            list.add(entry.getKey().nickName() + " : " + entry.getValue());
         }
         return list.toString();

      }
      else {
         return "Room not Found!";
      }
   }

}
