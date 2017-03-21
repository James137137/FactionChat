/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nz.co.lolnet.james137137.FactionChat.FactionsAPI;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;


/**
 *
 * @author James
 */
public class FactionsAPI_2_9_0 implements FactionsAPI{

    @Override
    public String getFactionName(Object player) {
        MPlayer uplayer = MPlayer.get(player);
        Faction faction = uplayer.getFaction();
        return faction.getName();
    }

    @Override
    public String getFactionID(Object player) {
        MPlayer uplayer = MPlayer.get(player);
        Faction faction = uplayer.getFaction();
        return faction.getUniverse()+"-" + getFactionName(player);
    }

    @Override
    public MyRel getRelationship(Object player1, Object player2) {
        MPlayer uplayer1 = MPlayer.get(player1);
        MPlayer uplayer2 = MPlayer.get(player2);

        Rel rel = uplayer1.getRelationTo(uplayer2.getFaction());
        if (null != rel)
        {
        switch (rel) {
            case NEUTRAL:
                return MyRel.NEUTRAL;
            case ALLY:
                return MyRel.ALLY;
            case TRUCE:
                return MyRel.TRUCE;
            case ENEMY:
                return MyRel.ENEMY;
            case LEADER:
                return MyRel.LEADER;
            case MEMBER:
                return MyRel.MEMBER;
            case RECRUIT:
                return MyRel.RECRUIT;
            case OFFICER:
                return MyRel.OFFICER;
            default:
                return null;  
        }
        }
        return null;
        
    }

    @Override
    public boolean isFactionless(Object player) {
        return MPlayer.get(player).getFaction().getName().contains("Wilderness");
    }

    @Override
    public String getPlayerTitle(Object player) {
        String title = MPlayer.get(player).getTitle();
        if (title.contains("no title set")) {
            return "";
        }
        return title;
    }

    @Override
    public MyRel getPlayerRank(Object player) {
        Rel role = MPlayer.get(player).getRole();
        switch (role) {
            case LEADER:
                return MyRel.LEADER;
            case OFFICER:
                return MyRel.OFFICER;
            case MEMBER:
                return MyRel.MEMBER;
            case RECRUIT:
                return MyRel.RECRUIT;
            default:
                return MyRel.NEUTRAL;
        }
    }
    
}
