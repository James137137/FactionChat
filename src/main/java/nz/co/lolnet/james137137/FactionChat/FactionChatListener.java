package nz.co.lolnet.james137137.FactionChat;

import java.util.Objects;
import java.util.Set;
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
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.PluginManager;

/**
 *
 * @author James
 */
public class FactionChatListener implements Listener {

    private static ChatChannel channel;
    private static OtherChatChannel otherChannel;
    private FactionChat plugin;
    static final Logger log = Bukkit.getLogger();
    EventPriority onPlayerChatEP;
    EventPriority onPlayerChatLocalOptionEP;
    EventPriority onPlayerCommandEP;
    EventPriority onPlayerJoinEP;

    protected FactionChatListener(FactionChat FactionChat) {
        this.plugin = FactionChat;
        if (FactionChat.FactionsEnable) {
            channel = new ChatChannel(FactionChat);
        }
        otherChannel = new OtherChatChannel(FactionChat);

        onPlayerChatEP = setupEventPriority("EventPriority.onPlayerChat");
        onPlayerChatLocalOptionEP = setupEventPriority("EventPriority.onPlayerChatLocalOption");
        onPlayerCommandEP = setupEventPriority("EventPriority.onPlayerCommand");
        onPlayerJoinEP = setupEventPriority("EventPriority.onPlayerJoin");

        PluginManager pm = Bukkit.getPluginManager();
        if (!plugin.getConfig().getBoolean("DontUseAsyncEvent")) {
            pm.registerEvent(org.bukkit.event.player.AsyncPlayerChatEvent.class, this, onPlayerChatEP, new EventExecutor() {

                @Override
                public void execute(Listener ll, Event event) throws EventException {
                    FactionChatListener.this.onPlayerChat((org.bukkit.event.player.AsyncPlayerChatEvent) event);
                }
            }, plugin);

            pm.registerEvent(org.bukkit.event.player.AsyncPlayerChatEvent.class, this, onPlayerChatLocalOptionEP, new EventExecutor() {

                @Override
                public void execute(Listener ll, Event event) throws EventException {
                    FactionChatListener.this.onPlayerChatLocalOption((org.bukkit.event.player.AsyncPlayerChatEvent) event);
                }
            }, plugin);

        } else {
            pm.registerEvent(org.bukkit.event.player.PlayerChatEvent.class, this, onPlayerChatEP, new EventExecutor() {

                @Override
                public void execute(Listener ll, Event event) throws EventException {
                    FactionChatListener.this.onPlayerChat_NonAsync((org.bukkit.event.player.PlayerChatEvent) event);
                }
            }, plugin);

            pm.registerEvent(org.bukkit.event.player.PlayerChatEvent.class, this, onPlayerChatLocalOptionEP, new EventExecutor() {

                @Override
                public void execute(Listener ll, Event event) throws EventException {
                    FactionChatListener.this.onPlayerChatLocalOption_NonAsync((org.bukkit.event.player.PlayerChatEvent) event);
                }
            }, plugin);
        }

        pm.registerEvent(PlayerCommandPreprocessEvent.class, this, onPlayerCommandEP, new EventExecutor() {

            @Override
            public void execute(Listener ll, Event event) throws EventException {
                FactionChatListener.this.onPlayerCommand((PlayerCommandPreprocessEvent) event);
            }
        }, plugin);

        pm.registerEvent(PlayerJoinEvent.class, this, onPlayerJoinEP, new EventExecutor() {

            @Override
            public void execute(Listener ll, Event event) throws EventException {
                FactionChatListener.this.onPlayerJoin((PlayerJoinEvent) event);
            }
        }, plugin);

    }

    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ChatMode.SetNewChatMode(player);
    }

    protected void onPlayerChat(org.bukkit.event.player.AsyncPlayerChatEvent event) {

        if (event.isCancelled()) {
            return;
        }
        boolean cancelEvent = onChat(event.getPlayer(), event.getMessage(), event.getRecipients());
        event.setCancelled(cancelEvent);

    }

    private void onPlayerChat_NonAsync(org.bukkit.event.player.PlayerChatEvent event) {
        if (event.isCancelled()) {
            return;
        }
        boolean cancelEvent = onChat(event.getPlayer(), event.getMessage(), event.getRecipients());
        event.setCancelled(cancelEvent);
    }

    private boolean onChat(Player talkingPlayer, String msg, Set<Player> recipients) {
        boolean setCancelled = false;
        if (FactionChat.useBanManager()) {
            if (BanManagerAPI.isMuted(talkingPlayer.getName())) {
                return setCancelled;
            }
        }
<<<<<<< HEAD

=======
        String msg = event.getMessage();
>>>>>>> parent of 60c62c6... Update AuthMe fix
        //FPlayer me = (FPlayer)FPlayers.i.get(talkingPlayer);
        String chatmode = ChatMode.getChatMode(talkingPlayer);
        if (!chatmode.equalsIgnoreCase("PUBLIC")) {
            boolean isFactionChat = false;
            if (plugin.FactionsEnable) {
                if (chatmode.equalsIgnoreCase("ALLY&TRUCE")) {
                    channel.fChatAT(talkingPlayer, msg);
                    setCancelled = true;
                    isFactionChat = true;

                } else if (chatmode.equalsIgnoreCase("ENEMY")) {
                    channel.fChatE(talkingPlayer, msg);
                    setCancelled = true;
                    isFactionChat = true;
                } else if (chatmode.equalsIgnoreCase("FACTION")) {
                    channel.fChatF(talkingPlayer, msg);
                    setCancelled = true;
                    isFactionChat = true;
                } else if (chatmode.equalsIgnoreCase("ALLY")) {
                    channel.fChatA(talkingPlayer, msg);
                    setCancelled = true;
                    isFactionChat = true;
                } else if (chatmode.equalsIgnoreCase("TRUCE")) {
                    channel.fChatTruce(talkingPlayer, msg);
                    setCancelled = true;
                    isFactionChat = true;
                } else if (chatmode.equalsIgnoreCase("LEADER")) {
                    channel.fChatLeader(talkingPlayer, msg);
                    setCancelled = true;
                    isFactionChat = true;
                } else if (chatmode.equalsIgnoreCase("OFFICER")) {
                    channel.fChatOfficer(talkingPlayer, msg);
                    setCancelled = true;
                    isFactionChat = true;
                }

                if (isFactionChat) {
                    log.info("[FactionChat] " + chatmode + "|" + talkingPlayer.getName() + ": " + msg);
                    return setCancelled;
                }

            }

            if (chatmode.equalsIgnoreCase("VIP")) {
                otherChannel.VIPChat(talkingPlayer, msg);
                setCancelled = true;
            } else if (chatmode.equalsIgnoreCase("UserAssistant")) {
                otherChannel.userAssistantChat(talkingPlayer, msg);
                setCancelled = true;
            } else if (chatmode.equalsIgnoreCase("JrMOD")) {
                otherChannel.jrModChat(talkingPlayer, msg);
                setCancelled = true;
            } else if (chatmode.equalsIgnoreCase("MOD")) {
                otherChannel.modChat(talkingPlayer, msg);
                setCancelled = true;
            } else if (chatmode.equalsIgnoreCase("SrMOD")) {
                otherChannel.SrModChat(talkingPlayer, msg);
                setCancelled = true;
            } else if (chatmode.equalsIgnoreCase("JrAdmin")) {
                otherChannel.JrAdminChat(talkingPlayer, msg);
                setCancelled = true;
            } else if (chatmode.equalsIgnoreCase("ADMIN")) {
                otherChannel.adminChat(talkingPlayer, msg);
                setCancelled = true;
            }

            log.info("[FactionChat] " + chatmode + "|" + talkingPlayer.getName() + ": " + msg);
        } else {
            if (ChatMode.mutePublicOptionEnabled && !talkingPlayer.hasPermission("FactionChat.mutebypass")) {
                for (final Player player : plugin.getServer().getOnlinePlayers()) {
                    if (ChatMode.IsPublicMuted(player)) {

                        if (player.getName().equals(talkingPlayer.getName())) {
                            ChatMode.MutePublicOption(player);
                        } else {
                            recipients.remove(player);
                        }

                    }
                }
            }

        }

        return setCancelled;
    }

    private void onPlayerChatLocalOption_NonAsync(org.bukkit.event.player.PlayerChatEvent event) {
        if (event.isCancelled()) {
            return;
        }
        onPlayerChatLocalOption(event.getPlayer(), event.getRecipients());
    }
    
    protected void onPlayerChatLocalOption(org.bukkit.event.player.AsyncPlayerChatEvent event) {
        if (event.isCancelled()) {
            return;
        }
        onPlayerChatLocalOption(event.getPlayer(), event.getRecipients());
    }

    private void onPlayerChatLocalOption(Player player, Set<Player> recipients) {
        FileConfiguration config = this.plugin.getConfig();
<<<<<<< HEAD
=======
        Player player = event.getPlayer();
>>>>>>> parent of 60c62c6... Update AuthMe fix
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
<<<<<<< HEAD
                recipients.remove(player1);
=======
                event.getRecipients().remove(player1);
>>>>>>> parent of 60c62c6... Update AuthMe fix
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
                String senderFaction = FactionChat.factionsAPI.getFactionName(player);
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
