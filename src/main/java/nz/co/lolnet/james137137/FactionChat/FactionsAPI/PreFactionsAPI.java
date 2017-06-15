/*
The purpose of the class is to alter stuff within the factionsAPI classes without having to recomplie all the version again
*/
package nz.co.lolnet.james137137.FactionChat.FactionsAPI;

import nz.co.lolnet.james137137.FactionChat.API.StaffJoinFeature;

/**
 *
 * @author James
 */
public class PreFactionsAPI implements FactionsAPI{

    FactionsAPI factionAPI;
    public PreFactionsAPI(FactionsAPI factionAPI) {
        this.factionAPI = factionAPI;
    }
    
    public FactionsAPI getFactionsAPI()
    {
        return factionAPI;
    }
    
    @Override
    public String getFactionName(Object player) {
        if (StaffJoinFeature.isRedirected(player))
        {
            player = StaffJoinFeature.getTarget(player);
        }
        return factionAPI.getFactionName(player);
    }

    @Override
    public String getFactionID(Object player) {
        if (StaffJoinFeature.isRedirected(player))
        {
            player = StaffJoinFeature.getTarget(player);
        }
        return factionAPI.getFactionID(player);
    }

    @Override
    public MyRel getRelationship(Object player1, Object player2) {
        if (StaffJoinFeature.isRedirected(player1))
        {
            player1 = StaffJoinFeature.getTarget(player1);
        }
        if (StaffJoinFeature.isRedirected(player2))
        {
            player2 = StaffJoinFeature.getTarget(player2);
        }
        return factionAPI.getRelationship(player1, player2);
    }

    @Override
    public boolean isFactionless(Object player) {
        if (StaffJoinFeature.isRedirected(player))
        {
            player = StaffJoinFeature.getTarget(player);
        }
        return factionAPI.isFactionless(player);
    }

    @Override
    public String getPlayerTitle(Object player) {
        if (StaffJoinFeature.isRedirected(player))
        {
            player = StaffJoinFeature.getTarget(player);
        }
        return factionAPI.getPlayerTitle(player);
    }

    @Override
    public MyRel getPlayerRank(Object player) {
        if (StaffJoinFeature.isRedirected(player))
        {
            return MyRel.STAFF;
        }
        return factionAPI.getPlayerRank(player);
    }
    
}
