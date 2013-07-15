/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat;

import static nz.co.lolnet.james137137.FactionChat.FactionChat.FactionsEnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class FactionChatAPI {
    private static FactionChat factionChat;
    private static boolean useFaction2;
    private ChatChannel channel;
    private ChatChannel2 channel2;
    
    public void setupAPI(FactionChat fc)
    {
        factionChat = fc;
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
    
    public String getFactionName(Player player)
    {
        if (useFaction2)
        {
            return channel2.getFactionName(player);
        }
        else
        {
            return channel.getFactionName(player);
        }
    }
    
    public String getChatMode (Player player)
    {
        return ChatMode.getChatMode(player);
    }
}