/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.james137137.factionchat.command;

import com.james137137.factionchat.Config;
import com.james137137.factionchat.FactionChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author James
 */
public class CommandHander {

    FactionChat plugin;
    public CommandHander(FactionChat aThis) {
        plugin = aThis;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName().toLowerCase();
        if (plugin.config.disabledCommands.contains(commandName)) {
            sender.sendMessage(Config.Messages.DisableCommand);
            return true;
        }
        
        if (commandName.equalsIgnoreCase("fc") || commandName.equalsIgnoreCase("fchat")) {
            new CommandFC(sender, args);
            return true;
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }
    
}
