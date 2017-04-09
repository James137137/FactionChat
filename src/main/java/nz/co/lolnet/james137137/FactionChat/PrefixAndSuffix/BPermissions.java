/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nz.co.lolnet.james137137.FactionChat.PrefixAndSuffix;

import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.CalculableType;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class BPermissions implements PrefixAndSuffix{
    
    @Override
    public boolean init() {
        return true;
    }
    @Override
    public String getPrefix(Player player) {
        return ApiLayer.getValue(player.getWorld().getName(), CalculableType.USER, player.getName(), "prefix");
    }

    @Override
    public String getSuffix(Player player) {
        return ApiLayer.getValue(player.getWorld().getName(), CalculableType.USER, player.getName(), "suffix");
    }
    
    
}
