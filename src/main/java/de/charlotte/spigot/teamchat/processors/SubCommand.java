package de.charlotte.spigot.teamchat.processors;

import org.bukkit.entity.Player;

public interface SubCommand {
    void handle(Player player);
}
