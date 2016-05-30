/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat.FactionsAPI;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Relation;
import com.massivecraft.factions.struct.Role;
import org.bukkit.entity.Player;


/**
 *
 * @author James
 */
public class FactionsAPI_1_6 implements FactionsAPI {

    @Override
    public String getFactionName(Object player) {
        
        return FPlayers.getInstance().getByPlayer((Player) player).getFaction().getTag();
    }

    @Override
    public String getFactionID(Object player) {
        return FPlayers.getInstance().getByPlayer((Player) player).getFaction().getId();
    }

    @Override
    public MyRel getRelationship(Object player1, Object player2) {
        FPlayer fSenderPlayer = FPlayers.getInstance().getByPlayer((Player) player1);
        Faction SenderFaction = fSenderPlayer.getFaction();
        FPlayer fplayer = FPlayers.getInstance().getByPlayer((Player) player2);
        Relation rel = SenderFaction.getRelationTo(fplayer);
        if (fSenderPlayer.getFactionId().equals(fplayer.getFactionId()))
        {
            return MyRel.MEMBER;
        }
        if (rel == Relation.NEUTRAL)
        {
            return MyRel.NEUTRAL;
        }
        if (rel == Relation.ALLY)
        {
            return MyRel.ALLY;
        }
        if (rel == Relation.ENEMY)
        {
            return MyRel.ENEMY;
        }
        if (rel == Relation.MEMBER)
        {
            return MyRel.MEMBER;
        }
        
        return null;
    }

    @Override
    public boolean isFactionless(Object player) {
        return getFactionName(player).contains("Wilderness");
    }

    @Override
    public String getPlayerTitle(Object player) {
        String title = FPlayers.getInstance().getByPlayer((Player) player).getTitle();
        if (title.contains("no title set")) {
            return "";
        }
        return title;
    }

    @Override
    public MyRel getPlayerRank(Object player) {
        Role role = FPlayers.getInstance().getByPlayer((Player) player).getRole();
        if (role.equals(Role.ADMIN)) {
            return MyRel.LEADER;
        } else if (role.equals(Role.MODERATOR)) {
            return MyRel.OFFICER;
        } else if (role.equals(Role.NORMAL)) {
            return MyRel.MEMBER;
        } else {
            return MyRel.NEUTRAL;
        }
    }

}
