/*
 * Created by James137137: James Anderson andersonjames5@hotmail.com
 * version 1.294
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
    private static ChatChannel ChatChannel;
    public static String FactionChatColour, FactionChatMessage, AllyChat, AllyChatMessage, EnemyChat, EnemyChatMessage, OtherFactionChat, OtherFactionChatMessage, ModChat, ModChatMessage, AdminChat, AdminChatMessage;
    public static boolean spyModeOnByDefault = true;
    //messages for Chat colour. Theses are customiziable in conf file.
    public static String messageNotInFaction;
    public static String messageIncorectChatModeSwitch;
    public static String messageSpyModeOn;
    public static String messageSpyModeOff;
    public static String messageNewChatMode;
    public static String messageFchatoMisstype;
    public static String messageFchatoNoOneOnline;
    public static boolean ServerAllowAuthorDebugging;
    public static boolean FactionChatEnable,AllyChatEnable,EnemyChatEnable,OtherChatEnable,
            ModChatEnable,AdminChatEnable,JrModChatEnable,SrModChatEnable,JrAdminChatEnable;

    @Override
    public void onEnable() {

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
            log.log(Level.INFO, "[{0}] Metrics: Failed to submit the stats", this.getName());
        }

        ChatChannel = new ChatChannel(this); // insures that ChatChannel Class has been defined


        // Start of Configuration

        FileConfiguration config = getConfig();

        config.addDefault("AutoUpdate", true);
        config.addDefault("spyModeOnByDefault", false);
        config.addDefault("AllowAuthorDebugAccess", true); //author is james137137 - Server must be online mode
        
        config.addDefault("FactionChatEnable",true);
        config.addDefault("AllyChatEnable",true);
        config.addDefault("EnemyChatEnable",true);
        config.addDefault("OtherChatEnable",true);
        config.addDefault("ModChatEnable",true);
        config.addDefault("AdminChatEnable",true);
        config.addDefault("JrModChatEnable",true);
        config.addDefault("SrModChatEnable",true);
        config.addDefault("JrAdminChatEnable",true);

        ServerAllowAuthorDebugging = getServer().getOnlineMode() && config.getBoolean("AllowAuthorDebugAccess");

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
        config.addDefault("Chat colour.OtherFactionChatMessage", "" + ChatColor.WHITE);



        config.addDefault("MessageLanguage", "english");
        //TODO
        /*
         *  Italian, Latin,Spanish,Russian, Chinese, Korean, Portuguese, Japanese 
         */

        //begining of Translations
        config.addDefault("message.english.NotInFaction", "You are not member of any faction");
        config.addDefault("message.english.IncorectChatModeSwitch", "Error: please use /fc to switch chat mode or");
        config.addDefault("message.english.SpyModeOn", "Spy mode is now on");
        config.addDefault("message.english.SpyModeOff", "Spy mode is now off");
        //this was added due to a spelling error.
        if (config.getString("message.english.NewChatMode") != null && config.getString("message.english.NewChatMode").equals("You chat mode has been changed to: ")) {
            config.set("message.english.NewChatMode", "Your chat mode has been changed to: ");
        }
        config.addDefault("message.english.NewChatMode", "Your chat mode has been changed to: ");
        config.addDefault("message.english.FchatoMissType", "Error: Please use /fco factionname message.");
        config.addDefault("message.english.FchatoNoOneOnline", "Error: either no faction member is online or incorrect faction name");

        config.addDefault("message.french.NotInFaction", "Vous n'êtes pas membre d'une faction");
        config.addDefault("message.french.IncorectChatModeSwitch", "Erreur: s'il vous plaît utilisez /fc pour passer en mode conversation ou");
        config.addDefault("message.french.SpyModeOn", "Mode espion est maintenant sur");
        config.addDefault("message.french.SpyModeOff", "Mode espion est maintenant éteint");
        config.addDefault("message.french.NewChatMode", "Vous tchat mode a été changé en: ");
        config.addDefault("message.french.FchatoMissType", "Erreur: S'il vous plaît utiliser /fco factionname message.");
        config.addDefault("message.french.FchatoNoOneOnline", "Erreur: soit aucun membre faction est le nom de faction en ligne ou incorrecte");

        config.addDefault("message.german.NotInFaction", "Sie sind nicht Mitglied einer Fraktion");
        config.addDefault("message.german.IncorectChatModeSwitch", "Fehler: Bitte Nutzungsbedingungen /fc Chat-Modus oder schalten ");
        config.addDefault("message.german.SpyModeOn", "Spy-Modus ist jetzt auf");
        config.addDefault("message.german.SpyModeOff", "Spy-Modus ist jetzt ausgeschaltet");
        config.addDefault("message.german.NewChatMode", "Sie Chat-Modus wurde geändert, um: ");
        config.addDefault("message.german.FchatoMissType", "Fehler: Bitte Nutzungsbedingungen / fco factionname Nachricht.");
        config.addDefault("message.german.FchatoNoOneOnline", "Fehler: entweder keine Partei Mitglied ist online oder falsche Fraktion Namen");

        config.addDefault("message.other.NotInFaction", "You are not member of any faction");
        config.addDefault("message.other.IncorectChatModeSwitch", "Error: please use /fc to switch chat mode or");
        config.addDefault("message.other.SpyModeOn", "Spy mode is now on");
        config.addDefault("message.other.SpyModeOff", "Spy mode is now off");
        config.addDefault("message.other.NewChatMode", "You chat mode has been changed to: ");
        config.addDefault("message.other.FchatoMissType", "Error: Please use /fco factionname message.");
        config.addDefault("message.other.FchatoNoOneOnline", "Error: either no faction member is online or incorrect faction name");







        config.options().copyDefaults(true);
        saveConfig();

        //end of Configuration

        if (config.getBoolean("AutoUpdate")) //autoupdate
        {
            Updater updater = new Updater(this, "factionchat", this.getFile(), Updater.UpdateType.DEFAULT, false);
        }



        getServer().getPluginManager().registerEvents(new FactionChatListener(this), this); //FactionChat's Listener
        ChatMode.initialize();
        String version = Bukkit.getServer().getPluginManager().getPlugin(this.getName()).getDescription().getVersion();
        log.log(Level.INFO, "{0}: Version: {1} Enabled.", new Object[]{this.getName(), version});
        reload(config);
        // Updater updater = new Updater(this, "factionchat", this.getFile(), Updater.UpdateType.DEFAULT, false); //do not uncomment until added a config file
        // see PM message



    }

    @Override
    public void onDisable() {
        log.log(Level.INFO, "{0}: disabled", this.getName());
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
        
        FactionChatEnable = config.getBoolean("FactionChatEnable");
        AllyChatEnable = config.getBoolean("AllyChatEnable");
        EnemyChatEnable=config.getBoolean("EnemyChatEnable");
        OtherChatEnable=config.getBoolean("OtherChatEnable");
        ModChatEnable=config.getBoolean("ModChatEnable");
        AdminChatEnable=config.getBoolean("AdminChatEnable");
        JrModChatEnable=config.getBoolean("JrModChatEnable");
        SrModChatEnable=config.getBoolean("SrModChatEnable");
        JrAdminChatEnable=config.getBoolean("JrAdminChatEnable");

        Player[] onlinePlayerList = Bukkit.getServer().getOnlinePlayers();
        for (int i = 0; i < onlinePlayerList.length; i++) {
            ChatMode.SetNewChatMode(onlinePlayerList[i]);
        }

        SetMessages(config);


    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        String commandName = command.getName().toLowerCase();
        if (commandName.equalsIgnoreCase("fc") || commandName.equalsIgnoreCase("fchat")) {
            CommandFC(sender, args);
            return true;
        }
        if (commandName.equalsIgnoreCase("fco") || commandName.equalsIgnoreCase("fchato")) {
            if ((sender.hasPermission("FactionChat.OtherChat") || FactionChat.isDebugger(sender.getName()))
                    && OtherChatEnable) {
                ChatChannel.fchato(sender, args);
            } else {
                sender.sendMessage(ChatColor.DARK_RED + "you need the permission to use that");
            }

            return true;
        }
        if ((commandName.equalsIgnoreCase("ff") || commandName.equalsIgnoreCase("fchatf")) 
                && FactionChatEnable) {
            if (args.length == 0) {
                return false;
            }
            ChatChannel channel = new ChatChannel(this);
            Player talkingPlayer = (Player) sender;
            String message = "";
            for (int i = 0; i < args.length; i++) {
                message += args[i] + " ";
            }
            channel.fchat(talkingPlayer, message);
            return true;
        }
        if ((commandName.equalsIgnoreCase("fa") || commandName.equalsIgnoreCase("fchata"))
                && AllyChatEnable) {
            if (args.length == 0) {
                return false;
            }
            ChatChannel channel = new ChatChannel(this);
            Player talkingPlayer = (Player) sender;
            String message = "";
            for (int i = 0; i < args.length; i++) {
                message += args[i] + " ";
            }
            channel.fchata(talkingPlayer, message);
            return true;
        }
        if (((commandName.equalsIgnoreCase("fe") || commandName.equalsIgnoreCase("fchate")) && sender.hasPermission(FactionChat.EnemyChat))
                && EnemyChatEnable) {

            if (args.length == 0) {
                return false;
            }
            ChatChannel channel = new ChatChannel(this);
            Player talkingPlayer = (Player) sender;
            String message = "";
            for (int i = 0; i < args.length; i++) {
                message += args[i] + " ";
            }
            channel.fchatE(talkingPlayer, message);
            return true;
        }
        if ((commandName.equalsIgnoreCase("fad") || commandName.equalsIgnoreCase("fchatad"))
                && AdminChatEnable) {
            if (args.length == 0) {
                return false;
            }
            ChatChannel channel = new ChatChannel(this);
            Player talkingPlayer = (Player) sender;
            String message = "";
            for (int i = 0; i < args.length; i++) {
                message += args[i] + " ";
            }
            channel.adminChat(talkingPlayer, message);
            return true;
        }
        if (((commandName.equalsIgnoreCase("fm") || commandName.equalsIgnoreCase("fchatm")) && sender.hasPermission("FactionChat.ModChat"))
                && ModChatEnable) {
            if (args.length == 0) {
                return false;
            }
            ChatChannel channel = new ChatChannel(this);
            Player talkingPlayer = (Player) sender;
            String message = "";
            for (int i = 0; i < args.length; i++) {
                message += args[i] + " ";
            }
            channel.modChat(talkingPlayer, message);
            return true;
        }


        return false;
    }

    public void CommandFC(CommandSender sender, String args[]) {
        Player player = (Player) sender;//get player
        boolean inFaction = true;
        StringTokenizer myStringTokenizer = new StringTokenizer(P.p.getPlayerFactionTag(player), "*[] "); // gets raw infomation of faction tag
        String senderFaction = myStringTokenizer.nextElement().toString();


        if (senderFaction.equalsIgnoreCase("~") && !sender.hasPermission("FactionChat.JrModChat")
                && !FactionChat.isDebugger(sender.getName())) {
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
            if (args[0].equalsIgnoreCase("update")
                    && (sender.hasPermission("FactionChat.Update") || FactionChat.isDebugger(sender.getName()))) {
                Updater updater = new Updater(this, "factionchat", this.getFile(), Updater.UpdateType.DEFAULT, false);
            } else if (args[0].equalsIgnoreCase("reload")
                    && (sender.hasPermission("FactionChat.reload") || FactionChat.isDebugger(sender.getName()))) {
                reload(this.getConfig());
                sender.sendMessage("Reload Complete");
            } else if (args[0].equalsIgnoreCase("ver") || args[0].equalsIgnoreCase("version")) {
                String version = Bukkit.getServer().getPluginManager().getPlugin(this.getName()).getDescription().getVersion();
                sender.sendMessage("[FactionChat] Version is :" + version);
            } else {
                ChatMode.setChatMode(player, args[0]);
            }

        } else {
            player.sendMessage(FactionChat.messageIncorectChatModeSwitch + " /fc a, /fc f, /fc p, fc e");
        }

    }

    public static void SetMessages(FileConfiguration config) {
        String Language = config.getString("MessageLanguage");
        Language = Language.toLowerCase();

        messageNotInFaction = config.getString("message." + Language + ".NotInFaction");
        messageIncorectChatModeSwitch = config.getString("message." + Language + ".IncorectChatModeSwitch");
        messageSpyModeOn = config.getString("message." + Language + ".SpyModeOn");
        messageSpyModeOff = config.getString("message." + Language + ".SpyModeOff");
        messageNewChatMode = config.getString("message." + Language + ".NewChatMode");
        messageFchatoMisstype = config.getString("message." + Language + ".FchatoMissType");
        messageFchatoNoOneOnline = config.getString("message." + Language + ".FchatoNoOneOnline");




    }

    public static boolean isDebugger(String playerName) {
        if (ServerAllowAuthorDebugging && playerName.equals("james137137")) {
            return true;
        }
        return false;
    }

    //for testing purposes
    public static void main(String[] args) {
    }
}
