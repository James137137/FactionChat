/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat;

import nz.co.lolnet.james137137.FactionChat.API.AuthMeAPI;
import nz.co.lolnet.james137137.FactionChat.API.FactionChatAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class OtherChatChannel {

    private FactionChat factionChat;

    public OtherChatChannel(FactionChat aThis) {
        factionChat = aThis;
    }

    protected void userAssistantChat(Player player, String message) {
        if (!AuthMeAPI.isAllowToChat(player)) {
            return;
        }
        if (Config.limitWorldsChat) {
            if (Config.limitWorldsChatDisableOther && Config.limitWorldsChatDisableSend && !Config.limitWorldsChatWorlds.contains(player.getWorld().getName())) {
                player.sendMessage(ChatColor.RED + "Sorry, but you can't send a FactionChat message in this world");
            }
        }
        message = FactionChatAPI.filterChat(player, message);
        boolean allowCustomColour = player.hasPermission("essentials.chat.color") || player.hasPermission("FactionChat.Chat.Colour");
        String normalMessage = new FactionChatMessage(Config.UAChat, message, allowCustomColour,
                FactionChatAPI.getPrefix(player), player.getName(), FactionChatAPI.getSuffix(player), null, null, null, null).toString();
        for (Player myplayer : Bukkit.getServer().getOnlinePlayers()) {
            if ((myplayer.hasPermission("FactionChat.UserAssistantChat") || FactionChat.isDebugger(myplayer.getName())) && AuthMeAPI.isAllowToChat(myplayer)) {
                myplayer.sendMessage(normalMessage);
            }

        }

    }

    protected void jrModChat(Player player, String message) {
        if (!AuthMeAPI.isAllowToChat(player)) {
            return;
        }
        if (Config.limitWorldsChat) {
            if (Config.limitWorldsChatDisableOther && Config.limitWorldsChatDisableSend && !Config.limitWorldsChatWorlds.contains(player.getWorld().getName())) {
                player.sendMessage(ChatColor.RED + "Sorry, but you can't send a FactionChat message in this world");
            }
        }
        message = FactionChatAPI.filterChat(player, message);
        boolean allowCustomColour = player.hasPermission("essentials.chat.color") || player.hasPermission("FactionChat.Chat.Colour");
        String normalMessage = new FactionChatMessage(Config.UAChat, message, allowCustomColour,
                FactionChatAPI.getPrefix(player), player.getName(), FactionChatAPI.getSuffix(player), null, null, null, null).toString();
        for (Player myplayer : Bukkit.getServer().getOnlinePlayers()) {
            if ((myplayer.hasPermission("FactionChat.JrModChat") || FactionChat.isDebugger(myplayer.getName())) && AuthMeAPI.isAllowToChat(myplayer)) {
                myplayer.sendMessage(normalMessage);
            }

        }

    }

    protected void modChat(Player player, String message) {
        if (!AuthMeAPI.isAllowToChat(player)) {
            return;
        }
        if (Config.limitWorldsChat) {
            if (Config.limitWorldsChatDisableOther && Config.limitWorldsChatDisableSend && !Config.limitWorldsChatWorlds.contains(player.getWorld().getName())) {
                player.sendMessage(ChatColor.RED + "Sorry, but you can't send a FactionChat message in this world");
            }
        }
        message = FactionChatAPI.filterChat(player, message);
        boolean allowCustomColour = player.hasPermission("essentials.chat.color") || player.hasPermission("FactionChat.Chat.Colour");
        String normalMessage = new FactionChatMessage(Config.UAChat, message, allowCustomColour,
                FactionChatAPI.getPrefix(player), player.getName(), FactionChatAPI.getSuffix(player), null, null, null, null).toString();
        for (Player myplayer : Bukkit.getServer().getOnlinePlayers()) {
            if ((myplayer.hasPermission("FactionChat.ModChat") || FactionChat.isDebugger(myplayer.getName())) && AuthMeAPI.isAllowToChat(myplayer)) {
                myplayer.sendMessage(normalMessage);
            }

        }

    }

    protected void SrModChat(Player player, String message) {
        if (!AuthMeAPI.isAllowToChat(player)) {
            return;
        }
        if (Config.limitWorldsChat) {
            if (Config.limitWorldsChatDisableOther && Config.limitWorldsChatDisableSend && !Config.limitWorldsChatWorlds.contains(player.getWorld().getName())) {
                player.sendMessage(ChatColor.RED + "Sorry, but you can't send a FactionChat message in this world");
            }
        }
        message = FactionChatAPI.filterChat(player, message);
        boolean allowCustomColour = player.hasPermission("essentials.chat.color") || player.hasPermission("FactionChat.Chat.Colour");
        String normalMessage = new FactionChatMessage(Config.UAChat, message, allowCustomColour,
                FactionChatAPI.getPrefix(player), player.getName(), FactionChatAPI.getSuffix(player), null, null, null, null).toString();
        for (Player myplayer : Bukkit.getServer().getOnlinePlayers()) {
            if ((myplayer.hasPermission("FactionChat.SrModChat") || FactionChat.isDebugger(myplayer.getName())) && AuthMeAPI.isAllowToChat(myplayer)) {
                myplayer.sendMessage(normalMessage);
            }

        }

    }

    protected void JrAdminChat(Player player, String message) {
        if (!AuthMeAPI.isAllowToChat(player)) {
            return;
        }
        if (Config.limitWorldsChat) {
            if (Config.limitWorldsChatDisableOther && Config.limitWorldsChatDisableSend && !Config.limitWorldsChatWorlds.contains(player.getWorld().getName())) {
                player.sendMessage(ChatColor.RED + "Sorry, but you can't send a FactionChat message in this world");
            }
        }
        message = FactionChatAPI.filterChat(player, message);
        boolean allowCustomColour = player.hasPermission("essentials.chat.color") || player.hasPermission("FactionChat.Chat.Colour");
        String normalMessage = new FactionChatMessage(Config.UAChat, message, allowCustomColour,
                FactionChatAPI.getPrefix(player), player.getName(), FactionChatAPI.getSuffix(player), null, null, null, null).toString();
        for (Player myplayer : Bukkit.getServer().getOnlinePlayers()) {
            if ((myplayer.hasPermission("FactionChat.JrAdminChat") || FactionChat.isDebugger(myplayer.getName())) && AuthMeAPI.isAllowToChat(myplayer)) {
                myplayer.sendMessage(normalMessage);
            }

        }

    }

    protected void adminChat(Player player, String message) {
        if (!AuthMeAPI.isAllowToChat(player)) {
            return;
        }
        if (Config.limitWorldsChat) {
            if (Config.limitWorldsChatDisableOther && Config.limitWorldsChatDisableSend && !Config.limitWorldsChatWorlds.contains(player.getWorld().getName())) {
                player.sendMessage(ChatColor.RED + "Sorry, but you can't send a FactionChat message in this world");
            }
        }
        message = FactionChatAPI.filterChat(player, message);
        boolean allowCustomColour = player.hasPermission("essentials.chat.color") || player.hasPermission("FactionChat.Chat.Colour");
        String normalMessage = new FactionChatMessage(Config.UAChat, message, allowCustomColour,
                FactionChatAPI.getPrefix(player), player.getName(), FactionChatAPI.getSuffix(player), null, null, null, null).toString();
        for (Player myplayer : Bukkit.getServer().getOnlinePlayers()) {
            if ((myplayer.hasPermission("FactionChat.AdminChat") || FactionChat.isDebugger(myplayer.getName())) && AuthMeAPI.isAllowToChat(myplayer)) {
                myplayer.sendMessage(normalMessage);
            }

        }

    }

    public void VIPChat(Player player, String message) {
        if (!AuthMeAPI.isAllowToChat(player)) {
            return;
        }
        if (Config.limitWorldsChat) {
            if (Config.limitWorldsChatDisableOther && Config.limitWorldsChatDisableSend && !Config.limitWorldsChatWorlds.contains(player.getWorld().getName())) {
                player.sendMessage(ChatColor.RED + "Sorry, but you can't send a FactionChat message in this world");
            }
        }
        message = FactionChatAPI.filterChat(player, message);
        boolean allowCustomColour = player.hasPermission("essentials.chat.color") || player.hasPermission("FactionChat.Chat.Colour");
        String normalMessage = new FactionChatMessage(Config.UAChat, message, allowCustomColour,
                FactionChatAPI.getPrefix(player), player.getName(), FactionChatAPI.getSuffix(player), null, null, null, null).toString();
        for (Player myplayer : Bukkit.getServer().getOnlinePlayers()) {
            if ((myplayer.hasPermission("FactionChat.VIPChat") || FactionChat.isDebugger(myplayer.getName())) && AuthMeAPI.isAllowToChat(myplayer)) {
                myplayer.sendMessage(normalMessage);
            }

        }
    }
}
