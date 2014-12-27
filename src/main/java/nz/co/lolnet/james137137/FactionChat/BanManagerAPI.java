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

    public static boolean isMuted(Player player) {
        try {
            return me.confuser.banmanager.BmAPI.isMuted(player);
        } catch (Exception e) {
            try {
                return me.confuser.banmanager.BmAPI.isMuted(player.getName());
            } catch (Exception e2) {
            }
        }
        return false;
    }
}
