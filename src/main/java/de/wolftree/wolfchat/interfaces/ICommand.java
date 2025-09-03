package de.wolftree.wolfchat.interfaces;

import de.wolftree.wolfchat.Member;

public interface ICommand {

   String execute(Member member, String[] args);

}
