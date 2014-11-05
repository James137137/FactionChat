package nz.co.lolnet.james137137.FactionChat;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scoreboard.ScoreboardManager;

/**
 *
 * @author James
 */
public class FactionChatListener2 implements Listener {

    private ChatChannel2 channel;
    private OtherChatChannel otherChannel;
    private FactionChat plugin;
    static final Logger log = Bukkit.getLogger();
    public ScoreboardManager scoreboardmanager;
    EventPriority onPlayerChatEP;
    EventPriority onPlayerChatLocalOptionEP;
    EventPriority onPlayerCommandEP;
    EventPriority onPlayerJoinEP;

    protected FactionChatListener2(FactionChat FactionChat) {
        scoreboardmanager = Bukkit.getScoreboardManager();
        this.plugin = FactionChat;
        if (FactionChat.FactionsEnable) {
            channel = new ChatChannel2(FactionChat);
        }
        otherChannel = new OtherChatChannel(FactionChat);

        onPlayerChatEP = setupEventPriority("EventPriority.onPlayerChat");
        onPlayerChatLocalOptionEP = setupEventPriority("EventPriority.onPlayerChatLocalOption");
        onPlayerCommandEP = setupEventPriority("EventPriority.onPlayerCommand");
        onPlayerJoinEP = setupEventPriority("EventPriority.onPlayerJoin");

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvent(AsyncPlayerChatEvent.class, this, onPlayerChatEP, new EventExecutor() {

            @Override
            public void execute(Listener ll, Event event) throws EventException {
                FactionChatListener2.this.onPlayerChat( (AsyncPlayerChatEvent) event);
            }
        }, plugin);
        
        pm.registerEvent(AsyncPlayerChatEvent.class, this, onPlayerChatLocalOptionEP, new EventExecutor() {

            @Override
            public void execute(Listener ll, Event event) throws EventException {
                FactionChatListener2.this.onPlayerChatLocalOption( (AsyncPlayerChatEvent) event);
            }
        }, plugin);
        
        pm.registerEvent(PlayerCommandPreprocessEvent.class, this, onPlayerCommandEP, new EventExecutor() {

            @Override
            public void execute(Listener ll, Event event) throws EventException {
                FactionChatListener2.this.onPlayerCommand( (PlayerCommandPreprocessEvent) event);
            }
        }, plugin);
        
        pm.registerEvent(PlayerJoinEvent.class, this, onPlayerJoinEP, new EventExecutor() {

            @Override
            public void execute(Listener ll, Event event) throws EventException {
                FactionChatListener2.this.onPlayerJoin((PlayerJoinEvent) event);
            }
        }, plugin);
    }

    protected void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ChatMode.SetNewChatMode(player);
    }

    protected void onPlayerChat(AsyncPlayerChatEvent event) {

        if (event.isCancelled()) {
            return;
        }
        Player talkingPlayer = event.getPlayer();
        if (FactionChat.useBanManager())
        {
            if (BanManagerAPI.isMuted(talkingPlayer.getName()))
            {
                return;
            }
        }
        String msg = event.getMessage();
        //FPlayer me = (FPlayer)FPlayers.i.get(talkingPlayer);
        String chatmode = ChatMode.getChatMode(talkingPlayer);
        if (!chatmode.equalsIgnoreCase("PUBLIC")) {
            boolean isFactionChat = false;
            if (plugin.FactionsEnable) {
                if (chatmode.equalsIgnoreCase("ALLY&TRUCE")) {
                    channel.fChatAT(talkingPlayer, msg);
                    event.setCancelled(true);
                    isFactionChat = true;

                } else if (chatmode.equalsIgnoreCase("ENEMY")) {
                    channel.fChatE(talkingPlayer, msg);
                    event.setCancelled(true);
                    isFactionChat = true;
                } else if (chatmode.equalsIgnoreCase("FACTION")) {
                    channel.fChatF(talkingPlayer, msg);
                    event.setCancelled(true);
                    isFactionChat = true;
                } else if (chatmode.equalsIgnoreCase("ALLY")) {
                    channel.fChatA(talkingPlayer, msg);
                    event.setCancelled(true);
                    isFactionChat = true;
                } else if (chatmode.equalsIgnoreCase("TRUCE")) {
                    channel.fChatTruce(talkingPlayer, msg);
                    event.setCancelled(true);
                    isFactionChat = true;
                } else if (chatmode.equalsIgnoreCase("LEADER")) {
                    channel.fChatLeader(talkingPlayer, msg);
                    event.setCancelled(true);
                    isFactionChat = true;
                } else if (chatmode.equalsIgnoreCase("OFFICER")) {
                    channel.fChatOfficer(talkingPlayer, msg);
                    event.setCancelled(true);
                    isFactionChat = true;
                }

                if (isFactionChat) {
                    log.log(Level.INFO, "[FactionChat] {0}|{1}: {2}", new Object[]{chatmode, talkingPlayer.getName(), msg});
                    return;
                }

            }

            if (chatmode.equalsIgnoreCase("VIP")) {
                otherChannel.VIPChat(talkingPlayer, msg);
                event.setCancelled(true);
            }
            if (chatmode.equalsIgnoreCase("UserAssistant")) {
                otherChannel.userAssistantChat(talkingPlayer, msg);
                event.setCancelled(true);
            } else if (chatmode.equalsIgnoreCase("JrMOD")) {
                otherChannel.jrModChat(talkingPlayer, msg);
                event.setCancelled(true);
            } else if (chatmode.equalsIgnoreCase("MOD")) {
                otherChannel.modChat(talkingPlayer, msg);
                event.setCancelled(true);
            } else if (chatmode.equalsIgnoreCase("SrMOD")) {
                otherChannel.SrModChat(talkingPlayer, msg);
                event.setCancelled(true);
            } else if (chatmode.equalsIgnoreCase("JrAdmin")) {
                otherChannel.JrAdminChat(talkingPlayer, msg);
                event.setCancelled(true);
            } else if (chatmode.equalsIgnoreCase("ADMIN")) {
                otherChannel.adminChat(talkingPlayer, msg);
                event.setCancelled(true);
            }

            log.log(Level.INFO, "[FactionChat] {0}|{1}: {2}", new Object[]{chatmode, talkingPlayer.getName(), msg});
        }
        else
        {
            if (ChatMode.mutePublicOptionEnabled && !talkingPlayer.hasPermission("FactionChat.mutebypass"))
            {
                for (final Player player : plugin.getServer().getOnlinePlayers()) {
                    if (ChatMode.IsPublicMuted(player)) {
                        
                        if (player.getName().equals(talkingPlayer.getName()))
                        {
                            ChatMode.MutePublicOption(player);
                        }
                        else
                        {
                           event.getRecipients().remove(player); 
                        }
                        
                    }
                }
            }
            
          
        }


    }
    
    protected void onPlayerChatLocalOption(AsyncPlayerChatEvent event) {

        if (event.isCancelled()) {
            return;
        }
        FileConfiguration config = this.plugin.getConfig();
        Player player = event.getPlayer();
        Boolean localChatP = ChatMode.LocalChat.get(player.getName());
        if (!Objects.equals(localChatP, Boolean.TRUE)) {
            
            if (!config.getBoolean("Features.LocalChat.Enable")) {
                return;
            }
            
            if (player.hasPermission("FactionChat.LocalChatBypass.PublicSend")) {
                return;
            }
        }

        double MaxDistance = config.getDouble("Features.LocalChat.Radius.Public");
        if (MaxDistance < 0) {
            return;
        }

        for (Player player1 : this.plugin.getServer().getOnlinePlayers()) {
            if (FactionChatAPI.getDistance(player, player1) > MaxDistance && !player1.hasPermission("FactionChat.LocalChatBypass.PublicReceive")) {
                event.getRecipients().remove(player1);
            }
        }
    }

    protected void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled() || !FactionChat.FactionsEnable) {
            return;
        }
        String message = event.getMessage();
        String[] split = message.split(" ");
        if (split.length < 2) {
            return;
        }
        if (split[0].equalsIgnoreCase("/factions") || split[0].equalsIgnoreCase(("/" + plugin.FactionsCommand))) {
            if (split[1].equalsIgnoreCase("chat") || split[1].equalsIgnoreCase("c")) {
                event.setCancelled(true);
                Player player = event.getPlayer();
                String senderFaction = channel.getFactionName(player);
                if (senderFaction.contains("Wilderness") && !player.hasPermission("FactionChat.UserAssistantChat")
                        && !plugin.isDebugger(player.getName())) {
                    //checks if player is in a faction
                    //mangaddp juniormoderators FactionChat.JrModChat
                    player.sendMessage(ChatColor.RED + plugin.messageNotInFaction);
                    return;
                }
                if (split.length >= 3) {
                    ChatMode.setChatMode(player, split[2]);
                } else {
                    ChatMode.NextChatMode(player);
                }
            }
        }
    }
    
    private EventPriority setupEventPriority(String configNode) {
        int input = plugin.getConfig().getInt(configNode);
        if (input == 1) {
            return EventPriority.LOWEST;
        } else if (input == 2) {
            return EventPriority.LOW;
        } else if (input == 3) {
            return EventPriority.NORMAL;
        } else if (input == 4) {
            return EventPriority.HIGH;
        } else if (input == 5) {
            return EventPriority.HIGHEST;
        } else if (input == 6) {
            return EventPriority.MONITOR;
        } else {
            return EventPriority.NORMAL;
        }
    }
}
