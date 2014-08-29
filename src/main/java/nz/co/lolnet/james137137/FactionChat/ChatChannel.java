package nz.co.lolnet.james137137.FactionChat;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Rel;
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
    private static Player[] onlinePlayerList;
    boolean IncludeTitle;

    ChatChannel(FactionChat aThis) {
        plugin = FactionChat.plugin;
        IncludeTitle = FactionChat.plugin.getConfig().getBoolean("FactionChatMessage.IncludeTitle");
    }

    protected static String getPlayerRank(Player player) {
        Rel role = ((FPlayer) FPlayers.i.get(player)).getRole();
        if (role.equals(Rel.LEADER)) {
            return FactionChat.LeaderRank;
        } else if (role.equals(Rel.OFFICER)) {
            return FactionChat.OfficerRank;
        } else if (role.equals(Rel.MEMBER)) {
            return FactionChat.MemberRank;
        } else if (role.equals(Rel.RECRUIT)) {
            return FactionChat.RecruitRank;
        } else {
            return "";
        }

    }

    /**
     *
     * Returns player's Factions Name.
     *
     */
    protected static String getFactionName(Player player) {
        return ((FPlayer) FPlayers.i.get(player)).getFaction().getTag();
    }

    protected static String getFactionID(Player player) {
        return ((FPlayer) FPlayers.i.get(player)).getFaction().getId();
    }

    /**
     *
     * Returns player's Factions Name.
     *
     */
    protected static String getFactionName(FPlayer fPlayer) {
        return fPlayer.getFaction().getTag() + ChatColor.RESET;
    }

    /**
     *
     * Returns player's Factions Title.
     *
     */
    protected static String getPlayerTitle(Player player) {
        String title = ((FPlayer) FPlayers.i.get(player)).getTitle();
        if (title.contains("no title set")) {
            return "";
        }
        return title;
    }

    /*
     * Sends a message to the player's Faction only.
     */
    protected void fChatF(Player player, String message) {

        if (!ChatMode.canChat(player.getName())) {
            return;
        }
        String senderFaction = getFactionName(player); //obtains player's faction name
        if (senderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String[] intput1 = {senderFaction, getPlayerRank(player), FactionChatAPI.getPrefix(player) + player.getName() + FactionChatAPI.getSuffix(player) + ChatColor.RESET, message};
        String playerTitle = getPlayerTitle(player);
        String[] input2 = {ChatMode.FormatString(FactionChat.FactionChatMessage, intput1, playerTitle, allowCustomColour)};
        String normalMessage = ChatMode.FormatString(FactionChat.FactionChatMessage, intput1, playerTitle, allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2, playerTitle, allowCustomColour);
        senderFaction = getFactionID(player);
        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

            if (getFactionID(myPlayer).equalsIgnoreCase(senderFaction) && myPlayer.hasPermission("FactionChat.FactionChat")) {
                myPlayer.sendMessage(normalMessage);
            } else if (ChatMode.isSpyOn(myPlayer)) {

                myPlayer.sendMessage(spyMessage);
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
        String sSenderFaction = getFactionName(player); //obtains player's faction name

        if (sSenderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String playerTitle = getPlayerTitle(player);
        String[] intput1 = {sSenderFaction, getPlayerRank(player), FactionChatAPI.getPrefix(player) + player.getName() + FactionChatAPI.getSuffix(player) + ChatColor.RESET, message};
        String[] input2 = {ChatMode.FormatString(FactionChat.AllyTruceChat, intput1, playerTitle, allowCustomColour)};
        String normalMessage = ChatMode.FormatString(FactionChat.AllyTruceChat, intput1, playerTitle, allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2, playerTitle, allowCustomColour);

        FPlayer fSenderPlayer = (FPlayer) FPlayers.i.get(player);
        Faction SenderFaction = fSenderPlayer.getFaction();

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

            FPlayer fplayer = (FPlayer) FPlayers.i.get(myPlayer);

            if ((SenderFaction.getRelationTo(fplayer) == Rel.ALLY || SenderFaction.getRelationTo(fplayer) == Rel.TRUCE
                    || getFactionName(myPlayer).equalsIgnoreCase(sSenderFaction))
                    && myPlayer.hasPermission("FactionChat.AllyChat") && player.hasPermission("FactionChat.TruceChat")) {
                fplayer.sendMessage(normalMessage);
            } else if (ChatMode.isSpyOn(myPlayer)) {
                fplayer.sendMessage(spyMessage);
            }
        }
    }

    public void fChatA(Player player, String message) {
        
        if (!ChatMode.canChat(player.getName())) {
            return;
        }
        
        String sSenderFaction = getFactionName(player); //obtains player's faction name

        if (sSenderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String playerTitle = getPlayerTitle(player);
        String[] intput1 = {sSenderFaction, getPlayerRank(player), FactionChatAPI.getPrefix(player) + player.getName() + FactionChatAPI.getSuffix(player) + ChatColor.RESET, message};
        String[] input2 = {ChatMode.FormatString(FactionChat.AllyChat, intput1, playerTitle, allowCustomColour)};
        String normalMessage = ChatMode.FormatString(FactionChat.AllyChat, intput1, playerTitle, allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2, playerTitle, allowCustomColour);

        FPlayer fSenderPlayer = (FPlayer) FPlayers.i.get(player);
        Faction SenderFaction = fSenderPlayer.getFaction();

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

            FPlayer fplayer = (FPlayer) FPlayers.i.get(myPlayer);

            if ((SenderFaction.getRelationTo(fplayer) == Rel.ALLY
                    || getFactionName(myPlayer).equalsIgnoreCase(sSenderFaction))
                    && myPlayer.hasPermission("FactionChat.AllyChat")) {
                fplayer.sendMessage(normalMessage);
            } else if (ChatMode.isSpyOn(myPlayer)) {
                fplayer.sendMessage(spyMessage);
            }
        }
    }

    public void fChatTruce(Player player, String message) {
        
        if (!ChatMode.canChat(player.getName())) {
            return;
        }
        
        String sSenderFaction = getFactionName(player); //obtains player's faction name

        if (sSenderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String playerTitle = getPlayerTitle(player);
        String[] intput1 = {sSenderFaction, getPlayerRank(player), FactionChatAPI.getPrefix(player) + player.getName() + FactionChatAPI.getSuffix(player) + ChatColor.RESET, message};
        String[] input2 = {ChatMode.FormatString(FactionChat.TruceChat, intput1, playerTitle, allowCustomColour)};
        String normalMessage = ChatMode.FormatString(FactionChat.TruceChat, intput1, playerTitle, allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2, playerTitle, allowCustomColour);

        FPlayer fSenderPlayer = (FPlayer) FPlayers.i.get(player);
        Faction SenderFaction = fSenderPlayer.getFaction();

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

            FPlayer fplayer = (FPlayer) FPlayers.i.get(myPlayer);

            if ((SenderFaction.getRelationTo(fplayer) == Rel.TRUCE
                    || sSenderFaction.equalsIgnoreCase(getFactionName(fplayer)) || getFactionName(myPlayer).equalsIgnoreCase(sSenderFaction))
                    && player.hasPermission("FactionChat.TruceChat")) {
                fplayer.sendMessage(normalMessage);
            } else if (ChatMode.isSpyOn(myPlayer)) {
                fplayer.sendMessage(spyMessage);
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
        
        String sSenderFaction = getFactionName(player); //obtains player's faction name

        if (sSenderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }

        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        FPlayer fSenderPlayer = (FPlayer) FPlayers.i.get(player);
        Faction SenderFaction = fSenderPlayer.getFaction();
        String playerTitle = getPlayerTitle(player);
        String[] intput1 = {sSenderFaction, getPlayerRank(player), FactionChatAPI.getPrefix(player) + player.getName() + FactionChatAPI.getSuffix(player) + ChatColor.RESET, message};
        String[] input2 = {ChatMode.FormatString(FactionChat.EnemyChat, intput1, playerTitle, allowCustomColour)};
        String normalMessage = ChatMode.FormatString(FactionChat.EnemyChat, intput1, playerTitle, allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2, playerTitle, allowCustomColour);

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

            FPlayer fplayer = (FPlayer) FPlayers.i.get(myPlayer);

            if ((SenderFaction.getRelationTo(fplayer) == Rel.ENEMY || sSenderFaction.equalsIgnoreCase(getFactionName(fplayer)) || getFactionName(myPlayer).equalsIgnoreCase(sSenderFaction))
                    && myPlayer.hasPermission("FactionChat.EnemyChat") && !isFactionless(myPlayer) && ChatMode.getChatMode(myPlayer).equals("ENEMY")) {
                fplayer.sendMessage(normalMessage);
            } else if (ChatMode.isSpyOn(myPlayer)) {
                fplayer.sendMessage(spyMessage);
            }
        }

    }

    protected void fchato(CommandSender sender, String[] args) {

        if (!ChatMode.canChat(sender.getName())) {
            return;
        }
        
        Player player = (Player) sender;//get player

        if (args.length <= 1) { //checks if there is a message in command eg "hello world" in /fchat hello world
            player.sendMessage(FactionChat.messageFchatoMisstype);
        } else {

            String senderFaction = getFactionName(player);

            if (senderFaction.contains("Wilderness")) { //checks if player is in a faction
                player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
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

                //this could be used in replaced of below (5 lines below)
                /*
                 FPlayer fSenderPlayer = (FPlayer)FPlayers.i.get(player);
                 Faction SenderFaction = fSenderPlayer.getFaction();
                 SenderFaction.sendMessage(ChatColor.DARK_GREEN + "[" + sSenderFaction + ChatColor.DARK_GREEN + "] " + ChatColor.RESET + player.getName() + ": " + message);
                 SenderFaction.getRelationTo(fSenderPlayer);
                 * */
                onlinePlayerList = Bukkit.getServer().getOnlinePlayers(); //get list of every online player
                String playersFaction; //creates string outside loop
                String targetFaction = args[0] + senderFaction.charAt(senderFaction.length() - 2) + senderFaction.charAt(senderFaction.length() - 1);

                int count = 0;
                boolean allowCustomColour = player.hasPermission("essentials.chat.color");
                String playerTitle = getPlayerTitle(player);
                String[] intput1 = {senderFaction, getPlayerRank(player), FactionChatAPI.getPrefix(player) + player.getName() + FactionChatAPI.getSuffix(player) + ChatColor.RESET, message};
                String[] input2 = {ChatMode.FormatString(FactionChat.OtherFactionChatSpy, intput1, playerTitle, allowCustomColour)};
                String toMessage = ChatMode.FormatString(FactionChat.OtherFactionChatTo, intput1, playerTitle, allowCustomColour);
                String FromMessage = ChatMode.FormatString(FactionChat.OtherFactionChatFrom, intput1, playerTitle, allowCustomColour);
                String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2, playerTitle, allowCustomColour);
                //start of loop
                for (int i = 0; i < onlinePlayerList.length; i++) {

                    playersFaction = getFactionName(onlinePlayerList[i]);

                    if (playersFaction.equalsIgnoreCase(senderFaction)) {
                        onlinePlayerList[i].sendMessage(toMessage);
                    } else if (playersFaction.equalsIgnoreCase(targetFaction)) {
                        onlinePlayerList[i].sendMessage(FromMessage);
                        count++;
                    } else if (ChatMode.isSpyOn(onlinePlayerList[i])) {
                        onlinePlayerList[i].sendMessage(spyMessage);
                    }

                }

                if (count == 0) {
                    player.sendMessage(ChatColor.RED + FactionChat.messageFchatoNoOneOnline);
                }
            }
        }

    }

    protected void fChatLeader(Player player, String message) {

        if (!ChatMode.canChat(player.getName())) {
            return;
        }
        
        String senderFaction = getFactionName(player); //obtains player's faction name

        if (senderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String playerTitle = getPlayerTitle(player);
        String[] intput1 = {senderFaction, FactionChatAPI.getPrefix(player) + player.getName() + FactionChatAPI.getSuffix(player) + ChatColor.RESET, message};
        String[] input2 = {ChatMode.FormatString(FactionChat.LeaderChat, intput1, playerTitle, allowCustomColour)};
        String normalMessage = ChatMode.FormatString(FactionChat.LeaderChat, intput1, playerTitle, allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2, playerTitle, allowCustomColour);
        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

            if (getPlayerRank(myPlayer).equals(FactionChat.LeaderRank) && ChatMode.getChatMode(myPlayer).equals("LEADER")) {
                myPlayer.sendMessage(normalMessage);
            } else if (ChatMode.isSpyOn(myPlayer)) {
                myPlayer.sendMessage(spyMessage);
            }
        }

    }

    protected void fChatOfficer(Player player, String message) {

        if (!ChatMode.canChat(player.getName())) {
            return;
        }
        
        String senderFaction = getFactionName(player); //obtains player's faction name

        if (senderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String playerTitle = getPlayerTitle(player);
        String[] intput1 = {senderFaction, FactionChatAPI.getPrefix(player) + player.getName() + FactionChatAPI.getSuffix(player) + ChatColor.RESET, message};
        String[] input2 = {ChatMode.FormatString(FactionChat.OfficerChat, intput1, playerTitle, allowCustomColour)};
        String normalMessage = ChatMode.FormatString(FactionChat.OfficerChat, intput1, playerTitle, allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2, playerTitle, allowCustomColour);
        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {

            if ((getPlayerRank(myPlayer).equals(FactionChat.LeaderRank) || getPlayerRank(myPlayer).equals(FactionChat.OfficerRank)) && ChatMode.getChatMode(myPlayer).equals("OFFICER")) {
                myPlayer.sendMessage(normalMessage);
            } else if (ChatMode.isSpyOn(myPlayer)) {
                myPlayer.sendMessage(spyMessage);
            }
        }

    }

    private boolean isFactionless(Player player) {
        return getFactionName(player).contains("Wilderness");

    }
}
