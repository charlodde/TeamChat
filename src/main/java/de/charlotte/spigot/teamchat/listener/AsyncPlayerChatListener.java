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
        String sentMessage = event.getMessage();
        if (sentMessage.startsWith("@team")) {

            if (!(player.hasPermission(ConfigHandler.getPermission("essential-permission")))) {
                event.setCancelled(true);
                player.sendMessage(ConfigHandler.getString("no-permission-error"));
                return;
            }

            if (!TeamChat.getInstance().teamChatPlayers.contains(player)) {
                event.setCancelled(true);
                player.sendMessage(ConfigHandler.getString("not-logged-in-message"));
                return;
            }

            if (TeamChat.getInstance().teamChatPlayers.contains(player)) {
                if (sentMessage.contains("@team") && player.hasPermission(ConfigHandler.getPermission("essential-permission")) && player.hasPermission(ConfigHandler.getPermission("write"))) {
                    event.setCancelled(true);
                    sentMessage = sentMessage.replace("@team", "");
                }

                if (event.getMessage().equalsIgnoreCase("@team") || event.getMessage().equalsIgnoreCase("@team ")) {
                    event.setCancelled(true);
                    player.sendMessage(ConfigHandler.getString("no-message-error"));
                    return;
                }

                String message = Objects.requireNonNull(TeamChat.getInstance().getConfig().getString("message-to-be-sent-in-teamchat")).replace("%player%", player.getName()).replace("%message%", sentMessage).replace("%prefix%", ConfigHandler.getString("prefix"));

                if (TeamChat.getInstance().getConfig().getBoolean("color-ops")) {
                    if (player.isOp()) {
                        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                            if (onlinePlayer.hasPermission(ConfigHandler.getPermission("read"))) {
                                String opMessage = ConfigHandler.getString("message-to-be-sent-in-teamchat-if-op").replace("%player%", player.getName()).replace("%message%", sentMessage);
                                onlinePlayer.sendMessage(opMessage);
                            }
                        }
                    } else {
                        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                            if (onlinePlayer.hasPermission(ConfigHandler.getPermission("read"))) {
                                onlinePlayer.sendMessage(message);
                            }
                        }
                    }
                } else {
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if (onlinePlayer.hasPermission(ConfigHandler.getPermission("read"))) {
                            onlinePlayer.sendMessage(message);
                        }
                    }
                }
            }
        }
    }
}
