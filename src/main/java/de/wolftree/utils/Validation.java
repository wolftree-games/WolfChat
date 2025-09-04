package de.wolftree.utils;

import org.jetbrains.annotations.NotNull;

public class Validation {
   public static boolean isValidText(@NotNull String text) {
      return isValidText(text, 8,24 );
   }

   public static boolean isValidText(@NotNull String text, int minLength, int maxLength) {
      if (text.length() >= minLength && text.length() <= maxLength) {
         return text.matches("^[a-zA-Z0-9]+(?:[_.-][a-zA-Z0-9]+)*$");
      }
      return false;
   }

}
