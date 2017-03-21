/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nz.co.lolnet.james137137.FactionChat.PrefixAndSuffix;

import org.bukkit.entity.Player;

/**
 *
 * @author James
 */
public interface PrefixAndSuffix {
    
    public void init() throws Exception;
    
    public String getPrefix(Player player);
    
    public String getSuffix(Player player);
}
