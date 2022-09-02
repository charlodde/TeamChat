package de.charlotte.spigot.teamchat.listener;

import de.charlotte.spigot.teamchat.handler.ConfigHandler;
import de.charlotte.spigot.teamchat.TeamChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void handlePlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        if (TeamChat.getInstance().teamChatPlayers.contains(player)) {
            TeamChat.getInstance().teamChatPlayers.remove(player);
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.hasPermission(ConfigHandler.getPermission("read"))) {
                    onlinePlayer.sendMessage(ConfigHandler.getString("quit-message").replace("%player%", player.getName()));
                }
            }
        }
    }
}
