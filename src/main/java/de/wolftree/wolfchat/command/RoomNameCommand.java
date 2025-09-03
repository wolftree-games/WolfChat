package de.wolftree.wolfchat.command;

import de.wolftree.wolfchat.ChatService;
import de.wolftree.wolfchat.Member;
import de.wolftree.wolfchat.interfaces.ICommand;


public class RoomNameCommand implements ICommand {
   private final ChatService chatService;

   public RoomNameCommand(ChatService chatService) {
      this.chatService = chatService;
   }

   @Override
   public String execute(Member member, String[] args) {
      if (args.length != 2)
         return "Usage: /roomname <oldRoomName> <newRoomName>";

      String oldRoomName = args[0];
      String newRoomName = args[1];

      //check rights

      String result = chatService.rooms().renameRoom(oldRoomName, newRoomName);
      if (result != null){
         return "Name of the room "+result+" changed to: "+ newRoomName;
      }
      return "Name of the room was not changed";

   }

}
