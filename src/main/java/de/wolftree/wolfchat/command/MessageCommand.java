package de.wolftree.wolfchat.command;

import de.wolftree.wolfchat.ChatService;
import de.wolftree.wolfchat.Member;
import de.wolftree.wolfchat.Room;
import de.wolftree.wolfchat.interfaces.IChatTransport;
import de.wolftree.wolfchat.interfaces.ICommand;
import java.util.Arrays;


public class MessageCommand implements ICommand {
   private final ChatService chatService;
   private final IChatTransport transport;

   public MessageCommand(ChatService chatService, IChatTransport transport) {
      this.chatService = chatService;
      this.transport = transport;
   }

   @Override
   public String execute(Member member, String[] args) {
      if (args.length < 2)
         return "Usage: /msg <room> <message>";

      String roomName = args[0];
      String msg = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

      Room room = chatService.rooms().getRoomByTitle(roomName);

      if (room != null){
         for (Member m : room.getMembers()) {
            transport.sendToMember(m.id(), member.id(), msg);
         }
         return "Message sent";
      }
      return "Room not found!";
   }
}
