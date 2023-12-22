package de.charlotte.spigot.teamchat.registry;

import de.charlotte.spigot.teamchat.TeamChat;
import de.charlotte.spigot.teamchat.listener.AsyncPlayerChatListener;
import de.charlotte.spigot.teamchat.listener.JoinListener;
import de.charlotte.spigot.teamchat.listener.QuitListener;
import lombok.Getter;

@Getter
public class ListenerRegistry {

    private final TeamChat instance;

    public ListenerRegistry(TeamChat instance) {
        this.instance = instance;
    }

    public void registerListeners() {
        instance.getServer().getPluginManager().registerEvents(new JoinListener(), instance);
        instance.getServer().getPluginManager().registerEvents(new QuitListener(), instance);
        instance.getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(), instance);
    }

}
