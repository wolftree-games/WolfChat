package de.wolftree.wolfchat;

import de.wolftree.Utils.Validation;

public class Member {
   private String id;
   private String nickname;
   private boolean systemUser;
   private final long createdTs;

   public Member(){
      this.createdTs = System.currentTimeMillis();
   }

   public long createdTs(){
      return this.createdTs;
   }

   public String id() {
      return id;
   }

   public Member id(String id) {
      this.id = id;
      return this;
   }

   public String nickName() {
      return nickname;
   }

   public Member nickname(String nickname) {
      if (!isValidNickname(nickname)){
         throw new IllegalArgumentException("Invalid nickname!");
      }
      this.nickname = nickname;
      return this;
   }

   public static boolean isValidNickname(String text){
      return Validation.isValidText(text,4,16);
   }

   public boolean isSystemUser() {
      return systemUser;
   }

   public Member isSystemUser(boolean systemUser) {
      this.systemUser = systemUser;
      return this;
   }

   @Override
   public int hashCode() {
      return id.hashCode();
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof Member other))
         return false;
      return id.equals(other.id);
   }
}
