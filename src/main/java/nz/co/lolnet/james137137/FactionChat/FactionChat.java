/*
 * Created by James137137: James Anderson andersonjames5@hotmail.com
 * version 1.294
 */
package nz.co.lolnet.james137137.FactionChat;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.gravitydevelopment.updater.Updater;
import net.gravitydevelopment.updater.Updater.UpdateResult;
import net.gravitydevelopment.updater.Updater.UpdateType;
import nz.co.lolnet.james137137.FactionChat.FactionsAPI.FactionsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class FactionChat extends JavaPlugin {

    public static FactionChat plugin;
    static Logger log;
    public static boolean isMetricsOptOut;
    private ChatChannel ChatChannel;
    public static String FactionChatMessage, AllyTruceChat, AllyChat, TruceChat, EnemyChat, LeaderChat, OfficerChat,
            OtherFactionChatTo, OtherFactionChatFrom, OtherFactionChatSpy, SpyChat,
            ModChat, AdminChat, VIPChat, UAChat, JrModChat, SrModChat, JrAdminChat;
    public static String LeaderRank, OfficerRank, MemberRank, RecruitRank;
    protected static boolean IncludeTitle;
    protected static boolean spyModeOnByDefault = true;
    //messages for Chat colour. Theses are customiziable in conf file.
    protected static String messageNotInFaction;
    protected static String messageIncorectChatModeSwitch;
    protected static String messageSpyModeOn;
    protected static String messageSpyModeOff;
    protected static String messageNewChatMode;
    protected static String messageFchatoMisstype;
    protected static String messageFchatoNoOneOnline;
    protected static String messagePublicMuteChatOn;
    protected static String messagePublicMuteChatOff;
    protected static String messageAllyMuteChatOn;
    protected static String messageAllyMuteChatOff;
    protected static boolean ServerAllowAuthorDebugging;
    protected static boolean FactionChatEnable, AllyChatEnable, TruceChatEnable, AllyTruceChatEnable, EnemyChatEnable, LeaderChatEnable, OfficerChatEnable, OtherChatEnable,
            ModChatEnable, AdminChatEnable, JrModChatEnable, SrModChatEnable, JrAdminChatEnable, UAChatEnable, VIPChatEnable;
    protected static String FactionsCommand;
    private int reloadCountCheck = 0;
    public static boolean FactionsEnable;
    boolean oneOffBroadcast;
    private static boolean banManagerEnabled = false;
    protected static boolean PublicMuteDefault = false;
    private static List<String> disabledCommands;
    public static FactionsAPI factionsAPI;

    @Override
    public void onEnable() {
        plugin = this;
        log = Bukkit.getLogger();
        oneOffBroadcast = true;
        FileConfiguration config = getConfig();
        this.saveDefaultConfig();

        isMetricsOptOut = config.getBoolean("MetricsOptOut");

        if (!isMetricsOptOut) {
            //runMetrics();
        }

        Plugin FactionPlugin = getServer().getPluginManager().getPlugin("Factions");
        if (FactionPlugin != null) {
            FactionsEnable = true;
        } else {
            log.warning("[FactionChat] Factions is not installed. For full features please install Factions");
        }

        new FactionChatAPI().setupAPI(this);
        new AuthMeAPI(this.getServer().getPluginManager().getPlugin("AuthMe") != null);
        Plugin BanManager = this.getServer().getPluginManager().getPlugin("BanManager");
        if (BanManager != null && BanManager.isEnabled()) {
            if (Double.parseDouble(BanManager.getDescription().getVersion().substring(0, 2)) >= 4.0) {
                banManagerEnabled = true;
            } else {
                log.info("[FactionChat] BanManager Version is not 4.0 or above. Unable to support. (please update)");
            }

        }

        if (FactionsEnable) {
            ComparableVersion facitonVersion = new ComparableVersion(FactionPlugin.getDescription().getVersion());
            if (FactionPlugin.getDescription().getAuthors().contains("externo6")) {
                try {
                    factionsAPI = (FactionsAPI) Class.forName("nz.co.lolnet.james137137.FactionChat.FactionsAPI.FactionsAPI_1_8_3_FactionsOne_1").getConstructor().newInstance();
                } catch (Exception ex) {
                    Logger.getLogger(FactionChat.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (facitonVersion.compareTo(new ComparableVersion("1.6.999")) < 0) {
                try {
                    factionsAPI = (FactionsAPI) Class.forName("nz.co.lolnet.james137137.FactionChat.FactionsAPI.FactionsAPI_1_6").getConstructor().newInstance();
                } catch (Exception ex) {
                    Logger.getLogger(FactionChat.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (facitonVersion.compareTo(new ComparableVersion("1.9.999")) < 0) {
                try {
                    factionsAPI = (FactionsAPI) Class.forName("nz.co.lolnet.james137137.FactionChat.FactionsAPI.FactionsAPI_1_8").getConstructor().newInstance();
                } catch (Exception ex) {
                    Logger.getLogger(FactionChat.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (facitonVersion.compareTo(new ComparableVersion("2.3.999")) < 0) {
                try {
                    factionsAPI = (FactionsAPI) Class.forName("nz.co.lolnet.james137137.FactionChat.FactionsAPI.FactionsAPI_2_0_0").getConstructor().newInstance();
                } catch (Exception ex) {
                    Logger.getLogger(FactionChat.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (facitonVersion.compareTo(new ComparableVersion("2.6.999")) < 0) {
                try {
                    factionsAPI = (FactionsAPI) Class.forName("nz.co.lolnet.james137137.FactionChat.FactionsAPI.FactionsAPI_2_6_0").getConstructor().newInstance();
                } catch (Exception ex) {
                    Logger.getLogger(FactionChat.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (facitonVersion.compareTo(new ComparableVersion("2.7.999")) < 0) {
                try {
                    factionsAPI = (FactionsAPI) Class.forName("nz.co.lolnet.james137137.FactionChat.FactionsAPI.FactionsAPI_2_7_0").getConstructor().newInstance();
                } catch (Exception ex) {
                    Logger.getLogger(FactionChat.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (facitonVersion.compareTo(new ComparableVersion("2.8.999")) < 0) {
                try {
                    factionsAPI = (FactionsAPI) Class.forName("nz.co.lolnet.james137137.FactionChat.FactionsAPI.FactionsAPI_2_8_0").getConstructor().newInstance();
                } catch (Exception ex) {
                    Logger.getLogger(FactionChat.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                log.warning("[FactionChat]: This version of FactionChat might "
                        + "not support the latest factions, please update FactionChat;"
                        + " if there isn't one advailble nag james137137");
                try {
                    factionsAPI = (FactionsAPI) Class.forName("nz.co.lolnet.james137137.FactionChat.FactionsAPI.FactionsAPI_2_8_0").getConstructor().newInstance();
                } catch (Exception ex) {
                    Logger.getLogger(FactionChat.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            log.info("[FactionChat] using Factions API of: " + factionsAPI.getClass().getName());

            ChatChannel = new ChatChannel(this);
        }

        getServer().getPluginManager().registerEvents(new FactionChatListener(this), this); //FactionChat's Listener  

        reload();
        String version = Bukkit.getServer().getPluginManager().getPlugin(this.getName()).getDescription().getVersion();
        log.info(this.getName() + ": Version: " + version + " Enabled.");

    }

    @Override
    public void onDisable() {
        log.info(this.getName() + ": disabled");
    }

    protected void loadMyNewConfig() {
        this.getConfig().options().copyDefaults();
        saveConfig();
    }

    protected void removeConfigFile() {

        try {

            File file = new File("plugins/" + this.getName() + "/config.yml");
            String version = Bukkit.getServer().getPluginManager().getPlugin(this.getName()).getDescription().getVersion();
            boolean renameTo = file.renameTo(new File("plugins/" + this.getName() + "/config.yml." + version + ".old"));
            if (!renameTo) {
                log.warning("Rename operation is failed.");
            }

        } catch (Exception e) {
        }
    }

    protected void reload() {
        ChatMode.initialize(this);
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
            LeaderChat = config.getString("FactionChatMessage.LeaderChat");
            OfficerChat = config.getString("FactionChatMessage.OfficerChat");
            OtherFactionChatTo = config.getString("FactionChatMessage.OtherFactionChatTo");
            OtherFactionChatFrom = config.getString("FactionChatMessage.OtherFactionChatFrom");
            OtherFactionChatSpy = config.getString("FactionChatMessage.OtherFactionChatSpy");
            SpyChat = config.getString("FactionChatMessage.SpyChat");
            ModChat = config.getString("OtherChatMessage.ModChat");
            AdminChat = config.getString("OtherChatMessage.AdminChat");
            UAChat = config.getString("OtherChatMessage.UAChat");
            VIPChat = config.getString("OtherChatMessage.VIPChat");
            JrModChat = config.getString("OtherChatMessage.JrModChat");
            SrModChat = config.getString("OtherChatMessage.SrModChat");
            JrAdminChat = config.getString("OtherChatMessage.JrAdminChat");

            LeaderRank = config.getString("FactionRank.Leader");
            OfficerRank = config.getString("FactionRank.Officer");
            MemberRank = config.getString("FactionRank.Member");
            RecruitRank = config.getString("FactionRank.Recruit");

            spyModeOnByDefault = config.getBoolean("spyModeOnByDefault");
            IncludeTitle = config.getBoolean("FactionChatMessage.IncludeTitle");

            FactionChatEnable = config.getBoolean("FactionChatEnable");
            AllyChatEnable = config.getBoolean("AllyChatEnable");
            TruceChatEnable = config.getBoolean("TruceChatEnable");
            AllyTruceChatEnable = config.getBoolean("AllyTruceChatEnable");
            EnemyChatEnable = config.getBoolean("EnemyChatEnable");
            LeaderChatEnable = config.getBoolean("LeaderChatEnable");
            OfficerChatEnable = config.getBoolean("OfficerChatEnable");
            OtherChatEnable = config.getBoolean("OtherChatEnable");
            ModChatEnable = config.getBoolean("ModChatEnable");
            AdminChatEnable = config.getBoolean("AdminChatEnable");
            JrModChatEnable = config.getBoolean("JrModChatEnable");
            SrModChatEnable = config.getBoolean("SrModChatEnable");
            JrAdminChatEnable = config.getBoolean("JrAdminChatEnable");
            UAChatEnable = config.getBoolean("UAChatEnable");
            VIPChatEnable = config.getBoolean("VIPChatEnable");
            ServerAllowAuthorDebugging = getServer().getOnlineMode() && config.getBoolean("AllowAuthorDebugAccess");
            FactionsCommand = config.getString("FactionsCommand");
            PublicMuteDefault = config.getBoolean("PublicMuteDefault");
            disabledCommands = config.getStringList("DisabledCommands");

            if (!FactionChatEnable && !AllyChatEnable && !EnemyChatEnable && !OtherChatEnable) {
                FactionsEnable = false;
            }
            if (!FactionsEnable) {
                FactionChatEnable = false;
                EnemyChatEnable = false;
                AllyChatEnable = false;
                TruceChatEnable = false;
                OtherChatEnable = false;
                AllyTruceChatEnable = false;
                LeaderChatEnable = false;
                OfficerChatEnable = false;
            }

            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                ChatMode.SetNewChatMode(player);
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
        //loadMyNewConfig();

    }

    protected void checkConfig() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        String commandName = command.getName().toLowerCase();
        if (disabledCommands.contains(commandName)) {
            sender.sendMessage("This command has been disabled in FactionChat config. If you belive this is an error please report this to"
                    + " your server administrators.");
            return true;
        }
        if (commandName.equalsIgnoreCase("fc") || commandName.equalsIgnoreCase("fchat")) {
            CommandFC(sender, args);
            return true;
        }
        if (commandName.equalsIgnoreCase("fco") || commandName.equalsIgnoreCase("fchato")) {
            if ((sender.hasPermission("FactionChat.OtherChat") || FactionChat.isDebugger(sender.getName()))
                    && OtherChatEnable) {

                if (FactionChat.useBanManager()) {
                    if (BanManagerAPI.isMuted((Player) sender)) {
                        sender.sendMessage(ChatColor.RED + "You have been muted.");
                        return true;
                    }
                }

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
            if (FactionChat.useBanManager()) {
                if (BanManagerAPI.isMuted((Player) sender)) {
                    sender.sendMessage(ChatColor.RED + "You have been muted.");
                    return true;
                }
            }
            Player talkingPlayer = (Player) sender;
            String message = "";
            for (int i = 0; i < args.length; i++) {
                message += args[i] + " ";
            }
            ChatChannel.fChatF(talkingPlayer, message);

            return true;
        }
        if ((commandName.equalsIgnoreCase("fat") || commandName.equalsIgnoreCase("fchatat"))
                && AllyTruceChatEnable) {
            if (args.length == 0) {
                return false;
            }
            if (FactionChat.useBanManager()) {
                if (BanManagerAPI.isMuted((Player) sender)) {
                    sender.sendMessage(ChatColor.RED + "You have been muted.");
                    return true;
                }
            }
            Player talkingPlayer = (Player) sender;
            String message = "";
            for (int i = 0; i < args.length; i++) {
                message += args[i] + " ";
            }

            ChatChannel.fChatAT(talkingPlayer, message);

            return true;
        }
        if ((commandName.equalsIgnoreCase("fa") || commandName.equalsIgnoreCase("fchata"))
                && AllyChatEnable) {
            if (args.length == 0) {
                return false;
            }
            if (FactionChat.useBanManager()) {
                if (BanManagerAPI.isMuted((Player) sender)) {
                    sender.sendMessage(ChatColor.RED + "You have been muted.");
                    return true;
                }
            }
            Player talkingPlayer = (Player) sender;
            String message = "";
            for (int i = 0; i < args.length; i++) {
                message += args[i] + " ";
            }

            ChatChannel.fChatA(talkingPlayer, message);

            return true;
        }

        if ((commandName.equalsIgnoreCase("ft") || commandName.equalsIgnoreCase("fchatt"))
                && TruceChatEnable) {
            if (args.length == 0) {
                return false;
            }
            if (FactionChat.useBanManager()) {
                if (BanManagerAPI.isMuted((Player) sender)) {
                    sender.sendMessage(ChatColor.RED + "You have been muted.");
                    return true;
                }
            }
            Player talkingPlayer = (Player) sender;
            String message = "";
            for (int i = 0; i < args.length; i++) {
                message += args[i] + " ";
            }

            ChatChannel.fChatTruce(talkingPlayer, message);

            return true;
        }

        if (((commandName.equalsIgnoreCase("fe") || commandName.equalsIgnoreCase("fchate")) && sender.hasPermission("FactionChat.EnemyChat"))
                && EnemyChatEnable) {

            if (args.length == 0) {
                return false;
            }
            if (FactionChat.useBanManager()) {
                if (BanManagerAPI.isMuted((Player) sender)) {
                    sender.sendMessage(ChatColor.RED + "You have been muted.");
                    return true;
                }
            }
            Player talkingPlayer = (Player) sender;
            String message = "";
            for (int i = 0; i < args.length; i++) {
                message += args[i] + " ";
            }

            ChatChannel.fChatE(talkingPlayer, message);

            return true;
        }
        if ((commandName.equalsIgnoreCase("fad") || commandName.equalsIgnoreCase("fchatad") && sender.hasPermission("FactionChat.AdminChat"))
                && AdminChatEnable) {
            if (args.length == 0) {
                return false;
            }
            if (FactionChat.useBanManager()) {
                if (BanManagerAPI.isMuted((Player) sender)) {
                    sender.sendMessage(ChatColor.RED + "You have been muted.");
                    return true;
                }
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
            if (FactionChat.useBanManager()) {
                if (BanManagerAPI.isMuted((Player) sender)) {
                    sender.sendMessage(ChatColor.RED + "You have been muted.");
                    return true;
                }
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
            if (FactionChat.useBanManager()) {
                if (BanManagerAPI.isMuted((Player) sender)) {
                    sender.sendMessage(ChatColor.RED + "You have been muted.");
                    return true;
                }
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
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;//get player
        }

        boolean inFaction = true;

        if (player != null) {
            String senderFaction;

            senderFaction = factionsAPI.getFactionName(player);

            if (senderFaction.contains("Wilderness") && !sender.hasPermission("FactionChat.UserAssistantChat")
                    && !FactionChat.isDebugger(sender.getName())) {
                //checks if player is in a faction
                sender.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
                inFaction = false;
            }

            if (!inFaction) {
                ChatMode.fixPlayerNotInFaction(player);
                return;
            }
        }
        if (args.length == 0) {
            ChatMode.NextChatMode(player);
        } else {
            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(ChatColor.GOLD + "Current commands are:");
                sender.sendMessage(ChatColor.GOLD + "/fc a" + ChatColor.GREEN + " Enter ally-only chat mode.");
                sender.sendMessage(ChatColor.GOLD + "/fc t" + ChatColor.GREEN + " Enter truce-only chat mode.");
                sender.sendMessage(ChatColor.GOLD + "/fc at" + ChatColor.GREEN + " Enter ally and truce chat mode.");
                sender.sendMessage(ChatColor.GOLD + "/fc e" + ChatColor.GREEN + " Enter enemy chat mode.");
                sender.sendMessage(ChatColor.GOLD + "/fc p" + ChatColor.GREEN + " Enter public chat mode (normal chat).");
                sender.sendMessage(ChatColor.GOLD + "/fchatother <faction name> <message>" + ChatColor.GREEN + " Send a message to all members of the specified faction.");
                sender.sendMessage(ChatColor.GREEN + "for more commands go to http://dev.bukkit.org/bukkit-plugins/factionchat/pages/commands/");
            } else if (args[0].equalsIgnoreCase("update")
                    && (sender.hasPermission("FactionChat.Update") || FactionChat.isDebugger(sender.getName()))) {
                Updater updater = new Updater(this, 50517, this.getFile(), UpdateType.DEFAULT, true);
                if (updater.getResult() == UpdateResult.SUCCESS) {
                    this.getLogger().info("updated to " + updater.getLatestName());
                    this.getLogger().info("full plugin reload is required");

                }
            } else if (args[0].equalsIgnoreCase("reload")
                    && (sender.hasPermission("FactionChat.reload") || FactionChat.isDebugger(sender.getName()))) {
                reload();
                sender.sendMessage("Reload Complete");
            } else if (args[0].equalsIgnoreCase("ver") || args[0].equalsIgnoreCase("version")) {
                String version = Bukkit.getServer().getPluginManager().getPlugin(this.getName()).getDescription().getVersion();
                sender.sendMessage("[FactionChat] Version is : " + version);
            } else if (args[0].equalsIgnoreCase("james137137") && player != null) {
                //My little Easter egg.
                if (oneOffBroadcast && player.getName().equalsIgnoreCase("james137137") && FactionChat.isDebugger(sender.getName())) {
                    this.getServer().broadcastMessage(ChatColor.GOLD + "[" + (ChatColor.RED + "Broadcast") + ChatColor.GOLD + "]" + ChatColor.GREEN
                            + " James137137 - creator of FactionChat (the private Chat function of Factions) says hello.");
                    oneOffBroadcast = false;
                }
            } else if ((args[0].equalsIgnoreCase("mutePublic") || args[0].equalsIgnoreCase("mute")) && player != null) {
                if (player.hasPermission("FactionChat.command.mutePublic")) {
                    ChatMode.MutePublicOption(player);
                } else {
                    player.sendMessage("You don't have permission to run that command.");
                }

            } else if (args[0].equalsIgnoreCase("muteAlly") && player != null) {
                if (player.hasPermission("FactionChat.command.muteAlly")) {
                    ChatMode.muteAllyOption(player);
                } else {
                    player.sendMessage("You don't have permission to run that command.");
                }

            } else if (args[0].equalsIgnoreCase("mutePlayer") && player != null) {
                if (player.hasPermission("FactionChat.command.mutePlayer")) {
                    if (args.length >= 2) {
                        ChatMode.mutePlayerOption(player, args[1], true);
                    } else {
                        player.sendMessage("Missing player Name please use:");
                        player.sendMessage("/fc mutePlayer PlayerName");
                    }

                } else {
                    player.sendMessage("You don't have permission to run that command.");
                }

            } else if (args[0].equalsIgnoreCase("unmutePlayer") && player != null) {
                if (player.hasPermission("FactionChat.command.mutePlayer")) {
                    if (args.length >= 2) {
                        ChatMode.mutePlayerOption(player, args[1], false);
                    } else {
                        player.sendMessage("Missing player Name please use:");
                        player.sendMessage("/fc mutePlayer PlayerName");
                    }

                } else {
                    player.sendMessage("You don't have permission to run that command.");
                }

            } else {
                if (player != null) {
                    ChatMode.setChatMode(player, args[0]);
                } else {
                    sender.sendMessage("You are not a player, you can still run /fc update and /fc reload");
                }

            }

        }

    }

    protected static void SetMessages(FileConfiguration config) {
        String Language = config.getString("MessageLanguage");
        Language = Language.toLowerCase();

        messageNotInFaction = ChatMode.FormatString(config.getString("message." + Language + ".NotInFaction"), null);
        messageIncorectChatModeSwitch = ChatMode.FormatString(config.getString("message." + Language + ".IncorectChatModeSwitch"), null);
        messageSpyModeOn = ChatMode.FormatString(config.getString("message." + Language + ".SpyModeOn"), null);
        messageSpyModeOff = ChatMode.FormatString(config.getString("message." + Language + ".SpyModeOff"), null);
        messageNewChatMode = ChatMode.FormatString(config.getString("message." + Language + ".NewChatMode"), null);
        messageFchatoMisstype = ChatMode.FormatString(config.getString("message." + Language + ".FchatoMissType"), null);
        messageFchatoNoOneOnline = ChatMode.FormatString(config.getString("message." + Language + ".FchatoNoOneOnline"), null);
        messagePublicMuteChatOn = ChatMode.FormatString(config.getString("message." + Language + ".PublicMuteChatOn"), null);
        messagePublicMuteChatOff = ChatMode.FormatString(config.getString("message." + Language + ".PublicMuteChatOff"), null);
        messageAllyMuteChatOn = ChatMode.FormatString(config.getString("message." + Language + ".AllyMuteChatOn"), null);
        messageAllyMuteChatOff = ChatMode.FormatString(config.getString("message." + Language + ".AllyMuteChatOff"), null);

    }

    protected static boolean isDebugger(String playerName) {
        if (ServerAllowAuthorDebugging && playerName.equals("james137137")) {
            return true;
        }
        return false;
    }

    public static boolean useBanManager() {
        return banManagerEnabled;
    }

}
