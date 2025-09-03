/*
 * WolfChat
 * Copyright (c) 2025 Andi S.
 * Licensed under the MIT License
*/

package de.wolftree.wolfchat;

import de.wolftree.wolfchat.command.Commands;
import de.wolftree.wolfchat.interfaces.IChatTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatService {
   private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

   private final String id;
   private final Rooms rooms = new Rooms(this);
   private final Members members = new Members(this);
   private final Commands commands = new Commands();
   private final IChatTransport transport;

   public ChatService(String id, IChatTransport transport)  {
      this.id = id;
      this.transport = transport;
   }

   public void handleMessage(String userId, String msg) {
      logger.debug("msg from id {} msg {}", userId, msg);
      Member member = members.getMemberById(userId);
      String response = commands.execute(member, msg);
      if (response != null) {
         this.transport.sendToMember(userId,Members.SERVER_ID, response);
      }
   }

   public String id() {
      return this.id;
   }

   public Commands commands(){
      return commands;
   }

   public Rooms rooms() {
      return this.rooms;
   }

   public Members members() {
      return this.members;
   }

}
