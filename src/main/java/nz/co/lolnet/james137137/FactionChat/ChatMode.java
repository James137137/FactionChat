package nz.co.lolnet.james137137.FactionChat;

import java.util.HashMap;
import java.util.Objects;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class ChatMode {

    public static boolean mutePublicOptionEnabled;
    protected static String FactionName, AllyName, TruceName, AllyTruceName, EnermyName, LeaderName, OfficerName, PublicName;
    protected static HashMap<String, String> playerChatMode = new HashMap();
    protected static HashMap<String, Boolean> spyMode = new HashMap();
    protected static HashMap<String, Long> lastChat = new HashMap();
    protected static HashMap<String, Boolean> playerMutePublicMode = new HashMap();
    protected static HashMap<String, Boolean> playerMuteAllyMode = new HashMap();
    protected static HashMap<String, Boolean> LocalChat = new HashMap();
    private static long chatTimeLimit;

    protected static void initialize(FactionChat plugin) {
        FileConfiguration config = plugin.getConfig();
        PublicName = FormatString(config.getString("message.ChatModeChange.PublicChat"), null);
        FactionName = FormatString(config.getString("message.ChatModeChange.FactionChat"), null);
        AllyName = FormatString(config.getString("message.ChatModeChange.AllyChat"), null);
        TruceName = FormatString(config.getString("message.ChatModeChange.TruceChat"), null);
        AllyTruceName = FormatString(config.getString("message.ChatModeChange.AllyTruceChat"), null);
        EnermyName = FormatString(config.getString("message.ChatModeChange.EnemyChat"), null);
        LeaderName = FormatString(config.getString("message.ChatModeChange.LeaderChat"), null);
        OfficerName = FormatString(config.getString("message.ChatModeChange.OfficerChat"), null);
        mutePublicOptionEnabled = config.getBoolean("AllowPublicMuteCommand");
        chatTimeLimit = config.getLong("ChatLimit");
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
        if (chatMode == null) {
            playerChatMode.put(playerName, "PUBLIC");
            return "PUBLIC";
        }
        return chatMode;
    }

    public static long getLastChat(String playerName) {
        return lastChat.get(playerName.toLowerCase());
    }
    
    public static void updateLastChat(String playerName) {
        lastChat.put(playerName.toLowerCase(),System.currentTimeMillis());
    }
    
    public static boolean canChat(String playerName) {
        return (System.currentTimeMillis() - getLastChat(playerName) >= chatTimeLimit);
    }
    

    protected static void SetNewChatMode(Player player) {
        String playerName = player.getName().toLowerCase();
        playerMutePublicMode.put(player.getName(), false);
        playerChatMode.put(playerName, "PUBLIC");

        if (player.hasPermission("FactionChat.spy") && FactionChat.spyModeOnByDefault) {
            spyMode.put(playerName, true);
        } else {
            spyMode.put(playerName, false);
        }
        lastChat.put(playerName,System.currentTimeMillis());

    }

    protected static void NextChatMode(Player player) {
        String playerName = player.getName().toLowerCase();
        String currentChatMode = (String) playerChatMode.get(playerName);
        if (FactionChat.PublicMuteDefault && !currentChatMode.equalsIgnoreCase("PUBLIC")) {
            if (!ChatMode.IsPublicMuted(player)) {
                ChatMode.MutePublicOption(player);
            }
        }
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

            if (currentChatMode.equalsIgnoreCase("FACTION") && player.hasPermission("FactionChat.TruceChat")) {
                playerChatMode.put(playerName, "TRUCE");
                if (FactionChat.TruceChatEnable) {
                    player.sendMessage(FactionChat.messageNewChatMode + TruceName);
                    return;
                }

            }

            if (currentChatMode.equalsIgnoreCase("TRUCE") && player.hasPermission("FactionChat.TruceChat") && player.hasPermission("FactionChat.AllyChat")) {
                playerChatMode.put(playerName, "ALLY&TRUCE");
                if (FactionChat.AllyTruceChatEnable) {
                    player.sendMessage(FactionChat.messageNewChatMode + AllyTruceName);
                    return;
                }

            }
        }

        playerChatMode.put(playerName, "PUBLIC");
        player.sendMessage(FactionChat.messageNewChatMode + PublicName);
        if (ChatMode.IsPublicMuted(player)) {
            LocalChat.put(playerName, false);
            ChatMode.MutePublicOption(player);
        }

    }

    protected static void setChatMode(Player player, String input) {
        String playerName = player.getName().toLowerCase();
        if (input.equalsIgnoreCase("PUBLIC") || input.equalsIgnoreCase("P")) {
            playerChatMode.put(playerName, "PUBLIC");
            playerMutePublicMode.put(playerName, false);
            LocalChat.put(playerName, false);
            player.sendMessage(FactionChat.messageNewChatMode + PublicName);
            return;
        }
        if (FactionChat.FactionsEnable) {
            boolean isFactionChat = false;
            if ((input.equalsIgnoreCase("ALLYTRUCE") || input.equalsIgnoreCase("AT"))) {
                isFactionChat = true;
                if (!FactionChat.AllyTruceChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.AllyChat") && player.hasPermission("FactionChat.TruceChat")) {
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
                } else if (player.hasPermission("FactionChat.TruceChat")) {
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

            } else if (FactionChatAPI.getPlayerRank(player).equals(FactionChat.LeaderRank)
                    && (input.equalsIgnoreCase("Leader") || input.equalsIgnoreCase("L"))) {
                isFactionChat = true;
                if (!FactionChat.LeaderChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                    return;
                }
                playerChatMode.put(playerName, "LEADER");
                player.sendMessage(FactionChat.messageNewChatMode + LeaderName);

            } else if ((FactionChatAPI.getPlayerRank(player).equals(FactionChat.LeaderRank)
                    || FactionChatAPI.getPlayerRank(player).equals(FactionChat.OfficerRank))
                    && (input.equalsIgnoreCase("Officer") || input.equalsIgnoreCase("O"))) {
                isFactionChat = true;
                if (!FactionChat.OfficerChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                    return;
                }
                playerChatMode.put(playerName, "OFFICER");
                player.sendMessage(FactionChat.messageNewChatMode + OfficerName);

            }

            if (isFactionChat) {
                return;
            }
        }
        
        if ((player.hasPermission("FactionChat.VIPChat") || FactionChat.isDebugger(player.getName()))
                && (input.equalsIgnoreCase("VIP") || input.equalsIgnoreCase("V"))) {
            if (!FactionChat.VIPChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            playerChatMode.put(playerName, "VIP");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.GOLD + playerChatMode.get(playerName));
        } else if ((player.hasPermission("FactionChat.ModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("MOD")) {
            if (!FactionChat.ModChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            playerChatMode.put(playerName, "MOD");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.BLUE + playerChatMode.get(playerName));
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
        } else if ((player.hasPermission("FactionChat.LocalChat.command"))
                && (input.equalsIgnoreCase("Local") || input.equalsIgnoreCase("loc"))) {
            if (!Objects.equals(LocalChat.get(playerName), Boolean.TRUE)) {
                LocalChat.put(playerName, true);

                player.sendMessage(FactionChat.messageNewChatMode + "LocalChat");
            } else {
                player.sendMessage(ChatColor.RED + "Please use /fc p or /fc g to change back to public(global) chat");
            }

        } else {
            player.sendMessage(FactionChat.messageIncorectChatModeSwitch + " /fc a, /fc f, /fc p, /fc e");
        }

    }

    protected static void setChatMode(Player player, String input, CommandSender sender) {
        String playerName = player.getName().toLowerCase();
        if (input.equalsIgnoreCase("PUBLIC") || input.equalsIgnoreCase("P")) {
            playerChatMode.put(playerName, "PUBLIC");
            playerMutePublicMode.put(playerName, false);
            player.sendMessage(FactionChat.messageNewChatMode + PublicName);
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

            } else if (FactionChatAPI.getPlayerRank(player).equals(FactionChat.LeaderRank)
                    && (input.equalsIgnoreCase("Leader") || input.equalsIgnoreCase("L"))) {
                isFactionChat = true;
                if (!FactionChat.LeaderChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                    return;
                }
                playerChatMode.put(playerName, "LEADER");
                player.sendMessage(FactionChat.messageNewChatMode + LeaderName);

            } else if ((FactionChatAPI.getPlayerRank(player).equals(FactionChat.LeaderRank)
                    || FactionChatAPI.getPlayerRank(player).equals(FactionChat.OfficerRank))
                    && (input.equalsIgnoreCase("Officer") || input.equalsIgnoreCase("O"))) {
                isFactionChat = true;
                if (!FactionChat.OfficerChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                    return;
                }
                playerChatMode.put(playerName, "OFFICER");
                player.sendMessage(FactionChat.messageNewChatMode + OfficerName);

            }

            if (isFactionChat) {
                return;
            }
        }

        if ((player.hasPermission("FactionChat.ModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("MOD")) {
            if (!FactionChat.ModChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            playerChatMode.put(playerName, "MOD");
            player.sendMessage(FactionChat.messageNewChatMode + ChatColor.BLUE + playerChatMode.get(playerName));
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
            playerMutePublicMode.put(playerName, false);
            player.sendMessage(FactionChat.messageNewChatMode + PublicName);
        }

    }

    protected static String FormatString(String message, String[] args) {

        message = message.replaceAll("/&", "/and");
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                message = message.replace("{" + i + "}", args[i]);
            }
            message = message.replace("{M}", args[args.length - 1]);
        }
        message = message.replaceAll("&", "" + (char) 167);
        message = message.replaceAll("/and", "&");
        return message;
    }

    protected static String FormatString(String message, String[] args,String playerTitle,boolean allowCostomColour) {
        message = message.replaceAll("/&", "/and");
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
            if (playerTitle != null)
            {
               message = message.replace("{T}", playerTitle); 
            }
            else
            {
                message = message.replace("{T}", "");
            }
        }
        message = message.replaceAll("/and", "&");
        return message;
    }

    public static void MutePublicOption(Player player) {
        if (!mutePublicOptionEnabled) {
            player.sendMessage(ChatColor.RED + "Sorry this feature is disabled");
            return;
        }
        Boolean isOn;
        if (playerMutePublicMode.get(player.getName()) == null) {
            isOn = false;
        } else {
            isOn = playerMutePublicMode.get(player.getName());
        }

        if (isOn) {
            player.sendMessage(ChatColor.GREEN + FactionChat.messagePublicMuteChatOff);
        } else {
            player.sendMessage(ChatColor.GREEN + FactionChat.messagePublicMuteChatOn);
        }
        playerMutePublicMode.put(player.getName(), !isOn);
    }

    public static boolean IsPublicMuted(Player player) {
        Boolean result = playerMutePublicMode.get(player.getName());
        if (result == null) {
            return false;
        } else {
            return result;
        }
    }

    public static void muteAllyOption(Player player) {
        Boolean isOn;
        if (playerMuteAllyMode.get(player.getName()) == null) {
            isOn = false;
        } else {
            isOn = playerMuteAllyMode.get(player.getName());
        }

        if (isOn) {
            player.sendMessage(ChatColor.GREEN + FactionChat.messageAllyMuteChatOff);
        } else {
            player.sendMessage(ChatColor.GREEN + FactionChat.messageAllyMuteChatOn);
        }
        playerMuteAllyMode.put(player.getName(), !isOn);
    }
    
    public static boolean IsAllyMuted(Player player) {
        Boolean result = playerMuteAllyMode.get(player.getName());
        if (result == null) {
            return false;
        } else {
            return result;
        }
    }
}
