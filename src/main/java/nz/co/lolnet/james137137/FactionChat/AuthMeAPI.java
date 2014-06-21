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

    public AuthMeAPI() {
    }
    
    
    public boolean isLoggedIn(Player player)
    {
        try {
            return fr.xephi.authme.api.API.isAuthenticated(player);
        } catch (Exception e) {
        } catch (NoClassDefFoundError e) {
        }
        return true;
    }
    
    public boolean isAllowToChat(Player player) {
        try {
            if (fr.xephi.authme.settings.Settings.isChatAllowed) return true;
            if (isLoggedIn(player)) return true;
            return false;
        } catch (Exception e) {
        } catch (NoClassDefFoundError e) {
        }
        return true;
    }
}
