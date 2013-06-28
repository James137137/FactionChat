/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.ScoreboardManager;

/**
 *
 * @author James
 */
public class OtherChatChannel {

    private FactionChat factionChat;
    private static Player[] onlinePlayerList;

    public OtherChatChannel(FactionChat aThis) {
        factionChat = aThis;
    }
    
    private static String FormatString (String message, String[] args)
    {
        if (args!=null)
        {
          for (int i = 0; i < args.length; i++) {
            message = message.replace(("{"+i+"}"), args[i]);
            }  
        }
        
            message = message.replaceAll("&",""+(char)167);
        
        return message;
    }

    protected void userAssistantChat(Player player, String message) {
        onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        String[] intput1 = {player.getName(),message};
        String normalMessage = FormatString(FactionChat.UAChat, intput1);
        for (Player myplayer : onlinePlayerList) {
            if (myplayer.hasPermission("FactionChat.UserAssistantChat") || FactionChat.isDebugger(myplayer.getName())) {
                myplayer.sendMessage(normalMessage);
            }

        }

    }

    protected void jrModChat(Player player, String message) {
        onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        String[] intput1 = {player.getName(),message};
        String normalMessage = FormatString(FactionChat.JrModChat, intput1);
        for (Player myplayer : onlinePlayerList) {
            if (myplayer.hasPermission("FactionChat.JrModChat") || FactionChat.isDebugger(myplayer.getName())) {
                myplayer.sendMessage(normalMessage);
            }

        }

    }

    protected void modChat(Player player, String message) {
        onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        String[] intput1 = {player.getName(),message};
        String normalMessage = FormatString(FactionChat.ModChat, intput1);
        for (Player myplayer : onlinePlayerList) {
            if (myplayer.hasPermission("FactionChat.ModChat") || FactionChat.isDebugger(myplayer.getName())) {
                myplayer.sendMessage(normalMessage);
            }

        }

    }

    protected void SrModChat(Player player, String message) {
        onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        String[] intput1 = {player.getName(),message};
        String normalMessage = FormatString(FactionChat.SrModChat, intput1);
        for (Player myplayer : onlinePlayerList) {
            if (myplayer.hasPermission("FactionChat.SrModChat") || FactionChat.isDebugger(myplayer.getName())) {
                myplayer.sendMessage(normalMessage);
            }

        }

    }

    protected void JrAdminChat(Player player, String message) {
        onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        String[] intput1 = {player.getName(),message};
        String normalMessage = FormatString(FactionChat.JrAdminChat, intput1);
        for (Player myplayer : onlinePlayerList) {
            if (myplayer.hasPermission("FactionChat.JrAdminChat") || FactionChat.isDebugger(myplayer.getName())) {
                myplayer.sendMessage(normalMessage);
            }

        }

    }

    protected void adminChat(Player player, String message) {
        onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        String[] intput1 = {player.getName(),message};
        String normalMessage = FormatString(FactionChat.AdminChat, intput1);
        for (Player myplayer : onlinePlayerList) {
            if (myplayer.hasPermission("FactionChat.AdminChat") || FactionChat.isDebugger(myplayer.getName())) {
                myplayer.sendMessage(normalMessage);
            }

        }

    }
}
