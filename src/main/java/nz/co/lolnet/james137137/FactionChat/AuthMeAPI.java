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

    private static boolean isLoggedIn(Player player) {
        if (enable) {
            boolean result;
            try {
                System.out.println("FactionChat debug: Authenticated: " + fr.xephi.authme.api.NewAPI.getInstance().isAuthenticated(player));
                System.out.println("FactionChat debug: Registered: " + fr.xephi.authme.api.NewAPI.getInstance().isRegistered(player.getName()));
                result = fr.xephi.authme.api.NewAPI.getInstance().isAuthenticated(player) && fr.xephi.authme.api.NewAPI.getInstance().isRegistered(player.getName());
            } catch (Exception e) {
                result = fr.xephi.authme.api.API.isAuthenticated(player) && fr.xephi.authme.api.API.isRegistered(player.getName());
            }
            return result;
        }
        return true;
    }

    public static boolean isAllowToChat(Player player) {
        System.out.println("FactionChat debug: " + player.getDisplayName());
        if (!enable) {
            System.out.println("FactionChat debug: AuthMeAPI Enabled = false");
            return true;
        }
        boolean result = isLoggedIn(player);
        System.out.println("FactionChat debug: AuthMeAPI isLoggedIn = " + result);
        System.out.println("FactionChat debug End: " + player.getDisplayName());
        return result;
    }
}
