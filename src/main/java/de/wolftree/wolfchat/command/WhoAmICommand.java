package de.wolftree.wolfchat.command;

import de.wolftree.wolfchat.ChatService;
import de.wolftree.wolfchat.Member;
import de.wolftree.wolfchat.interfaces.ICommand;
import java.time.Instant;


public class WhoAmICommand implements ICommand {
   private final ChatService chatService;

   public WhoAmICommand(ChatService chatService) {
      this.chatService = chatService;
   }

   @Override
   public String execute(Member member, String[] args) {
      if (args.length > 0)
         return "Usage: /whoami";

      if (member != null){
         Instant created = Instant.ofEpochMilli(member.createdTs());
         return "You are "+ member.nickName()+" ("+member.id()+") connected "+created.toString();
      }
      return "Seems you do not exist!";

   }

}
