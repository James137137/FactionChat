package nz.co.lolnet.james137137.FactionChat;

import nz.co.lolnet.james137137.FactionChat.API.FactionChatAPI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import nz.co.lolnet.james137137.FactionChat.API.StaffJoinFeature;
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
    protected static HashMap<String, Boolean> spyMode = new HashMap();
    protected static HashMap<String, Long> lastChat = new HashMap();
    protected static HashMap<String, Boolean> playerMutePublicMode = new HashMap();
    protected static HashMap<String, Boolean> playerMuteAllyMode = new HashMap();
    protected static HashMap<String, List<String>> playerMuteList = new HashMap();
    protected static HashMap<String, Boolean> LocalChat = new HashMap();
    private static long chatTimeLimit;
    private static HashMap<String, String> playerChatMode = new HashMap();

    protected static void initialize(FactionChat plugin) {
        FileConfiguration config = plugin.getConfig();
        
        PublicName = new FactionChatMessage(null, config.getString("message.ChatModeChange.PublicChat"), true).toString();
        FactionName = new FactionChatMessage(null, config.getString("message.ChatModeChange.FactionChat"), true).toString();
        AllyName = new FactionChatMessage(null, config.getString("message.ChatModeChange.AllyChat"), true).toString();
        TruceName = new FactionChatMessage(null, config.getString("message.ChatModeChange.TruceChat"), true).toString();
        AllyTruceName = new FactionChatMessage(null, config.getString("message.ChatModeChange.AllyTruceChat"), true, null,null, null, null, null, null, null).toString();
        EnermyName = new FactionChatMessage(null, config.getString("message.ChatModeChange.EnemyChat"), true).toString();
        LeaderName = new FactionChatMessage(null, config.getString("message.ChatModeChange.LeaderChat"), true).toString();
        OfficerName = new FactionChatMessage(null, config.getString("message.ChatModeChange.OfficerChat"), true).toString();
        mutePublicOptionEnabled = config.getBoolean("AllowPublicMuteCommand");
        chatTimeLimit = config.getLong("ChatLimit");
        playerChatMode = new HashMap();
    }

    public static void cleanup(Player player) {
        String playerName = player.getName();
        removePlayerChatMode(playerName);
        spyMode.remove(playerName);
        lastChat.remove(playerName);
        playerMutePublicMode.remove(playerName);
        playerMuteAllyMode.remove(playerName);
        playerMuteList.remove(playerName);
        LocalChat.remove(playerName);
        StaffJoinFeature.clearPlayer(player);
    }

    protected static boolean isSpyOn(Player player) {
        String playerName = player.getName();
        if (!player.hasPermission("FactionChat.spy") && !FactionChat.isDebugger(player.getName())) {
            return false;
        }
        if (spyMode.get(playerName) == null)
        {
            spyMode.put(playerName,Config.spyModeOnByDefault);
        }

        return (Boolean) spyMode.get(playerName);
    }

    protected static void changeSpyMode(Player player) {
        String playerName = player.getName();

        if (player.hasPermission("FactionChat.spy") || FactionChat.isDebugger(player.getName())) {
            if ((Boolean) spyMode.get(playerName)) {
                spyMode.put(playerName, false);
                player.sendMessage(Config.messageSpyModeOff);
            } else {
                spyMode.put(playerName, true);
                player.sendMessage(Config.messageSpyModeOn);
            }
        }

    }

    public static String getChatMode(Player player) {
        String playerName = player.getName();
        String chatMode = (String) getPlayerChatMode(playerName);
        if (chatMode == null) {
            setPlayerChatMode(playerName, "PUBLIC");
            return "PUBLIC";
        }
        return chatMode;
    }

    public static long getLastChat(String playerName) {
        return lastChat.get(playerName);
    }

    public static void updateLastChat(String playerName) {
        lastChat.put(playerName, System.currentTimeMillis());
    }

    public static boolean canChat(String playerName) {
        return (System.currentTimeMillis() - getLastChat(playerName) >= chatTimeLimit);
    }

    protected static void SetNewChatMode(Player player) {
        String playerName = player.getName();
        playerMutePublicMode.put(player.getName(), false);
        setPlayerChatMode(playerName, "PUBLIC");

        if (player.hasPermission("FactionChat.spy") && Config.spyModeOnByDefault) {
            spyMode.put(playerName, true);
        } else {
            spyMode.put(playerName, false);
        }
        lastChat.put(playerName, System.currentTimeMillis());
    }

    protected static void NextChatMode(Player player) {
        String playerName = player.getName();
        String currentChatMode = (String) getPlayerChatMode(playerName);
        if (Config.PublicMuteDefault && !currentChatMode.equalsIgnoreCase("PUBLIC")) {
            if (!ChatMode.IsPublicMuted(player)) {
                ChatMode.MutePublicOption(player);
            }
        }
        if (FactionChat.FactionsEnable) {
            if (currentChatMode.equalsIgnoreCase("PUBLIC")) {
                setPlayerChatMode(playerName, "ALLY");
                if (Config.AllyChatEnable && player.hasPermission("FactionChat.AllyChat")) {

                    player.sendMessage(Config.messageNewChatMode + AllyName);
                    return;
                }

            }
            if (currentChatMode.equalsIgnoreCase("ALLY") && player.hasPermission("FactionChat.FactionChat")) {
                setPlayerChatMode(playerName, "FACTION");
                if (Config.FactionChatEnable) {
                    player.sendMessage(Config.messageNewChatMode + FactionName);
                    return;
                }

            }

            if (currentChatMode.equalsIgnoreCase("FACTION") && player.hasPermission("FactionChat.TruceChat")) {
                setPlayerChatMode(playerName, "TRUCE");
                if (Config.TruceChatEnable) {
                    player.sendMessage(Config.messageNewChatMode + TruceName);
                    return;
                }

            }

            if (currentChatMode.equalsIgnoreCase("TRUCE") && player.hasPermission("FactionChat.TruceChat") && player.hasPermission("FactionChat.AllyChat")) {
                setPlayerChatMode(playerName, "ALLY&TRUCE");
                if (Config.AllyTruceChatEnable) {
                    player.sendMessage(Config.messageNewChatMode + AllyTruceName);
                    return;
                }

            }
        }

        setPlayerChatMode(playerName, "PUBLIC");
        player.sendMessage(Config.messageNewChatMode + PublicName);
        if (ChatMode.IsPublicMuted(player)) {
            LocalChat.put(playerName, false);
            ChatMode.MutePublicOption(player);
        }

    }

    protected static void setChatMode(Player player, String input) {
        String playerName = player.getName();
        if (input.equalsIgnoreCase("PUBLIC") || input.equalsIgnoreCase("P")) {
            setPlayerChatMode(playerName, "PUBLIC");
            playerMutePublicMode.put(playerName, false);
            LocalChat.put(playerName, false);
            player.sendMessage(Config.messageNewChatMode + PublicName);
            return;
        }
        if (FactionChat.FactionsEnable) {
            boolean isFactionChat = false;
            if ((input.equalsIgnoreCase("ALLYTRUCE") || input.equalsIgnoreCase("AT"))) {
                isFactionChat = true;
                if (!Config.AllyTruceChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.AllyChat") && player.hasPermission("FactionChat.TruceChat")) {
                    setPlayerChatMode(playerName, "ALLY&TRUCE");
                    player.sendMessage(Config.messageNewChatMode + AllyTruceName);
                }

            } else if ((input.equalsIgnoreCase("ALLY") || input.equalsIgnoreCase("A"))) {
                isFactionChat = true;
                if (!Config.AllyChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.AllyChat")) {
                    setPlayerChatMode(playerName, "ALLY");
                    player.sendMessage(Config.messageNewChatMode + AllyName);
                }

            } else if ((input.equalsIgnoreCase("TRUCE") || input.equalsIgnoreCase("T"))) {
                isFactionChat = true;
                if (!Config.TruceChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.TruceChat")) {
                    setPlayerChatMode(playerName, "TRUCE");
                    player.sendMessage(Config.messageNewChatMode + TruceName);
                }

            } else if ((input.equalsIgnoreCase("FACTION") || input.equalsIgnoreCase("F"))) {
                isFactionChat = true;
                if (!Config.FactionChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.FactionChat")) {
                    setPlayerChatMode(playerName, "FACTION");
                    player.sendMessage(Config.messageNewChatMode + FactionName);
                }

            } else if (((player.hasPermission("FactionChat.EnemyChat") || FactionChat.isDebugger(player.getName()))
                    && (input.equalsIgnoreCase("ENEMY") || input.equalsIgnoreCase("E")))) {
                isFactionChat = true;
                if (!Config.EnemyChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                    return;
                }
                setPlayerChatMode(playerName, "ENEMY");
                player.sendMessage(Config.messageNewChatMode + EnermyName);

            } else if (FactionChatAPI.getPlayerRank(player).equals(Config.LeaderRank)
                    && (input.equalsIgnoreCase("Leader") || input.equalsIgnoreCase("L"))) {
                isFactionChat = true;
                if (!Config.LeaderChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                    return;
                }
                setPlayerChatMode(playerName, "LEADER");
                player.sendMessage(Config.messageNewChatMode + LeaderName);

            } else if ((FactionChatAPI.getPlayerRank(player).equals(Config.LeaderRank)
                    || FactionChatAPI.getPlayerRank(player).equals(Config.OfficerRank))
                    && (input.equalsIgnoreCase("Officer") || input.equalsIgnoreCase("O"))) {
                isFactionChat = true;
                if (!Config.OfficerChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                    return;
                }
                setPlayerChatMode(playerName, "OFFICER");
                player.sendMessage(Config.messageNewChatMode + OfficerName);

            }

            if (isFactionChat) {
                return;
            }
        }

        if ((player.hasPermission("FactionChat.VIPChat") || FactionChat.isDebugger(player.getName()))
                && (input.equalsIgnoreCase("VIP") || input.equalsIgnoreCase("V"))) {
            if (!Config.VIPChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            setPlayerChatMode(playerName, "VIP");
            player.sendMessage(Config.messageNewChatMode + ChatColor.GOLD + getPlayerChatMode(playerName));
        } else if ((player.hasPermission("FactionChat.UserAssistantChat") || FactionChat.isDebugger(player.getName()))
                && (input.equalsIgnoreCase("UA") || input.equalsIgnoreCase("UserAssistant"))) {
            if (!Config.UAChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            setPlayerChatMode(playerName, "UserAssistant");
            player.sendMessage(Config.messageNewChatMode + ChatColor.DARK_PURPLE + getPlayerChatMode(playerName));
        } else if ((player.hasPermission("FactionChat.JrModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("JrMOD")) {
            if (!Config.JrModChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            setPlayerChatMode(playerName, "JrMOD");
            player.sendMessage(Config.messageNewChatMode + ChatColor.BLUE + getPlayerChatMode(playerName));
        } else if ((player.hasPermission("FactionChat.ModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("MOD")) {
            if (!Config.ModChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            setPlayerChatMode(playerName, "MOD");
            player.sendMessage(Config.messageNewChatMode + ChatColor.BLUE + getPlayerChatMode(playerName));
        } else if ((player.hasPermission("FactionChat.SrModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("SrMOD")) {
            if (!Config.SrModChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            setPlayerChatMode(playerName, "SrMOD");
            player.sendMessage(Config.messageNewChatMode + ChatColor.BLUE + getPlayerChatMode(playerName));
        } else if ((player.hasPermission("FactionChat.JrAdminChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("JrADMIN")) {
            if (!Config.JrAdminChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            setPlayerChatMode(playerName, "JrADMIN");
            player.sendMessage(Config.messageNewChatMode + ChatColor.DARK_RED + getPlayerChatMode(playerName));
        } else if ((player.hasPermission("FactionChat.AdminChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("ADMIN")) {
            if (!Config.AdminChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            setPlayerChatMode(playerName, "ADMIN");
            player.sendMessage(Config.messageNewChatMode + ChatColor.DARK_RED + getPlayerChatMode(playerName));
        } else if ((player.hasPermission("FactionChat.spy") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("SPY")) {
            ChatMode.changeSpyMode(player);
        } else if ((player.hasPermission("FactionChat.LocalChat.command"))
                && (input.equalsIgnoreCase("Local") || input.equalsIgnoreCase("loc"))) {
            if (!Objects.equals(LocalChat.get(playerName), Boolean.TRUE)) {
                LocalChat.put(playerName, true);

                player.sendMessage(Config.messageNewChatMode + "LocalChat");
            } else {
                player.sendMessage(ChatColor.RED + "Please use /fc p or /fc g to change back to public(global) chat");
            }

        } else {
            player.sendMessage(Config.messageIncorectChatModeSwitch + " /fc a, /fc f, /fc p, /fc e");
        }

    }

    protected static void setChatMode(Player player, String input, CommandSender sender) {
        String playerName = player.getName();
        if (input.equalsIgnoreCase("PUBLIC") || input.equalsIgnoreCase("P")) {
            setPlayerChatMode(playerName, "PUBLIC");
            playerMutePublicMode.put(playerName, false);
            player.sendMessage(Config.messageNewChatMode + PublicName);
            return;
        }
        if (FactionChat.FactionsEnable) {
            boolean isFactionChat = false;
            if ((input.equalsIgnoreCase("ALLYTRUCE") || input.equalsIgnoreCase("AT"))) {
                isFactionChat = true;
                if (!Config.AllyTruceChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.AllyChat")) {
                    setPlayerChatMode(playerName, "ALLY&TRUCE");
                    player.sendMessage(Config.messageNewChatMode + AllyTruceName);
                }

            } else if ((input.equalsIgnoreCase("ALLY") || input.equalsIgnoreCase("A"))) {
                isFactionChat = true;
                if (!Config.AllyChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.AllyChat")) {
                    setPlayerChatMode(playerName, "ALLY");
                    player.sendMessage(Config.messageNewChatMode + AllyName);
                }

            } else if ((input.equalsIgnoreCase("TRUCE") || input.equalsIgnoreCase("T"))) {
                isFactionChat = true;
                if (!Config.TruceChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.AllyChat")) {
                    setPlayerChatMode(playerName, "TRUCE");
                    player.sendMessage(Config.messageNewChatMode + TruceName);
                }

            } else if ((input.equalsIgnoreCase("FACTION") || input.equalsIgnoreCase("F"))) {
                isFactionChat = true;
                if (!Config.FactionChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                } else if (player.hasPermission("FactionChat.FactionChat")) {
                    setPlayerChatMode(playerName, "FACTION");
                    player.sendMessage(Config.messageNewChatMode + FactionName);
                }

            } else if (((player.hasPermission("FactionChat.EnemyChat") || FactionChat.isDebugger(player.getName()))
                    && (input.equalsIgnoreCase("ENEMY") || input.equalsIgnoreCase("E")))) {
                isFactionChat = true;
                if (!Config.EnemyChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                    return;
                }
                setPlayerChatMode(playerName, "ENEMY");
                player.sendMessage(Config.messageNewChatMode + EnermyName);

            } else if (FactionChatAPI.getPlayerRank(player).equals(Config.LeaderRank)
                    && (input.equalsIgnoreCase("Leader") || input.equalsIgnoreCase("L"))) {
                isFactionChat = true;
                if (!Config.LeaderChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                    return;
                }
                setPlayerChatMode(playerName, "LEADER");
                player.sendMessage(Config.messageNewChatMode + LeaderName);

            } else if ((FactionChatAPI.getPlayerRank(player).equals(Config.LeaderRank)
                    || FactionChatAPI.getPlayerRank(player).equals(Config.OfficerRank))
                    && (input.equalsIgnoreCase("Officer") || input.equalsIgnoreCase("O"))) {
                isFactionChat = true;
                if (!Config.OfficerChatEnable) {
                    player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                    return;
                }
                setPlayerChatMode(playerName, "OFFICER");
                player.sendMessage(Config.messageNewChatMode + OfficerName);

            }

            if (isFactionChat) {
                return;
            }
        }

        if ((player.hasPermission("FactionChat.UserAssistantChat") || FactionChat.isDebugger(player.getName()))
                && (input.equalsIgnoreCase("UA") || input.equalsIgnoreCase("UserAssistant"))) {
            if (!Config.UAChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            setPlayerChatMode(playerName, "UserAssistant");
            player.sendMessage(Config.messageNewChatMode + ChatColor.DARK_PURPLE + getPlayerChatMode(playerName));
        } else if ((player.hasPermission("FactionChat.JrModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("JrMOD")) {
            if (!Config.JrModChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            setPlayerChatMode(playerName, "JrMOD");
            player.sendMessage(Config.messageNewChatMode + ChatColor.BLUE + getPlayerChatMode(playerName));
        } else if ((player.hasPermission("FactionChat.ModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("MOD")) {
            if (!Config.ModChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            setPlayerChatMode(playerName, "MOD");
            player.sendMessage(Config.messageNewChatMode + ChatColor.BLUE + getPlayerChatMode(playerName));
        } else if ((player.hasPermission("FactionChat.SrModChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("SrMOD")) {
            if (!Config.SrModChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            setPlayerChatMode(playerName, "SrMOD");
            player.sendMessage(Config.messageNewChatMode + ChatColor.BLUE + getPlayerChatMode(playerName));
        } else if ((player.hasPermission("FactionChat.JrAdminChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("JrADMIN")) {
            if (!Config.JrAdminChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            setPlayerChatMode(playerName, "JrADMIN");
            player.sendMessage(Config.messageNewChatMode + ChatColor.DARK_RED + getPlayerChatMode(playerName));
        } else if ((player.hasPermission("FactionChat.AdminChat") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("ADMIN")) {
            if (!Config.AdminChatEnable) {
                player.sendMessage(ChatColor.RED + "Sorry this chat mode is disabled");
                return;
            }
            setPlayerChatMode(playerName, "ADMIN");
            player.sendMessage(Config.messageNewChatMode + ChatColor.DARK_RED + getPlayerChatMode(playerName));
        } else if ((player.hasPermission("FactionChat.spy") || FactionChat.isDebugger(player.getName()))
                && input.equalsIgnoreCase("SPY")) {
            ChatMode.changeSpyMode(player);
        } else {
            sender.sendMessage("player doesn't have that permission or incorrect Chat mode name");
        }
    }

    protected static void fixPlayerNotInFaction(Player player) {
        String playerName = player.getName();
        String chatMode = (String) getPlayerChatMode(playerName);

        if (!chatMode.equalsIgnoreCase("PUBLIC")) {
            setPlayerChatMode(playerName, "PUBLIC");
            playerMutePublicMode.put(playerName, false);
            player.sendMessage(Config.messageNewChatMode + PublicName);
        }

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
            player.sendMessage(ChatColor.GREEN + Config.messagePublicMuteChatOff);
        } else {
            player.sendMessage(ChatColor.GREEN + Config.messagePublicMuteChatOn);
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
            player.sendMessage(ChatColor.GREEN + Config.messageAllyMuteChatOff);
        } else {
            player.sendMessage(ChatColor.GREEN + Config.messageAllyMuteChatOn);
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

    static void mutePlayerOption(Player player, String playerToMute, boolean mute) {
        List<String> muteList = playerMuteList.get(player.getName());
        if (muteList == null) {
            muteList = new ArrayList<>();
        }
        Player player1 = FactionChat.plugin.getServer().getPlayer(playerToMute);
        if (player1 == null || !player1.isOnline()) {
            player.sendMessage(ChatColor.RED + playerToMute + " is not online.");
            return;
        }
        if (mute) {
            if (!muteList.contains(playerToMute)) {
                muteList.add(playerToMute);
                player.sendMessage(ChatColor.YELLOW + playerToMute + ChatColor.GREEN + " has been muted.");
            } else {
                player.sendMessage(ChatColor.RED + playerToMute + " is already muted. Use /fc unmute PlayerName to unmute");
            }

        } else {
            if (muteList.contains(playerToMute)) {
                muteList.remove(playerToMute);
                player.sendMessage(ChatColor.YELLOW + playerToMute + ChatColor.GREEN + " has been unmuted.");
            } else {
                player.sendMessage(ChatColor.RED + playerToMute + " is not muted.");
            }
        }
        playerMuteList.put(player.getName(), muteList);
    }

    public static boolean IsPlayerMutedTarget(Player player, Player target) {
        List<String> muteList = playerMuteList.get(player.getName());
        if (muteList != null) {
            return muteList.contains(target.getName());
        }
        return false;
    }

    public static String getPlayerChatMode(String playerName) {
        return playerChatMode.get(playerName);
    }

    public static void removePlayerChatMode(String playerName) {
        playerChatMode.remove(playerName);
    }

    public static void setPlayerChatMode(String playerName, String chatMode) {
        playerChatMode.put(playerName, chatMode);
    }

    
}
