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

    public boolean hasTruce() {
        try {
            Class.forName("com.massivecraft.factions.cmd.CmdRelationTruce");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    public String getFactionName(Player player) {

        return FPlayers.getInstance().getByPlayer(player).getFaction().getTag();
    }

    @Override
    public String getFactionID(Player player) {
        return FPlayers.getInstance().getByPlayer(player).getFaction().getId();
    }

    @Override
    public MyRel getRelationship(Player player1, Player player2) {
        FPlayer fSenderPlayer = FPlayers.getInstance().getByPlayer(player1);
        Faction SenderFaction = fSenderPlayer.getFaction();
        FPlayer fplayer = FPlayers.getInstance().getByPlayer(player2);
        Relation rel = SenderFaction.getRelationTo(fplayer);
        if (fSenderPlayer.getFactionId().equals(fplayer.getFactionId())) {
            return MyRel.MEMBER;
        }
        if (rel == Relation.NEUTRAL) {
            return MyRel.NEUTRAL;
        }
        if (rel == Relation.ALLY) {
            return MyRel.ALLY;
        }
        if (rel == Relation.ENEMY) {
            return MyRel.ENEMY;
        }
        if (rel == Relation.MEMBER) {
            return MyRel.MEMBER;
        }
        if (hasTruce() && rel == Relation.TRUCE) {
            return MyRel.TRUCE;
        }

        return null;
    }

    @Override
    public boolean isFactionless(Player player) {
        return getFactionName(player).contains("Wilderness");
    }

    @Override
    public String getPlayerTitle(Player player) {
        String title = FPlayers.getInstance().getByPlayer(player).getTitle();
        if (title.contains("no title set")) {
            return "";
        }
        return title;
    }

    @Override
    public MyRel getPlayerRank(Player player) {
        Role role = FPlayers.getInstance().getByPlayer(player).getRole();
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
