package de.charlotte.spigot.teamchat.handler;

import de.charlotte.spigot.teamchat.TeamChat;

import java.util.Objects;

public class ConfigHandler {

    public static String getString(String path) {
        return Objects.requireNonNull(TeamChat.getInstance().getConfig().getString(path)).replace("%prefix%", Objects.requireNonNull(TeamChat.getInstance().getConfig().getString("prefix")).replace("&", "§")).replace("&", "§");
    }

    public static String getPermission(String permissionName) {
        return Objects.requireNonNull(TeamChat.getInstance().getConfig().getString("permissions." + permissionName));
    }
}
