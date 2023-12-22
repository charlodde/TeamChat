package de.charlotte.spigot.teamchat;

import de.charlotte.spigot.teamchat.registry.CommandRegistry;
import de.charlotte.spigot.teamchat.registry.ListenerRegistry;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class TeamChat extends JavaPlugin {

    @Getter
    private static TeamChat instance;
    public static final String PREFIX = "§8[§6TeamChat§8] §f§l";
    public ArrayList<Player> teamChatPlayers = new ArrayList<>();

    @Override
    public void onEnable() {
       instance = this;

       registerCommands();
       registerListeners();
       saveDefaultConfig();
    }

    @Override
    public void onDisable() {
       instance = null;
    }

    private void registerListeners() {
        ListenerRegistry listenerRegistry = new ListenerRegistry(instance);
        listenerRegistry.registerListeners();
    }

    private void registerCommands() {
        CommandRegistry commandRegistry = new CommandRegistry(instance);
        commandRegistry.registerCommands();
    }
}
