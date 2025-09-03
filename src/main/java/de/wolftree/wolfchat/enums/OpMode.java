package de.wolftree.wolfchat.enums;

public enum OpMode {
   OWNER(1, "Room owner, full control"),
   OPERATOR(2, "Room operator, manage members"),
   MODERATOR(4, "Moderator"),
   MEMBER(0, "Normal Member");

   private final int level;
   private final String description;

   OpMode(int level, String description) {
      this.level = level;
      this.description = description;
   }

   public int level() {
      return level;
   }

   public String description() {
      return description;
   }

   /**
    * Finds the OpMode for a numeric level (default GUEST).
    */
   public static OpMode fromLevel(int level) {
      for (OpMode m : values()) {
         if (m.level == level) return m;
      }
      return MEMBER;
   }
}
