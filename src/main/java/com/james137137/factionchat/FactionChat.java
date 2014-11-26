/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.james137137.factionchat;

import com.james137137.factionchat.command.CommandHander;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.gravitydevelopment.updater.Updater;
import net.gravitydevelopment.updater.Updater.UpdateResult;
import net.gravitydevelopment.updater.Updater.UpdateType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author James
 */
public class FactionChat extends JavaPlugin {

    public static final Logger log = Bukkit.getLogger();
    private CommandHander commandHander;
    public Config config;

    @Override
    public void onEnable() {
        config = new Config(this);
        checkForUpdates();
        commandHander = new CommandHander(this);
        
        
        
        
        String version = Bukkit.getServer().getPluginManager().getPlugin(this.getName()).getDescription().getVersion();
        log.log(Level.INFO, "{0}: Version: {1} Enabled.", new Object[]{this.getName(), version});
    }

    @Override
    public void onDisable() {
        log.log(Level.INFO, "{0}: disabled", this.getName());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commandHander.onCommand(sender, command, label, args);
    }

    private void checkForUpdates() {
        if (Updater.AutoUpdateEnable) {
            Updater updater = new Updater(this, 50517, this.getFile(), UpdateType.DEFAULT, true);
            if (updater.getResult() == UpdateResult.SUCCESS) {
                this.getLogger().info("updated to " + updater.getLatestName());
                this.getLogger().info("full plugin reload is required");
            }
        }
    }
    
    @Override
    public void reloadConfig()
    {
        this.config = new Config(this);
    }

}
