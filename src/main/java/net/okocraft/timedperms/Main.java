package net.okocraft.timedperms;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import net.okocraft.timedperms.command.TimedPermsCommand;
import net.okocraft.timedperms.listener.PlayerListener;
import net.okocraft.timedperms.model.LocalPlayer;
import net.okocraft.timedperms.model.LocalPlayerFactory;
import net.okocraft.timedperms.placeholderapi.PlaceholderHook;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final TimedPermsCommand commandHandler = new TimedPermsCommand(this);
    private PlaceholderHook placeholderHook;

    @Override
    public void onEnable() {
        PlayerListener playerListener = new PlayerListener();
        getServer().getPluginManager().registerEvents(playerListener, this);
        playerListener.subscribeLuckPermsEvents();

        PluginCommand command = Objects.requireNonNull(getCommand("timedperms"));
        command.setExecutor(commandHandler);
        command.setTabCompleter(commandHandler);

        executor.scheduleAtFixedRate(() -> getServer().getOnlinePlayers().stream()
                        .map(Entity::getUniqueId)
                        .map(LocalPlayerFactory::get)
                        .forEach(LocalPlayer::countOne),
                1, 1, TimeUnit.SECONDS
        );

        try {
            placeholderHook = new PlaceholderHook(this);
            placeholderHook.register();
            getLogger().info("Using PlaceholderAPI.");
        } catch (NoClassDefFoundError ignored) {
        }
    }

    @Override
    public void onDisable() {
        if (placeholderHook != null) {
            placeholderHook.unregister();
        }

        executor.shutdownNow();
        getServer().getOnlinePlayers().stream()
                .map(Entity::getUniqueId)
                .map(LocalPlayerFactory::get)
                .forEach(LocalPlayer::saveAndClose);
    }
}
