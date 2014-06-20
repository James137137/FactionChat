/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat.PrefixAndSuffix;

import nz.co.lolnet.james137137.FactionChat.FactionChat;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 *
 * @author James
 */
public class PermissionsEXAPI {

    public PermissionsEXAPI(FactionChat plugin) {
    }

    public static String getPrefix(final Player player) {
        return PermissionsEx.getUser(player).getPrefix();
    }

    public static String getSuffix(final Player player) {
        return PermissionsEx.getUser(player).getSuffix();
    }

}
