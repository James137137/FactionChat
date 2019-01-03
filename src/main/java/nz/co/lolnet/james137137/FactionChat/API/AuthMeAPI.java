/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat.API;

import nz.co.lolnet.james137137.FactionChat.API.chat.AuthMeFilter;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class AuthMeAPI {

    public static boolean enable;

    public AuthMeAPI(boolean pluginLoaded) {
        enable = pluginLoaded;
        if (pluginLoaded) {
            AuthMeFilter authMeFilter = new AuthMeFilter();
            FactionChatAPI.chatFilter.add(authMeFilter);
            System.out.println("AuthMeAPI for FactionChat is loaded");
        }
    }

    private static boolean isLoggedIn(Player player) {
        if (enable) {
            boolean result = false;
            result = fr.xephi.authme.api.v3.AuthMeApi.getInstance().isAuthenticated(player);
            return result;
        }
        return true;
    }

    public static boolean isAllowToChat(Player player) {
        if (!enable) {
            return true;
        }
        return isLoggedIn(player);
    }
}
