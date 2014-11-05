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

<<<<<<< HEAD
    static boolean enable;

    public AuthMeAPI(boolean aThis) {
        enable = aThis;
    }

    private static boolean isLoggedIn(Player player) {
        if (enable) {
            return fr.xephi.authme.api.API.isAuthenticated(player);
        }
        return true;
    }

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
=======
    private static boolean enabled;
    public AuthMeAPI(boolean enabled) {
        AuthMeAPI.enabled = enabled;
    }
    
    
    public static boolean isLoggedIn(Player player)
    {
        if (!enabled) return true;
        return fr.xephi.authme.api.API.isRegistered(player.getName()) && fr.xephi.authme.api.API.isAuthenticated(player);
>>>>>>> parent of 60c62c6... Update AuthMe fix
    }
}
