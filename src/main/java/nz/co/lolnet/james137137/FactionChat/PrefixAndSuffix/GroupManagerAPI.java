/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat.PrefixAndSuffix;

import nz.co.lolnet.james137137.FactionChat.FactionChat;
import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author James
 */
public class GroupManagerAPI {

    public static GroupManager groupManager;

    public GroupManagerAPI(FactionChat plugin) {

        final Plugin GMplugin = plugin.getServer().getPluginManager().getPlugin("GroupManager");
        if (GMplugin != null && GMplugin.isEnabled()) {
            groupManager = (GroupManager) GMplugin;
        }
    }

    public static String getPrefix(final Player player) {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissions(player);
        if (handler == null) {
            return null;
        }
        return handler.getUserPrefix(player.getName());
    }

    public static String getSuffix(final Player player) {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissions(player);
        if (handler == null) {
            return null;
        }
        return handler.getUserSuffix(player.getName());
    }
}
