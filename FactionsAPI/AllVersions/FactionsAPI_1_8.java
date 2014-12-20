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
public class FactionsAPI_1_8 implements FactionsAPI{

    @Override
    public String getFactionName(Player player) {
        return ((FPlayer) FPlayers.i.get(player)).getFaction().getTag();
    }

    @Override
    public String getFactionID(Player player) {
        return ((FPlayer) FPlayers.i.get(player)).getFaction().getId();
    }

    @Override
    public MyRel getRelationship(Player player1, Player player2) {
        FPlayer fSenderPlayer = (FPlayer) FPlayers.i.get(player1);
        Faction SenderFaction = fSenderPlayer.getFaction();
        FPlayer fplayer = (FPlayer) FPlayers.i.get(player2);
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
    public boolean isFactionless(Player player) {
        return getFactionName(player).contains("Wilderness");
    }

    @Override
    public String getPlayerTitle(Player player) {
        String title = ((FPlayer) FPlayers.i.get(player)).getTitle();
        if (title.contains("no title set")) {
            return "";
        }
        return title;
    }

    @Override
    public MyRel getPlayerRank(Player player) {
        Rel role = ((FPlayer) FPlayers.i.get(player)).getRole();
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
