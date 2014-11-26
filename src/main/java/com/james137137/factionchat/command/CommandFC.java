/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.james137137.factionchat.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
class CommandFC {

    public CommandFC(CommandSender sender, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;//get player  
        }
        else
        {
            ConsoleCommandFC(args);
        }
        
    }

    private void ConsoleCommandFC(String[] args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
