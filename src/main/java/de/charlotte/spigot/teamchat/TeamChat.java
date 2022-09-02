package de.charlotte.spigot.teamchat;

import de.charlotte.spigot.teamchat.commands.TeamChatCommand;
import de.charlotte.spigot.teamchat.listener.AsyncPlayerChatListener;
import de.charlotte.spigot.teamchat.listener.JoinListener;
import de.charlotte.spigot.teamchat.listener.QuitListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Objects;

public final class TeamChat extends JavaPlugin {

    @Getter
    private static TeamChat instance;
    public static final String PREFIX = "§8[§6TeamChat§8] §f§l";
    public ArrayList<Player> teamChatPlayers = new ArrayList<>();

    @Override
    public void onEnable() {
       instance = this;

       instance.registerCommands();
       instance.registerListeners();
       instance.init();
       saveDefaultConfig();
    }

    @Override
    public void onDisable() {
       instance = null;
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new QuitListener(), this);
        getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(instance.getCommand("teamchat")).setExecutor(new TeamChatCommand());
    }

    private void init() {
        Bukkit.getConsoleSender().sendMessage("§c");
        Bukkit.getConsoleSender().sendMessage("§c$$$$$$$$\\                                 $$$$$$\\  $$\\                  $$\\                         $$\\       $$$$$$\\  ");
        Bukkit.getConsoleSender().sendMessage("§c\\__$$  __|                               $$  __$$\\ $$ |                 $$ |                      $$$$ |     $$$ __$$\\ ");
        Bukkit.getConsoleSender().sendMessage("§c   $$ | $$$$$$\\   $$$$$$\\  $$$$$$\\$$$$\\  $$ /  \\__|$$$$$$$\\   $$$$$$\\ $$$$$$\\         $$\\    $$\\  \\_$$ |     $$$$\\ $$ |");
        Bukkit.getConsoleSender().sendMessage("§c   $$ |$$  __$$\\  \\____$$\\ $$  _$$  _$$\\ $$ |      $$  __$$\\  \\____$$\\\\_$$  _|        \\$$\\  $$  |   $$ |     $$\\$$\\$$ |");
        Bukkit.getConsoleSender().sendMessage("§c   $$ |$$$$$$$$ | $$$$$$$ |$$ / $$ / $$ |$$ |      $$ |  $$ | $$$$$$$ | $$ |           \\$$\\$$  /    $$ |     $$ \\$$$$ |");
        Bukkit.getConsoleSender().sendMessage("§c   $$ |$$   ____|$$  __$$ |$$ | $$ | $$ |$$ |  $$\\ $$ |  $$ |$$  __$$ | $$ |$$\\         \\$$$  /     $$ |     $$ |\\$$$ |");
        Bukkit.getConsoleSender().sendMessage("§c   $$ |\\$$$$$$$\\ \\$$$$$$$ |$$ | $$ | $$ |\\$$$$$$  |$$ |  $$ |\\$$$$$$$ | \\$$$$  |         \\$  /$$\\ $$$$$$\\ $$\\\\$$$$$$  /");
        Bukkit.getConsoleSender().sendMessage("§c   \\__| \\_______| \\_______|\\__| \\__| \\__| \\______/ \\__|  \\__| \\_______|  \\____/           \\_/ \\__|\\______|\\__|\\______/ ");
        Bukkit.getConsoleSender().sendMessage("§c");
    }
}
