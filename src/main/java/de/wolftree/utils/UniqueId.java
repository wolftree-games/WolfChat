package de.wolftree.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class UniqueId {
   private static final int UNIQUE_ID_LENGTH = 12;

   public static String create(){
      return create(UNIQUE_ID_LENGTH);
   }

   public static String create(int length){
      final SecureRandom secureRandom = new SecureRandom();
      final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
      byte[] randomBytes = new byte[length];
      secureRandom.nextBytes(randomBytes);
      return base64Encoder.encodeToString(randomBytes).substring(0,length);
   }

}