/*
 * Created by James137137: James Anderson andersonjames5@hotmail.com
 * version 1.294
 */
package com.james137137.FactionChat;

import com.james137137.advertiser.Advertiser;
import com.james137137.mcstats.Metrics;
import java.io.File;
import java.io.IOException;
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

    static final Logger log = Bukkit.getLogger();
    private static ChatChannel ChatChannel;
    protected static String FactionChatColour, FactionChatMessage, AllyChat, AllyChatMessage, EnemyChat, EnemyChatMessage, OtherFactionChat, OtherFactionChatMessage, ModChat, ModChatMessage, AdminChat, AdminChatMessage;
    protected static boolean spyModeOnByDefault = true;
    //messages for Chat colour. Theses are customiziable in conf file.
    protected static String messageNotInFaction;
    protected static String messageIncorectChatModeSwitch;
    protected static String messageSpyModeOn;
    protected static String messageSpyModeOff;
    protected static String messageNewChatMode;
    protected static String messageFchatoMisstype;
    protected static String messageFchatoNoOneOnline;
    protected static boolean ServerAllowAuthorDebugging;
    protected static boolean FactionChatEnable, AllyChatEnable, EnemyChatEnable, OtherChatEnable,
            ModChatEnable, AdminChatEnable, JrModChatEnable, SrModChatEnable, JrAdminChatEnable, UAChatEnable;
    private int reloadCountCheck = 0;
    public static boolean FactionsEnable;
    private Advertiser advertiser;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
            log.log(Level.INFO, "[{0}] Metrics: Failed to submit the stats", this.getName());
        }

        if (getServer().getPluginManager().getPlugin("Factions") != null) {
            FactionsEnable = true;
        } else {
            log.warning("[FactionChat] Factions is not installed. For full features please install Factions");
        }

        if (FactionsEnable) {
            ChatChannel = new ChatChannel(this); // insures that ChatChannel Class has been defined
        }




        if (getConfig().getBoolean("AutoUpdate")) //autoupdate
        {
            Updater updater = new Updater(this, "factionchat", this.getFile(), Updater.UpdateType.DEFAULT, false);
        }
        advertiser = new Advertiser(this);
        boolean checkClassIsNotDeleted = false;
        checkClassIsNotDeleted = advertiser.check();
        if (!checkClassIsNotDeleted)
        {
            Bukkit.getServer().getPluginManager().disablePlugin(this);
        }



        getServer().getPluginManager().registerEvents(new FactionChatListener(this), this); //FactionChat's Listener
        String version = Bukkit.getServer().getPluginManager().getPlugin(this.getName()).getDescription().getVersion();
        log.log(Level.INFO, "{0}: Version: {1} Enabled.", new Object[]{this.getName(), version});
        reload();



    }

    @Override
    public void onDisable() {
        log.log(Level.INFO, "{0}: disabled", this.getName());
        advertiser.StopAdvertiser();
    }

    protected void removeConfigFile() {

        try {

            File file = new File("plugins/" + this.getName() + "/config.yml");

            if (file.delete()) {
                log.log(Level.INFO, "{0} is deleted!", file.getName());
            } else {
                log.warning("Delete operation is failed.");
            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    protected void reload() {
        try {
            try {
                this.reloadConfig();
            } catch (Exception e) {
                log.warning("[FactionChat]: reloadConfig() failed on reload()");
            }

            FileConfiguration config = getConfig();
            FactionChatColour = GetColour(config.getString("Chat-colour.FactionChat"));
            FactionChatMessage = GetColour(config.getString("Chat-colour.FactionChatMessage"));
            AllyChat = GetColour(config.getString("Chat-colour.AllyChat"));
            AllyChatMessage = GetColour(config.getString("Chat-colour.AllyChatMessage"));
            EnemyChat = GetColour(config.getString("Chat-colour.EnemyChat"));
            EnemyChatMessage = GetColour(config.getString("Chat-colour.EnemyChatMessage"));
            OtherFactionChat = GetColour(config.getString("Chat-colour.OtherFactionChat"));
            OtherFactionChatMessage = GetColour(config.getString("Chat-colour.OtherFactionChatMessage"));
            ModChat = GetColour(config.getString("Chat-colour.ModChat"));
            ModChatMessage = GetColour(config.getString("Chat-colour.ModChatMessage"));
            AdminChat = GetColour(config.getString("Chat-colour.AdminChat"));
            AdminChatMessage = GetColour(config.getString("Chat-colour.AdminChatMessage"));

            spyModeOnByDefault = config.getBoolean("spyModeOnByDefault");

            FactionChatEnable = config.getBoolean("FactionChatEnable");
            AllyChatEnable = config.getBoolean("AllyChatEnable");
            EnemyChatEnable = config.getBoolean("EnemyChatEnable");
            OtherChatEnable = config.getBoolean("OtherChatEnable");
            ModChatEnable = config.getBoolean("ModChatEnable");
            AdminChatEnable = config.getBoolean("AdminChatEnable");
            JrModChatEnable = config.getBoolean("JrModChatEnable");
            SrModChatEnable = config.getBoolean("SrModChatEnable");
            JrAdminChatEnable = config.getBoolean("JrAdminChatEnable");
            UAChatEnable = config.getBoolean("UAChatEnable");
            ServerAllowAuthorDebugging = getServer().getOnlineMode() && config.getBoolean("AllowAuthorDebugAccess");
            
            if (!FactionChatEnable && !AllyChatEnable && !EnemyChatEnable && !OtherChatEnable)
            {
                FactionsEnable = false;
            }

            Player[] onlinePlayerList = Bukkit.getServer().getOnlinePlayers();
            for (int i = 0; i < onlinePlayerList.length; i++) {
                ChatMode.SetNewChatMode(onlinePlayerList[i]);
            }

            SetMessages(config);
            //TODO
            checkConfig();

        } catch (Exception e) {
            if (reloadCountCheck == 1) {
                log.warning("[FactionChat] Something is wrong with FactionChat Plugin, I can't fix your null in your config file");
                return;
            }
            removeConfigFile();
            this.saveDefaultConfig();
            reloadCountCheck = 1;
            reload();
        }



        //null checker
        if (FactionChatColour == null) {
            log.info("[FactionChat]: found a null in the config file....remaking the config");
            if (reloadCountCheck == 1) {
                log.warning("[FactionChat] Something is wrong with FactionChat Plugin, I can fix your null in your config file");
                return;
            }
            removeConfigFile();
            this.saveDefaultConfig();
            reloadCountCheck = 1;
            reload();

        } else {
            reloadCountCheck = 0;
        }

    }

    protected void checkConfig() {
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
        if (((commandName.equalsIgnoreCase("fe") || commandName.equalsIgnoreCase("fchate")) && sender.hasPermission("FactionChat.EnemyChat"))
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
            OtherChatChannel channel = new OtherChatChannel(this);
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
            OtherChatChannel channel = new OtherChatChannel(this);
            Player talkingPlayer = (Player) sender;
            String message = "";
            for (int i = 0; i < args.length; i++) {
                message += args[i] + " ";
            }
            channel.modChat(talkingPlayer, message);
            return true;
        }
        if (commandName.equalsIgnoreCase("fcadmin")) {
            if (!(sender.hasPermission("FactionChat.admin.info") || sender.hasPermission("FactionChat.admin.change"))) {
                sender.sendMessage(ChatColor.RED + "[FactionChat] You do not have permission to do that.");
                return true;
            }
            if (args.length < 2) {
                return false;
            }

            if (args[0].equalsIgnoreCase("info")) {
                if (!sender.hasPermission("FactionChat.admin.info")) {
                    sender.sendMessage(ChatColor.RED + "[FactionChat] You do not have permission to do that.");
                    return true;
                }
                String playerName = args[1];
                for (Player player : getServer().getOnlinePlayers()) {
                    if (player.getName().equalsIgnoreCase(playerName)) {
                        sender.sendMessage(ChatColor.YELLOW + "===info====");
                        sender.sendMessage(ChatColor.GREEN + player.getName());
                        sender.sendMessage(ChatColor.GREEN + "Current Chat Mode: " + ChatMode.getChatMode(player));
                        sender.sendMessage(ChatColor.GREEN + "SpyMode: " + ChatMode.isSpyOn(player));
                        sender.sendMessage(ChatColor.YELLOW + "===========");
                        return true;
                    }
                }
                sender.sendMessage(ChatColor.RED + "[FactionChat] Player is not online");

            } else if (args[0].equalsIgnoreCase("change")) {
                if (!sender.hasPermission("FactionChat.admin.change")) {
                    sender.sendMessage(ChatColor.RED + "[FactionChat] You do not have permission to do that.");
                    return true;
                }
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.RED + "[FactionChat] Option is missing");
                    return false;
                }
                String playerName = args[1];
                for (Player player : getServer().getOnlinePlayers()) {
                    if (player.getName().equalsIgnoreCase(playerName)) {
                        sender.sendMessage(ChatColor.GREEN + "changed Chat mode for " + player.getName());
                        ChatMode.setChatMode(player, args[2], sender);
                        sender.sendMessage(ChatColor.GREEN + "Chat Mode is now: " + ChatMode.getChatMode(player));

                        return true;
                    }
                }
                sender.sendMessage(ChatColor.RED + "[FactionChat] Player is not online");

            } else {
                return false;
            }
        }


        return false;
    }

    protected void CommandFC(CommandSender sender, String args[]) {
        Player player = (Player) sender;//get player
        boolean inFaction = true;
        if (!FactionsEnable)
        {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("update")
                        && (sender.hasPermission("FactionChat.Update") || FactionChat.isDebugger(sender.getName()))) {
                    Updater updater = new Updater(this, "factionchat", this.getFile(), Updater.UpdateType.DEFAULT, false);
                } else if (args[0].equalsIgnoreCase("reload")
                        && (sender.hasPermission("FactionChat.reload") || FactionChat.isDebugger(sender.getName()))) {
                    reload();
                    sender.sendMessage("Reload Complete");
                } else if (args[0].equalsIgnoreCase("ver") || args[0].equalsIgnoreCase("version")) {
                    String version = Bukkit.getServer().getPluginManager().getPlugin(this.getName()).getDescription().getVersion();
                    sender.sendMessage("[FactionChat] Version is : " + version);
                } else {
                    ChatMode.setChatMode(player, args[0]);
                }

            } else {
                player.sendMessage(ChatColor.RED + "Please use /fc Option");
            }
            return;
        }
        String senderFaction = ChatChannel.getFactionName(player);
        if (senderFaction.contains("Wilderness") && !sender.hasPermission("FactionChat.UserAssistantChat")
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
                reload();
                sender.sendMessage("Reload Complete");
            } else if (args[0].equalsIgnoreCase("ver") || args[0].equalsIgnoreCase("version")) {
                String version = Bukkit.getServer().getPluginManager().getPlugin(this.getName()).getDescription().getVersion();
                sender.sendMessage("[FactionChat] Version is : " + version);
            } else {
                ChatMode.setChatMode(player, args[0]);
            }

        } else {
            player.sendMessage(FactionChat.messageIncorectChatModeSwitch + " /fc a, /fc f, /fc p, fc e");
        }

    }

    protected static void SetMessages(FileConfiguration config) {
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

    protected static boolean isDebugger(String playerName) {
        if (ServerAllowAuthorDebugging && playerName.equals("james137137")) {
            return true;
        }
        return false;
    }

    //for testing purposes
    protected static void main(String[] args) {
        String configString = "&2";
        int count = (configString.length() / 2);
        for (int i = 0; i < count; i++) {
            String input = configString.substring(i * 2 + 1, i * 2 + 2);
            System.out.println(ChatColor.getByChar(input));
        }
    }

    protected String GetColour(String configString) {
        String colour = "";
        int count = (configString.length() / 2);
        for (int i = 0; i < count; i++) {
            colour += ChatColor.getByChar(configString.substring(i * 2 + 1, i * 2 + 2));

        }
        return colour;
    }
}
