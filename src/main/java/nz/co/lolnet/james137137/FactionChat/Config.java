/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class Config {

    public static String OtherFactionChatTo;
    public static String JrAdminChat;
    public static String AllyChat;
    public static String SpyChat;
    public static String OtherFactionChatFrom;
    public static String UAChat;
    public static String ModChat;
    public static String LeaderChat;
    public static String EnemyChat;
    public static String OtherFactionChatSpy;
    public static String JrModChat;
    public static String VIPChat;
    public static String OfficerChat;
    public static String TruceChat;
    public static String FactionChatMessage;
    public static String AdminChat;
    public static String AllyTruceChat;
    public static String SrModChat;
    public static String OfficerRank;
    public static String messagePublicMuteChatOff;
    public static boolean VIPChatEnable;
    public static String messageFchatoNoOneOnline;
    public static boolean AdminChatEnable;
    public static boolean LeaderChatEnable;
    public static boolean JrAdminChatEnable;
    public static String RecruitRank;
    public static String messagePublicMuteChatOn;
    public static boolean ModChatEnable;
    public static boolean IncludeTitle;
    public static String messageSpyModeOn;
    public static boolean OfficerChatEnable;
    public static boolean EnemyChatEnable;
    public static String messageAllyMuteChatOn;
    public static String messageSpyModeOff;
    public static String MemberRank;
    public static boolean UAChatEnable;
    public static boolean spyModeOnByDefault = true;
    public static String messageIncorectChatModeSwitch;
    public static boolean OtherChatEnable;
    public static boolean SrModChatEnable;
    public static String messageFchatoMisstype;
    public static boolean FactionChatEnable;
    public static boolean TruceChatEnable;
    public static boolean ServerAllowAuthorDebugging;
    public static boolean AllyChatEnable;
    public static String LeaderRank;
    public static String messageAllyMuteChatOff;
    public static String messageNewChatMode;
    public static boolean AllyTruceChatEnable;
    public static boolean JrModChatEnable;
    //messages for Chat colour. Theses are customiziable in conf file.
    public static String messageNotInFaction;
    public static boolean PublicMuteDefault = false;
    public static List<String> disabledCommands;
    public static boolean banManagerEnabled = false;

    public static void reload() {
        ChatMode.initialize(FactionChat.plugin);
        try {
            try {
                FactionChat.plugin.reloadConfig();
            } catch (Exception e) {
                FactionChat.log.warning("[FactionChat]: reloadConfig() failed on reload()");
            }
            FileConfiguration config = FactionChat.plugin.getConfig();
            Config.FactionChatMessage = config.getString("FactionChatMessage.FactionChat");
            Config.AllyTruceChat = config.getString("FactionChatMessage.AllyTruceChat");
            Config.AllyChat = config.getString("FactionChatMessage.AllyChat");
            Config.TruceChat = config.getString("FactionChatMessage.TruceChat");
            Config.EnemyChat = config.getString("FactionChatMessage.EnemyChat");
            Config.LeaderChat = config.getString("FactionChatMessage.LeaderChat");
            Config.OfficerChat = config.getString("FactionChatMessage.OfficerChat");
            Config.OtherFactionChatTo = config.getString("FactionChatMessage.OtherFactionChatTo");
            Config.OtherFactionChatFrom = config.getString("FactionChatMessage.OtherFactionChatFrom");
            Config.OtherFactionChatSpy = config.getString("FactionChatMessage.OtherFactionChatSpy");
            Config.SpyChat = config.getString("FactionChatMessage.SpyChat");
            Config.ModChat = config.getString("OtherChatMessage.ModChat");
            Config.AdminChat = config.getString("OtherChatMessage.AdminChat");
            Config.UAChat = config.getString("OtherChatMessage.UAChat");
            Config.VIPChat = config.getString("OtherChatMessage.VIPChat");
            Config.JrModChat = config.getString("OtherChatMessage.JrModChat");
            Config.SrModChat = config.getString("OtherChatMessage.SrModChat");
            Config.JrAdminChat = config.getString("OtherChatMessage.JrAdminChat");
            Config.LeaderRank = config.getString("FactionRank.Leader");
            Config.OfficerRank = config.getString("FactionRank.Officer");
            Config.MemberRank = config.getString("FactionRank.Member");
            Config.RecruitRank = config.getString("FactionRank.Recruit");
            Config.spyModeOnByDefault = config.getBoolean("spyModeOnByDefault");
            Config.IncludeTitle = config.getBoolean("FactionChatMessage.IncludeTitle");
            Config.FactionChatEnable = config.getBoolean("FactionChatEnable");
            Config.AllyChatEnable = config.getBoolean("AllyChatEnable");
            Config.TruceChatEnable = config.getBoolean("TruceChatEnable");
            Config.AllyTruceChatEnable = config.getBoolean("AllyTruceChatEnable");
            Config.EnemyChatEnable = config.getBoolean("EnemyChatEnable");
            Config.LeaderChatEnable = config.getBoolean("LeaderChatEnable");
            Config.OfficerChatEnable = config.getBoolean("OfficerChatEnable");
            Config.OtherChatEnable = config.getBoolean("OtherChatEnable");
            Config.ModChatEnable = config.getBoolean("ModChatEnable");
            Config.AdminChatEnable = config.getBoolean("AdminChatEnable");
            Config.JrModChatEnable = config.getBoolean("JrModChatEnable");
            Config.SrModChatEnable = config.getBoolean("SrModChatEnable");
            Config.JrAdminChatEnable = config.getBoolean("JrAdminChatEnable");
            Config.UAChatEnable = config.getBoolean("UAChatEnable");
            Config.VIPChatEnable = config.getBoolean("VIPChatEnable");
            Config.ServerAllowAuthorDebugging = FactionChat.plugin.getServer().getOnlineMode() && config.getBoolean("AllowAuthorDebugAccess");
            FactionChat.FactionsCommand = config.getString("FactionsCommand");
            Config.PublicMuteDefault = config.getBoolean("PublicMuteDefault");
            Config.disabledCommands = config.getStringList("DisabledCommands");
            if (!Config.FactionChatEnable && !Config.AllyChatEnable && !Config.EnemyChatEnable && !Config.OtherChatEnable) {
                FactionChat.FactionsEnable = false;
            }
            if (!FactionChat.FactionsEnable) {
                Config.FactionChatEnable = false;
                Config.EnemyChatEnable = false;
                Config.AllyChatEnable = false;
                Config.TruceChatEnable = false;
                Config.OtherChatEnable = false;
                Config.AllyTruceChatEnable = false;
                Config.LeaderChatEnable = false;
                Config.OfficerChatEnable = false;
            }
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                ChatMode.SetNewChatMode(player);
            }
            FactionChat.SetMessages(config);
            //TODO
            FactionChat.plugin.checkConfig();
        } catch (Exception e) {
            if (FactionChat.plugin.reloadCountCheck == 1) {
                FactionChat.log.warning("[FactionChat] Something is wrong with FactionChat Plugin, I can't fix your null in your config file");
                return;
            }
            FactionChat.plugin.removeConfigFile();
            FactionChat.plugin.saveDefaultConfig();
            FactionChat.plugin.reloadCountCheck = 1;
            reload();
        }
        //null checker
        if (Config.FactionChatMessage == null) {
            FactionChat.log.info("[FactionChat]: found a null in the config file....remaking the config");
            if (FactionChat.plugin.reloadCountCheck == 1) {
                FactionChat.log.warning("[FactionChat] Something is wrong with FactionChat Plugin, I can't fix your null in your config file");
                return;
            }
            FactionChat.plugin.removeConfigFile();
            FactionChat.plugin.saveDefaultConfig();
            FactionChat.plugin.reloadCountCheck = 1;
            reload();
        } else {
            FactionChat.plugin.reloadCountCheck = 0;
        }
        //loadMyNewConfig();
    }
    
}
