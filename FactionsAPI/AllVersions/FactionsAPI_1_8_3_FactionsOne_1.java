/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat.FactionsAPI;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Rel;
import org.bukkit.entity.Player;


/**
 *
 * @author James
 */
public class FactionsAPI_1_8_3_FactionsOne_1 implements FactionsAPI {

    @Override
    public String getFactionName(Object player) {
        if (player instanceof Player)
        {
            return ((FPlayer) FPlayers.i.get((Player) player)).getFaction().getTag();
        } else if (player instanceof String)
        {
            return ((FPlayer) FPlayers.i.get((String) player)).getFaction().getTag();
        }
        else
        {
            return null;
        }
    }

    @Override
    public String getFactionID(Object player) {
        if (player instanceof Player)
        {
            return ((FPlayer) FPlayers.i.get((Player) player)).getFaction().getId();
        } else if (player instanceof String)
        {
            return ((FPlayer) FPlayers.i.get((Player) player)).getFaction().getId();
        }
        else
        {
            return null;
        }
        
    }

    @Override
    public MyRel getRelationship(Object player1, Object player2) {
        FPlayer fSenderPlayer = null;
        FPlayer fplayer = null;
        if (player1 instanceof Player)
        {
            fSenderPlayer = (FPlayer) FPlayers.i.get((Player) player1);
        } else if (player1 instanceof String)
        {
            fSenderPlayer = (FPlayer) FPlayers.i.get((String) player1);
        }
        
        if (player2 instanceof Player)
        {
            fplayer = (FPlayer) FPlayers.i.get((Player) player2);
        } else if (player2 instanceof String)
        {
            fplayer = (FPlayer) FPlayers.i.get((String) player2);
        }
        
        Faction SenderFaction = fSenderPlayer.getFaction();
        Rel rel = SenderFaction.getRelationTo(fplayer);
        if (rel == Rel.NEUTRAL)
        {
            return MyRel.NEUTRAL;
        }
        if (rel == Rel.ALLY)
        {
            return MyRel.ALLY;
        }
        if (rel == Rel.TRUCE)
        {
            return MyRel.TRUCE;
        }
        if (rel == Rel.ENEMY)
        {
            return MyRel.ENEMY;
        }
        if (rel == Rel.LEADER)
        {
            return MyRel.LEADER;
        }
        if (rel == Rel.MEMBER)
        {
            return MyRel.MEMBER;
        }
        if (rel == Rel.RECRUIT)
        {
            return MyRel.RECRUIT;
        }
        if (rel == Rel.OFFICER)
        {
            return MyRel.OFFICER;
        }
        
        return null;
    }

    @Override
    public boolean isFactionless(Object player) {
        return getFactionName(player).contains("Wilderness");
    }

    @Override
    public String getPlayerTitle(Object player) {
        String title = null;
        
        if (player instanceof Player)
        {
            title = ((FPlayer) FPlayers.i.get((Player) player)).getTitle();
        } else if (player instanceof String)
        {
            title = ((FPlayer) FPlayers.i.get((String) player)).getTitle();
        }
        
        if (title.contains("no title set")) {
            return "";
        }
        return title;
    }

    @Override
    public MyRel getPlayerRank(Object player) {
        Rel role = null;
        if (player instanceof Player)
        {
            role = ((FPlayer) FPlayers.i.get((Player) player)).getRole();
        } else if (player instanceof String)
        {
            role = ((FPlayer) FPlayers.i.get((String) player)).getRole();
        }
        if (role.equals(Rel.LEADER)) {
            return MyRel.LEADER;
        } else if (role.equals(Rel.OFFICER)) {
            return MyRel.OFFICER;
        } else if (role.equals(Rel.MEMBER)) {
            return MyRel.MEMBER;
        } else if (role.equals(Rel.RECRUIT)) {
            return MyRel.RECRUIT;
        } else {
            return MyRel.NEUTRAL;
        }
    }

}
