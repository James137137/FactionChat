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
class AuthMeAPI {

    static boolean enable;

    public AuthMeAPI(boolean aThis) {
        enable = aThis;
    }

    public static boolean isLoggedIn(Player player) {
        if (enable) {
            boolean result = false;
            try {
                result = fr.xephi.authme.api.NewAPI.getInstance().isAuthenticated(player);
            } catch (Exception e) {
                result = fr.xephi.authme.api.API.isAuthenticated(player);
            }
            return result;
        }
        return true;
    }

    @Deprecated
    public static boolean isAllowToChat(Player player) {
        if (!enable) {
            return true;
        }
        if (fr.xephi.authme.settings.Settings.isChatAllowed) {
            return true;
        }
        if (isLoggedIn(player)) {
            return true;
        }
        return false;
    }
}
