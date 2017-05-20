/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat.API.chat;

import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public interface ChatFilter {
    
    
    public String filterMessage(Player player, String message);
    
    public boolean canReceiveFactionChatMessage(Player player);
}
