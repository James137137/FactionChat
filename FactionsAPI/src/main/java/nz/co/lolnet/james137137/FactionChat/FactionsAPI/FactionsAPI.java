/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nz.co.lolnet.james137137.FactionChat.FactionsAPI;


/**
 *
 * @author James
 */
public interface FactionsAPI {
    
    
    public String getFactionName(Object player);
    
    public String getFactionID(Object player);
    
    public MyRel getRelationship(Object player1, Object player2);
    
    public boolean isFactionless(Object player);
    
    public String getPlayerTitle(Object player);
    
    public MyRel getPlayerRank(Object player);
}
