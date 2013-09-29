package nz.co.lolnet.james137137.FactionChat;

import com.massivecraft.factions.Rel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.massivecraft.factions.entity.UPlayer;
import com.massivecraft.factions.entity.Faction;

/**
 *
 * @author James
 */
public class ChatChannel2 {

    private FactionChat plugin;

    ChatChannel2(FactionChat aThis) {
        plugin = aThis;
    }

    /**
     *
     * Returns player's Factions Name.
     *
     */
    protected static String getFactionName(Player player) {
        UPlayer uPlayer = UPlayer.get(player);
        Faction faction = uPlayer.getFaction();
        return faction.getName();
    }

    protected int getRelationshipId(Player player1, Player player2) {
        UPlayer uPlayer1 = UPlayer.get(player1);
        UPlayer uPlayer2 = UPlayer.get(player2);

        return uPlayer1.getRelationTo(uPlayer2).getValue();
    }

    public boolean isFactionless(Player player) {
        return UPlayer.get(player).getFaction().getName().contains("Wilderness");
    }

    /**
     *
     * Returns player's Factions Title.
     *
     */
    protected String getPlayerTitle(Player player) {
        String title = UPlayer.get(player).getTitle() + "-";
        if (title.contains("no title set"))
        {
            return "";
        }
        return title;
    }
    
    protected static String getPlayerRank (Player player)
    {
        Rel role = UPlayer.get(player).getRole();
        if (role.equals(Rel.LEADER))
        {
            return FactionChat.LeaderRank;
        }
        else if (role.equals(Rel.OFFICER))
        {
            return FactionChat.OfficerRank;
        }else if (role.equals(Rel.MEMBER))
        {
            return FactionChat.MemberRank;
        }
        else if (role.equals(Rel.RECRUIT))
        {
            return FactionChat.RecruitRank;
        }
        else
        {
           return ""; 
        }
        
    }

    /*
     * Sends a message to the player's Faction only.
     */
    protected void fChatF(Player player, String message) {
        if (isFactionless(player)) {
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;
        }
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String[] intput1 = {getFactionName(player),getPlayerRank(player), getPlayerTitle(player)+player.getName(), message};
        String[] input2 = {ChatMode.FormatString(FactionChat.FactionChatMessage, intput1,allowCustomColour)};
        String normalMessage = ChatMode.FormatString(FactionChat.FactionChatMessage, intput1,allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2,allowCustomColour);

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {


            if (getRelationshipId(player, myPlayer) > 40 && myPlayer.hasPermission("FactionChat.FactionChat")) {
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
        if (isFactionless(player)) {
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;
        }
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String[] intput1 = {getFactionName(player),getPlayerRank(player), getPlayerTitle(player)+player.getName(), message};
        String[] input2 = {ChatMode.FormatString(FactionChat.AllyTruceChat, intput1,allowCustomColour)};
        String normalMessage = ChatMode.FormatString(FactionChat.AllyTruceChat, intput1,allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2,allowCustomColour);

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {


            if (getRelationshipId(player, myPlayer) > 20 && myPlayer.hasPermission("FactionChat.AllyChat")) {
                myPlayer.sendMessage(normalMessage);
            } else if (ChatMode.isSpyOn(myPlayer)) {

                myPlayer.sendMessage(spyMessage);
            }
        }
    }
    
    protected void fChatA(Player player, String message) {
        if (isFactionless(player)) {
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;
        }
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String[] intput1 = {getFactionName(player),getPlayerRank(player), getPlayerTitle(player)+player.getName(), message};
        String[] input2 = {ChatMode.FormatString(FactionChat.AllyChat, intput1,allowCustomColour)};
        String normalMessage = ChatMode.FormatString(FactionChat.AllyChat, intput1,allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2,allowCustomColour);

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {


            if (getRelationshipId(player, myPlayer) > 30 && myPlayer.hasPermission("FactionChat.AllyChat")) {
                myPlayer.sendMessage(normalMessage);
            } else if (ChatMode.isSpyOn(myPlayer)) {

                myPlayer.sendMessage(spyMessage);
            }
        }
    }
    
    protected void fChatTruce(Player player, String message) {
        if (isFactionless(player)) {
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;
        }
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String[] intput1 = {getFactionName(player),getPlayerRank(player), getPlayerTitle(player)+player.getName(), message};
        String[] input2 = {ChatMode.FormatString(FactionChat.TruceChat, intput1,allowCustomColour)};
        String normalMessage = ChatMode.FormatString(FactionChat.TruceChat, intput1,allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2,allowCustomColour);

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {


            if (((getRelationshipId(player, myPlayer) > 20 && getRelationshipId(player, myPlayer) < 40) || getRelationshipId(player, myPlayer) > 40)
                    && myPlayer.hasPermission("FactionChat.AllyChat")) {
                myPlayer.sendMessage(normalMessage);
            } else if (ChatMode.isSpyOn(myPlayer)) {

                myPlayer.sendMessage(spyMessage);
            }
        }
    }

    /*
     * Sends a message to the player's Faction 
     * and everyone that is in a Faction that enermies with the player's Faction.
     */
    protected void fChatE(Player player, String message) {

        if (isFactionless(player)) {
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;
        }
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String[] intput1 = {getFactionName(player),getPlayerRank(player), getPlayerTitle(player)+player.getName(), message};
        String[] input2 = {ChatMode.FormatString(FactionChat.EnemyChat, intput1,allowCustomColour)};
        String normalMessage = ChatMode.FormatString(FactionChat.EnemyChat, intput1,allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2,allowCustomColour);

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {


            if (getRelationshipId(player, myPlayer) < 20 && myPlayer.hasPermission("FactionChat.FactionChat") && !isFactionless(myPlayer)) {
                myPlayer.sendMessage(normalMessage);
            } else if (ChatMode.isSpyOn(myPlayer)) {

                myPlayer.sendMessage(spyMessage);
            }
        }

    }

    protected void fchato(CommandSender sender, String[] args) {

        Player player = (Player) sender;//get player


        if (isFactionless(player)) {
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;
        }

        if (args.length <= 1) { //checks if there is a message in command eg "hello world" in /fchat hello world
            player.sendMessage(ChatColor.RED + FactionChat.messageFchatoMisstype);
            return;
        }
        String targetFaction = args[0];
        String message = "";
        for (int i = 1; i < args.length; i++) {
            message += args[i] + " ";
        }

        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String[] intput1 = {getFactionName(player),getPlayerRank(player), getPlayerTitle(player)+player.getName(), message};
        String[] input2 = {ChatMode.FormatString(FactionChat.OtherFactionChatSpy, intput1,allowCustomColour)};
        String toMessage = ChatMode.FormatString(FactionChat.OtherFactionChatTo, intput1,allowCustomColour);
        String FromMessage = ChatMode.FormatString(FactionChat.OtherFactionChatFrom, intput1,allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat, input2,allowCustomColour);

        String playerFaction = getFactionName(player);
        String myPlayerFaction;
        int count = 0;
        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {
            myPlayerFaction = getFactionName(myPlayer);
            if (myPlayerFaction.equalsIgnoreCase(playerFaction)) {
                myPlayer.sendMessage(toMessage);
            } else if (myPlayerFaction.equalsIgnoreCase(targetFaction)) {
                myPlayer.sendMessage(FromMessage);
                count++;
            } else if (ChatMode.isSpyOn(myPlayer)) {
                myPlayer.sendMessage(spyMessage);
            }

        }

        if (count == 0) {
            player.sendMessage(ChatColor.RED + FactionChat.messageFchatoNoOneOnline);
        }



    }
    
    protected void fChatLeader(Player player, String message) {


        String senderFaction = getFactionName(player); //obtains player's faction name

        
        if (senderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String[] intput1 = {senderFaction,player.getName(),message};
        String[] input2 = {ChatMode.FormatString(FactionChat.LeaderChat, intput1,allowCustomColour)};
        String normalMessage = ChatMode.FormatString(FactionChat.LeaderChat, intput1,allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat,input2,allowCustomColour);
        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {


            if (getPlayerRank(myPlayer).equals(FactionChat.LeaderRank) && ChatMode.getChatMode(myPlayer).equals("LEADER")) {
                myPlayer.sendMessage(normalMessage);
            } else if (ChatMode.isSpyOn(myPlayer)) {
                myPlayer.sendMessage(spyMessage);
            }
        }






    }
    
    protected void fChatOfficer(Player player, String message) {


        String senderFaction = getFactionName(player); //obtains player's faction name

        
        if (senderFaction.contains("Wilderness")) { //checks if player is in a faction
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;

        }
        boolean allowCustomColour = player.hasPermission("essentials.chat.color");
        String[] intput1 = {senderFaction,player.getName(),message};
        String[] input2 = {ChatMode.FormatString(FactionChat.OfficerChat, intput1,allowCustomColour)};
        String normalMessage = ChatMode.FormatString(FactionChat.OfficerChat, intput1,allowCustomColour);
        String spyMessage = ChatMode.FormatString(FactionChat.SpyChat,input2,allowCustomColour);
        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {


            if ((getPlayerRank(myPlayer).equals(FactionChat.LeaderRank) || getPlayerRank(myPlayer).equals(FactionChat.OfficerRank)) && ChatMode.getChatMode(myPlayer).equals("OFFICER")) {
                myPlayer.sendMessage(normalMessage);
            } else if (ChatMode.isSpyOn(myPlayer)) {
                myPlayer.sendMessage(spyMessage);
            }
        }






    }
}
