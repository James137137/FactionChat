/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.james137137.FactionChat;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class ChatMode {

    

    
    public static List<Boolean> spyon = new ArrayList<>();
    public static List<String> chatModes = new ArrayList<>();
    public static List<String> playerNames = new ArrayList<>();

    public static void initialize() {

    }
    
    public static int getPlayerID (Player player)
    {
        String playerName = player.getName();
        for (int i = 0; i < playerNames.size(); i++) {
            if (playerNames.get(i).equalsIgnoreCase(playerName)) {
                return i;
            }
        }
        
        
        
        return -1;
    }
    
    public static boolean isSpyOn (Player player)
    {
        if (!player.hasPermission("FactionChat.spy")) {
            return false;
        }
        if (spyon.get(getPlayerID(player))) {
            return true;
        }
        return false;
    }
    
    public static void changeSpyMode(Player player)
    {
        
        if (player.hasPermission("FactionChat.spy")) {
            int playerID = getPlayerID(player);
            if (spyon.get(playerID)) {
            spyon.set(playerID, false);
            player.sendMessage(FactionChat.messageSpyModeOff);
        } else
            {
                spyon.set(playerID, true);
                player.sendMessage(FactionChat.messageSpyModeOn);
            }
        }

    }
    


    public static String getChatMode(Player player) {
        String playerName = player.getName();
        int playerid = getPlayerID(player);
        if (playerid >= 0)
        {
            return chatModes.get(playerid);
        }
        

        
        System.out.println("[FactionChat] player not found in ChatMode" + playerid + " = " + playerName);
        System.out.println("Trying to create new Chatmode for player" + playerName);
        ChatMode.SetNewChatMode(player);
        playerid = getPlayerID(player);
        if (playerid >= 0)
        {
            return chatModes.get(playerid);
        }
        System.out.println("[FactionChat] player not found in ChatMode and could not recreate " + playerid + " = " + playerName);
        
        return "ERROR";
    }

    public static void SetNewChatMode(Player player) {
        int playerid = getPlayerID(player);
        

        if (playerid == -1) {
            playerNames.add(player.getName());
            chatModes.add("PUBLIC");
            
            if (FactionChat.spyModeOnByDefault) 
            {
                spyon.add(true);
            }
            else
            {
                spyon.add(false);
            }
        } else {
            chatModes.set(playerid, "PUBLIC");
        }

    }

    public static void NextChatMode(Player player) {
        int playerid = getPlayerID(player);
        String currentChatMode = chatModes.get(playerid);
        if (currentChatMode.equalsIgnoreCase("PUBLIC")) {
            chatModes.set(playerid, "ALLY");
            player.sendMessage(FactionChat.messageNewChatMode + FactionChat.AllyChat +chatModes.get(playerid));
        } else if (currentChatMode.equalsIgnoreCase("ALLY")) {
            chatModes.set(playerid, "FACTION");
            player.sendMessage(FactionChat.messageNewChatMode +FactionChat.FactionChatColour+ chatModes.get(playerid));
        } else if (currentChatMode.equalsIgnoreCase("FACTION")) {
            chatModes.set(playerid, "PUBLIC");
            player.sendMessage(FactionChat.messageNewChatMode + chatModes.get(playerid));
        } else
        {
            chatModes.set(playerid, "PUBLIC");
            player.sendMessage(FactionChat.messageNewChatMode + chatModes.get(playerid));
        }
        
        

    }

    public static void setChatMode(Player player, String input) {
        int playerid = getPlayerID(player);
                
                
                    
                

                if (input.equalsIgnoreCase("PUBLIC") || input.equalsIgnoreCase("P")) {
                    chatModes.set(playerid, "PUBLIC");
                    player.sendMessage(FactionChat.messageNewChatMode + chatModes.get(playerid));
                } else if (input.equalsIgnoreCase("ALLY") || input.equalsIgnoreCase("A")) {
                    chatModes.set(playerid, "ALLY");
                    player.sendMessage(FactionChat.messageNewChatMode + FactionChat.AllyChat +chatModes.get(playerid));
                } else if (input.equalsIgnoreCase("FACTION") || input.equalsIgnoreCase("F")) {
                    chatModes.set(playerid, "FACTION");
                    player.sendMessage(FactionChat.messageNewChatMode +FactionChat.FactionChatColour+ chatModes.get(playerid));
                  
                } else if (player.hasPermission("FactionChat.EnemyChat") && (input.equalsIgnoreCase("ENEMY") || input.equalsIgnoreCase("E"))) {
                    chatModes.set(playerid,"ENEMY");
                    player.sendMessage(FactionChat.messageNewChatMode +FactionChat.EnemyChat + chatModes.get(playerid));
                
                }else if (player.hasPermission("FactionChat.JrModChat") && input.equalsIgnoreCase("JrMOD"))
                {
                    chatModes.set(playerid, "JrMOD");
                    player.sendMessage(FactionChat.messageNewChatMode +FactionChat.ModChat+ chatModes.get(playerid));
                }else if (player.hasPermission("FactionChat.ModChat") && input.equalsIgnoreCase("MOD"))
                {
                    chatModes.set(playerid, "MOD");
                    player.sendMessage(FactionChat.messageNewChatMode +FactionChat.ModChat+ chatModes.get(playerid));
                } else if (player.hasPermission("FactionChat.SrModChat") && input.equalsIgnoreCase("SrMOD"))
                {
                    chatModes.set(playerid, "SrMOD");
                    player.sendMessage(FactionChat.messageNewChatMode +FactionChat.ModChat+ chatModes.get(playerid));
                } else if (player.hasPermission("FactionChat.JrAdminChat") && input.equalsIgnoreCase("JrADMIN"))
                {
                    chatModes.set(playerid, "JrADMIN");
                    player.sendMessage(FactionChat.messageNewChatMode +FactionChat.AdminChat + chatModes.get(playerid));
                } else if (player.hasPermission("FactionChat.AdminChat") && input.equalsIgnoreCase("ADMIN"))
                {
                    chatModes.set(playerid, "ADMIN");
                    player.sendMessage(FactionChat.messageNewChatMode +FactionChat.AdminChat + chatModes.get(playerid));
                } else if (player.hasPermission("FactionChat.spy") && input.equalsIgnoreCase("SPY"))
                {
                    ChatMode.changeSpyMode(player);
                }
                else{
                    player.sendMessage(FactionChat.messageIncorectChatModeSwitch +" /fc a, /fc f, /fc p, /fc e");
                }
                
            }
                
            
        





    

    public static void RemovePlayer(Player player) {
        int playerid = getPlayerID(player);
        chatModes.remove(playerid);
        playerNames.remove(playerid);


    }

    public static void fixPlayerNotInFaction(Player player) {
        
                int playerid = getPlayerID(player);
                
        
                if (!chatModes.get(playerid).equalsIgnoreCase("PUBLIC"))
                        {
                            chatModes.set(playerid, "PUBLIC");
                            player.sendMessage(FactionChat.messageNewChatMode + chatModes.get(playerid));
                        }
                    
    }
}
