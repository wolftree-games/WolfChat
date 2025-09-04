package de.wolftree.wolfchat;

import de.wolftree.utils.UniqueId;
import de.wolftree.utils.Validation;
import de.wolftree.wolfchat.enums.OpMode;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Room {
   protected final ConcurrentHashMap<Member, OpMode> members = new ConcurrentHashMap<>();

   private String id;
   private String title;
   private String password;
   private final long createdTs;

   Room(@NotNull String title) {
      this(UniqueId.create(), title, null);
   }
   Room(@NotNull String title, @Nullable String password) {
      this(UniqueId.create(), title, password);
   }
   Room(@NotNull String id, @NotNull String title, @Nullable String password) {
      if (!isValidTitle(title)) {
         throw new IllegalArgumentException("title invalid");
      }
      if (password != null && !isValidPassword(password)) {
         throw new IllegalArgumentException("password have to be valid if set");
      }

      this.id = id;
      this.title = title;
      this.password = password;
      this.createdTs = System.currentTimeMillis();
   }

   protected boolean addMember(Member member, OpMode operatorMode){
      if (this.members.putIfAbsent(member, operatorMode) == null){
         return true;
      }
      return false;
   }

   protected boolean changeMemberOpMode(Member member, OpMode operatorMode){
      return this.members.put(member,operatorMode) != null;
   }

   protected boolean removeMember(Member member){
      if (this.members.remove(member) != null){
         return true;
      }
      return false;
   }

   public static boolean isValidTitle(String text){
      return Validation.isValidText(text,4,24);
   }

   public static boolean isValidPassword(String text){
      return Validation.isValidText(text,4,12);
   }

   public Collection<Member> getMembers() {
      return members.keySet();
   }

   public int memberCount() {
      return members.size();
   }

   public boolean hasMembers() {
      return !members.isEmpty();
   }

   public boolean hasMember(@NotNull Member member) {
      if (member == null)
         return false;
      return members.containsKey(member);
   }

   public OpMode getMemberOpMode(@NotNull Member member){
      OpMode opMode = members.get(member);
      return opMode;
   }

   public boolean hasMemberOpMode(Member member, OpMode opMode){
      OpMode op = members.get(member);
      if (op == null)
         return false;
      return op.equals(opMode);
   }

   public boolean checkPassword(@Nullable String password) {
      if (this.password == null || this.password.isBlank()) {
         return true;
      }
      return this.password.equals(password);
   }

   public String id(){
      return this.id;
   }

   public String title(){
      return this.title;
   }
   Room title(String title){
      if (!Validation.isValidText(title)) {
         throw new IllegalArgumentException("title invalid");
      }
      this.title = title;
      return this;
   }

   public String password(){
      return this.password;
   }
   Room password(String password){
      if (password != null && !Validation.isValidText(password)) {
         throw new IllegalArgumentException("password has to be valid if set");
      }
      this.password = password;
      return this;
   }

}
