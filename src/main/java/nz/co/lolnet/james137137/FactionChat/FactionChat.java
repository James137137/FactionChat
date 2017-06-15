/*
 * Created by James137137: James Anderson andersonjames5@hotmail.com
 * version 1.294
 */
package nz.co.lolnet.james137137.FactionChat;

import nz.co.lolnet.james137137.FactionChat.API.AuthMeAPI;
import nz.co.lolnet.james137137.FactionChat.API.FactionChatAPI;
import nz.co.lolnet.james137137.FactionChat.API.BanManagerAPI;
import nz.co.lolnet.james137137.FactionChat.API.EssentialsAPI;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.gravitydevelopment.updater.Updater;
import net.gravitydevelopment.updater.Updater.UpdateResult;
import net.gravitydevelopment.updater.Updater.UpdateType;
import nz.co.lolnet.james137137.FactionChat.API.ChatFormat;
import nz.co.lolnet.james137137.FactionChat.API.McMMOAPI;
import nz.co.lolnet.james137137.FactionChat.API.StaffJoinFeature;

import nz.co.lolnet.james137137.FactionChat.FactionsAPI.FactionsAPI;
import nz.co.lolnet.james137137.FactionChat.FactionsAPI.PreFactionsAPI;
import nz.co.lolnet.james137137.FactionChat.FactionsInfoServer.FactionInfoServer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class FactionChat extends JavaPlugin {

    public static FactionChat plugin;
    static Logger log;
    public static boolean isMetricsOptOut;
    public static boolean useEssentialsNick = false;
    private ChatChannel ChatChannel;
    protected static String FactionsCommand;
    protected int reloadCountCheck = 0;
    public static boolean FactionsEnable;
    boolean oneOffBroadcast;
    public static FactionsAPI factionsAPI;
    public static BanManagerAPI banManagerAPI;

    @Override
    public void onEnable() {
        plugin = this;
        String version = Bukkit.getServer().getPluginManager().getPlugin(this.getName()).getDescription().getVersion();
        log = Bukkit.getLogger();
        log.info(this.getName() + ": Version: " + version + " Enabling");

        oneOffBroadcast = true;
        FileConfiguration config = getConfig();
        this.saveDefaultConfig();

        isMetricsOptOut = config.getBoolean("MetricsOptOut");

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
        }

        Plugin FactionPlugin = getServer().getPluginManager().getPlugin("Factions");
        if (FactionPlugin != null) {
            FactionsEnable = true;
        } else {
            log.warning("[FactionChat] Factions is not installed. For full features please install Factions");
        }

        new FactionChatAPI().setupAPI(this);

        new EssentialsAPI(this.getServer().getPluginManager().getPlugin("Essentials") != null);
        new AuthMeAPI(this.getServer().getPluginManager().getPlugin("AuthMe") != null);
        if (this.getServer().getPluginManager().getPlugin("mcMMO") != null) {
            getServer().getPluginManager().registerEvents(new McMMOAPI(this), this);
        }
        Plugin BanManager = this.getServer().getPluginManager().getPlugin("BanManager");
        if (BanManager != null && BanManager.isEnabled()) {
            if (Double.parseDouble(BanManager.getDescription().getVersion().substring(0, 2)) >= 4.0) {
                Config.banManagerEnabled = true;
                banManagerAPI = new BanManagerAPI(plugin);
            } else {
                log.info("[FactionChat] BanManager Version is not 4.0 or above. Unable to support. (please update)");
            }

        }

        if (FactionsEnable) {
            PreFactionsAPI preFactionsAPI = new PreFactionsAPI(loadFactionsAPI());
            factionsAPI = preFactionsAPI;

            log.info("[FactionChat] using Factions API of: " + preFactionsAPI.getFactionsAPI().getClass().getName());

            ChatChannel = new ChatChannel(this);
        }

        getServer().getPluginManager().registerEvents(new FactionChatListener(this), this); //FactionChat's Listener  

        Config.reload();
        if (this.getConfig().getBoolean("FactionInfoServer.enable")) {
            new FactionInfoServer(this.getConfig().getInt("FactionInfoServer.port"));
        }
        log.info(this.getName() + ": Version: " + version + " Enabled.");

    }

    @Override
    public void onDisable() {
        plugin = null;
        log.info(this.getName() + ": disabled");
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

    protected void checkConfig() {

        if (ChatFormat.getFactionChatMessage().contains("{M}") || ChatFormat.getFactionChatMessage().contains("%MESSAGE%")) {
            log.warning("[FactionChat]: " + ChatColor.RED + "Config has changed as of Version 1.10.0.");
            log.warning("[FactionChat]: " + ChatColor.RED + "Please redo your config.yml (I backed it up)");
            removeConfigFile();
            this.saveDefaultConfig();
            Config.reload();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        String commandName = command.getName().toLowerCase();

        if (!(sender instanceof Player)) { // this is for console only
            if (commandName.equalsIgnoreCase("fc") || commandName.equalsIgnoreCase("fchat")) {
                if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage(ChatColor.GOLD + "Current commands are:");
                    sender.sendMessage(ChatColor.GOLD + "/fc a" + ChatColor.GREEN + " Enter ally-only chat mode.");
                    sender.sendMessage(ChatColor.GOLD + "/fc t" + ChatColor.GREEN + " Enter truce-only chat mode.");
                    sender.sendMessage(ChatColor.GOLD + "/fc at" + ChatColor.GREEN + " Enter ally and truce chat mode.");
                    sender.sendMessage(ChatColor.GOLD + "/fc e" + ChatColor.GREEN + " Enter enemy chat mode.");
                    sender.sendMessage(ChatColor.GOLD + "/fc p" + ChatColor.GREEN + " Enter public chat mode (normal chat).");
                    sender.sendMessage(ChatColor.GOLD + "/fchatother <faction name> <message>" + ChatColor.GREEN + " Send a message to all members of the specified faction.");
                    sender.sendMessage(ChatColor.GREEN + "for more commands go to http://dev.bukkit.org/bukkit-plugins/factionchat/pages/commands/");
                    return true;
                } else if (args[0].equalsIgnoreCase("update")
                        && (sender.hasPermission("FactionChat.Update") || FactionChat.isDebugger(sender.getName()))) {
                    Updater updater = new Updater(this, 50517, this.getFile(), UpdateType.DEFAULT, true);
                    if (updater.getResult() == UpdateResult.SUCCESS) {
                        this.getLogger().info("updated to " + updater.getLatestName());
                        this.getLogger().info("full plugin reload is required");
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("reload")
                        && (sender.hasPermission("FactionChat.reload") || FactionChat.isDebugger(sender.getName()))) {
                    Config.reload();
                    sender.sendMessage("Reload Complete");
                    return true;
                } else if (args[0].equalsIgnoreCase("ver") || args[0].equalsIgnoreCase("version")) {
                    String version = Bukkit.getServer().getPluginManager().getPlugin(this.getName()).getDescription().getVersion();
                    sender.sendMessage("[FactionChat] Version is : " + version);
                    return true;
                }
            }

            sender.sendMessage("You must run this command in-game.");
            return false;
        }

        if (Config.disabledCommands.contains(commandName)) {
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
                    && Config.OtherChatEnable) {

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
                && Config.FactionChatEnable) {
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
                && Config.AllyTruceChatEnable) {
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
                && Config.AllyChatEnable) {
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
                && Config.TruceChatEnable) {
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
                && Config.EnemyChatEnable) {

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
                && Config.AdminChatEnable) {
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
                && Config.ModChatEnable) {
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
                && Config.ModChatEnable) {
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
                sender.sendMessage(ChatColor.RED + Config.messageNotInFaction);
                inFaction = false;
            }

            if (!inFaction) {
                ChatMode.fixPlayerNotInFaction(player);
                return;
            }
        }
        if (args.length == 0) {
            ChatMode.NextChatMode(player);
        } else if (args[0].equalsIgnoreCase("help")) {
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
            Config.reload();
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

        } else if (args[0].equalsIgnoreCase("join")) {
            if (sender.hasPermission("factionchat.spy")) {
                if (args.length >= 2) {
                    String playerName = args[1];
                    if (playerName.equalsIgnoreCase("clear") || playerName.equalsIgnoreCase("none") || playerName.equalsIgnoreCase("c") || playerName.equalsIgnoreCase("off")) {
                        StaffJoinFeature.clearPlayer(sender);
                        sender.sendMessage("cleared");
                    } else {
                        StaffJoinFeature.setTarget(sender, playerName);
                        sender.sendMessage("You have entered the same faction as " + playerName + " for chat");
                    }
                } else {
                    sender.sendMessage("Missing playerName");
                }
            } else {
                player.sendMessage("You don't have permission to run that command.");
            }

        } else if (player != null) {
            ChatMode.setChatMode(player, args[0]);
        } else {
            sender.sendMessage("You are not a player, you can still run /fc update and /fc reload");
        }

    }

    protected static void SetMessages(FileConfiguration config) {
        String Language = config.getString("MessageLanguage");
        Language = Language.toLowerCase();

        Config.messageNotInFaction = new FactionChatMessage(null, config.getString("message." + Language + ".NotInFaction"), true).toString();
        Config.messageIncorectChatModeSwitch = new FactionChatMessage(null, config.getString("message." + Language + ".IncorectChatModeSwitch"), true).toString();
        Config.messageSpyModeOn = new FactionChatMessage(null, config.getString("message." + Language + ".SpyModeOn"), true).toString();
        Config.messageSpyModeOff = new FactionChatMessage(null, config.getString("message." + Language + ".SpyModeOff"), true).toString();
        Config.messageNewChatMode = new FactionChatMessage(null, config.getString("message." + Language + ".NewChatMode"), true).toString();
        Config.messageFchatoMisstype = new FactionChatMessage(null, config.getString("message." + Language + ".FchatoMissType"), true).toString();
        Config.messageFchatoNoOneOnline = new FactionChatMessage(null, config.getString("message." + Language + ".FchatoNoOneOnline"), true).toString();
        Config.messagePublicMuteChatOn = new FactionChatMessage(null, config.getString("message." + Language + ".PublicMuteChatOn"), true).toString();
        Config.messagePublicMuteChatOff = new FactionChatMessage(null, config.getString("message." + Language + ".PublicMuteChatOff"), true).toString();
        Config.messageAllyMuteChatOn = new FactionChatMessage(null, config.getString("message." + Language + ".AllyMuteChatOn"), true).toString();
        Config.messageAllyMuteChatOff = new FactionChatMessage(null, config.getString("message." + Language + ".AllyMuteChatOff"), true).toString();

    }

    protected static boolean isDebugger(String playerName) {
        if (Config.ServerAllowAuthorDebugging && playerName.equals("james137137")) {
            return true;
        }
        return false;
    }

    public static boolean useBanManager() {
        return Config.banManagerEnabled;
    }

    private FactionsAPI loadFactionsAPI() {

        ComparableVersion facitonVersion = new ComparableVersion(getServer().getPluginManager().getPlugin("Factions").getDescription().getVersion());
        log.info("[FactionChat] loading Factions API for factions version " + facitonVersion);
        if (FactionChat.plugin.getDescription().getAuthors().contains("externo6")) {
            try {
                return (FactionsAPI) Class.forName("nz.co.lolnet.james137137.FactionChat.FactionsAPI.FactionsAPI_1_8_3_FactionsOne_1").getConstructor().newInstance();
            } catch (Exception ex) {
                Logger.getLogger(FactionChat.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (facitonVersion.compareTo(new ComparableVersion("1.6.999")) < 0) {
            try {
                return (FactionsAPI) Class.forName("nz.co.lolnet.james137137.FactionChat.FactionsAPI.FactionsAPI_1_6").getConstructor().newInstance();
            } catch (Exception ex) {
                Logger.getLogger(FactionChat.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (facitonVersion.compareTo(new ComparableVersion("1.9.999")) < 0) {
            try {
                return (FactionsAPI) Class.forName("nz.co.lolnet.james137137.FactionChat.FactionsAPI.FactionsAPI_1_8").getConstructor().newInstance();
            } catch (Exception ex) {
                Logger.getLogger(FactionChat.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (facitonVersion.compareTo(new ComparableVersion("2.3.999")) < 0) {
            try {
                return (FactionsAPI) Class.forName("nz.co.lolnet.james137137.FactionChat.FactionsAPI.FactionsAPI_2_0_0").getConstructor().newInstance();
            } catch (Exception ex) {
                Logger.getLogger(FactionChat.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (facitonVersion.compareTo(new ComparableVersion("2.6.999")) < 0) {
            try {
                return (FactionsAPI) Class.forName("nz.co.lolnet.james137137.FactionChat.FactionsAPI.FactionsAPI_2_6_0").getConstructor().newInstance();
            } catch (Exception ex) {
                Logger.getLogger(FactionChat.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (facitonVersion.compareTo(new ComparableVersion("2.7.999")) < 0) {
            try {
                return (FactionsAPI) Class.forName("nz.co.lolnet.james137137.FactionChat.FactionsAPI.FactionsAPI_2_7_0").getConstructor().newInstance();
            } catch (Exception ex) {
                Logger.getLogger(FactionChat.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (facitonVersion.compareTo(new ComparableVersion("2.8.999")) < 0) {
            try {
                return (FactionsAPI) Class.forName("nz.co.lolnet.james137137.FactionChat.FactionsAPI.FactionsAPI_2_8_0").getConstructor().newInstance();
            } catch (Exception ex) {
                Logger.getLogger(FactionChat.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (facitonVersion.compareTo(new ComparableVersion("2.9.999")) < 0) {
            try {
                return (FactionsAPI) Class.forName("nz.co.lolnet.james137137.FactionChat.FactionsAPI.FactionsAPI_2_9_0").getConstructor().newInstance();
            } catch (Exception ex) {
                Logger.getLogger(FactionChat.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (facitonVersion.compareTo(new ComparableVersion("2.12.999")) < 0) {
            try {
                return (FactionsAPI) Class.forName("nz.co.lolnet.james137137.FactionChat.FactionsAPI.FactionsAPI_2_12_0").getConstructor().newInstance();
            } catch (Exception ex) {
                Logger.getLogger(FactionChat.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            log.warning("[FactionChat]: This version of FactionChat might "
                    + "not support the latest factions, please update FactionChat;"
                    + " if there isn't one advailble nag james137137. Your version is: " + facitonVersion);
            try {
                return (FactionsAPI) Class.forName("nz.co.lolnet.james137137.FactionChat.FactionsAPI.FactionsAPI_2_12_0").getConstructor().newInstance();
            } catch (Exception ex) {
                Logger.getLogger(FactionChat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

}
