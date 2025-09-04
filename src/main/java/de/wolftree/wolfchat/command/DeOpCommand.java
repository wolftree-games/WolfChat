package de.wolftree.wolfchat.command;

import de.wolftree.wolfchat.ChatService;
import de.wolftree.wolfchat.Member;
import de.wolftree.wolfchat.Room;
import de.wolftree.wolfchat.enums.OpMode;
import de.wolftree.wolfchat.interfaces.ICommand;


public class DeOpCommand implements ICommand {
   private final ChatService chatService;

   public DeOpCommand(ChatService chatService) {
      this.chatService = chatService;
   }

   @Override
   public String execute(Member member, String[] args) {
      if (args.length != 3)
         return "Usage: /deop <roomname> <nickname>";

      String roomName = args[0];
      String opNickname = args[1];

      //fetch member from target room
      Room room = chatService.rooms().getRoomByTitle(roomName);
      Member opMember = chatService.members().getMemberByTitle(opNickname);

      if (room != null && room.hasMember(member) && room.hasMember(opMember) ){
         if (room.hasMemberOpMode(member, OpMode.MEMBER)){
            chatService.rooms().setOpModeMember(room, opMember, OpMode.OPERATOR);
            return "Member "+opMember.nickName()+" is no longer operator in room "+room.title();
         }
         else
            return "Forbidden";
      }
      return "Not possible";
   }
}
