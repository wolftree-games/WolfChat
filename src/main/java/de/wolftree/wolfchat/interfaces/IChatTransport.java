package de.wolftree.wolfchat.interfaces;

public interface IChatTransport {
   void sendToMember(String toMemberId, String fromMemberId, String message);

}
