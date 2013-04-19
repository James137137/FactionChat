package com.james137137.FactionChat;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author James
 */
public class FactionChatListener implements Listener {

    private FactionChat FactionChat;
    static final Logger log = Logger.getLogger("Minecraft");

    public FactionChatListener(FactionChat FactionChat) {
        this.FactionChat = FactionChat;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ChatMode.SetNewChatMode(player);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        if (event.isCancelled()) {
            return;
        }



        ChatChannel channel = new ChatChannel(FactionChat);
        Player talkingPlayer = event.getPlayer();
        String msg = event.getMessage();
        //FPlayer me = (FPlayer)FPlayers.i.get(talkingPlayer);
        String chatmode = ChatMode.getChatMode(talkingPlayer);
        if (!chatmode.equalsIgnoreCase("PUBLIC")) {
            if (chatmode.equalsIgnoreCase("ALLY")) {
                channel.fchata(talkingPlayer, msg);
                event.setCancelled(true);

            } else if (chatmode.equalsIgnoreCase("ENEMY")) {
                channel.fchatE(talkingPlayer, msg);
                event.setCancelled(true);
            } else if (chatmode.equalsIgnoreCase("FACTION")) {
                channel.fchat(talkingPlayer, msg);
                event.setCancelled(true);
            } else if (chatmode.equalsIgnoreCase("JrMOD")) {
                channel.jrModChat(talkingPlayer, msg);
                event.setCancelled(true);
            } else if (chatmode.equalsIgnoreCase("MOD")) {
                channel.modChat(talkingPlayer, msg);
                event.setCancelled(true);
            } else if (chatmode.equalsIgnoreCase("SrMOD")) {
                channel.SrModChat(talkingPlayer, msg);
                event.setCancelled(true);
            } else if (chatmode.equalsIgnoreCase("JrAdmin")) {
                channel.JrAdminChat(talkingPlayer, msg);
                event.setCancelled(true);
            } else if (chatmode.equalsIgnoreCase("ADMIN")) {
                channel.adminChat(talkingPlayer, msg);
                event.setCancelled(true);
            }

            log.log(Level.INFO, "[FactionChat] {0}|{1}: {2}", new Object[]{chatmode, talkingPlayer.getName(), msg});
        }


    }
}
