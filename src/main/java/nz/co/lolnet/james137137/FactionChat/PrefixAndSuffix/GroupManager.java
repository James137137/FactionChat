/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat.PrefixAndSuffix;

import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author James
 */
public class GroupManager implements PrefixAndSuffix{

    public static org.anjocaido.groupmanager.GroupManager groupManager;

    @Override
    public boolean init() {
        final Plugin GMplugin = Bukkit.getServer().getPluginManager().getPlugin("GroupManager");
        if (GMplugin != null && GMplugin.isEnabled()) {
            groupManager = (org.anjocaido.groupmanager.GroupManager) GMplugin;
            return true;
        } else
        {
            return false;
        }
    }
    
    @Override
    public String getPrefix(Player player) {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissions(player);
        if (handler == null) {
            return null;
        }
        return handler.getUserPrefix(player.getName());
    }

    @Override
    public String getSuffix(Player player) {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissions(player);
        if (handler == null) {
            return null;
        }
        return handler.getUserSuffix(player.getName());
    }
    
}
