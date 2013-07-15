package nz.co.lolnet.james137137.FactionChat;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class ChatMode {

    protected static ArrayList<Boolean> spyon = new ArrayList<Boolean>();
    protected static ArrayList<String> chatModes = new ArrayList<String>();
    protected static ArrayList<String> playerNames = new ArrayList<String>();

    protected static void initialize() {
    }

    protected static int getPlayerID(Player player) {
        String playerName = player.getName();
        for (int i = 0; i < playerNames.size(); i++) {
            if (playerNames.get(i).equalsIgnoreCase(playerName)) {
                return i;
            }
        }



        return -1;
    }

    protected static boolean isSpyOn(Player player) {
        if (!player.hasPermission("FactionChat.spy") && !FactionChat.isDebugger(player.getName())) {
            return false;
        }
        if (spyon.get(getPlayerID(player))) {
            return true;
        }
        return false;
    }

    protected static void changeSpyMode(Player player) {

        if (player.hasPermission("FactionChat.spy") || FactionChat.isDebugger(player.getName())) {
            int playerID = getPlayerID(player);
            if (spyon.get(playerID)) {
                spyon.set(playerID, false);
                player.sendMessage(FactionChat.messageSpyModeOff);
            } else {
                spyon.set(playerID, true);
                player.sendMessage(FactionChat.messageSpyModeOn);
            }
        }

    }

    protected static String getChatMode(Player player) {
        String playerName = player.getName();
        int playerid = getPlayerID(player);
        if (playerid >= 0) {
            return chatModes.get(playerid);
        }



        Bukkit.getLogger().info("[FactionChat] player not found in ChatMode" + playerid + " = " + playerName);
        Bukkit.getLogger().info("Trying to create new Chatmode for player" + playerName);
        SetNewChatMode(player);
        playerid = getPlayerID(player);
        if (playerid >= 0) {
            return chatModes.get(playerid);
        }
        Bukkit.getLogger().info("[FactionChat] player not found in ChatMode and could not recreate " + playerid + " = " + playerName);

        return "ERROR";
    }

    protected static void SetNewChatMode(Player player) {
        int playerid = getPlayerID(player);


        if (playerid == -1) {
            playerNames.add(player.getName());
            chatModes.add("PUBLIC");

            if (player.hasPermission("FactionChat.spy") && FactionChat.spyModeOnByDefault) {
                spyon.add(true);
            } else {
                spyon.add(false);
            }
        } else {
            chatModes.set(playerid, "PUBLIC");
        }

    }

    protected static void NextChatMode(Player player) {
        int playerid = getPlayerID(player);
        if (FactionChat.FactionsEnable) {
            if (chatModes.get(playerid).equalsIgnoreCase("PUBLIC")) {
                chatModes.set(playerid, "ALLY");
                if (FactionChat.AllyChatEnable && player.hasPermission("FactionChat.AllyChat")) {
                    
                    player.sendMessage(FactionChat.messageNewChatMode + ChatColor.GREEN + chatModes.get(playerid));
                    return;
                }

            }
            if (chatModes.get(playerid).equalsIgnoreCase("ALLY") && player.hasPermission("FactionChat.FactionChat")) {
                chatModes.set(playerid, "FACTION");
                if (FactionChat.FactionChatEnable) {
                    player.sendMessage(FactionChat.messageNewChatMode + ChatColor.DARK_GREEN + chatModes.get(playerid));
                    return;
                }

            }
            
            if (chatModes.get(playerid).equalsIgnoreCase("FACTION") && player.hasPermission("FactionChat.AllyChat")) {
                chatModes.set(playerid, "TRUCE");
                if (FactionChat.FactionChatEnable) {
                    player.sendMessage(FactionChat.messageNewChatMode + ChatColor.GREEN + chatModes.get(playerid));
                    return;
                }

            }
            
            if (chatModes.get(playerid).equalsIgnoreCase("TRUCE") && player.hasPermission("FactionChat.AllyChat")) {
                chatModes.set(playerid, "ALLY&TRUCE");
                if (FactionChat.FactionChatEnable) {
                    player.sendMessage(FactionChat.messageNewChatMode + ChatColor.GREEN + chatModes.get(playerid));
                    return;
                }

            }
        }




        chatModes.set(playerid, "PUBLIC");
        player.sendMessage(FactionChat.messageNewChatMode + chatModes.get(playerid));




    }

    protected static void setChatMode(Player player, String input) {
        int playerid = getPlayerID(player);
        if (input.equalsIgnoreCase("PUBLIC") || input.equalsIgnoreCase("P")) {
            chatModes.set(playerid, "PUBLIC");
            player.sendMessage(FactionChat.messageNewChatMode + chatModes.get(playerid));
            return;
        }
        if (FactionChat.FactionsEnable) {
            boolean isFactionChat = false;
            if ((input.equalsIgnoreCase("ALLYTRUCE") || input.equalsIgnoreCase("AT"))) {
                isFactionChat = true;
                if (!FactionChat.AllyChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.AllyChat")) {
                    chatModes.set(playerid, "ALLY&TRUCE");
                    player.sendMessage(FactionChat.messageNewChatMode + ChatColor.GREEN+ chatModes.get(playerid));
                }

            } else if ((input.equalsIgnoreCase("ALLY") || input.equalsIgnoreCase("A"))) {
                isFactionChat = true;
                if (!FactionChat.AllyChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.AllyChat")) {
                    chatModes.set(playerid, "ALLY");
                    player.sendMessage(FactionChat.messageNewChatMode + ChatColor.GREEN + chatModes.get(playerid));
                }


            } else if ((input.equalsIgnoreCase("TRUCE") || input.equalsIgnoreCase("T"))) {
                isFactionChat = true;
                if (!FactionChat.AllyChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.AllyChat")) {
                    chatModes.set(playerid, "TRUCE");
                    player.sendMessage(FactionChat.messageNewChatMode + ChatColor.GREEN + chatModes.get(playerid));
                }


            } else if ((input.equalsIgnoreCase("FACTION") || input.equalsIgnoreCase("F"))) {
                isFactionChat = true;
                if (!FactionChat.FactionChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.FactionChat")) {
                    chatModes.set(playerid, "FACTION");
                    player.sendMessage(FactionChat.messageNewChatMode + ChatColor.DARK_GREEN + chatModes.get(playerid));
                }


            } else if (((player.hasPermission("FactionChat.EnemyChat") || FactionChat.isDebugger(player.getName()))
                    && (input.equalsIgnoreCase("ENEMY") || input.equalsIgnoreCase("E")))) {
                isFactionChat = true;
                if (!FactionChat.EnemyChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                    return;
                }
                chatModes.set(playerid, "ENEMY");
                player.sendMessage(FactionChat.messageNewChatMode + ChatColor.RED + chatModes.get(playerid));

            }

            if (isFactionChat) {
                return;
            }
        }



        if ((player.hasPermission("FactionChat.UserAssistantChat") || FactionChat.isDebugger(player.getName()))
                && (input.equalsIgnoreCase("UA") || input.equalsIgnoreCase("UserAssistant"))) {
            if (!FactionChat.UAChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            chatModes.set(playerid, "UserAssistant");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.DARK_PURPLE + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.JrModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("JrMOD")) {
            if (!FactionChat.JrModChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            chatModes.set(playerid, "JrMOD");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.BLUE + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.ModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("MOD")) {
            if (!FactionChat.ModChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            chatModes.set(playerid, "MOD");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.BLUE + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.SrModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("SrMOD")) {
            if (!FactionChat.SrModChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            chatModes.set(playerid, "SrMOD");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.BLUE + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.JrAdminChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("JrADMIN")) {
            if (!FactionChat.JrAdminChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            chatModes.set(playerid, "JrADMIN");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.DARK_RED + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.AdminChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("ADMIN")) {
            if (!FactionChat.AdminChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            chatModes.set(playerid, "ADMIN");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.DARK_RED + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.spy") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("SPY")) {
            ChatMode.changeSpyMode(player);
        } else {
            player.sendMessage(FactionChat.messageIncorectChatModeSwitch + " /fc a, /fc f, /fc p, /fc e");
        }

    }

    protected static void setChatMode(Player player, String input, CommandSender sender) {
        int playerid = getPlayerID(player);
        if (input.equalsIgnoreCase("PUBLIC") || input.equalsIgnoreCase("P")) {
            chatModes.set(playerid, "PUBLIC");
            player.sendMessage(FactionChat.messageNewChatMode + chatModes.get(playerid));
            return;
        }
        if (FactionChat.FactionsEnable) {
            boolean isFactionChat = false;
            if ((input.equalsIgnoreCase("ALLYTRUCE") || input.equalsIgnoreCase("AT"))) {
                isFactionChat = true;
                if (!FactionChat.AllyChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.AllyChat")) {
                    chatModes.set(playerid, "ALLY&TRUCE");
                    player.sendMessage(FactionChat.messageNewChatMode + ChatColor.GREEN+ chatModes.get(playerid));
                }

            } else if ((input.equalsIgnoreCase("ALLY") || input.equalsIgnoreCase("A"))) {
                isFactionChat = true;
                if (!FactionChat.AllyChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.AllyChat")) {
                    chatModes.set(playerid, "ALLY");
                    player.sendMessage(FactionChat.messageNewChatMode + ChatColor.GREEN + chatModes.get(playerid));
                }


            } else if ((input.equalsIgnoreCase("TRUCE") || input.equalsIgnoreCase("T"))) {
                isFactionChat = true;
                if (!FactionChat.AllyChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.AllyChat")) {
                    chatModes.set(playerid, "TRUCE");
                    player.sendMessage(FactionChat.messageNewChatMode + ChatColor.GREEN + chatModes.get(playerid));
                }


            } else if ((input.equalsIgnoreCase("FACTION") || input.equalsIgnoreCase("F"))) {
                isFactionChat = true;
                if (!FactionChat.FactionChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.FactionChat")) {
                    chatModes.set(playerid, "FACTION");
                    player.sendMessage(FactionChat.messageNewChatMode + ChatColor.DARK_GREEN + chatModes.get(playerid));
                }


            } else if (((player.hasPermission("FactionChat.EnemyChat") || FactionChat.isDebugger(player.getName()))
                    && (input.equalsIgnoreCase("ENEMY") || input.equalsIgnoreCase("E")))) {
                isFactionChat = true;
                if (!FactionChat.EnemyChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                    return;
                }
                chatModes.set(playerid, "ENEMY");
                player.sendMessage(FactionChat.messageNewChatMode + ChatColor.RED + chatModes.get(playerid));

            }

            if (isFactionChat) {
                return;
            }
        }



        if ((player.hasPermission("FactionChat.UserAssistantChat") || FactionChat.isDebugger(player.getName()))
                && (input.equalsIgnoreCase("UA") || input.equalsIgnoreCase("UserAssistant"))) {
            if (!FactionChat.UAChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            chatModes.set(playerid, "UserAssistant");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.DARK_PURPLE + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.JrModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("JrMOD")) {
            if (!FactionChat.JrModChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            chatModes.set(playerid, "JrMOD");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.BLUE + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.ModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("MOD")) {
            if (!FactionChat.ModChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            chatModes.set(playerid, "MOD");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.BLUE + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.SrModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("SrMOD")) {
            if (!FactionChat.SrModChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            chatModes.set(playerid, "SrMOD");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.BLUE + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.JrAdminChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("JrADMIN")) {
            if (!FactionChat.JrAdminChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            chatModes.set(playerid, "JrADMIN");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.DARK_RED + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.AdminChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("ADMIN")) {
            if (!FactionChat.AdminChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            chatModes.set(playerid, "ADMIN");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.DARK_RED + chatModes.get(playerid));
        } else if ((player.hasPermission("FactionChat.spy") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("SPY")) {
            ChatMode.changeSpyMode(player);
        } else {
            sender.sendMessage("player doesn't have that permission or incorrect Chat mode name");
        }
    }

    protected static void RemovePlayer(Player player) {
        int playerid = getPlayerID(player);
        chatModes.remove(playerid);
        playerNames.remove(playerid);


    }

    protected static void fixPlayerNotInFaction(Player player) {

        int playerid = getPlayerID(player);


        if (!chatModes.get(playerid).equalsIgnoreCase("PUBLIC")) {
            chatModes.set(playerid, "PUBLIC");
            player.sendMessage(FactionChat.messageNewChatMode + chatModes.get(playerid));
        }

    }
}
