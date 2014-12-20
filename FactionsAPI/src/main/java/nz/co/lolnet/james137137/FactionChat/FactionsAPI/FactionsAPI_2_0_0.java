/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat.FactionsAPI;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.UPlayer;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class FactionsAPI_2_0_0 implements FactionsAPI {

    @Override
    public String getFactionName(Player player) {
        UPlayer uPlayer = UPlayer.get(player);
        Faction faction = uPlayer.getFaction();

        return faction.getName();
    }

    @Override
    public String getFactionID(Player player) {
        UPlayer uPlayer = UPlayer.get(player);
        Faction faction = uPlayer.getFaction();
        return faction.getId();
    }

    @Override
    public MyRel getRelationship(Player player1, Player player2) {
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
    public boolean isFactionless(Player player) {
        return UPlayer.get(player).getFaction().getName().contains("Wilderness");
    }

    @Override
    public String getPlayerTitle(Player player) {
        String title = UPlayer.get(player).getTitle();
        if (title.contains("no title set")) {
            return "";
        }
        return title;
    }

    @Override
    public MyRel getPlayerRank(Player player) {
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
