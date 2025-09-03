package de.wolftree.wolfchat.command;

import de.wolftree.wolfchat.ChatService;
import de.wolftree.wolfchat.Member;
import de.wolftree.wolfchat.Room;
import de.wolftree.wolfchat.enums.OpMode;
import de.wolftree.wolfchat.interfaces.ICommand;
import de.wolftree.i18n.I18n;
import java.util.Locale;

/*
   if the room is existing, simple join
   if the room is not existing, create it and join as operator
 */

public class JoinCommand implements ICommand {
   private final ChatService chatService;
   private final I18n i18n = new I18n(Locale.ENGLISH);

   public JoinCommand(ChatService chatService) {
      this.chatService = chatService;
   }

   @Override
   public String execute(Member member, String[] args) {
      if (args.length < 1 || args.length > 2)
         return i18n.tr("cmd.join.usage");

      String roomTitle = args[0];
      String password = args.length > 1 ? args[1] : null;

      Room room = chatService.rooms().getRoomByTitle(roomTitle);
      if (room == null){
         if (password != null)
            room = chatService.rooms().createRoom(roomTitle, password);
         else
            room = chatService.rooms().createRoom(roomTitle);

         chatService.rooms().addRoomMember(room, member, OpMode.OWNER);
         return i18n.tr("cmd.join.roomCreated",roomTitle);
      }
      else {
         if (!room.checkPassword(password)){
            return i18n.tr("common.password_incorrect");
         }

         chatService.rooms().addRoomMember(room, member, OpMode.MEMBER);
         return i18n.tr("cmd.join.roomJoined", roomTitle);
      }
   }
}
