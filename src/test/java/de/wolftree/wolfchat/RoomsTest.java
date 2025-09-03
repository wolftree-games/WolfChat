package de.wolftree.wolfchat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.wolftree.wolfchat.interfaces.IChatTransport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomsTest implements IChatTransport {

   @BeforeEach
   void setUp() {


   }

   @Test
   void createRoom() {
      String title = "testroom";
      ChatService cs = new ChatService("test",this);

      assertNull(cs.rooms().getRoomByTitle(title));
      cs.rooms().createRoom(title);

      Room r = cs.rooms().getRoomByTitle(title);
      assertNotNull(r);
      assertEquals(r.title(), title);
   }

   @Test
   void createRoomPassword() {
      String title = "testroom";
      String pass = "testpass";
      ChatService cs = new ChatService("test",this);

      assertNull(cs.rooms().getRoomByTitle(title));
      cs.rooms().createRoom(title, pass);

      Room r = cs.rooms().getRoomByTitle(title);
      assertNotNull(r);
      assertEquals(r.title(), title);
      assertEquals(r.password(), pass);
      assertTrue(r.checkPassword(pass));
   }

   @Test
   void renameRoom() {
   }

   @Test
   void testRenameRoom() {
   }

   @Test
   void removeRoom() {
   }

   @Test
   void testRemoveRoom() {
   }

   @Test
   void getRoomByTitle() {
   }

   @Test
   void addRoomMember() {
   }

   @Test
   void removeRoomMember() {
   }

   @Test
   void getRoomMembers() {
   }

   @Test
   void testGetRoomMembers() {
   }

   @Override
   public void sendToMember(String toMemberId, String fromMemberId, String message) {

   }
}