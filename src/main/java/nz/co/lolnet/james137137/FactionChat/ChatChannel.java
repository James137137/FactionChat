package nz.co.lolnet.james137137.FactionChat;

import nz.co.lolnet.james137137.FactionChat.API.ChatFormat;
import nz.co.lolnet.james137137.FactionChat.API.FactionChatAPI;
import nz.co.lolnet.james137137.FactionChat.API.EssentialsAPI;
import nz.co.lolnet.james137137.FactionChat.FactionsAPI.MyRel;
import nz.co.lolnet.james137137.FactionChat.ChatModeType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class ChatChannel {

    private FactionChat plugin;
    boolean IncludeTitle;

    ChatChannel(FactionChat aThis) {
        plugin = FactionChat.plugin;
        IncludeTitle = FactionChat.plugin.getConfig().getBoolean("FactionChatMessage.IncludeTitle");
    }

    /*
     * Sends a message to the player's Faction only.
     */
    protected void fChatF(Player player, String message) {

        if (!ChatMode.canChat(player.getName())) {
            return;
        }
        if (Config.limitWorldsChat) {
            if (Config.limitWorldsChatDisableSend && !Config.limitWorldsChatWorlds.contains(player.getWorld().getName())) {
                player.sendMessage(ChatColor.RED + "Sorry, but you can't send a FactionChat message in this world");
            }
        }
        String senderFaction = FactionChat.factionsAPI.getFactionName(player); //obtains player's faction name
        if (senderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + Config.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        message = FactionChatAPI.filterChat(player, message);
        String playerName;
        if (FactionChat.useEssentialsNick) {
            playerName = "~" + EssentialsAPI.getNickname(player);
            if (playerName.equalsIgnoreCase("~" + player.getName())) {
                playerName = player.getName();
            }
        } else {
            playerName = player.getName();
        }
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String playerTitle = "";
        if (Config.IncludeTitle) {
            playerTitle = FactionChat.factionsAPI.getPlayerTitle(player);
        }

        String playerPrefix = FactionChatAPI.getPrefix(player);
        String playerSuffix = FactionChatAPI.getSuffix(player);
        String playerFactionTitle = playerTitle;
        String playerFactionRank = FactionChat.factionsAPI.getPlayerRank(player).toString();
        String FacitonName = senderFaction;
        String otherFactionName = null;

        String normalMessage = new FactionChatMessage(ChatFormat.getFactionChatMessage(), message, allowCustomColour, playerPrefix, playerName, playerSuffix, playerFactionTitle, playerFactionRank, FacitonName, otherFactionName).toString();
        String spyMessage = new FactionChatMessage(ChatFormat.getSpyChat(), normalMessage, allowCustomColour).toString();

        senderFaction = FactionChat.factionsAPI.getFactionID(player);
        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

            if (!ChatMode.IsPlayerMutedTarget(myPlayer, player) && FactionChat.factionsAPI.getFactionID(myPlayer).equalsIgnoreCase(senderFaction) && myPlayer.hasPermission("FactionChat.FactionChat")) {
                if (FactionChatAPI.canReceiveChat(myPlayer)) {
                    myPlayer.sendMessage(normalMessage);
                }
            } else if (ChatMode.isSpyOn(myPlayer)) {

                if (FactionChatAPI.canReceiveChat(myPlayer)) {
                    myPlayer.sendMessage(spyMessage);
                }
            }
        }

    }

    /*
     * Sends a message to the player's Faction 
     * and everyone that is in a Faction that is ally or truce with the player's Faction.
     */
    protected void fChatAT(Player player, String message) {

        if (!ChatMode.canChat(player.getName())) {
            return;
        }
        if (Config.limitWorldsChat) {
            if (Config.limitWorldsChatDisableSend && !Config.limitWorldsChatWorlds.contains(player.getWorld().getName())) {
                player.sendMessage(ChatColor.RED + "Sorry, but you can't send a FactionChat message in this world");
            }
        }
        String sSenderFaction = FactionChat.factionsAPI.getFactionName(player); //obtains player's faction name

        if (sSenderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + Config.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        message = FactionChatAPI.filterChat(player, message);
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String playerTitle = "";
        if (Config.IncludeTitle) {
            playerTitle = FactionChat.factionsAPI.getPlayerTitle(player);
        }
        String playerName;
        if (FactionChat.useEssentialsNick) {
            playerName = "~" + EssentialsAPI.getNickname(player);
            if (playerName.equalsIgnoreCase("~" + player.getName())) {
                playerName = player.getName();
            }
        } else {
            playerName = player.getName();
        }

        String playerPrefix = FactionChatAPI.getPrefix(player);
        String playerSuffix = FactionChatAPI.getSuffix(player);
        String playerFactionTitle = playerTitle;
        String playerFactionRank = FactionChat.factionsAPI.getPlayerRank(player).toString();
        String FacitonName = sSenderFaction;
        String otherFactionName = null;

        String normalMessage = new FactionChatMessage(ChatFormat.getAllyTruceChat(), message, allowCustomColour, playerPrefix, playerName, playerSuffix, playerFactionTitle, playerFactionRank, FacitonName, otherFactionName).toString();
        String spyMessage = new FactionChatMessage(ChatFormat.getSpyChat(), normalMessage, allowCustomColour).toString();

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

            MyRel relationship = FactionChat.factionsAPI.getRelationship(player, myPlayer);

            if (!ChatMode.IsPlayerMutedTarget(myPlayer, player) && (relationship == MyRel.ALLY || relationship == MyRel.TRUCE
                    || FactionChat.factionsAPI.getFactionName(myPlayer).equalsIgnoreCase(sSenderFaction))
                    && myPlayer.hasPermission("FactionChat.AllyChat") && myPlayer.hasPermission("FactionChat.TruceChat") && !ChatMode.IsAllyMuted(myPlayer)) {
                if (FactionChatAPI.canReceiveChat(myPlayer)) {
                    myPlayer.sendMessage(normalMessage);
                }
            } else if (ChatMode.isSpyOn(myPlayer)) {
                if (FactionChatAPI.canReceiveChat(myPlayer)) {
                    myPlayer.sendMessage(spyMessage);
                }
            }
        }
    }

    public void fChatA(Player player, String message) {

        if (!ChatMode.canChat(player.getName())) {
            return;
        }
        if (Config.limitWorldsChat) {
            if (Config.limitWorldsChatDisableSend && !Config.limitWorldsChatWorlds.contains(player.getWorld().getName())) {
                player.sendMessage(ChatColor.RED + "Sorry, but you can't send a FactionChat message in this world");
            }
        }

        String sSenderFaction = FactionChat.factionsAPI.getFactionName(player); //obtains player's faction name

        if (sSenderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + Config.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        message = FactionChatAPI.filterChat(player, message);
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String playerTitle = "";
        if (Config.IncludeTitle) {
            playerTitle = FactionChat.factionsAPI.getPlayerTitle(player);
        }
        String playerName;
        if (FactionChat.useEssentialsNick) {
            playerName = "~" + EssentialsAPI.getNickname(player);
            if (playerName.equalsIgnoreCase("~" + player.getName())) {
                playerName = player.getName();
            }
        } else {
            playerName = player.getName();
        }

        String playerPrefix = FactionChatAPI.getPrefix(player);
        String playerSuffix = FactionChatAPI.getSuffix(player);
        String playerFactionTitle = playerTitle;
        String playerFactionRank = FactionChat.factionsAPI.getPlayerRank(player).toString();
        String FacitonName = sSenderFaction;
        String otherFactionName = null;

        String normalMessage = new FactionChatMessage(ChatFormat.getAllyChat(), message, allowCustomColour, playerPrefix, playerName, playerSuffix, playerFactionTitle, playerFactionRank, FacitonName, otherFactionName).toString();
        String spyMessage = new FactionChatMessage(ChatFormat.getSpyChat(), normalMessage, allowCustomColour).toString();

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

            MyRel relationship = FactionChat.factionsAPI.getRelationship(player, myPlayer);

            if (!ChatMode.IsPlayerMutedTarget(myPlayer, player) && (relationship == MyRel.ALLY
                    || FactionChat.factionsAPI.getFactionName(myPlayer).equalsIgnoreCase(sSenderFaction))
                    && myPlayer.hasPermission("FactionChat.AllyChat") && !ChatMode.IsAllyMuted(myPlayer)) {
                if (FactionChatAPI.canReceiveChat(myPlayer)) {
                    myPlayer.sendMessage(normalMessage);
                }
            } else if (ChatMode.isSpyOn(myPlayer)) {
                if (FactionChatAPI.canReceiveChat(myPlayer)) {
                    myPlayer.sendMessage(spyMessage);
                }
            }
        }
    }

    public void fChatTruce(Player player, String message) {

        if (!ChatMode.canChat(player.getName())) {
            return;
        }
        if (Config.limitWorldsChat) {
            if (Config.limitWorldsChatDisableSend && !Config.limitWorldsChatWorlds.contains(player.getWorld().getName())) {
                player.sendMessage(ChatColor.RED + "Sorry, but you can't send a FactionChat message in this world");
            }
        }

        String sSenderFaction = FactionChat.factionsAPI.getFactionName(player); //obtains player's faction name

        if (sSenderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + Config.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        message = FactionChatAPI.filterChat(player, message);
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String playerTitle = "";
        if (Config.IncludeTitle) {
            playerTitle = FactionChat.factionsAPI.getPlayerTitle(player);
        }
        String playerName;
        if (FactionChat.useEssentialsNick) {
            playerName = "~" + EssentialsAPI.getNickname(player);
            if (playerName.equalsIgnoreCase("~" + player.getName())) {
                playerName = player.getName();
            }
        } else {
            playerName = player.getName();
        }
        String playerPrefix = FactionChatAPI.getPrefix(player);
        String playerSuffix = FactionChatAPI.getSuffix(player);
        String playerFactionTitle = playerTitle;
        String playerFactionRank = FactionChat.factionsAPI.getPlayerRank(player).toString();
        String FacitonName = sSenderFaction;
        String otherFactionName = null;

        String normalMessage = new FactionChatMessage(ChatFormat.getTruceChat(), message, allowCustomColour, playerPrefix, playerName, playerSuffix, playerFactionTitle, playerFactionRank, FacitonName, otherFactionName).toString();
        String spyMessage = new FactionChatMessage(ChatFormat.getSpyChat(), normalMessage, allowCustomColour).toString();

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

            MyRel relationship = FactionChat.factionsAPI.getRelationship(player, myPlayer);

            if (!ChatMode.IsPlayerMutedTarget(myPlayer, player) && (relationship == MyRel.TRUCE
                    || sSenderFaction.equalsIgnoreCase(FactionChat.factionsAPI.getFactionName(myPlayer)) || FactionChat.factionsAPI.getFactionName(myPlayer).equalsIgnoreCase(sSenderFaction))
                    && myPlayer.hasPermission("FactionChat.TruceChat") && !ChatMode.IsAllyMuted(myPlayer)) {
                if (FactionChatAPI.canReceiveChat(myPlayer)) {
                    myPlayer.sendMessage(normalMessage);
                }
            } else if (ChatMode.isSpyOn(myPlayer)) {
                if (FactionChatAPI.canReceiveChat(myPlayer)) {
                    myPlayer.sendMessage(spyMessage);
                }
            }
        }
    }

    /*
     * Sends a message to the player's Faction 
     * and everyone that is in a Faction that enermies with the player's Faction.
     */
    protected void fChatE(Player player, String message) {

        if (!ChatMode.canChat(player.getName())) {
            return;
        }
        if (Config.limitWorldsChat) {
            if (Config.limitWorldsChatDisableSend && !Config.limitWorldsChatWorlds.contains(player.getWorld().getName())) {
                player.sendMessage(ChatColor.RED + "Sorry, but you can't send a FactionChat message in this world");
            }
        }

        String sSenderFaction = FactionChat.factionsAPI.getFactionName(player); //obtains player's faction name

        if (sSenderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + Config.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        message = FactionChatAPI.filterChat(player, message);
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String playerTitle = "";
        if (Config.IncludeTitle) {
            playerTitle = FactionChat.factionsAPI.getPlayerTitle(player);
        }
        String playerName;
        if (FactionChat.useEssentialsNick) {
            playerName = "~" + EssentialsAPI.getNickname(player);
            if (playerName.equalsIgnoreCase("~" + player.getName())) {
                playerName = player.getName();
            }
        } else {
            playerName = player.getName();
        }
        String playerPrefix = FactionChatAPI.getPrefix(player);
        String playerSuffix = FactionChatAPI.getSuffix(player);
        String playerFactionTitle = playerTitle;
        String playerFactionRank = FactionChat.factionsAPI.getPlayerRank(player).toString();
        String FacitonName = sSenderFaction;
        String otherFactionName = null;

        String normalMessage = new FactionChatMessage(ChatFormat.getEnemyChat(), message, allowCustomColour, playerPrefix, playerName, playerSuffix, playerFactionTitle, playerFactionRank, FacitonName, otherFactionName).toString();
        String spyMessage = new FactionChatMessage(ChatFormat.getSpyChat(), normalMessage, allowCustomColour).toString();

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

            MyRel relationship = FactionChat.factionsAPI.getRelationship(player, myPlayer);

            if (!ChatMode.IsPlayerMutedTarget(myPlayer, player) && (relationship == MyRel.ENEMY
                    || sSenderFaction.equalsIgnoreCase(FactionChat.factionsAPI.getFactionName(player)) || FactionChat.factionsAPI.getFactionName(myPlayer).equalsIgnoreCase(sSenderFaction))
                    && myPlayer.hasPermission("FactionChat.EnemyChat") && !FactionChat.factionsAPI.isFactionless(myPlayer) && ChatMode.getChatMode(myPlayer) == ChatModeType.ENEMY) {
                if (FactionChatAPI.canReceiveChat(myPlayer)) {
                    myPlayer.sendMessage(normalMessage);
                }
            } else if (ChatMode.isSpyOn(myPlayer)) {
                if (FactionChatAPI.canReceiveChat(myPlayer)) {
                    myPlayer.sendMessage(spyMessage);
                }
            }
        }

    }

    protected void fchato(CommandSender sender, String[] args) {

        if (!ChatMode.canChat(sender.getName())) {
            return;
        }
        if (Config.limitWorldsChat && sender instanceof Player) {
            if (Config.limitWorldsChatDisableSend && !Config.limitWorldsChatWorlds.contains(((Player) sender).getWorld().getName())) {
                sender.sendMessage(ChatColor.RED + "Sorry, but you can't send a FactionChat message in this world");
            }
        }

        Player player = (Player) sender;//get player

        if (args.length <= 1) { //checks if there is a message in command eg "hello world" in /fchat hello world
            player.sendMessage(Config.messageFchatoMisstype);
        } else {

            String senderFaction = FactionChat.factionsAPI.getFactionName(player);

            if (senderFaction.contains("Wilderness")) { //checks if player is in a faction
                player.sendMessage(ChatColor.RED + Config.messageNotInFaction);
                //start of sending the message
                /*
                 * checks every player online to see if they belong in the senders faction if so it receives the message
                 * 
                 * added a admin chatspy with permision faction.spy
                 */
            } else {
                String message = "";
                for (int i = 1; i < args.length; i++) {
                    message += args[i] + " ";
                }
                message = FactionChatAPI.filterChat(player, message);

                String playersFaction; //creates string outside loop
                String targetFaction = args[0] + senderFaction.charAt(senderFaction.length() - 2) + senderFaction.charAt(senderFaction.length() - 1);

                int count = 0;
                boolean allowCustomColour = player.hasPermission("essentials.chat.color");
                String playerTitle = "";
                if (Config.IncludeTitle) {
                    playerTitle = FactionChat.factionsAPI.getPlayerTitle(player);
                }
                String playerName;
                if (FactionChat.useEssentialsNick) {
                    playerName = "~" + EssentialsAPI.getNickname(player);
                    if (playerName.equalsIgnoreCase("~" + player.getName())) {
                        playerName = player.getName();
                    }
                } else {
                    playerName = player.getName();
                }
                String playerPrefix = FactionChatAPI.getPrefix(player);
                String playerSuffix = FactionChatAPI.getSuffix(player);
                String playerFactionTitle = playerTitle;
                String playerFactionRank = FactionChat.factionsAPI.getPlayerRank(player).toString();
                String FacitonName = senderFaction;
                String otherFactionName = targetFaction;

                String toMessage = new FactionChatMessage(ChatFormat.getOtherFactionChatTo(), message, allowCustomColour, playerPrefix, playerName, playerSuffix, playerFactionTitle, playerFactionRank, FacitonName, otherFactionName).toString();
                String FromMessage = new FactionChatMessage(ChatFormat.getOtherFactionChatFrom(), message, allowCustomColour, playerPrefix, playerName, playerSuffix, playerFactionTitle, playerFactionRank, FacitonName, otherFactionName).toString();
                String spyMessage = new FactionChatMessage(ChatFormat.getSpyChat(), toMessage, allowCustomColour).toString();

                //start of loop
                for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

                    playersFaction = FactionChat.factionsAPI.getFactionName(myPlayer);

                    if (playersFaction.equalsIgnoreCase(senderFaction)) {
                        if (FactionChatAPI.canReceiveChat(myPlayer)) {
                            myPlayer.sendMessage(toMessage);
                        }
                    } else if (playersFaction.equalsIgnoreCase(targetFaction)) {
                        if (FactionChatAPI.canReceiveChat(myPlayer)) {
                            myPlayer.sendMessage(FromMessage);
                        }
                        count++;
                    } else if (ChatMode.isSpyOn(myPlayer)) {
                        if (FactionChatAPI.canReceiveChat(myPlayer)) {
                            myPlayer.sendMessage(spyMessage);
                        }
                    }

                }

                if (count == 0) {
                    player.sendMessage(ChatColor.RED + Config.messageFchatoNoOneOnline);
                }
            }
        }

    }

    protected void fChatLeader(Player player, String message) {

        if (!ChatMode.canChat(player.getName())) {
            return;
        }
        if (Config.limitWorldsChat) {
            if (Config.limitWorldsChatDisableSend && !Config.limitWorldsChatWorlds.contains(player.getWorld().getName())) {
                player.sendMessage(ChatColor.RED + "Sorry, but you can't send a FactionChat message in this world");
            }
        }

        String senderFaction = FactionChat.factionsAPI.getFactionName(player); //obtains player's faction name

        if (senderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + Config.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        message = FactionChatAPI.filterChat(player, message);
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String playerTitle = "";
        if (Config.IncludeTitle) {
            playerTitle = FactionChat.factionsAPI.getPlayerTitle(player);
        }
        String playerName;
        if (FactionChat.useEssentialsNick) {
            playerName = "~" + EssentialsAPI.getNickname(player);
            if (playerName.equalsIgnoreCase("~" + player.getName())) {
                playerName = player.getName();
            }
        } else {
            playerName = player.getName();
        }

        String playerPrefix = FactionChatAPI.getPrefix(player);
        String playerSuffix = FactionChatAPI.getSuffix(player);
        String playerFactionTitle = playerTitle;
        String playerFactionRank = FactionChat.factionsAPI.getPlayerRank(player).toString();
        String FacitonName = senderFaction;
        String otherFactionName = null;

        String normalMessage = new FactionChatMessage(ChatFormat.getOtherFactionChatTo(), message, allowCustomColour, playerPrefix, playerName, playerSuffix, playerFactionTitle, playerFactionRank, FacitonName, otherFactionName).toString();
        String spyMessage = new FactionChatMessage(ChatFormat.getSpyChat(), normalMessage, allowCustomColour).toString();

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

            if (!ChatMode.IsPlayerMutedTarget(myPlayer, player) && FactionChatAPI.getPlayerRank(player).equals(Config.LeaderRank) && ChatMode.getChatMode(myPlayer) == ChatModeType.LEADER) {
                if (FactionChatAPI.canReceiveChat(myPlayer)) {
                    myPlayer.sendMessage(normalMessage);
                }
            } else if (ChatMode.isSpyOn(myPlayer)) {
                if (FactionChatAPI.canReceiveChat(myPlayer)) {
                    myPlayer.sendMessage(spyMessage);
                }
            }
        }

    }

    protected void fChatOfficer(Player player, String message) {

        if (!ChatMode.canChat(player.getName())) {
            return;
        }
        if (Config.limitWorldsChat) {
            if (Config.limitWorldsChatDisableSend && !Config.limitWorldsChatWorlds.contains(player.getWorld().getName())) {
                player.sendMessage(ChatColor.RED + "Sorry, but you can't send a FactionChat message in this world");
            }
        }

        String senderFaction = FactionChat.factionsAPI.getFactionName(player); //obtains player's faction name

        if (senderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + Config.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        message = FactionChatAPI.filterChat(player, message);
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String playerTitle = "";
        if (Config.IncludeTitle) {
            playerTitle = FactionChat.factionsAPI.getPlayerTitle(player);
        }
        String playerName;
        if (FactionChat.useEssentialsNick) {
            playerName = "~" + EssentialsAPI.getNickname(player);
            if (playerName.equalsIgnoreCase("~" + player.getName())) {
                playerName = player.getName();
            }
        } else {
            playerName = player.getName();
        }
        String playerPrefix = FactionChatAPI.getPrefix(player);
        String playerSuffix = FactionChatAPI.getSuffix(player);
        String playerFactionTitle = playerTitle;
        String playerFactionRank = FactionChat.factionsAPI.getPlayerRank(player).toString();
        String FacitonName = senderFaction;
        String otherFactionName = null;

        String normalMessage = new FactionChatMessage(ChatFormat.getOfficerChat(), message, allowCustomColour, playerPrefix, playerName, playerSuffix, playerFactionTitle, playerFactionRank, FacitonName, otherFactionName).toString();
        String spyMessage = new FactionChatMessage(ChatFormat.getSpyChat(), normalMessage, allowCustomColour).toString();

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

            if (!ChatMode.IsPlayerMutedTarget(myPlayer, player) && (FactionChatAPI.getPlayerRank(player).equals(Config.LeaderRank)
                    || FactionChatAPI.getPlayerRank(player).equals(Config.OfficerRank)) && ChatMode.getChatMode(myPlayer) == ChatModeType.OFFICER) {
                if (FactionChatAPI.canReceiveChat(myPlayer)) {
                    myPlayer.sendMessage(normalMessage);
                }
            } else if (ChatMode.isSpyOn(myPlayer)) {
                if (FactionChatAPI.canReceiveChat(myPlayer)) {
                    myPlayer.sendMessage(spyMessage);
                }
            }
        }

    }

}
