# FactionChat API Overview

This document describes the classes made available by the FactionChat plugin for Bukkit. These APIs allow other plugins to interact with faction specific chat channels and access information about players and factions.

## FactionChatAPI

`FactionChatAPI` is the main entry point. It is automatically initialised when the plugin starts. Most methods are static and can be used without additional setup.

### Common Methods

| Method | Description |
| ------ | ----------- |
| `getFactionName(Player player)` | Returns the name of the player's faction. |
| `getChatMode(Player player)` | Returns the player's current chat mode (e.g. PUBLIC, FACTION, ALLY). |
| `getPlayerRank(Player player)` | Returns the player's rank within their faction. |
| `isFactionChatMessage(AsyncPlayerChatEvent event)` | `true` if the chat event was sent in a FactionChat channel. |
| `getDistance(Player a, Player b)` | Distance in blocks between two online players. |
| `getPrefix(Player player)` | Group prefix for the player if a supported permissions plugin is available. |
| `getSuffix(Player player)` | Group suffix for the player if a supported permissions plugin is available. |
| `filterChat(Player player, String message)` | Run the configured `ChatFilter` implementations over a message. |
| `canReceiveChat(Player player)` | Checks whether the player is allowed to receive faction chat after filters. |

## Chat Filters

Plugins can register custom message filters by implementing the `ChatFilter` interface and adding an instance to `FactionChatAPI.chatFilter`. A filter may change the message contents or block delivery to specific players.

```
public interface ChatFilter {
    String filterMessage(Player player, String message);
    boolean canReceiveFactionChatMessage(Player player);
}
```

See `ChatFilterExample` for a simple demonstration.

## FactionChat Events

`FactionChatPlayerChatEvent` is fired whenever the plugin handles a faction chat message. It extends `PlayerEvent` and implements `Cancellable`. Listeners can check the chat mode, recipients or cancel the message entirely.

## FactionsAPI

The plugin abstracts different versions of the Factions plugin through the `FactionsAPI` interface. Implementations such as `FactionsAPI_2_14_0` provide faction name lookups, relationship checks and player rank information.

Key methods include:

- `getFactionName(Object player)`
- `getFactionID(Object player)`
- `getRelationship(Object player1, Object player2)`
- `isFactionless(Object player)`
- `getPlayerTitle(Object player)`
- `getPlayerRank(Object player)`

## FactionInfoServer

When enabled in `config.yml`, the plugin can start a simple TCP server allowing external programs to query faction information. Messages such as `getFactionName~~~~<player>` return data using the same methods supplied by `FactionsAPI`.

## Chat Formatting

`ChatFormat` stores configurable chat templates which are applied by `FactionChatMessage`. These can be customised via the plugin configuration to control how faction chat appears to players.

---
