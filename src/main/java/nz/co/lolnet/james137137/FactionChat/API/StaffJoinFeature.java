package nz.co.lolnet.james137137.FactionChat.API;

import java.util.HashMap;
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
    
    public static String getTarget(Object player)
    {
        if (!isRedirected(player))
        {
            return null;
        }
        if (player instanceof String) {
            return StaffFactionsDirect.get((String) player);
        } else if (player instanceof Player) {
            return StaffFactionsDirect.get(((Player) (player)).getName());
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
