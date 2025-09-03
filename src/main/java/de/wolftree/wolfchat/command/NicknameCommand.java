package de.wolftree.wolfchat.command;

import de.wolftree.wolfchat.ChatService;
import de.wolftree.wolfchat.Member;
import de.wolftree.wolfchat.interfaces.ICommand;


public class NicknameCommand implements ICommand {
   private final ChatService chatService;

   public NicknameCommand(ChatService chatService) {
      this.chatService = chatService;
   }

   @Override
   public String execute(Member member, String[] args) {
      if (args.length != 1)
         return "Usage: /nickname <nickname>";

      String nickname = args[0];

      String oldNickname = chatService.members().changeNickname(member,nickname);
      if (oldNickname != null){
         return "Nickname changed: "+oldNickname+" to "+nickname;
      }
      return "Nickname was not changed";

   }

}
