/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat;

import java.util.List;
import nz.co.lolnet.james137137.FactionChat.API.ChatFormat;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class Config {

    public static String OfficerRank;
    public static String messagePublicMuteChatOff;

    public static boolean OtherChatEnable;
    public static boolean VIPChatEnable;
    public static boolean UAChatEnable;
    public static boolean JrModChatEnable;
    public static boolean ModChatEnable;
    public static boolean SrModChatEnable;
    public static boolean JrAdminChatEnable;
    public static boolean AdminChatEnable;

    public static boolean FactionChatEnable;
    public static boolean TruceChatEnable;
    public static boolean AllyChatEnable;
    public static boolean AllyTruceChatEnable;
    public static boolean EnemyChatEnable;
    public static boolean OfficerChatEnable;
    public static boolean LeaderChatEnable;

    public static boolean IncludeTitle;
    public static boolean spyModeOnByDefault = true;
    public static boolean PublicMuteDefault = false;

    public static String messageFchatoNoOneOnline;
    public static String RecruitRank;
    public static String messagePublicMuteChatOn;

    public static String messageSpyModeOn;

    public static String messageAllyMuteChatOn;
    public static String messageSpyModeOff;
    public static String MemberRank;

    public static String messageIncorectChatModeSwitch;

    public static String messageFchatoMisstype;

    public static boolean ServerAllowAuthorDebugging;

    public static String LeaderRank;
    public static String messageAllyMuteChatOff;
    public static String messageNewChatMode;

    //messages for Chat colour. Theses are customiziable in conf file.
    public static String messageNotInFaction;

    public static List<String> disabledCommands;
    public static boolean banManagerEnabled = false;

    // LimitWorldStuff
    public static boolean limitWorldsChat = false;
    public static List<String> limitWorldsChatWorlds;
    public static boolean limitWorldsChatDisableSend = true;
    public static boolean limitWorldsChatDisableReceive = true;
    public static boolean limitWorldsChatDisableOther = true;

    public static void reload() {
        ChatMode.initialize(FactionChat.plugin);
        try {
            try {
                FactionChat.plugin.reloadConfig();
            } catch (Exception e) {
                FactionChat.log.warning("[FactionChat]: reloadConfig() failed on reload()");
            }
            FileConfiguration config = FactionChat.plugin.getConfig();
            ChatFormat.setFactionChatMessage(config.getString("FactionChatMessage.FactionChat"));
            ChatFormat.setAllyTruceChat(config.getString("FactionChatMessage.AllyTruceChat"));
            ChatFormat.setAllyChat(config.getString("FactionChatMessage.AllyChat"));
            ChatFormat.setTruceChat(config.getString("FactionChatMessage.TruceChat"));
            ChatFormat.setEnemyChat(config.getString("FactionChatMessage.EnemyChat"));

            ChatFormat.setLeaderChat(config.getString("FactionChatMessage.LeaderChat"));

            ChatFormat.setOfficerChat(config.getString("FactionChatMessage.OfficerChat"));
            ChatFormat.setOtherFactionChatTo(config.getString("FactionChatMessage.OtherFactionChatTo"));
            ChatFormat.setOtherFactionChatFrom(config.getString("FactionChatMessage.OtherFactionChatFrom"));
            ChatFormat.setSpyChat(config.getString("FactionChatMessage.SpyChat"));
            ChatFormat.setModChat(config.getString("OtherChatMessage.ModChat"));
            ChatFormat.setAdminChat(config.getString("OtherChatMessage.AdminChat"));
            ChatFormat.setUAChat(config.getString("OtherChatMessage.UAChat"));
            ChatFormat.setVIPChat(config.getString("OtherChatMessage.VIPChat"));
            ChatFormat.setJrModChat(config.getString("OtherChatMessage.JrModChat"));
            ChatFormat.setSrModChat(config.getString("OtherChatMessage.SrModChat"));
            ChatFormat.setJrAdminChat(config.getString("OtherChatMessage.JrAdminChat"));
            
            
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

            limitWorldsChat = config.getBoolean("limitworlds.enable");
            limitWorldsChatWorlds = config.getStringList("limitworlds.worlds");
            limitWorldsChatDisableSend = config.getBoolean("limitworlds.disablesendoutside");
            limitWorldsChatDisableReceive = config.getBoolean("limitworlds.disablereceiveoutside");
            limitWorldsChatDisableOther = config.getBoolean("limitworlds.disableOtherChat");

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
        if (ChatFormat.getFactionChatMessage() == null) {
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
