/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat.API.Event;

import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import nz.co.lolnet.james137137.FactionChat.ChatModeType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 *
 * @author James
 */
public class FactionChatPlayerChatEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    Player player;
    private ChatModeType chatMode;
    private String message;
    Set<Player> recipients;

    public FactionChatPlayerChatEvent(Player player) {
        super(player);
        this.player = player;
    }
    

    public FactionChatPlayerChatEvent(Player talkingPlayer, ChatModeType chatmode, String msg, Set<Player> recipients) {
        super(talkingPlayer);
        this.player = talkingPlayer;
        this.chatMode = chatmode;
        this.message = msg;
        this.recipients = recipients;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean bln) {
        cancel = bln;
    }

    public ChatModeType getChatMode() {
        return chatMode;
    }

    public String getMessage() {
        return message;
    }

    public void setRecipients(Set<Player> recipients) {
        this.recipients = recipients;
    }

    public Set<Player> getRecipients() {
        return recipients;
    }

    public void setChatMode(ChatModeType chatMode) {
        this.chatMode = chatMode;
    }

    
    
    
    

}
