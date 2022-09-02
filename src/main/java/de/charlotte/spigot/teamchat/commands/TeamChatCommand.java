package de.charlotte.spigot.teamchat.commands;

import de.charlotte.spigot.teamchat.handler.ConfigHandler;
import de.charlotte.spigot.teamchat.TeamChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TeamChatCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ConfigHandler.getString("console-executor-error"));
            return false;
        }

        final Player player = (Player) sender;
        if (!(player.hasPermission(ConfigHandler.getPermission("essential-permission")))) {
            player.sendMessage(ConfigHandler.getString("no-permission-error"));
            return false;
        }

        if (args.length == 0) {
            helpMenu(player);
        }
        if (args.length == 1) {

            switch (args[0]) {
                case "help":
                    helpMenu(player);
                    break;
                case "join":
                    if (player.hasPermission(ConfigHandler.getPermission("join"))) {
                        if (TeamChat.getInstance().teamChatPlayers.contains(player)) {
                            player.sendMessage(ConfigHandler.getString("already-logged-in-error"));
                            return false;
                        } else {
                            TeamChat.getInstance().teamChatPlayers.add(player);
                            player.sendMessage(ConfigHandler.getString("joined-teamchat-message"));
                        }
                    } else {
                        player.sendMessage(ConfigHandler.getString("no-permission-error"));
                    }
                    break;

                case "leave":
                    if (player.hasPermission(ConfigHandler.getPermission("leave"))) {
                        if (!(TeamChat.getInstance().teamChatPlayers.contains(player))) {
                            player.sendMessage(ConfigHandler.getString("not-logged-in-message"));
                            return false;
                        } else {
                            TeamChat.getInstance().teamChatPlayers.remove(player);
                            player.sendMessage(ConfigHandler.getString("left-teamchat-message"));
                        }
                    } else {
                        player.sendMessage(ConfigHandler.getString("no-permission-error"));
                    }
                    break;

                case "loggedin":
                    if (player.hasPermission(ConfigHandler.getPermission("see-all-logged-in-players"))) {
                        if (TeamChat.getInstance().teamChatPlayers.isEmpty()) {
                            player.sendMessage(ConfigHandler.getString("no-players-logged-in-message"));
                            return false;
                        } else {
                            player.sendMessage(TeamChat.PREFIX + "Players in team chat: ");
                            for (Player p : TeamChat.getInstance().teamChatPlayers) {
                                player.sendMessage("§c" + p.getName());
                            }
                        }
                    } else {
                        player.sendMessage(ConfigHandler.getString("no-permission-error"));
                    }
                    break;
            }
        }
        return false;
    }


    public List<String> onTabComplete(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String alias, final String[] args) {
        final ArrayList<String> tab = new ArrayList<>();
        if (args.length == 1) {
            tab.add("join");
            tab.add("leave");
            tab.add("loggedin");
            tab.add("help");
        }
        return tab;
    }

    private void helpMenu(Player player) {
        player.sendMessage(TeamChat.PREFIX + "§f§lTeamChat Help Menu");
        player.sendMessage(TeamChat.PREFIX + " §8- §f/teamchat join - Join the teamchat");
        player.sendMessage(TeamChat.PREFIX + " §8- §f/teamchat leave - Leave the teamchat");
        player.sendMessage(TeamChat.PREFIX + " §8- §f@team <message> - Send a message in the teamchat");
        player.sendMessage(TeamChat.PREFIX + " §8- §f/teamchat loggedin - See all players in the teamchat");
        player.sendMessage(TeamChat.PREFIX + " §8- §f/teamchat help - Show this menu");
    }
}
