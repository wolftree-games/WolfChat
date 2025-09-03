package de.wolftree.wolfchat.command;

import de.wolftree.wolfchat.ChatService;
import de.wolftree.wolfchat.Member;
import de.wolftree.wolfchat.Room;
import de.wolftree.wolfchat.interfaces.ICommand;


public class LeaveCommand implements ICommand {
   private final ChatService chatService;

   public LeaveCommand(ChatService chatService) {
      this.chatService = chatService;
   }

   @Override
   public String execute(Member member, String[] args) {
      if (args.length != 1)
         return "Usage: /leave <roomTitle>";

      String roomTitle = args[0];

      Room room = chatService.rooms().getRoomByTitle(roomTitle);
      if (room != null){
         if (chatService.rooms().removeRoomMember(room, member)){
            return "You left the room.";
         }
         return "You are not a member of the room";
      }
      else {
         return "Room doesn't exist";
      }
   }

}
