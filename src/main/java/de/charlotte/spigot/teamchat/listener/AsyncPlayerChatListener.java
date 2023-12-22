package de.charlotte.spigot.teamchat.listener;

import de.charlotte.spigot.teamchat.handler.ConfigHandler;
import de.charlotte.spigot.teamchat.TeamChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Objects;

public class AsyncPlayerChatListener implements Listener {

    @EventHandler
    public void handleChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        final String sentMessage = event.getMessage();

        if (sentMessage.startsWith("@team")) {
            if (!player.hasPermission(ConfigHandler.getPermission("essential-permission"))) {
                cancelEventAndSendMessage(event, player, ConfigHandler.getString("no-permission-error"));
                return;
            }

            if (!TeamChat.getInstance().teamChatPlayers.contains(player)) {
                cancelEventAndSendMessage(event, player, ConfigHandler.getString("not-logged-in-message"));
                return;
            }

            event.setCancelled(true);

            String userMessage = sentMessage.replaceFirst("@team", "").trim();

            if (userMessage.isEmpty()) {
                player.sendMessage(ConfigHandler.getString("no-message-error"));
                return;
            }

            String message = Objects.requireNonNull(TeamChat.getInstance().getConfig().getString("message-to-be-sent-in-teamchat"))
                    .replace("%player%", player.getName())
                    .replace("%message%", userMessage)
                    .replace("%prefix%", ConfigHandler.getString("prefix"));

            Bukkit.getOnlinePlayers().stream()
                    .filter(onlinePlayer -> onlinePlayer.hasPermission(ConfigHandler.getPermission("read")))
                    .forEach(onlinePlayer -> {
                        if (TeamChat.getInstance().getConfig().getBoolean("color-ops") && player.isOp()) {
                            String opMessage = ConfigHandler.getString("message-to-be-sent-in-teamchat-if-op")
                                    .replace("%player%", player.getName())
                                    .replace("%message%", userMessage);
                            onlinePlayer.sendMessage(opMessage);
                        } else {
                            onlinePlayer.sendMessage(message);
                        }
                    });
        }
    }

    private void cancelEventAndSendMessage(AsyncPlayerChatEvent event, Player player, String message) {
        event.setCancelled(true);
        player.sendMessage(message);
    }
}
