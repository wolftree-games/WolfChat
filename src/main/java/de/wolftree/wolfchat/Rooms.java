package de.wolftree.wolfchat;

import de.wolftree.wolfchat.enums.OpMode;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Rooms {
   //private final ConcurrentHashMap<String, Room> list = new ConcurrentHashMap<>();
   private final ConcurrentHashMap<String, Room> roomsByTitle = new ConcurrentHashMap<>();
   private final ChatService chatService;

   Rooms(ChatService chatService){
      this.chatService = chatService;
   }

   public Room createRoom(String title) throws IllegalArgumentException {
      return createRoom(title,null);
   }
   public Room createRoom(String title, @Nullable String password) throws IllegalArgumentException {
      return roomsByTitle.compute(title, (t, existingRoom) -> {
         if (existingRoom != null) {
            throw new IllegalArgumentException("Room title already exists: " + title);
         }

         Room room = new Room(title, password);
         //list.putIfAbsent(room.id(), room);
         return room;
      });
   }

   public String renameRoom(String title, String newTitle){
      Room room = getRoomByTitle(title);
      if (room == null){
         throw new IllegalArgumentException("Room not found");
      }
      return renameRoom(room, newTitle);
   }
   public String renameRoom(Room room, String newTitle){
      if (!Room.isValidTitle(newTitle)){
         throw new IllegalArgumentException("Room title not valid");
      }

      String oldTitle = room.title();
      Room existing = roomsByTitle.putIfAbsent(newTitle, room);
      if (existing != null) {
         throw new IllegalArgumentException("Room title already in use: " + newTitle);
      }
      room.title(newTitle);
      roomsByTitle.remove(oldTitle);

      return oldTitle;
   }

   public boolean removeRoom(@NotNull String title){
      Room room = getRoomByTitle(title);
      if (room == null){
         return false;
      }
      return removeRoom(room);
   }
   public boolean removeRoom(@NotNull Room room){
      return roomsByTitle.remove(room.title()) != null;
   }

   /*
   public Room getRoomById(String id){
      return list.get(id);
   }
   */

   public Room getRoomByTitle(String title){
      if (!Room.isValidTitle(title)){
         throw new IllegalArgumentException("Room title not valid");
      }
      return roomsByTitle.get(title);
   }

   public boolean addRoomMember(@NotNull Room room, Member member, OpMode operatorMode){
      return room.addMember(member, operatorMode);
   }

   public boolean removeRoomMember(@NotNull Room room, Member member){
      boolean result = room.removeMember(member);

      //if the room is empty it will be removed
      if (!room.hasMembers()){
         removeRoom(room);
      }
      return result;
   }

   public HashMap<Member, OpMode> getRoomMembers(@NotNull String roomTitle){
      Room room = getRoomByTitle(roomTitle);
      if (room != null) {
         return getRoomMembers(room);
      }
      else {
         return null;
      }
   }
   public HashMap<Member, OpMode> getRoomMembers(@NotNull Room room){
      Members members = chatService.members();

      HashMap<Member, OpMode> result = new HashMap<>();

      room.members.forEach((m, opMode) -> {
         if (m != null) {
            result.put(m, opMode);
         }
      });
      return result;

      /*
      return room.members.keySet().stream()
          .map(members::getMemberById)
          .filter(Objects::nonNull)
          .toList();
       */
   }

   public long count(){
      return roomsByTitle.size();
   }


}
