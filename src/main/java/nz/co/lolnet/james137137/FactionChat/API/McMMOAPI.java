/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat.API;


import com.gmail.nossr50.datatypes.chat.ChatMode;
import nz.co.lolnet.james137137.FactionChat.API.Event.FactionChatPlayerChatEvent;
import nz.co.lolnet.james137137.FactionChat.FactionChat;
import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.util.player.UserManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 *
 * @author James
 */
public class McMMOAPI implements Listener{

    public McMMOAPI(FactionChat plugin) {
        
    }
    
    
    @EventHandler
    public void onPlayerFactionChat(FactionChatPlayerChatEvent event) {
        McMMOPlayer mcMMOPlayer = UserManager.getOfflinePlayer(event.getPlayer());
        if (mcMMOPlayer == null) {
            return;
        }
        if (mcMMOPlayer.isChatEnabled(ChatMode.PARTY) || mcMMOPlayer.isChatEnabled(ChatMode.ADMIN)) {
            event.setCancelled(true);
        }
    }
}
