package de.charlotte.spigot.teamchat.processor;

import org.bukkit.entity.Player;

public interface SubCommand {
    void handle(Player player);
}
