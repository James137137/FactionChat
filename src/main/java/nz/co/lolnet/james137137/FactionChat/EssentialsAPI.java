/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat;

import com.earth2me.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class EssentialsAPI {

    static boolean enable;
    static Essentials essentials = null;

    EssentialsAPI(boolean aThis) {
        enable = aThis;
        FactionChat.useEssentialsNick = FactionChat.plugin.getConfig().getBoolean("Essentials.useNickName");
        if (enable) {
            essentials = (Essentials) FactionChat.plugin.getServer().getPluginManager().getPlugin("Essentials");
        }
    }

    public static String getNickname(Player player) {
        if (enable && FactionChat.useEssentialsNick) { // this should always be true whnen called.
            return essentials.getUser(player).getNickname();
        }
        Bukkit.getServer().getLogger().warning("FactionChat's EssentialsAPI getNickname method was called when it shouldn't of been");
        return player.getName();
    }

}
