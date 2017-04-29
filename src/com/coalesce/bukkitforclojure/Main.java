package com.coalesce.bukkitforclojure;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    private static Logger logger;
    private static Main instance;
    private String mainNs;
    private YamlConfiguration clojureConfig;
    private final IFn require = Clojure.var("clojure.core", "require");
    private final Map<Class<? extends Event>, Set<IFn>> events = new HashMap<>();
    private final EventExecutor executor = (listener, event) -> fireEvent(event);
    private final Map<Command, IFn> commands = new HashMap<>();

    @Override
    public void onLoad() {
        Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
        instance = this;
    }

    @Override
    public void onEnable() {
        logger = getLogger();
        try (InputStream stream = getResource("clojure.yml")) {
            clojureConfig = YamlConfiguration.loadConfiguration(stream);
            mainNs = clojureConfig.getString("clojure");
            if (mainNs == null) {
                setEnabled(false);
                throw new RuntimeException("The plugin couldn't be enabled due to Clojure ns missing.");
            }
            require.invoke(Clojure.read(mainNs));
        } catch (IOException e) {
            e.printStackTrace();
        }

        IFn setData = Clojure.var(mainNs, "setData");
        if (setData == null) {
            setEnabled(false);
            throw new RuntimeException("The clojure plugin couldn't be enabled due to missing setData method.");
        }
        setData.invoke(this, getDataFolder(), getConfig(), getDescription(), getServer(), getLogger());

        IFn onEnable = Clojure.var(mainNs, "onEnable");
        if (onEnable != null) {
            onEnable.invoke();
        }
    }

    @Override
    public void onDisable() {
        IFn onDisable = Clojure.var(mainNs, "onDisable");
        if (onDisable != null) {
            onDisable.invoke();
        }
    }

    public void registerEvent(final Class<? extends Event> event, final EventPriority priority, final IFn method) {
        registerEvent(event, priority, method, false);
    }

    @SuppressWarnings({"WeakerAccess", "SameParameterValue"})
    public void registerEvent(final Class<? extends Event> event, final EventPriority priority, final IFn method, final boolean ignoreCancelled) {
        Set<IFn> methods = events.getOrDefault(event, new HashSet<>());
        methods.add(method);
        events.put(event, methods);
        getServer().getPluginManager().registerEvent(event, new Listener() {
        }, priority, executor, this, ignoreCancelled);
    }

    private <T extends Event> void fireEvent(final T event) {
        Set<IFn> methods = events.get(event.getClass());
        if (methods == null) {
            return; // No events have been registered from the plugin, thus no need to fire any.
        }
        methods.forEach(it -> it.invoke(event));
    }

    public void registerCommand(final String name, final IFn method) {
        PluginCommand command = getCommand(name);
        command.setExecutor(this);
        commands.put(command, method);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        IFn command = commands.get(cmd);
        if (command == null) {
            return true;
        }
        try {
            command.invoke(sender, command, label, args);
        } catch (Exception ex) {
            // Due to clojure having no return statement, this will be kind of the statement.
            // If the developer expects one, use a try-catch block in Clojure.
        }
        return true;
    }

    public static Logger sLogger() {
        return logger;
    }

    public String getMainNs() {
        return mainNs;
    }

    public YamlConfiguration getClojureConfig() {
        return clojureConfig;
    }

    public static Main getInstance() {
        return instance;
    }
}
