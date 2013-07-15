package nz.co.lolnet.james137137.FactionChat;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scoreboard.ScoreboardManager;

/**
 *
 * @author James
 */
public class FactionChatListener2 implements Listener {

    private  ChatChannel2 channel;
    private OtherChatChannel otherChannel;
    private FactionChat FactionChat;
    static final Logger log = Bukkit.getLogger();
    public ScoreboardManager scoreboardmanager;

    protected FactionChatListener2(FactionChat FactionChat) {
        scoreboardmanager = Bukkit.getScoreboardManager();
        this.FactionChat = FactionChat;
        if (FactionChat.FactionsEnable) {
            channel = new ChatChannel2(FactionChat);
        }
        otherChannel = new OtherChatChannel(FactionChat);

    }

    @EventHandler
    protected void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ChatMode.SetNewChatMode(player);
    }

    @EventHandler(priority = EventPriority.LOW)
    protected void onPlayerChat(AsyncPlayerChatEvent event) {

        if (event.isCancelled()) {
            return;
        }
        Player talkingPlayer = event.getPlayer();
        String msg = event.getMessage();
        //FPlayer me = (FPlayer)FPlayers.i.get(talkingPlayer);
        String chatmode = ChatMode.getChatMode(talkingPlayer);
        if (!chatmode.equalsIgnoreCase("PUBLIC")) {
            boolean isFactionChat = false;
            if (FactionChat.FactionsEnable) {
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
                }

                if (isFactionChat) {
                    log.log(Level.INFO, "[FactionChat] {0}|{1}: {2}", new Object[]{chatmode, talkingPlayer.getName(), msg});
                    return;
                }

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


    }

    @EventHandler(priority = EventPriority.LOWEST)
    protected void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        String message = event.getMessage();
        String Command = "";
        String remainer = "";
        for (int i = 1; i < message.length(); i++) {
            if (message.charAt(i) == ' ') {
                remainer = message.substring(i + 1, message.length());
                break;
            }
            Command += message.charAt(i);
        }
        if (remainer.length() == 0) {
            return;
        }
        if (Command.equalsIgnoreCase("factions") || Command.equalsIgnoreCase("f")) {
            String subCommand = "";
            for (int i = 0; i < remainer.length(); i++) {
                if (remainer.charAt(i) == ' ') {
                    remainer = remainer.substring(i + 1, remainer.length());
                    break;
                }
                subCommand += remainer.charAt(i);
            }

            if (subCommand.equalsIgnoreCase("chat") || subCommand.equalsIgnoreCase("c")) {
                event.setCancelled(true);
                String playerChannel = "";
                for (int i = 0; i < remainer.length(); i++) {
                    if (remainer.charAt(i) == ' ') {
                        break;
                    }
                    playerChannel += remainer.charAt(i);
                }

                boolean inFaction = true;

                String senderFaction = channel.getFactionName(player);
                if (senderFaction.contains("Wilderness") && !player.hasPermission("FactionChat.JrModChat")
                        && !FactionChat.isDebugger(player.getName())) {
                    //checks if player is in a faction
                    //mangaddp juniormoderators FactionChat.JrModChat
                    player.sendMessage(ChatColor.RED + FactionChat.messageNotInFaction);
                    inFaction = false;
                }

                if (!inFaction) {
                    ChatMode.fixPlayerNotInFaction(player);

                    return;
                }

                if (playerChannel.equalsIgnoreCase("chat") || playerChannel.equalsIgnoreCase("c")) {
                    ChatMode.NextChatMode(player);
                } else {
                    ChatMode.setChatMode(player, playerChannel);
                }
            }

        }


    }
}
