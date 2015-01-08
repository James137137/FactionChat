/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat;

import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class BanManagerAPI {

    static Boolean useDevBM;

    public static boolean isMuted(Player player) {
        if (useDevBM == null) {
            useDevBM = FactionChat.plugin.getConfig().getBoolean("UseDevBanManager");
        }
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
    }
}
