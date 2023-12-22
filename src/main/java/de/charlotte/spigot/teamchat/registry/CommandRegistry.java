package de.charlotte.spigot.teamchat.registry;

import de.charlotte.spigot.teamchat.TeamChat;
import de.charlotte.spigot.teamchat.commands.TeamChatCommand;
import lombok.Getter;

import java.util.Objects;

@Getter
public class CommandRegistry {

    private final TeamChat instance;

    public CommandRegistry(TeamChat instance) {
        this.instance = instance;
    }

    public void registerCommands() {
        Objects.requireNonNull(instance.getCommand("teamchat")).setExecutor(new TeamChatCommand());
    }
}
