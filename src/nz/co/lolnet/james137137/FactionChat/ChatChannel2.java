package nz.co.lolnet.james137137.FactionChat;

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

    private String FormatString(String message, String[] args) {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                message = message.replace(("{" + i + "}"), args[i]);
            }
        }

        message = message.replaceAll("&", "" + (char) 167);

        return message;
    }

    /**
     *
     * Returns player's Factions Name.
     *
     */
    protected String getFactionName(Player player) {
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

    /*
     * Sends a message to the player's Faction only.
     */
    protected void fchat(Player player, String message) {
        if (isFactionless(player)) {
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;
        }
        String[] intput1 = {getFactionName(player), getPlayerTitle(player)+player.getName(), message};
        String[] input2 = {FormatString(FactionChat.FactionChatMessage, intput1)};
        String normalMessage = FormatString(FactionChat.FactionChatMessage, intput1);
        String spyMessage = FormatString(FactionChat.SpyChat, input2);

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
    protected void fchata(Player player, String message) {
        if (isFactionless(player)) {
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;
        }
        String[] intput1 = {getFactionName(player), getPlayerTitle(player)+player.getName(), message};
        String[] input2 = {FormatString(FactionChat.AllyChat, intput1)};
        String normalMessage = FormatString(FactionChat.AllyChat, intput1);
        String spyMessage = FormatString(FactionChat.SpyChat, input2);

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {


            if (getRelationshipId(player, myPlayer) > 20 && myPlayer.hasPermission("FactionChat.FactionChat")) {
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
    protected void fchatE(Player player, String message) {

        if (isFactionless(player)) {
            player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
            ChatMode.fixPlayerNotInFaction(player);
            return;
        }
        String[] intput1 = {getFactionName(player), getPlayerTitle(player)+player.getName(), message};
        String[] input2 = {FormatString(FactionChat.EnemyChat, intput1)};
        String normalMessage = FormatString(FactionChat.EnemyChat, intput1);
        String spyMessage = FormatString(FactionChat.SpyChat, input2);

        for (Player myPlayer : Bukkit.getServer().getOnlinePlayers()) {


            if (getRelationshipId(player, myPlayer) < 20 && myPlayer.hasPermission("FactionChat.FactionChat")) {
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

        String[] intput1 = {getFactionName(player), getPlayerTitle(player)+player.getName(), message};
        String[] input2 = {FormatString(FactionChat.OtherFactionChatSpy, intput1)};
        String toMessage = FormatString(FactionChat.OtherFactionChatTo, intput1);
        String FromMessage = FormatString(FactionChat.OtherFactionChatFrom, intput1);
        String spyMessage = FormatString(FactionChat.SpyChat, input2);

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
}
