/*
 * Created by James137137: James Anderson andersonjames5@hotmail.com
 * version 1.26
 */
package com.james137137.FactionChat;

import com.james137137.mcstats.Metrics;
import com.massivecraft.factions.P;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.h31ix.updater.Updater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FactionChat extends JavaPlugin {

    static final Logger log = Logger.getLogger("Minecraft");
    private ChatChannel ChatChannel;
    public static String FactionChatColour,FactionChatMessage, AllyChat,AllyChatMessage,EnemyChat,EnemyChatMessage,OtherFactionChat,OtherFactionChatMessage, ModChat, ModChatMessage, AdminChat, AdminChatMessage;
    public static boolean spyModeOnByDefault = true;
    
    
    //messages
    public static String messageNotInFaction;
    public static String messageIncorectChatModeSwitch;
    public static String messageSpyModeOn;
    public static String messageSpyModeOff;
    public static String messageNewChatMode;
    public static String messageFchatoMisstype;
    public static String messageFchatoNoOneOnline;
    
    
    
    

    @Override
    public void onEnable() {

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
        }
        ChatChannel = new ChatChannel();

        FileConfiguration config = getConfig();

        config.addDefault("AutoUpdate", true);
        config.addDefault("Chat colour.FactionChat", "" + ChatColor.DARK_GREEN);
        config.addDefault("Chat colour.FactionChatMessage", "" + ChatColor.WHITE);
        config.addDefault("Chat colour.AllyChat", "" + ChatColor.GREEN);
        config.addDefault("Chat colour.AllyChatMessage", "" + ChatColor.WHITE);
        config.addDefault("Chat colour.EnemyChat", "" + ChatColor.RED);
        config.addDefault("Chat colour.EnemyChatMessage", "" + ChatColor.WHITE);
        config.addDefault("Chat colour.OtherFactionChat", "" + ChatColor.DARK_PURPLE);
        config.addDefault("Chat colour.OtherFactionMessage", "" + ChatColor.WHITE);
        config.addDefault("Chat colour.ModChat", "" + ChatColor.AQUA);
        config.addDefault("Chat colour.ModChatMessage", "" + ChatColor.GREEN);
        config.addDefault("Chat colour.AdminChat", "" + ChatColor.DARK_RED);
        config.addDefault("Chat colour.AdminChatMessage", "" + ChatColor.GREEN);
        config.addDefault("spyModeOnByDefault", true);
        
        
        
        config.addDefault("MessageLanguage","english");
        //TODO
        /*
         *  Italian, Latin,Spanish,Russian, Chinese, Korean, Portuguese, Japanese 
         */
        config.addDefault("message.english.NotInFaction","You are not member of any faction");
        config.addDefault("message.english.IncorectChatModeSwitch","Error: please use /fc to switch chat mode or");
        config.addDefault("message.english.SpyModeOn","Spy mode is now on");
        config.addDefault("message.english.SpyModeOff","Spy mode is now off");
        if (config.getString("message.english.NewChatMode")!=null && config.getString("message.english.NewChatMode").equals("You chat mode has been changed to: "))
        {
            config.set("message.english.NewChatMode","Your chat mode has been changed to: ");
        }
        config.addDefault("message.english.NewChatMode","Your chat mode has been changed to: ");
        config.addDefault("message.english.FchatoMissType","Error: Please use /fco factionname message.");
        config.addDefault("message.english.FchatoNoOneOnline","Error: either no faction member is online or incorrect faction name");
        
        config.addDefault("message.french.NotInFaction","Vous n'êtes pas membre d'une faction");
        config.addDefault("message.french.IncorectChatModeSwitch","Erreur: s'il vous plaît utilisez /fc pour passer en mode conversation ou");
        config.addDefault("message.french.SpyModeOn","Mode espion est maintenant sur");
        config.addDefault("message.french.SpyModeOff","Mode espion est maintenant éteint");
        config.addDefault("message.french.NewChatMode","Vous tchat mode a été changé en: ");
        config.addDefault("message.french.FchatoMissType","Erreur: S'il vous plaît utiliser /fco factionname message.");
        config.addDefault("message.french.FchatoNoOneOnline","Erreur: soit aucun membre faction est le nom de faction en ligne ou incorrecte");
        
        config.addDefault("message.german.NotInFaction","Sie sind nicht Mitglied einer Fraktion");
        config.addDefault("message.german.IncorectChatModeSwitch","Fehler: Bitte Nutzungsbedingungen /fc Chat-Modus oder schalten ");
        config.addDefault("message.german.SpyModeOn","Spy-Modus ist jetzt auf");
        config.addDefault("message.german.SpyModeOff","Spy-Modus ist jetzt ausgeschaltet");
        config.addDefault("message.german.NewChatMode","Sie Chat-Modus wurde geändert, um: ");
        config.addDefault("message.german.FchatoMissType","Fehler: Bitte Nutzungsbedingungen / fco factionname Nachricht.");
        config.addDefault("message.german.FchatoNoOneOnline","Fehler: entweder keine Partei Mitglied ist online oder falsche Fraktion Namen");
        
        config.addDefault("message.other.NotInFaction","You are not member of any faction");
        config.addDefault("message.other.IncorectChatModeSwitch","Error: please use /fc to switch chat mode or");
        config.addDefault("message.other.SpyModeOn","Spy mode is now on");
        config.addDefault("message.other.SpyModeOff","Spy mode is now off");
        config.addDefault("message.other.NewChatMode","You chat mode has been changed to: ");
        config.addDefault("message.other.FchatoMissType","Error: Please use /fco factionname message.");
        config.addDefault("message.other.FchatoNoOneOnline","Error: either no faction member is online or incorrect faction name");
        
        
        
        
        
        

        config.options().copyDefaults(true);
        saveConfig();
        
        if (config.getBoolean("AutoUpdate"))
        {
            Updater updater = new Updater(this, "factionchat", this.getFile(), Updater.UpdateType.DEFAULT, false);
        }



        getServer().getPluginManager().registerEvents(new FactionChatListener(this), this);
        ChatMode.initialize();
        String version = Bukkit.getServer().getPluginManager().getPlugin("FactionChat").getDescription().getVersion();
        log.log(Level.INFO, "FactionChat:Version {0} enabled", version);
        reload(config);
        // Updater updater = new Updater(this, "factionchat", this.getFile(), Updater.UpdateType.DEFAULT, false); //do not uncomment until added a config file
        // see PM message
        
       

    }

    public void onDisable() {
        log.info("FactionChat: disabled");
    }

    public void reload(FileConfiguration config) {

        reloadConfig();
        FactionChatColour = config.getString("Chat colour.FactionChat");
        FactionChatMessage = config.getString("Chat colour.FactionChatMessage");
        AllyChat = config.getString("Chat colour.AllyChat");
        AllyChatMessage = config.getString("Chat colour.AllyChatMessage");
        EnemyChat = config.getString("Chat colour.EnemyChat");
        EnemyChatMessage = config.getString("Chat colour.EnemyChatMessage");
        OtherFactionChat = config.getString("Chat colour.OtherFactionChat");
        OtherFactionChatMessage = config.getString("Chat colour.OtherFactionChatMessage");
        ModChat = config.getString("Chat colour.ModChat");
        ModChatMessage = config.getString("Chat colour.ModChatMessage");
        AdminChat = config.getString("Chat colour.AdminChat");
        AdminChatMessage = config.getString("Chat colour.AdminChatMessage");
        spyModeOnByDefault = config.getBoolean("spyModeOnByDefault");

        Player[] onlinePlayerList = Bukkit.getServer().getOnlinePlayers();
        for (int i = 0; i < onlinePlayerList.length; i++) {
            ChatMode.SetNewChatMode(onlinePlayerList[i]);
        }
        
        SetMessages(config);


    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        String commandName = command.getName().toLowerCase();
        String[] trimmedArgs = args;
        if (commandName.equalsIgnoreCase("fc") || commandName.equalsIgnoreCase("fchat")) {
            CommandFC(sender, args);
            return true;
        }
        if (commandName.equalsIgnoreCase("fco") || commandName.equalsIgnoreCase("fchato")) {
            if (sender.hasPermission("FactionChat.OtherChat"))
            {
                ChatChannel.fchato(sender, args);
            }
            else
            {
                sender.sendMessage(ChatColor.DARK_RED+"you need the permission: FactionChat.OtherChat to use that");
            }
            
            return true;
        }


        return false;
    }

    public void CommandFC(CommandSender sender, String args[]) {
        Player player = (Player)sender;//get player
        boolean inFaction = true;
        StringTokenizer myStringTokenizer = new StringTokenizer(P.p.getPlayerFactionTag(player), "*[] "); // gets raw infomation of faction tag
        String senderFaction = myStringTokenizer.nextElement().toString();


        if (senderFaction.equalsIgnoreCase("~") && !sender.hasPermission("FactionChat.JrModChat")) {
            //checks if player is in a faction
            //mangaddp juniormoderators FactionChat.JrModChat
            sender.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            inFaction = false;
        }

        if (!inFaction) {
            ChatMode.fixPlayerNotInFaction(player);

            return;
        }
        if (args.length == 0) {
            ChatMode.NextChatMode(player);
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("update") && sender.hasPermission("FactionChat.Update"))
            {
                Updater updater = new Updater(this, "factionchat", this.getFile(), Updater.UpdateType.DEFAULT, false);
            }else if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("FactionChat.reload"))
            {
                reload(this.getConfig());
                sender.sendMessage("Reload Complete");
            }
            else {
              ChatMode.setChatMode(player, args[0]);  
            }
            
        } else {
            player.sendMessage(FactionChat.messageIncorectChatModeSwitch + " /fc a, /fc f, /fc p, fc e");
        }

    }
    
    public static void SetMessages(FileConfiguration config)
    {
        String Language = config.getString("MessageLanguage");
        Language = Language.toLowerCase();
        
      messageNotInFaction = config.getString("message."+Language+".NotInFaction");
      messageIncorectChatModeSwitch = config.getString("message."+Language+".IncorectChatModeSwitch");
      messageSpyModeOn = config.getString("message."+Language+".SpyModeOn");
      messageSpyModeOff = config.getString("message."+Language+".SpyModeOff");
      messageNewChatMode = config.getString("message."+Language+".NewChatMode");
      messageFchatoMisstype = config.getString("message."+Language+".FchatoMissType");
      messageFchatoNoOneOnline = config.getString("message."+Language+".FchatoNoOneOnline");
    
    
    
    
    }
}