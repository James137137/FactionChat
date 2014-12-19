/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.james137137.factionchat.factionsapi;

import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public interface FactionsAPI {
    
    
    public String getFactionName(Player player);
    
    public String getFactionID(Player player);
    
    public MyRel getRelationship(Player player1, Player player2);
    
    public boolean isFactionless(Player player);
    
    public String getPlayerTitle(Player player);
    
    public MyRel getPlayerRank(Player player);
}
