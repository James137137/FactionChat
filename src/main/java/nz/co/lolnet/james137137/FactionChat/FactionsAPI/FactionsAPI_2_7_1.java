/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nz.co.lolnet.james137137.FactionChat.FactionsAPI;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import nz.co.lolnet.james137137.FactionChat.FactionChat;
import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public class FactionsAPI_2_7_1 implements FactionsAPI{

    @Override
    public String getFactionName(Player player) {
        MPlayer uplayer = MPlayer.get(player);
        Faction faction = uplayer.getFaction();
        return faction.getName();
    }

    @Override
    public String getFactionID(Player player) {
        MPlayer uplayer = MPlayer.get(player);
        Faction faction = uplayer.getFaction();
        return faction.getUniverse()+"-" + getFactionName(player);
    }

    @Override
    public MyRel getRelationship(Player player1, Player player2) {
        MPlayer uplayer1 = MPlayer.get(player1);
        MPlayer uplayer2 = MPlayer.get(player2);

        Rel rel = uplayer1.getRelationTo(uplayer2.getFaction());
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
        return MPlayer.get(player).getFaction().getName().contains("Wilderness");
    }

    @Override
    public String getPlayerTitle(Player player) {
        if (FactionChat.plugin.getConfig().getBoolean("FactionChatMessage.IncludeTitle")) {
            return "";
        }
        String title = MPlayer.get(player).getTitle();
        if (title.contains("no title set")) {
            return "";
        }
        return title;
    }

    @Override
    public String getPlayerRank(Player player) {
        Rel role = MPlayer.get(player).getRole();
        if (role.equals(Rel.LEADER)) {
            return FactionChat.LeaderRank;
        } else if (role.equals(Rel.OFFICER)) {
            return FactionChat.OfficerRank;
        } else if (role.equals(Rel.MEMBER)) {
            return FactionChat.MemberRank;
        } else if (role.equals(Rel.RECRUIT)) {
            return FactionChat.RecruitRank;
        } else {
            return "";
        }
    }
    
}
