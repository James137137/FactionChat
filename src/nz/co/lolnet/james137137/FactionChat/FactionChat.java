/*
 * Created by James137137: James Anderson andersonjames5@hotmail.com
 * version 1.294
 */
package nz.co.lolnet.james137137.FactionChat;

import nz.co.lolnet.james137137.mcstats.Metrics;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nz.co.lolnet.james137137.h31ix.updater.Updater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class FactionChat extends JavaPlugin {

    static final Logger log = Bukkit.getLogger();
    private ChatChannel ChatChannel;
    private ChatChannel2 ChatChannel2;
    public static String FactionChatMessage, AllyTruceChat,AllyChat,TruceChat, EnemyChat,
            OtherFactionChatTo, OtherFactionChatFrom, OtherFactionChatSpy, SpyChat,
            ModChat, AdminChat, UAChat, JrModChat, SrModChat, JrAdminChat;
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
    boolean useFaction2 = false;

    @Override
    public void onEnable() {
        new FactionChatAPI().setupAPI(this);
        this.saveDefaultConfig();

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
            log.log(Level.INFO, "[{0}] Metrics: Failed to submit the stats", this.getName());
        }
        Plugin FactionPlugin = getServer().getPluginManager().getPlugin("Factions");
        if (FactionPlugin != null) {
            FactionsEnable = true;
        } else {
            log.warning("[FactionChat] Factions is not installed. For full features please install Factions");
        }

        if (FactionsEnable) {
            if (Double.parseDouble(FactionPlugin.getDescription().getVersion().substring(0, 2)) >= 2.0) {
                useFaction2 = true;
                ChatChannel2 = new ChatChannel2(this);
            } else {
                useFaction2 = false;
                ChatChannel = new ChatChannel(this);
            }
        }
        

        if (useFaction2)
        {
         getServer().getPluginManager().registerEvents(new FactionChatListener2(this), this); //FactionChat's Listener   
        }
        else
        {
          getServer().getPluginManager().registerEvents(new FactionChatListener(this), this); //FactionChat's Listener  
        }
        
        String version = Bukkit.getServer().getPluginManager().getPlugin(this.getName()).getDescription().getVersion();
        log.log(Level.INFO, "{0}: Version: {1} Enabled.", new Object[]{this.getName(), version});
        reload();



    }

    @Override
    public void onDisable() {
        log.log(Level.INFO, "{0}: disabled", this.getName());
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
            
            FactionChatMessage = config.getString("FactionChatMessage.FactionChat");
            AllyTruceChat = config.getString("FactionChatMessage.AllyTruceChat");
            AllyChat = config.getString("FactionChatMessage.AllyChat");
            TruceChat = config.getString("FactionChatMessage.TruceChat");
            EnemyChat = config.getString("FactionChatMessage.EnemyChat");
            OtherFactionChatTo = config.getString("FactionChatMessage.OtherFactionChatTo");
            OtherFactionChatFrom = config.getString("FactionChatMessage.OtherFactionChatFrom");
            OtherFactionChatSpy = config.getString("FactionChatMessage.OtherFactionChatSpy");
            SpyChat = config.getString("FactionChatMessage.SpyChat");
            ModChat = config.getString("OtherChatMessage.ModChat");
            AdminChat = config.getString("OtherChatMessage.AdminChat");
            UAChat = config.getString("OtherChatMessage.UAChat");
            JrModChat = config.getString("OtherChatMessage.JrModChat");
            SrModChat = config.getString("OtherChatMessage.SrModChat");
            JrAdminChat = config.getString("OtherChatMessage.JrAdminChat");

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

            if (!FactionChatEnable && !AllyChatEnable && !EnemyChatEnable && !OtherChatEnable) {
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
        if (FactionChatMessage == null) {
            log.info("[FactionChat]: found a null in the config file....remaking the config");
            if (reloadCountCheck == 1) {
                log.warning("[FactionChat] Something is wrong with FactionChat Plugin, I can't fix your null in your config file");
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
                if (useFaction2)
                {
                  ChatChannel2.fchato(sender, args);  
                }else
                {
                  ChatChannel.fchato(sender, args);   
                }
                
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
            Player talkingPlayer = (Player) sender;
            String message = "";
            for (int i = 0; i < args.length; i++) {
                message += args[i] + " ";
            }
            if (useFaction2) {
                ChatChannel2.fChatF(talkingPlayer, message);
            } else {
                ChatChannel.fChatF(talkingPlayer, message);
            }
            return true;
        }
        if ((commandName.equalsIgnoreCase("fa") || commandName.equalsIgnoreCase("fchata"))
                && AllyChatEnable) {
            if (args.length == 0) {
                return false;
            }
            Player talkingPlayer = (Player) sender;
            String message = "";
            for (int i = 0; i < args.length; i++) {
                message += args[i] + " ";
            }
            if (useFaction2) {
                ChatChannel2.fChatAT(talkingPlayer, message);
            } else {
                ChatChannel.fChatAT(talkingPlayer, message);
            }
            return true;
        }
        if ((commandName.equalsIgnoreCase("fao") || commandName.equalsIgnoreCase("fchatao"))
                && AllyChatEnable) {
            if (args.length == 0) {
                return false;
            }
            Player talkingPlayer = (Player) sender;
            String message = "";
            for (int i = 0; i < args.length; i++) {
                message += args[i] + " ";
            }
            if (useFaction2) {
                ChatChannel2.fChatA(talkingPlayer, message);
            } else {
                ChatChannel.fChatA(talkingPlayer, message);
            }
            return true;
        }
        
        if ((commandName.equalsIgnoreCase("ft") || commandName.equalsIgnoreCase("fchatt"))
                && AllyChatEnable) {
            if (args.length == 0) {
                return false;
            }
            Player talkingPlayer = (Player) sender;
            String message = "";
            for (int i = 0; i < args.length; i++) {
                message += args[i] + " ";
            }
            if (useFaction2) {
                ChatChannel2.fChatTruce(talkingPlayer, message);
            } else {
                ChatChannel.fChatTruce(talkingPlayer, message);
            }
            return true;
        }
        
        if (((commandName.equalsIgnoreCase("fe") || commandName.equalsIgnoreCase("fchate")) && sender.hasPermission("FactionChat.EnemyChat"))
                && EnemyChatEnable) {

            if (args.length == 0) {
                return false;
            }
            Player talkingPlayer = (Player) sender;
            String message = "";
            for (int i = 0; i < args.length; i++) {
                message += args[i] + " ";
            }
            if (useFaction2) {
                ChatChannel2.fChatE(talkingPlayer, message);
            } else {
                ChatChannel.fChatE(talkingPlayer, message);
            }
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
        
        if (((commandName.equalsIgnoreCase("fcu") || commandName.equalsIgnoreCase("fchatua")) && sender.hasPermission("FactionChat.UserAssistantChat"))
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
            channel.userAssistantChat(talkingPlayer, message);
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
        if (!FactionsEnable) {
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
        String senderFaction;
        if (useFaction2) {
                senderFaction = ChatChannel2.getFactionName(player);
            } else {
                senderFaction = ChatChannel.getFactionName(player);
            }
        
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
