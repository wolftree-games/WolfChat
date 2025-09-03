package de.wolftree.wolfchat.command;

import de.wolftree.wolfchat.ChatService;
import de.wolftree.wolfchat.Member;
import de.wolftree.wolfchat.interfaces.ICommand;


public class OpModeCommand implements ICommand {
   private final ChatService chatService;

   public OpModeCommand(ChatService chatService) {
      this.chatService = chatService;
   }

   @Override
   public String execute(Member member, String[] args) {
      if (args.length != 2)
         return "Usage: /opmode <roomname> <nickname>";

      String roomName = args[0];
      String nickname = args[1];

      //check rights
      //TODO

      //check if user member of the room and owner or operator


      return "Name of the room was not changed";

   }

}
