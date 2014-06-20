/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat;

import static nz.co.lolnet.james137137.FactionChat.FactionChat.FactionsEnable;
import nz.co.lolnet.james137137.FactionChat.PrefixAndSuffix.GroupManagerAPI;
import nz.co.lolnet.james137137.FactionChat.PrefixAndSuffix.PermissionsEXAPI;
import nz.co.lolnet.james137137.FactionChat.PrefixAndSuffix.bPermissionsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author James
 */
public class FactionChatAPI {

    private static FactionChat factionChat;
    private static boolean useFaction2;
    private static ChatChannel channel;
    private static ChatChannel2 channel2;
    private static int pluginToUseForPrefixAndSuffix = 0;
    private static boolean IncludePrefix;
    private static boolean IncludeSuffix;

    public void setupAPI(FactionChat plugin) {
        factionChat = plugin;
        IncludePrefix = plugin.getConfig().getBoolean("FactionChatMessage.IncludePrefix");
        IncludeSuffix = plugin.getConfig().getBoolean("FactionChatMessage.IncludeSuffix");
        setupPreSuffixPlugin(plugin);
    }

    private void setupPreSuffixPlugin(FactionChat plugin) {
        Plugin myPlugin = plugin.getServer().getPluginManager().getPlugin("GroupManager");
        if (myPlugin != null && myPlugin.isEnabled()) {
            pluginToUseForPrefixAndSuffix = 1;
            new GroupManagerAPI(plugin);
            return;
        }
        myPlugin = plugin.getServer().getPluginManager().getPlugin("bPermissions");
        if (myPlugin != null && myPlugin.isEnabled()) {
            pluginToUseForPrefixAndSuffix = 2;
            new bPermissionsAPI(plugin);
            return;
        }
        myPlugin = plugin.getServer().getPluginManager().getPlugin("PermissionsEx");
        if (myPlugin != null && myPlugin.isEnabled()) {
            pluginToUseForPrefixAndSuffix = 3;
            new PermissionsEXAPI(plugin);
            return;
        }
    }

    public FactionChatAPI() {
        if (FactionsEnable) {
            if (Double.parseDouble(Bukkit.getServer().getPluginManager().getPlugin("Factions").getDescription().getVersion().substring(0, 2)) >= 2.0) {
                useFaction2 = true;
                channel2 = new ChatChannel2(factionChat);
            } else {
                useFaction2 = false;
                channel = new ChatChannel(factionChat);
            }
        }
    }

    public static String getFactionName(Player player) {
        if (useFaction2) {
            return ChatChannel2.getFactionName(player);
        } else {
            return ChatChannel.getFactionName(player);
        }
    }

    public static String getChatMode(Player player) {
        return ChatMode.getChatMode(player);
    }

    public static String getPlayerRank(Player player) {
        if (useFaction2) {
            return ChatChannel2.getPlayerRank(player);
        } else {
            return ChatChannel.getPlayerRank(player);
        }
    }

    public static double getDistance(Player playerA, Player playerB) {
        if (playerA == null || playerB == null || !playerA.isOnline() || !playerB.isOnline()) {
            return -1.0;
        }

        double distance;
        Location locationA = playerA.getLocation();
        Location locationB = playerB.getLocation();
        distance = Math.pow(locationA.getX() - locationB.getX(), 2) + Math.pow(locationA.getY() - locationB.getY(), 2) + Math.pow(locationA.getZ() - locationB.getZ(), 2);
        distance = Math.sqrt(distance);

        return distance;
    }

    public static String getPrefix(final Player player) {
        String prefix = "";
        if (IncludePrefix) {
            if (pluginToUseForPrefixAndSuffix == 0) {
                return "";
            } else if (pluginToUseForPrefixAndSuffix == 1) {
                prefix = GroupManagerAPI.getPrefix(player);
            } else if (pluginToUseForPrefixAndSuffix == 2) {
                prefix = bPermissionsAPI.getPrefix(player);
            } else if (pluginToUseForPrefixAndSuffix == 3) {
                prefix = PermissionsEXAPI.getPrefix(player);
            }
            if (prefix != null) {
                prefix = prefix.replaceAll("&", "" + (char) 167);
            } else {
                prefix = "";
            }
        }

        return prefix;
    }

    public static String getSuffix(final Player player) {
        String suffix = "";
        if (IncludeSuffix) {
            if (pluginToUseForPrefixAndSuffix == 0) {
                return "";
            } else if (pluginToUseForPrefixAndSuffix == 1) {
                suffix = GroupManagerAPI.getSuffix(player);
            } else if (pluginToUseForPrefixAndSuffix == 2) {
                suffix = bPermissionsAPI.getSuffix(player);
            } else if (pluginToUseForPrefixAndSuffix == 3) {
                suffix = PermissionsEXAPI.getSuffix(player);
            }
            if (suffix != null) {
                suffix = suffix.replaceAll("&", "" + (char) 167);
            } else {
                suffix = "";
            }
        }

        return suffix;
    }
}
