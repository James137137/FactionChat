/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.james137137.factionchat;

import java.util.List;
import net.gravitydevelopment.updater.Updater;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author James
 */
public class Config {

    FactionChat plugin;
    FileConfiguration config;
    private Messages messages;
    
    public List<String> disabledCommands;

    Config(FactionChat plugin) {
        this.plugin = plugin;
        loadConfig();
        setupMessage();
        Updater.AutoUpdateEnable = config.getBoolean("AutoUpdate");

        
        
        disabledCommands = config.getStringList("DisabledCommands");
    }
    

    private void loadConfig() {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
    }

    private void setupMessage() {
        messages = new Messages(plugin.getConfig());
        
    }

    public static class Messages {

        public static String NotInFaction;
        public static String IncorectChatModeSwitch;
        public static String SpyModeOn;
        public static String SpyModeOff;
        public static String NewChatMode;
        public static String FchatoMissType;
        public static String FchatoNoOneOnline;
        public static String PublicMuteChatOn;
        public static String PublicMuteChatOff;
        public static String AllyMuteChatOn;
        public static String AllyMuteChatOff;
        public static String DisableCommand;

        private Messages(FileConfiguration config) {
            NotInFaction = config.getString("Messages.NotInFaction").replaceAll("&", "" + (char) 167);
            IncorectChatModeSwitch = config.getString("Messages.IncorectChatModeSwitch").replaceAll("&", "" + (char) 167);
            SpyModeOn = config.getString("Messages.SpyModeOn").replaceAll("&", "" + (char) 167);
            SpyModeOff = config.getString("Messages.SpyModeOff").replaceAll("&", "" + (char) 167);
            NewChatMode = config.getString("Messages.NewChatMode").replaceAll("&", "" + (char) 167);
            FchatoMissType = config.getString("Messages.FchatoMissType").replaceAll("&", "" + (char) 167);
            FchatoNoOneOnline = config.getString("Messages.FchatoNoOneOnline").replaceAll("&", "" + (char) 167);
            PublicMuteChatOn = config.getString("Messages.PublicMuteChatOn").replaceAll("&", "" + (char) 167);
            PublicMuteChatOff = config.getString("Messages.PublicMuteChatOff").replaceAll("&", "" + (char) 167);
            AllyMuteChatOn = config.getString("Messages.AllyMuteChatOn").replaceAll("&", "" + (char) 167);
            AllyMuteChatOff = config.getString("Messages.AllyMuteChatOff").replaceAll("&", "" + (char) 167);
            DisableCommand = config.getString("Messages.DisableCommand").replaceAll("&", "" + (char) 167);
        }
    }

}
