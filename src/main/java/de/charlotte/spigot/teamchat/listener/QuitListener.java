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
    public void handlePlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (TeamChat.getInstance().teamChatPlayers.contains(player)) {
            TeamChat.getInstance().teamChatPlayers.remove(player);

            Bukkit.getOnlinePlayers().stream()
                    .filter(onlinePlayer -> onlinePlayer.hasPermission(ConfigHandler.getPermission("read")))
                    .forEach(onlinePlayer -> onlinePlayer.sendMessage(ConfigHandler.getString("quit-message").replace("%player%", player.getName())));
        }
    }
}
