/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.co.lolnet.james137137.FactionChat.PrefixAndSuffix;

import net.milkbowl.vault.chat.Chat;
import static org.bukkit.Bukkit.getServer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 *
 * @author James
 */
public class VaultChat implements PrefixAndSuffix{

     public static Chat chat = null;
    @Override
    public void init() throws Exception{
        setupChat();
    }

    @Override
    public String getPrefix(Player player) {
        return chat.getPlayerPrefix(player);
    }

    @Override
    public String getSuffix(Player player) {
        return chat.getPlayerSuffix(player);
    }
    
    private boolean setupChat() throws Exception{
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }
    
}
