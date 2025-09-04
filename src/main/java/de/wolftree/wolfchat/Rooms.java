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

   /**
    * create a room with the given title
    * @param title
    * @return the created {@link Room}
    * @throws IllegalArgumentException
    */
   public Room createRoom(String title) throws IllegalArgumentException {
      return createRoom(title,null);
   }

   /**
    * creates a room with the given title and password
    * @param title
    * @param password
    * @return the created {@link Room}
    * @throws IllegalArgumentException
    */
   public Room createRoom(String title, @Nullable String password) throws IllegalArgumentException {
      return roomsByTitle.compute(title, (t, existingRoom) -> {
         if (existingRoom != null) {
            throw new IllegalArgumentException("Room title already exists: " + title);
         }

         Room room = new Room(title, password);
         return room;
      });
   }

   /**
    * renames an existing room
    * @param title
    * @param newTitle
    * @return old title
    * @throws IllegalArgumentException
    */
   public String renameRoom(String title, String newTitle){
      Room room = getRoomByTitle(title);
      if (room == null){
         throw new IllegalArgumentException("Room not found");
      }
      return renameRoom(room, newTitle);
   }

   /**
    * renames an existing room
    * @param room
    * @param newTitle
    * @return old title
    * @throws IllegalArgumentException
    */
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

   /**
    * remove room by title
    * @param title
    * @return true on success
    */
   public boolean removeRoom(@NotNull String title){
      Room room = getRoomByTitle(title);
      if (room == null){
         return false;
      }
      return removeRoom(room);
   }

   /**
    * removes a room by object
    * @param room
    * @return true on success
    */
   public boolean removeRoom(@NotNull Room room){
      return roomsByTitle.remove(room.title()) != null;
   }

   /*
   public Room getRoomById(String id){
      return list.get(id);
   }
   */

   /**
    * get room by title
    * @param title
    * @return {@link Room} or null
    * @throws IllegalArgumentException
    */
   public Room getRoomByTitle(String title){
      if (!Room.isValidTitle(title)){
         throw new IllegalArgumentException("Room title not valid");
      }
      return roomsByTitle.get(title);
   }

   /**
    * adds a member to an existing room
    *
    * @param room
    * @param member
    * @param operatorMode
    * @return true on success
    */
   public boolean addRoomMember(@NotNull Room room, Member member, OpMode operatorMode){
      return room.addMember(member, operatorMode);
   }

   /**
    * set an {@link OpMode} operationMode to the user
    *
    * @param room
    * @param member
    * @param operatorMode
    * @return true on success
    */
   public boolean setOpModeMember(@NotNull Room room, Member member, OpMode operatorMode){
      return room.changeMemberOpMode(member, operatorMode);
   }

   /**
    * removes a member from a room
    * @param room
    * @param member
    * @return
    */
   public boolean removeRoomMember(@NotNull Room room, Member member){
      boolean result = room.removeMember(member);

      //if the room is empty it will be removed
      if (!room.hasMembers()){
         removeRoom(room);
      }
      return result;
   }

   /**
    * return a collection of all members of the given room by title
    * @param roomTitle
    * @return {@link HashMap}
    */
   public HashMap<Member, OpMode> getRoomMembers(@NotNull String roomTitle){
      Room room = getRoomByTitle(roomTitle);
      if (room != null) {
         return getRoomMembers(room);
      }
      else {
         return null;
      }
   }

   /**
    * return a collections of all members of the given room
    * @param room
    * @return {@link HashMap}
    */
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

   /**
    * returns the count of all registered members
    * @return
    */
   public long count(){
      return roomsByTitle.size();
   }


}
