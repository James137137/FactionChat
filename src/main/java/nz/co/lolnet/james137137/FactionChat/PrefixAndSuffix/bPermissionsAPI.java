/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nz.co.lolnet.james137137.FactionChat.PrefixAndSuffix;

import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.CalculableType;
import nz.co.lolnet.james137137.FactionChat.FactionChat;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class bPermissionsAPI {

    public bPermissionsAPI(FactionChat plugin) {
    }
    
    public static String getPrefix(final Player player) {
        return ApiLayer.getValue(player.getWorld().getName(), CalculableType.USER, player.getName(), "prefix");
    }
    
    public static String getSuffix(final Player player) {
        return ApiLayer.getValue(player.getWorld().getName(), CalculableType.USER, player.getName(), "suffix");
    }
}
