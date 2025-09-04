package de.wolftree.wolfchat.command;

import de.wolftree.wolfchat.ChatService;
import de.wolftree.wolfchat.Member;
import de.wolftree.wolfchat.Members;
import de.wolftree.wolfchat.Room;
import de.wolftree.wolfchat.enums.OpMode;
import de.wolftree.wolfchat.interfaces.IChatTransport;
import de.wolftree.wolfchat.interfaces.ICommand;


public class KickCommand implements ICommand {
   private final ChatService chatService;
   private final IChatTransport transport;

   public KickCommand(ChatService chatService, IChatTransport transport) {
      this.chatService = chatService;
      this.transport = transport;
   }

   @Override
   public String execute(Member member, String[] args) {
      if (args.length != 2)
         return "Usage: /kick <roomName> <memberName>";

      String roomName = args[0];
      String memberName = args[1];

      //fetch member from target room
      Room room = chatService.rooms().getRoomByTitle(roomName);
      Member kickMember = chatService.members().getMemberByTitle(memberName);

      if (room != null && room.hasMember(member) && room.hasMember(kickMember) ){
         if (room.hasMemberOpMode(member, OpMode.OWNER) ||
             room.hasMemberOpMode(member, OpMode.OPERATOR)){

            if (chatService.rooms().removeRoomMember(room, kickMember)){
               transport.sendToMember(kickMember.id(), Members.SERVER_ID, "you were removed from room "+roomName);
               return "Member "+kickMember.nickName()+" was removed from room "+room.title();
            }
            else
               return "Member was not removed";
         }
         else
            return "Forbidden";
      }
      return "Not possible";
   }
}
