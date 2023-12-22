package de.charlotte.spigot.teamchat.commands;

import de.charlotte.spigot.teamchat.TeamChat;
import de.charlotte.spigot.teamchat.handler.ConfigHandler;
import de.charlotte.spigot.teamchat.processors.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class TeamChatCommand implements CommandExecutor, TabCompleter {

    private final Map<String, SubCommand> subCommandHandlers = new HashMap<>();

    public TeamChatCommand() {
        subCommandHandlers.put("help", this::helpMenu);
        subCommandHandlers.put("join", this::handleJoinCommand);
        subCommandHandlers.put("leave", this::handleLeaveCommand);
        subCommandHandlers.put("loggedin", this::handleLoggedInCommand);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ConfigHandler.getString("console-executor-error"));
            return false;
        }

        if (!player.hasPermission(ConfigHandler.getPermission("essential-permission"))) {
            player.sendMessage(ConfigHandler.getString("no-permission-error"));
            return false;
        }

        if (args.length == 0) {
            helpMenu(player);
        } else {
            handleCommand(player, args[0]);
        }

        return true;
    }


    private void handleJoinCommand(Player player) {
        handlePermissionCommand(player, ConfigHandler.getPermission("join"),
                () -> {
                    if (TeamChat.getInstance().teamChatPlayers.contains(player)) {
                        player.sendMessage(ConfigHandler.getString("already-logged-in-error"));
                    } else {
                        TeamChat.getInstance().teamChatPlayers.add(player);
                        player.sendMessage(ConfigHandler.getString("joined-teamchat-message"));
                    }
                });
    }

    private void handleLeaveCommand(Player player) {
        handlePermissionCommand(player, ConfigHandler.getPermission("leave"),
                () -> {
                    if (!TeamChat.getInstance().teamChatPlayers.contains(player)) {
                        player.sendMessage(ConfigHandler.getString("not-logged-in-message"));
                    } else {
                        TeamChat.getInstance().teamChatPlayers.remove(player);
                        player.sendMessage(ConfigHandler.getString("left-teamchat-message"));
                    }
                });
    }

    private void handleLoggedInCommand(Player player) {
        handlePermissionCommand(player, ConfigHandler.getPermission("see-all-logged-in-players"),
                () -> {
                    if (TeamChat.getInstance().teamChatPlayers.isEmpty()) {
                        player.sendMessage(ConfigHandler.getString("no-players-logged-in-message"));
                    } else {
                        player.sendMessage(TeamChat.PREFIX + "Players in team chat: ");
                        TeamChat.getInstance().teamChatPlayers.forEach(p -> player.sendMessage("§c" + p.getName()));
                    }
                });
    }


    private void handlePermissionCommand(@NotNull Player player, String permission, Runnable action) {
        if (player.hasPermission(permission)) {
            action.run();
        } else {
            player.sendMessage(ConfigHandler.getString("no-permission-error"));
        }
    }

    private void helpMenu(@NotNull Player player) {
        player.sendMessage(TeamChat.PREFIX + "§f§lTeamChat Help Menu");
        player.sendMessage(TeamChat.PREFIX + " §8- §f/teamchat join - Join the teamchat");
        player.sendMessage(TeamChat.PREFIX + " §8- §f/teamchat leave - Leave the teamchat");
        player.sendMessage(TeamChat.PREFIX + " §8- §f@team <message> - Send a message in the teamchat");
        player.sendMessage(TeamChat.PREFIX + " §8- §f/teamchat loggedin - See all players in the teamchat");
        player.sendMessage(TeamChat.PREFIX + " §8- §f/teamchat help - Show this menu");
    }

    @Override
    public List<String> onTabComplete(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String alias, final String @NotNull [] args) {
        final ArrayList<String> tab = new ArrayList<>();
        if (args.length == 1) {
            Collections.addAll(tab, "join", "leave", "loggedin", "help");
        }
        return tab;
    }

    private void handleCommand(Player player, String subCommand) {
        SubCommand subCommandHandler = subCommandHandlers.get(subCommand);
        if (subCommandHandler != null) {
            subCommandHandler.handle(player);
        } else {
            player.sendMessage(ConfigHandler.getString("invalid-command-error"));
        }
    }

}
