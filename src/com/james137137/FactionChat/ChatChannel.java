/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.james137137.FactionChat;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import com.massivecraft.factions.struct.Rel;
import java.util.StringTokenizer;
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

    ChatChannel(FactionChat aThis) {
        factionChat = aThis;
    }

    private String getFactionName(Player player) {
        String factionName = null;
        StringTokenizer myStringTokenizer = new StringTokenizer(P.p.getPlayerFactionTag(player), "*[] ");
        while (myStringTokenizer.hasMoreTokens()) {
            factionName = myStringTokenizer.nextToken();
        }
        return factionName;
    }

    public void fchat(Player player, String message) {







        String senderFaction = getFactionName(player); //obtains player's faction name


        if (senderFaction.equalsIgnoreCase("~")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            //start of sending the message
                /*
             * checks every player online to see if they belong in the senders faction if so it receives the message
             * 
             * added a admin chatspy with permision faction.spy
             */
        } else {


            //this could be used in replaced of below (5 lines below)
                /*
             FPlayer fSenderPlayer = (FPlayer)FPlayers.i.get(player);
             Faction SenderFaction = fSenderPlayer.getFaction();
             SenderFaction.sendMessage(ChatColor.DARK_GREEN + "[" + sSenderFaction + ChatColor.DARK_GREEN + "] " + ChatColor.RESET + player.getName() + ": " + message);
             SenderFaction.getRelationTo(fSenderPlayer);
             * */


            Player[] onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
            String playersFaction; //creates string outside loop

            //start of loop
            for (int i = 0; i < onlinePlayerList.length; i++) {

                playersFaction = getFactionName(onlinePlayerList[i]);


                if (playersFaction.equalsIgnoreCase(senderFaction)) {
                    onlinePlayerList[i].sendMessage(FactionChat.FactionChatColour + "[" + senderFaction + FactionChat.FactionChatColour + "] " + ChatColor.RESET + player.getName() + ": " + FactionChat.FactionChatMessage + message);
                } else if (ChatMode.isSpyOn(onlinePlayerList[i])) {
                    onlinePlayerList[i].sendMessage(FactionChat.FactionChatColour + "Spy: [" + senderFaction + ChatColor.DARK_GREEN + "] " + ChatColor.RESET + player.getName() + ": " + FactionChat.FactionChatMessage + message);
                }




            }
        }




    }

    public void fchata(Player player, String message) {



        String sSenderFaction = getFactionName(player); //obtains player's faction name


        if (sSenderFaction.equalsIgnoreCase("~")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            //start of sending the message
                /*
             * checks every player online to see if they belong in the senders faction if so it receives the message
             * 
             * added a admin chatspy with permision faction.spy
             */
        } else {



            FPlayer fSenderPlayer = (FPlayer) FPlayers.i.get(player);
            Faction SenderFaction = fSenderPlayer.getFaction();
            Player[] onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
            String sPlayersFaction; //creates string outside loop

            //start of loop
            // fSenderPlayer.sendMessage(message);
            for (Player myplayer : onlinePlayerList) {
                FPlayer fplayer = (FPlayer) FPlayers.i.get(myplayer);
                sPlayersFaction = getFactionName(myplayer);

                if (SenderFaction.getRelationTo(fplayer) == Rel.ALLY || SenderFaction.getRelationTo(fplayer) == Rel.TRUCE || sSenderFaction.equalsIgnoreCase(sPlayersFaction)) {
                    fplayer.sendMessage(FactionChat.AllyChat + "Ally: [" + sSenderFaction + FactionChat.AllyChat + "] " + ChatColor.RESET + player.getName() + ": " + FactionChat.AllyChatMessage + message);
                } else if (ChatMode.isSpyOn(myplayer)) {
                    fplayer.sendMessage(FactionChat.AllyChat + "Spy: Ally: [" + sSenderFaction + FactionChat.AllyChat + "] " + ChatColor.RESET + player.getName() + ": " + FactionChat.AllyChatMessage + message);
                }
            }

        }

    }

    public void jrModChat(Player player, String message) {
        Player[] onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        for (Player myplayer : onlinePlayerList) {
            if (myplayer.hasPermission("FactionChat.JrModChat") || FactionChat.isDebugger(myplayer.getName())) {
                myplayer.sendMessage(ChatColor.AQUA + "[JrMod-Chat]: " + ChatColor.RESET + player.getName() + ": " + ChatColor.GREEN + message);
            }

        }

    }

    public void modChat(Player player, String message) {
        Player[] onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        for (Player myplayer : onlinePlayerList) {
            if (myplayer.hasPermission("FactionChat.ModChat") || FactionChat.isDebugger(myplayer.getName())) {
                myplayer.sendMessage(ChatColor.AQUA + "[Mod-Chat]: " + ChatColor.RESET + player.getName() + ": " + ChatColor.GREEN + message);
            }

        }

    }

    public void SrModChat(Player player, String message) {
        Player[] onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        for (Player myplayer : onlinePlayerList) {
            if (myplayer.hasPermission("FactionChat.SrModChat") || FactionChat.isDebugger(myplayer.getName())) {
                myplayer.sendMessage(ChatColor.AQUA + "[SrMod-Chat]: " + ChatColor.RESET + player.getName() + ": " + ChatColor.GREEN + message);
            }

        }

    }

    public void JrAdminChat(Player player, String message) {
        Player[] onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        for (Player myplayer : onlinePlayerList) {
            if (myplayer.hasPermission("FactionChat.JrAdminChat") || FactionChat.isDebugger(myplayer.getName())) {
                myplayer.sendMessage(ChatColor.DARK_RED + "[JrAdmin-Chat]: " + ChatColor.RESET + player.getName() + ": " + ChatColor.GREEN + message);
            }

        }

    }

    public void adminChat(Player player, String message) {
        Player[] onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
        for (Player myplayer : onlinePlayerList) {
            if (myplayer.hasPermission("FactionChat.AdminChat") || FactionChat.isDebugger(myplayer.getName())) {
                myplayer.sendMessage(ChatColor.DARK_RED + "[Admin-Chat]: " + ChatColor.RESET + player.getName() + ": " + ChatColor.GREEN + message);
            }

        }

    }

    public void fchato(CommandSender sender, String[] args) {

        Player player = (Player) sender;//get player


        if (args.length <= 1) { //checks if there is a message in command eg "hello world" in /fchat hello world
            player.sendMessage(FactionChat.messageFchatoMisstype);
        } else {


            String senderFaction = getFactionName(player);


            if (senderFaction.equalsIgnoreCase("~")) { //checks if player is in a faction
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


                Player[] onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
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

    public void fchatE(Player player, String message) {




        String sSenderFaction = getFactionName(player); //obtains player's faction name


        if (sSenderFaction.equalsIgnoreCase("~")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            //start of sending the message
                /*
             * checks every player online to see if they belong in the senders faction if so it receives the message
             * 
             * added a admin chatspy with permision faction.spy
             */
        } else {



            FPlayer fSenderPlayer = (FPlayer) FPlayers.i.get(player);
            Faction SenderFaction = fSenderPlayer.getFaction();
            Player[] onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
            String sPlayersFaction; //creates string outside loop

            //start of loop
            //fSenderPlayer.sendMessage(message);
            for (Player myplayer : onlinePlayerList) {
                FPlayer fplayer = (FPlayer) FPlayers.i.get(myplayer);
                sPlayersFaction = getFactionName(myplayer);


                if (SenderFaction.getRelationTo(fplayer) == Rel.ENEMY || sSenderFaction.equalsIgnoreCase(sPlayersFaction)) {
                    fplayer.sendMessage(FactionChat.EnemyChat + "Enemy: [" + sSenderFaction + FactionChat.EnemyChat + "] " + ChatColor.RESET + player.getName() + ": " + FactionChat.EnemyChatMessage + message);
                } else if (ChatMode.isSpyOn(myplayer)) {
                    fplayer.sendMessage(FactionChat.EnemyChat + "Spy: Enemy: [" + sSenderFaction + FactionChat.EnemyChat + "] " + ChatColor.RESET + player.getName() + ": " + FactionChat.EnemyChatMessage + message);
                }
            }

        }

    }
}
