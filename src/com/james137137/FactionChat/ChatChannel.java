/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.james137137.FactionChat;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Rel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class ChatChannel {

    private FactionChat factionChat;
    private static Player[] onlinePlayerList;

    ChatChannel(FactionChat aThis) {
        factionChat = aThis;
    }

    /**
     *
     * Returns player's Factions Name.
     *
     */
    protected static String getFactionName(Player player) {
        return ((FPlayer) FPlayers.i.get(player)).getFaction().getTag();
    }

    /**
     *
     * Returns player's Factions Name.
     *
     */
    protected static String getFactionName(FPlayer fPlayer) {
        return fPlayer.getFaction().getTag();
    }

    /**
     *
     * Returns player's Factions Title.
     *
     */
    protected static String getPlayerTitle(Player player) {
        String title = ((FPlayer) FPlayers.i.get(player)).getTitle();
        if (title.length() > 0) {
            title += "-";
        }
        return title;
    }

    /*
     * Sends a message to the player's Faction only.
     */
    protected void fchat(Player player, String message) {


        String senderFaction = getFactionName(player); //obtains player's faction name

        if (senderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {


            if (getFactionName(myPlayer).equalsIgnoreCase(senderFaction)) {
                myPlayer.sendMessage(FactionChat.FactionChatColour + "[" + senderFaction + FactionChat.FactionChatColour + "] " + ChatColor.RESET + getPlayerTitle(player) + player.getName() + ": " + FactionChat.FactionChatMessage + message);
            } else if (ChatMode.isSpyOn(myPlayer)) {
                myPlayer.sendMessage(FactionChat.FactionChatColour + "Spy: [" + senderFaction + ChatColor.DARK_GREEN + "] " + ChatColor.RESET + player.getName() + ": " + FactionChat.FactionChatMessage + message);
            }
        }






    }

    /*
     * Sends a message to the player's Faction 
     * and everyone that is in a Faction that is ally or truce with the player's Faction.
     */
    protected void fchata(Player player, String message) {

        String sSenderFaction = getFactionName(player); //obtains player's faction name

        if (sSenderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }

        FPlayer fSenderPlayer = (FPlayer) FPlayers.i.get(player);
        Faction SenderFaction = fSenderPlayer.getFaction();

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {


            FPlayer fplayer = (FPlayer) FPlayers.i.get(myPlayer);


            if (SenderFaction.getRelationTo(fplayer) == Rel.ALLY || SenderFaction.getRelationTo(fplayer) == Rel.TRUCE || sSenderFaction.equalsIgnoreCase(getFactionName(fplayer))) {
                fplayer.sendMessage(FactionChat.AllyChat + "Ally: [" + sSenderFaction + FactionChat.AllyChat + "] " + ChatColor.RESET + getPlayerTitle(player) + player.getName() + ": " + FactionChat.AllyChatMessage + message);
            } else if (ChatMode.isSpyOn(myPlayer)) {
                fplayer.sendMessage(FactionChat.AllyChat + "Spy: Ally: [" + sSenderFaction + FactionChat.AllyChat + "] " + ChatColor.RESET + player.getName() + ": " + FactionChat.AllyChatMessage + message);
            }
        }
    }

    /*
     * Sends a message to the player's Faction 
     * and everyone that is in a Faction that enermies with the player's Faction.
     */
    protected void fchatE(Player player, String message) {

        String sSenderFaction = getFactionName(player); //obtains player's faction name

        if (sSenderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }

        FPlayer fSenderPlayer = (FPlayer) FPlayers.i.get(player);
        Faction SenderFaction = fSenderPlayer.getFaction();

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {


            FPlayer fplayer = (FPlayer) FPlayers.i.get(myPlayer);


            if (SenderFaction.getRelationTo(fplayer) == Rel.ENEMY || sSenderFaction.equalsIgnoreCase(getFactionName(fplayer))) {
                fplayer.sendMessage(FactionChat.EnemyChat + "Enemy: [" + sSenderFaction + FactionChat.EnemyChat + "] " + ChatColor.RESET + getPlayerTitle(player) + player.getName() + ": " + FactionChat.EnemyChatMessage + message);
            } else if (ChatMode.isSpyOn(myPlayer)) {
                fplayer.sendMessage(FactionChat.EnemyChat + "Spy: Enemy: [" + sSenderFaction + FactionChat.EnemyChat + "] " + ChatColor.RESET + player.getName() + ": " + FactionChat.EnemyChatMessage + message);
            }
        }

    }

    protected void userAssistantChat(Player player, String message) {
        onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        for (Player myplayer : onlinePlayerList) {
            if (myplayer.hasPermission("FactionChat.UserAssistantChat") || FactionChat.isDebugger(myplayer.getName())) {
                myplayer.sendMessage(ChatColor.DARK_PURPLE + "[UA-Chat]: " + ChatColor.RESET + player.getName() + ": " + factionChat.ModChatMessage + message);
            }

        }

    }

    protected void jrModChat(Player player, String message) {
        onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        for (Player myplayer : onlinePlayerList) {
            if (myplayer.hasPermission("FactionChat.JrModChat") || FactionChat.isDebugger(myplayer.getName())) {
                myplayer.sendMessage(FactionChat.ModChat + "[JrMod-Chat]: " + ChatColor.RESET + player.getName() + ": " + factionChat.ModChatMessage + message);
            }

        }

    }

    protected void modChat(Player player, String message) {
        onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        for (Player myplayer : onlinePlayerList) {
            if (myplayer.hasPermission("FactionChat.ModChat") || FactionChat.isDebugger(myplayer.getName())) {
                myplayer.sendMessage(FactionChat.ModChat + "[Mod-Chat]: " + ChatColor.RESET + player.getName() + ": " + factionChat.ModChatMessage + message);
            }

        }

    }

    protected void SrModChat(Player player, String message) {
        onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        for (Player myplayer : onlinePlayerList) {
            if (myplayer.hasPermission("FactionChat.SrModChat") || FactionChat.isDebugger(myplayer.getName())) {
                myplayer.sendMessage(FactionChat.ModChat + "[SrMod-Chat]: " + ChatColor.RESET + player.getName() + ": " + factionChat.ModChatMessage + message);
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

    protected void fchato(CommandSender sender, String[] args) {

        Player player = (Player) sender;//get player


        if (args.length <= 1) { //checks if there is a message in command eg "hello world" in /fchat hello world
            player.sendMessage(FactionChat.messageFchatoMisstype);
        } else {


            String senderFaction = getFactionName(player);


            if (senderFaction.contains("Wilderness")) { //checks if player is in a faction
                player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
                //start of sending the message
                /*
                 * checks every player online to see if they belong in the senders faction if so it receives the message
                 * 
                 * added a admin chatspy with permision faction.spy
                 */
            } else {
                String message = "";
                for (int i = 1; i < args.length; i++) {
                    message += args[i] + " ";
                }

                //this could be used in replaced of below (5 lines below)
                /*
                 FPlayer fSenderPlayer = (FPlayer)FPlayers.i.get(player);
                 Faction SenderFaction = fSenderPlayer.getFaction();
                 SenderFaction.sendMessage(ChatColor.DARK_GREEN + "[" + sSenderFaction + ChatColor.DARK_GREEN + "] " + ChatColor.RESET + player.getName() + ": " + message);
                 SenderFaction.getRelationTo(fSenderPlayer);
                 * */


                onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
                String playersFaction; //creates string outside loop
                String targetFaction = args[0] + senderFaction.charAt(senderFaction.length() - 2) + senderFaction.charAt(senderFaction.length() - 1);

                int count = 0;

                //start of loop
                for (int i = 0; i < onlinePlayerList.length; i++) {

                    playersFaction = getFactionName(onlinePlayerList[i]);


                    if (playersFaction.equalsIgnoreCase(senderFaction)) {
                        onlinePlayerList[i].sendMessage(FactionChat.OtherFactionChat + "[@" + targetFaction + FactionChat.OtherFactionChat + "] " + ChatColor.RESET + player.getName() + ": " + FactionChat.OtherFactionChatMessage + message);
                    } else if (playersFaction.equalsIgnoreCase(targetFaction)) {
                        onlinePlayerList[i].sendMessage(FactionChat.OtherFactionChat + "from: [" + FactionChat.OtherFactionChat + senderFaction + FactionChat.OtherFactionChat + "] " + ChatColor.RESET + player.getName() + ": " + FactionChat.OtherFactionChatMessage + message);
                        count++;
                    } else if (ChatMode.isSpyOn(onlinePlayerList[i])) {
                        onlinePlayerList[i].sendMessage(FactionChat.OtherFactionChat + "Spy: [" + FactionChat.OtherFactionChat + senderFaction + FactionChat.OtherFactionChat + " @" + FactionChat.OtherFactionChat + targetFaction + FactionChat.OtherFactionChat + "] " + ChatColor.RESET + player.getName() + ": " + FactionChat.OtherFactionChatMessage + message);
                    }

                }

                if (count == 0) {
                    player.sendMessage(ChatColor.RED + FactionChat.messageFchatoNoOneOnline);
                }
            }
        }



    }
}
