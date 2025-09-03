package de.wolftree.wolfchat;


import de.wolftree.Utils.UniqueId;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Members {
   private static final Logger logger = LoggerFactory.getLogger(Members.class);
   public static final String SERVER_ID = "SERVER";

   private final ConcurrentHashMap<String, Member> list = new ConcurrentHashMap<>();
   private final ConcurrentHashMap<String, Member> nicknames = new ConcurrentHashMap<>();

   private final ChatService chatService;

   Members(ChatService chatService){
      this.chatService = chatService;

      addMember(new Member().id("SERVER").nickname("Server").isSystemUser(true));
   }

   public boolean addMember(@NotNull Member member) {
      if (list.putIfAbsent(member.id(),member) == null){
         if (nicknames.putIfAbsent(member.nickName(), member) == null){
            return true;
         }
         else {
            String nickname = member.nickName();
            int tries = 5;
            while (--tries >= 0) {
               member.nickname(nickname + "-" + UniqueId.create(5));
               if (nicknames.putIfAbsent(member.nickName(), member) == null){
                  return true;
               }
            }
         }
         list.remove(member.id());
      }
      return false;
   }

   public void removeMember(@NotNull String memberId) {
      Member m = list.get(memberId);
      removeMember(m);
   }
   public void removeMember(@NotNull Member member) {
      if (member != null) {
         list.remove(member.id());
      }
   }

   public String changeNickname(@NotNull Member member, String newNickname){
      String oldNickname = member.nickName();
      if (nicknames.putIfAbsent(newNickname, member) == null){
         member.nickname(newNickname);
         nicknames.remove(oldNickname);
         return oldNickname;
      }
      return null;
   }

   public Member getMemberByTitle(String memberTitle){
      return nicknames.get(memberTitle);
   }

   public Member getMemberById(String memberId){
      return list.get(memberId);
   }
}
