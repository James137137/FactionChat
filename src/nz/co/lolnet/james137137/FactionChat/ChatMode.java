package nz.co.lolnet.james137137.FactionChat;

import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class ChatMode {
    protected static String FactionName,AllyName,TruceName,AllyTruceName,EnermyName,PublicName;
    protected static HashMap playerChatMode = new HashMap();
    protected static HashMap spyMode = new HashMap();

    protected static void initialize(FactionChat plugin) {
        FileConfiguration config = plugin.getConfig();
        PublicName = FormatString(config.getString("message.ChatModeChange.PublicChat"),null);
        FactionName = FormatString(config.getString("message.ChatModeChange.FactionChat"),null);
        AllyName = FormatString(config.getString("message.ChatModeChange.AllyChat"),null);
        TruceName = FormatString(config.getString("message.ChatModeChange.TruceChat"),null);
        AllyTruceName = FormatString(config.getString("message.ChatModeChange.AllyTruceChat"),null);
        EnermyName = FormatString(config.getString("message.ChatModeChange.EnemyChat"),null);
    }

    
    protected static boolean isSpyOn(Player player) {
        String playerName = player.getName().toLowerCase();
        if (!player.hasPermission("FactionChat.spy") && !FactionChat.isDebugger(player.getName())) {
            return false;
        }
        
        return (Boolean) spyMode.get(playerName);
    }

    protected static void changeSpyMode(Player player) {
        String playerName = player.getName().toLowerCase();
        
        if (player.hasPermission("FactionChat.spy") || FactionChat.isDebugger(player.getName())) {
            if ((Boolean) spyMode.get(playerName)) {
                spyMode.put(playerName, false);
                player.sendMessage(FactionChat.messageSpyModeOff);
            } else {
                spyMode.put(playerName, true);
                player.sendMessage(FactionChat.messageSpyModeOn);
            }
        }

    }

    protected static String getChatMode(Player player) {
        String playerName = player.getName().toLowerCase();
        String chatMode = (String) playerChatMode.get(playerName);
        if (chatMode == null)
        {
            playerChatMode.put(playerName, "PUBLIC");
            return "PUBLIC";
        }
        return chatMode;
    }

    protected static void SetNewChatMode(Player player) {
        String playerName = player.getName().toLowerCase();
        playerChatMode.put(playerName, "PUBLIC");
        
        if (player.hasPermission("FactionChat.spy") && FactionChat.spyModeOnByDefault) {
                spyMode.put(playerName, true);
            } else {
                spyMode.put(playerName, false);
            }

    }

    protected static void NextChatMode(Player player) {
        String playerName = player.getName().toLowerCase();
        String currentChatMode = (String) playerChatMode.get(playerName);
        if (FactionChat.FactionsEnable) {
            if (currentChatMode.equalsIgnoreCase("PUBLIC")) {
                playerChatMode.put(playerName, "ALLY");
                if (FactionChat.AllyChatEnable && player.hasPermission("FactionChat.AllyChat")) {
                    
                    player.sendMessage(FactionChat.messageNewChatMode + AllyName);
                    return;
                }

            }
            if (currentChatMode.equalsIgnoreCase("ALLY") && player.hasPermission("FactionChat.FactionChat")) {
                playerChatMode.put(playerName, "FACTION");
                if (FactionChat.FactionChatEnable) {
                    player.sendMessage(FactionChat.messageNewChatMode + FactionName);
                    return;
                }

            }
            
            if (currentChatMode.equalsIgnoreCase("FACTION") && player.hasPermission("FactionChat.AllyChat")) {
                playerChatMode.put(playerName, "TRUCE");
                if (FactionChat.TruceChatEnable) {
                    player.sendMessage(FactionChat.messageNewChatMode + TruceName);
                    return;
                }

            }
            
            if (currentChatMode.equalsIgnoreCase("TRUCE") && player.hasPermission("FactionChat.AllyChat")) {
                playerChatMode.put(playerName, "ALLY&TRUCE");
                if (FactionChat.AllyTruceChatEnable) {
                    player.sendMessage(FactionChat.messageNewChatMode + AllyTruceName);
                    return;
                }

            }
        }




        playerChatMode.put(playerName, "PUBLIC");        
        player.sendMessage(FactionChat.messageNewChatMode + PublicName);




    }

    protected static void setChatMode(Player player, String input) {
        String playerName = player.getName().toLowerCase();
        if (input.equalsIgnoreCase("PUBLIC") || input.equalsIgnoreCase("P")) {
            playerChatMode.put(playerName, "PUBLIC");
            player.sendMessage(FactionChat.messageNewChatMode +PublicName);
            return;
        }
        if (FactionChat.FactionsEnable) {
            boolean isFactionChat = false;
            if ((input.equalsIgnoreCase("ALLYTRUCE") || input.equalsIgnoreCase("AT"))) {
                isFactionChat = true;
                if (!FactionChat.AllyTruceChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.AllyChat")) {
                    playerChatMode.put(playerName, "ALLY&TRUCE");
                    player.sendMessage(FactionChat.messageNewChatMode + AllyTruceName);
                }

            } else if ((input.equalsIgnoreCase("ALLY") || input.equalsIgnoreCase("A"))) {
                isFactionChat = true;
                if (!FactionChat.AllyChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.AllyChat")) {
                    playerChatMode.put(playerName, "ALLY");
                    player.sendMessage(FactionChat.messageNewChatMode + AllyName);
                }


            } else if ((input.equalsIgnoreCase("TRUCE") || input.equalsIgnoreCase("T"))) {
                isFactionChat = true;
                if (!FactionChat.TruceChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.AllyChat")) {
                    playerChatMode.put(playerName, "TRUCE");
                    player.sendMessage(FactionChat.messageNewChatMode + TruceName);
                }


            } else if ((input.equalsIgnoreCase("FACTION") || input.equalsIgnoreCase("F"))) {
                isFactionChat = true;
                if (!FactionChat.FactionChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.FactionChat")) {
                    playerChatMode.put(playerName, "FACTION");
                    player.sendMessage(FactionChat.messageNewChatMode + FactionName);
                }


            } else if (((player.hasPermission("FactionChat.EnemyChat") || FactionChat.isDebugger(player.getName()))
                    && (input.equalsIgnoreCase("ENEMY") || input.equalsIgnoreCase("E")))) {
                isFactionChat = true;
                if (!FactionChat.EnemyChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                    return;
                }
                playerChatMode.put(playerName, "ENEMY");
                player.sendMessage(FactionChat.messageNewChatMode + EnermyName);

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
            playerChatMode.put(playerName, "UserAssistant");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.DARK_PURPLE + playerChatMode.get(playerName));
        } else if ((player.hasPermission("FactionChat.JrModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("JrMOD")) {
            if (!FactionChat.JrModChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            playerChatMode.put(playerName, "JrMOD");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.BLUE + playerChatMode.get(playerName));
        } else if ((player.hasPermission("FactionChat.ModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("MOD")) {
            if (!FactionChat.ModChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            playerChatMode.put(playerName, "MOD");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.BLUE + playerChatMode.get(playerName));
        } else if ((player.hasPermission("FactionChat.SrModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("SrMOD")) {
            if (!FactionChat.SrModChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            playerChatMode.put(playerName, "SrMOD");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.BLUE + playerChatMode.get(playerName));
        } else if ((player.hasPermission("FactionChat.JrAdminChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("JrADMIN")) {
            if (!FactionChat.JrAdminChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            playerChatMode.put(playerName, "JrADMIN");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.DARK_RED + playerChatMode.get(playerName));
        } else if ((player.hasPermission("FactionChat.AdminChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("ADMIN")) {
            if (!FactionChat.AdminChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            playerChatMode.put(playerName, "ADMIN");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.DARK_RED + playerChatMode.get(playerName));
        } else if ((player.hasPermission("FactionChat.spy") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("SPY")) {
            ChatMode.changeSpyMode(player);
        } else {
            player.sendMessage(FactionChat.messageIncorectChatModeSwitch + " /fc a, /fc f, /fc p, /fc e");
        }

    }

    protected static void setChatMode(Player player, String input, CommandSender sender) {
        String playerName = player.getName().toLowerCase();
        if (input.equalsIgnoreCase("PUBLIC") || input.equalsIgnoreCase("P")) {
            playerChatMode.put(playerName, "PUBLIC");
            player.sendMessage(FactionChat.messageNewChatMode +PublicName);
            return;
        }
        if (FactionChat.FactionsEnable) {
            boolean isFactionChat = false;
            if ((input.equalsIgnoreCase("ALLYTRUCE") || input.equalsIgnoreCase("AT"))) {
                isFactionChat = true;
                if (!FactionChat.AllyTruceChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.AllyChat")) {
                    playerChatMode.put(playerName, "ALLY&TRUCE");
                    player.sendMessage(FactionChat.messageNewChatMode + AllyTruceName);
                }

            } else if ((input.equalsIgnoreCase("ALLY") || input.equalsIgnoreCase("A"))) {
                isFactionChat = true;
                if (!FactionChat.AllyChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.AllyChat")) {
                    playerChatMode.put(playerName, "ALLY");
                    player.sendMessage(FactionChat.messageNewChatMode + AllyName);
                }


            } else if ((input.equalsIgnoreCase("TRUCE") || input.equalsIgnoreCase("T"))) {
                isFactionChat = true;
                if (!FactionChat.TruceChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.AllyChat")) {
                    playerChatMode.put(playerName, "TRUCE");
                    player.sendMessage(FactionChat.messageNewChatMode + TruceName);
                }


            } else if ((input.equalsIgnoreCase("FACTION") || input.equalsIgnoreCase("F"))) {
                isFactionChat = true;
                if (!FactionChat.FactionChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.FactionChat")) {
                    playerChatMode.put(playerName, "FACTION");
                    player.sendMessage(FactionChat.messageNewChatMode + FactionName);
                }


            } else if (((player.hasPermission("FactionChat.EnemyChat") || FactionChat.isDebugger(player.getName()))
                    && (input.equalsIgnoreCase("ENEMY") || input.equalsIgnoreCase("E")))) {
                isFactionChat = true;
                if (!FactionChat.EnemyChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                    return;
                }
                playerChatMode.put(playerName, "ENEMY");
                player.sendMessage(FactionChat.messageNewChatMode + EnermyName);

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
            playerChatMode.put(playerName, "UserAssistant");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.DARK_PURPLE + playerChatMode.get(playerName));
        } else if ((player.hasPermission("FactionChat.JrModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("JrMOD")) {
            if (!FactionChat.JrModChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            playerChatMode.put(playerName, "JrMOD");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.BLUE + playerChatMode.get(playerName));
        } else if ((player.hasPermission("FactionChat.ModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("MOD")) {
            if (!FactionChat.ModChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            playerChatMode.put(playerName, "MOD");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.BLUE + playerChatMode.get(playerName));
        } else if ((player.hasPermission("FactionChat.SrModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("SrMOD")) {
            if (!FactionChat.SrModChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            playerChatMode.put(playerName, "SrMOD");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.BLUE + playerChatMode.get(playerName));
        } else if ((player.hasPermission("FactionChat.JrAdminChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("JrADMIN")) {
            if (!FactionChat.JrAdminChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            playerChatMode.put(playerName, "JrADMIN");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.DARK_RED + playerChatMode.get(playerName));
        } else if ((player.hasPermission("FactionChat.AdminChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("ADMIN")) {
            if (!FactionChat.AdminChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            playerChatMode.put(playerName, "ADMIN");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.DARK_RED + playerChatMode.get(playerName));
        } else if ((player.hasPermission("FactionChat.spy") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("SPY")) {
            ChatMode.changeSpyMode(player);
        } else {
            sender.sendMessage("player doesn't have that permission or incorrect Chat mode name");
        }
    }

    protected static void fixPlayerNotInFaction(Player player) {
        String playerName = player.getName().toLowerCase();
        String chatMode = (String) playerChatMode.get(playerName);
        


        if (!chatMode.equalsIgnoreCase("PUBLIC")) {
            playerChatMode.put(playerName, "PUBLIC");
            player.sendMessage(FactionChat.messageNewChatMode + PublicName);
        }

    }

    protected static String FormatString(String message, String[] args) {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                message = message.replace("{" + i + "}", args[i]);
            }
            message = message.replace("{M}", args[args.length-1]);
        }
        message = message.replaceAll("&", "" + (char) 167);
        return message;
    }
    
    protected static String FormatString(String message, String[] args,boolean allowCostomColour) {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                message = message.replace("{" + i + "}", args[i]);
            }
        }
        message = message.replaceAll("&", "" + (char) 167);
        if (args != null) {
            message = message.replace("{M}", args[args.length - 1]);

            if (allowCostomColour) {
                message = message.replaceAll("&", "" + (char) 167);
            }
        }
        return message;
    }
}
