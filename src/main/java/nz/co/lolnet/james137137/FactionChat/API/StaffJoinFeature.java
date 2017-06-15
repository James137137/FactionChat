package nz.co.lolnet.james137137.FactionChat.API;

import java.util.HashMap;
import nz.co.lolnet.james137137.FactionChat.FactionChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class StaffJoinFeature {

    public static HashMap<String, String> StaffFactionsDirect = new HashMap<>(); // StafffName, targetName

    public static boolean isRedirected(Object player) {
        if (player instanceof String) {
            return StaffFactionsDirect.containsKey((String) player);
        } else if (player instanceof Player) {
            return StaffFactionsDirect.containsKey(((Player) (player)).getName());
        }
        return false;
    }

    public static void clearPlayer(Object player) {
        if (player instanceof String) {
            StaffFactionsDirect.remove((String) player);
        } else if (player instanceof Player) {
            StaffFactionsDirect.remove(((Player) (player)).getName());
        }
    }
    
    public static Object getTarget(Object player)
    {
        if (!isRedirected(player))
        {
            return player;
        }
        if (player instanceof String) {
            String playerName = StaffFactionsDirect.get((String) player);
            System.out.println(playerName);
            Player player1 = getPlayer(playerName);
            if (player1 == null)
            {
                clearPlayer(player);
                return player;
            }
        } else if (player instanceof Player) {
            String playerName = StaffFactionsDirect.get(((Player) (player)).getName());
            System.out.println(playerName);
            Player player1 = Bukkit.getServer().getPlayer(playerName);
            if (player1 == null)
            {
                clearPlayer(player);
                return player;
            }
        }
        System.out.println("This isn't good...");
        System.out.println(player.getClass().getName());
        return player;
    }
    
    public static void setTarget(Object player, String target)
    {
        if (player instanceof String) {
            StaffFactionsDirect.put((String) player,target);
        } else if (player instanceof Player) {
            StaffFactionsDirect.put(((Player) (player)).getName(),target);
        }
    }

    private static Player getPlayer(String playerName) {
        for (Player player : FactionChat.plugin.getServer().getOnlinePlayers())
        {
            if (player.getName().equalsIgnoreCase(playerName))
            {
                return player;
            }
        }
        return null;
    }
}
