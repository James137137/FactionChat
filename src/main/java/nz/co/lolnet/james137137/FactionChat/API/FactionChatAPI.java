/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat.API;

import java.util.logging.Level;
import java.util.logging.Logger;
import nz.co.lolnet.james137137.FactionChat.API.chat.ChatFilter;
import nz.co.lolnet.james137137.FactionChat.ChatMode;
import nz.co.lolnet.james137137.FactionChat.FactionChat;
import nz.co.lolnet.james137137.FactionChat.PrefixAndSuffix.PrefixAndSuffix;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author James
 */
public class FactionChatAPI {

    public static ChatFilter chatFilter = null;
    private static FactionChat factionChat;
    private static boolean IncludePrefix;
    private static boolean IncludeSuffix;
    public static PrefixAndSuffix prefixAndSuffix = null;

    public void setupAPI(FactionChat plugin) {
        factionChat = plugin;
        IncludePrefix = plugin.getConfig().getBoolean("FactionChatMessage.IncludePrefix");
        IncludeSuffix = plugin.getConfig().getBoolean("FactionChatMessage.IncludeSuffix");
        setupPreSuffixPlugin(plugin);
    }

    private void setupPreSuffixPlugin(FactionChat plugin) {
        Plugin myPlugin = plugin.getServer().getPluginManager().getPlugin("GroupManager");
        if (myPlugin != null && myPlugin.isEnabled()) {
            try {
                prefixAndSuffix = (PrefixAndSuffix) Class.forName("nz.co.lolnet.james137137.FactionChat.PrefixAndSuffix.GroupManager").getConstructor().newInstance();
                prefixAndSuffix.init();
            } catch (Exception ex) {
                IncludePrefix = false;
                IncludeSuffix = false;
                Logger.getLogger(FactionChatAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }
        myPlugin = plugin.getServer().getPluginManager().getPlugin("bPermissions");
        if (myPlugin != null && myPlugin.isEnabled()) {
            try {
                prefixAndSuffix = (PrefixAndSuffix) Class.forName("nz.co.lolnet.james137137.FactionChat.PrefixAndSuffix.BPermissions").getConstructor().newInstance();
                prefixAndSuffix.init();
            } catch (Exception ex) {
                IncludePrefix = false;
                IncludeSuffix = false;
                Logger.getLogger(FactionChatAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }
        myPlugin = plugin.getServer().getPluginManager().getPlugin("PermissionsEx");
        if (myPlugin != null && myPlugin.isEnabled()) {
            try {
                prefixAndSuffix = (PrefixAndSuffix) Class.forName("nz.co.lolnet.james137137.FactionChat.PrefixAndSuffix.PermissionsEX").getConstructor().newInstance();
                prefixAndSuffix.init();
            } catch (Exception ex) {
                IncludePrefix = false;
                IncludeSuffix = false;
                Logger.getLogger(FactionChatAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }
        if (prefixAndSuffix == null) {
            myPlugin = plugin.getServer().getPluginManager().getPlugin("Vault");
            if (myPlugin != null && myPlugin.isEnabled()) {
                try {
                    prefixAndSuffix = (PrefixAndSuffix) Class.forName("nz.co.lolnet.james137137.FactionChat.PrefixAndSuffix.VaultChat").getConstructor().newInstance();
                    prefixAndSuffix.init();
                } catch (Exception ex) {
                    IncludePrefix = false;
                    IncludeSuffix = false;
                    Logger.getLogger(FactionChatAPI.class.getName()).log(Level.SEVERE, null, ex);
                }
                return;
            }

        }
        if (prefixAndSuffix == null) {
            IncludePrefix = false;
            IncludeSuffix = false;
        }
    }

    public FactionChatAPI() {
    }

    public static String getFactionName(Player player) {

        return factionChat.factionsAPI.getFactionName(player);
    }

    public static String getChatMode(Player player) {
        return ChatMode.getChatMode(player);
    }

    public static String getPlayerRank(Player player) {
        return factionChat.factionsAPI.getPlayerRank(player).toString();
    }

    public static boolean isFactionChatMessage(org.bukkit.event.player.AsyncPlayerChatEvent event) {

        String chatmode = ChatMode.getChatMode(event.getPlayer());
        return !chatmode.equalsIgnoreCase("PUBLIC");
    }

    @Deprecated
    public static boolean isFactionChatMessage(org.bukkit.event.player.PlayerChatEvent event) {

        String chatmode = ChatMode.getChatMode(event.getPlayer());
        return !chatmode.equalsIgnoreCase("PUBLIC");
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
            prefix = prefixAndSuffix.getPrefix(player);
            if (prefix != null) {
                prefix = prefix.replace("&", "" + (char) 167);
            } else {
                prefix = "";
            }
        }

        return prefix;
    }

    public static String getSuffix(final Player player) {
        String suffix = "";
        if (IncludeSuffix) {
            suffix = prefixAndSuffix.getSuffix(player);
            if (suffix != null) {
                suffix = suffix.replace("&", "" + (char) 167);
            } else {
                suffix = "";
            }
        }

        return suffix;
    }

    public static String filterChat(Player player, String message) {
        if (chatFilter != null) {
            return chatFilter.filterMessage(player, message);
        } else {
            return message;
        }
    }
}
