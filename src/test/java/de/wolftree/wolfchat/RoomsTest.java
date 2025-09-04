package de.wolftree.wolfchat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.wolftree.wolfchat.enums.OpMode;
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
   void removeRoom() {
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
   void setOpModeMember() {
      String title = "testroom";
      String pass = "testpass";
      ChatService cs = new ChatService("test",this);

      assertNull(cs.rooms().getRoomByTitle(title));
      cs.rooms().createRoom(title, pass);
      Room r = cs.rooms().getRoomByTitle(title);
      assertNotNull(r);

      Member m = new Member().id("111").nickname("testuser");
      cs.members().addMember(m);
      cs.rooms().addRoomMember(r, m, OpMode.OWNER );
      assertTrue(r.hasMemberOpMode(m,OpMode.OWNER));

      r.changeMemberOpMode(m,OpMode.OPERATOR);
      assertTrue(r.hasMemberOpMode(m,OpMode.OPERATOR));
   }


   @Override
   public void sendToMember(String toMemberId, String fromMemberId, String message) {

   }
}