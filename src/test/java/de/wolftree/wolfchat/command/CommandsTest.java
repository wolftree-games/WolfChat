package de.wolftree.wolfchat.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.wolftree.wolfchat.ChatService;
import de.wolftree.wolfchat.Member;
import de.wolftree.wolfchat.interfaces.IChatTransport;
import de.wolftree.wolfchat.interfaces.ICommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandsTest implements IChatTransport {

   @BeforeEach
   void setUp() {
   }

   @Test
   void registerCommand() {
      ChatService cs = new ChatService("test",this);

      Commands commands = cs.commands();
      assertThrows(IllegalArgumentException.class, () -> {
         commands.register(null, null);
      });
      assertThrows(IllegalArgumentException.class, () -> {
         commands.register(null, new ICommand() {
            @Override
            public String execute(Member member, String[] args) {
               return "ok";
            }
            });
      });
      assertThrows(IllegalArgumentException.class, () -> {
         commands.register("test", null);
      });
      assertThrows(IllegalArgumentException.class, () -> {
         commands.register("test", null);
      });
      assertSame(commands, commands.register("test",new ICommand() {
         @Override
         public String execute(Member member, String[] args) {
            return "test ok";
         }
      }));

      assertEquals("test ok",commands.execute(new Member(), "/test"));
   }

   @Test
   void unregisterCommand() {
      ChatService cs = new ChatService("test",this);

      Commands commands = cs.commands();

      assertFalse( commands.unregister(null) );

      assertFalse( commands.unregister("") );

      assertFalse( commands.unregister("not existing") );

      assertSame(commands, commands.register("test",new ICommand() {
         @Override
         public String execute(Member member, String[] args) {
            return "test ok";
         }
      }));
      assertEquals("test ok",commands.execute(new Member(), "/test"));

      assertTrue( commands.unregister("test") );
   }

   @Test
   void hasCommand() {
      ChatService cs = new ChatService("test",this);

      Commands commands = cs.commands();

      assertFalse( commands.hasCommand(null) );

      assertFalse( commands.hasCommand("") );

      assertFalse( commands.hasCommand("not existing") );

      assertSame(commands, commands.register("test",new ICommand() {
         @Override
         public String execute(Member member, String[] args) {
            return "test ok";
         }
      }));
      assertEquals("test ok",commands.execute(new Member(), "/test"));

      assertTrue( commands.hasCommand("test") );
   }

   @Test
   void execute() {
      ChatService cs = new ChatService("test",this);
      Commands commands = cs.commands();
      assertSame(commands, commands.register("test",new ICommand() {
         @Override
         public String execute(Member member, String[] args) {
            return "test ok";
         }
      }));
      assertEquals("test ok",commands.execute(new Member(), "/test"));
   }

   @Override
   public void sendToMember(String toMemberId, String fromMemberId, String message) {

   }
}