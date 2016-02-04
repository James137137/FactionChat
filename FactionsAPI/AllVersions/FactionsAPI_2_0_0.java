/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat.FactionsAPI;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.UPlayer;


/**
 *
 * @author James
 */
public class FactionsAPI_2_0_0 implements FactionsAPI {

    @Override
    public String getFactionName(Object player) {
        UPlayer uPlayer = UPlayer.get(player);
        Faction faction = uPlayer.getFaction();

        return faction.getName();
    }

    @Override
    public String getFactionID(Object player) {
        UPlayer uPlayer = UPlayer.get(player);
        Faction faction = uPlayer.getFaction();
        return faction.getId();
    }

    @Override
    public MyRel getRelationship(Object player1, Object player2) {
        Rel rel;
        UPlayer uplayer1 = UPlayer.get(player1);
        UPlayer uplayer2 = UPlayer.get(player2);
        rel = uplayer1.getRelationTo(uplayer2);
        if (rel == Rel.NEUTRAL) {
            return MyRel.NEUTRAL;
        }
        if (rel == Rel.ALLY) {
            return MyRel.ALLY;
        }
        if (rel == Rel.TRUCE) {
            return MyRel.TRUCE;
        }
        if (rel == Rel.ENEMY) {
            return MyRel.ENEMY;
        }
        if (rel == Rel.LEADER) {
            return MyRel.LEADER;
        }
        if (rel == Rel.MEMBER) {
            return MyRel.MEMBER;
        }
        if (rel == Rel.RECRUIT) {
            return MyRel.RECRUIT;
        }
        if (rel == Rel.OFFICER) {
            return MyRel.OFFICER;
        }

        return null;
    }

    @Override
    public boolean isFactionless(Object player) {
        return UPlayer.get(player).getFaction().getName().contains("Wilderness");
    }

    @Override
    public String getPlayerTitle(Object player) {
        String title = UPlayer.get(player).getTitle();
        if (title.contains("no title set")) {
            return "";
        }
        return title;
    }

    @Override
    public MyRel getPlayerRank(Object player) {
        Rel role = UPlayer.get(player).getRole();
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
