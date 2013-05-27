/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.james137137.FactionChat;

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

    protected void userAssistantChat(Player player, String message) {
        onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        for (Player myplayer : onlinePlayerList) {
            if (myplayer.hasPermission("FactionChat.UserAssistantChat") || FactionChat.isDebugger(myplayer.getName())) {
                myplayer.sendMessage(ChatColor.DARK_PURPLE + "[UA-Chat]: " + ChatColor.RESET + player.getName() + ": " + FactionChat.ModChatMessage + message);
            }

        }

    }

    protected void jrModChat(Player player, String message) {
        onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        for (Player myplayer : onlinePlayerList) {
            if (myplayer.hasPermission("FactionChat.JrModChat") || FactionChat.isDebugger(myplayer.getName())) {
                myplayer.sendMessage(FactionChat.ModChat + "[JrMod-Chat]: " + ChatColor.RESET + player.getName() + ": " + FactionChat.ModChatMessage + message);
            }

        }

    }

    protected void modChat(Player player, String message) {
        onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        for (Player myplayer : onlinePlayerList) {
            if (myplayer.hasPermission("FactionChat.ModChat") || FactionChat.isDebugger(myplayer.getName())) {
                myplayer.sendMessage(FactionChat.ModChat + "[Mod-Chat]: " + ChatColor.RESET + player.getName() + ": " + FactionChat.ModChatMessage + message);
            }

        }

    }

    protected void SrModChat(Player player, String message) {
        onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        for (Player myplayer : onlinePlayerList) {
            if (myplayer.hasPermission("FactionChat.SrModChat") || FactionChat.isDebugger(myplayer.getName())) {
                myplayer.sendMessage(FactionChat.ModChat + "[SrMod-Chat]: " + ChatColor.RESET + player.getName() + ": " + FactionChat.ModChatMessage + message);
            }

        }

    }

    protected void JrAdminChat(Player player, String message) {
        onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        for (Player myplayer : onlinePlayerList) {
            if (myplayer.hasPermission("FactionChat.JrAdminChat") || FactionChat.isDebugger(myplayer.getName())) {
                myplayer.sendMessage(FactionChat.AdminChat + "[JrAdmin-Chat]: " + ChatColor.RESET + player.getName() + ": " + FactionChat.AdminChatMessage + message);
            }

        }

    }

    protected void adminChat(Player player, String message) {
        onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        for (Player myplayer : onlinePlayerList) {
            if (myplayer.hasPermission("FactionChat.AdminChat") || FactionChat.isDebugger(myplayer.getName())) {
                myplayer.sendMessage(FactionChat.AdminChat + "[Admin-Chat]: " + ChatColor.RESET + player.getName() + ": " + FactionChat.AdminChatMessage + message);
            }

        }

    }

    protected void UHC(Player talkingPlayer, String msg, ScoreboardManager scoreboardmanager) {
        String talkingPlayerTeam;

        try {
            talkingPlayerTeam = scoreboardmanager.getMainScoreboard().getPlayerTeam(talkingPlayer).getName();
        } catch (Exception e) {
            talkingPlayer.sendMessage(ChatColor.RED + "You are not in a team");
            ChatMode.fixPlayerNotInFaction(talkingPlayer);
            return;
        }

        onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        for (Player myplayer : onlinePlayerList) {

            try {

                String myplayerTeam = scoreboardmanager.getMainScoreboard().getPlayerTeam(myplayer).getName();
                if (myplayerTeam.equalsIgnoreCase(talkingPlayerTeam) || myplayer.hasPermission("FactionChat.UHC.Spy")) {
                    myplayer.sendMessage(ChatColor.DARK_GREEN + ("[" + talkingPlayerTeam + "] ") + ChatColor.RESET + talkingPlayer.getName() + ": " + msg);
                }

            } catch (Exception e) {
            }







        }
    }
}
