/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat.PrefixAndSuffix;

import org.bukkit.entity.Player;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 *
 * @author James
 */
public class PermissionsEX implements PrefixAndSuffix {

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public String getPrefix(Player player) {
        return PermissionsEx.getUser(player).getPrefix();
    }

    @Override
    public String getSuffix(Player player) {
        return PermissionsEx.getUser(player).getSuffix();
    }

}
