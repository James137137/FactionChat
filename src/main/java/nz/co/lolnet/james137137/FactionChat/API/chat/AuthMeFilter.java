/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat.API.chat;

import nz.co.lolnet.james137137.FactionChat.API.AuthMeAPI;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class AuthMeFilter implements ChatFilter{
    
    @Override
    public String filterMessage(Player player, String message) {
        return message;
    }

    @Override
    public boolean canReceiveFactionChatMessage(Player player) {
        if (AuthMeAPI.isAllowToChat(player))
        {
            return true;
        }
        return false;
    }
    
}
