/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat.API.chat;


import nz.co.lolnet.james137137.FactionChat.API.FactionChatAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class ChatFilterExample implements nz.co.lolnet.james137137.FactionChat.API.chat.ChatFilter{

    
    public ChatFilterExample() {
        if (Bukkit.getServer().getPluginManager().getPlugin("FactionChat") != null)
        {
            FactionChatAPI.chatFilter.add(this);
        }
    }

    String[] badwords = {"cat","dog","fish"};
    @Override
    public String filterMessage(Player player, String message) {
        if (player.isOp() || player.hasPermission("FactionChat.ChatFilter.bypassExample"))
        {
            return message;
        }
        for (String badword : badwords) {
            message = message.replace(badword, "****");
        }
        return message;
    }

    @Override
    public boolean canReceiveFactionChatMessage(Player player) {
        return true;
    }
    
}
