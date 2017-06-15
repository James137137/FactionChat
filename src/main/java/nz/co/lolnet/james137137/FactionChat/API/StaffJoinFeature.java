package nz.co.lolnet.james137137.FactionChat.API;

import java.util.HashMap;
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
            return null;
        }
        if (player instanceof String) {
            String playerName = StaffFactionsDirect.get((String) player);
            Player player1 = Bukkit.getServer().getPlayer(playerName);
            if (player1 == null)
            {
                clearPlayer(player);
                return player;
            }
        } else if (player instanceof Player) {
            String playerName = StaffFactionsDirect.get(((Player) (player)).getName());
            Player player1 = Bukkit.getServer().getPlayer(playerName);
            if (player1 == null)
            {
                clearPlayer(player);
                return player;
            }
        }
        return null;
    }
    
    public static void setTarget(Object player, String target)
    {
        if (player instanceof String) {
            StaffFactionsDirect.put((String) player,target);
        } else if (player instanceof Player) {
            StaffFactionsDirect.put(((Player) (player)).getName(),target);
        }
    }
}
