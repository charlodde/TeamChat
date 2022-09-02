package de.charlotte.spigot.teamchat.listener;

import de.charlotte.spigot.teamchat.handler.ConfigHandler;
import de.charlotte.spigot.teamchat.TeamChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void handlePlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        if (!(TeamChat.getInstance().teamChatPlayers.contains(player))) {
            if (player.hasPermission(ConfigHandler.getPermission("autojoin"))) {
                TeamChat.getInstance().teamChatPlayers.add(player);
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.hasPermission(ConfigHandler.getPermission("read"))) {
                        onlinePlayer.sendMessage(ConfigHandler.getString("join-message").replace("%player%", player.getName()));
                    }
                }
            }
        }
    }
}
