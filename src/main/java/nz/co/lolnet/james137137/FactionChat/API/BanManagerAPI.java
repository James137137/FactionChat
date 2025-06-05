/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat.API;

import nz.co.lolnet.james137137.FactionChat.API.Event.FactionChatPlayerChatEvent;
import nz.co.lolnet.james137137.FactionChat.FactionChat;
import nz.co.lolnet.james137137.FactionChat.ChatModeType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 *
 * @author James
 */
public class BanManagerAPI implements Listener {

    private static Boolean useDevBM = false;

    public BanManagerAPI(FactionChat plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        useDevBM = true;
    }

    @EventHandler
    public void onPlayerChat(FactionChatPlayerChatEvent event) {
        if (event.getChatMode() != ChatModeType.PUBLIC && isMuted(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    public static boolean isMuted(Player player) {
        if (useDevBM == null) {
            useDevBM = FactionChat.plugin.getConfig().getBoolean("UseDevBanManager");
        }
        return false;
        /* commented out for now TODO Support this again
        try {
            if (useDevBM) {
                boolean muted = me.confuser.banmanager.BmAPI.isMuted(player);
                return muted;
            } else {
                boolean muted = me.confuser.banmanager.BmAPI.isMuted(player.getName());
                return muted;
            }
        } catch (Exception e) {
            return false;
        }
        */
    }
}