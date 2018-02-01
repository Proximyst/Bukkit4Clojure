package com.proximyst.bukkitforclojure;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import org.bukkit.ChatColor;
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
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main
      extends JavaPlugin {
  private static Logger logger;
  private static Main instance;
  private String mainNs;
  private YamlConfiguration clojureConfig;
  private final IFn require = Clojure.var(
        "clojure.core",
        "require"
  );
  private final Map<Class<? extends Event>, Set<IFn>> events = new HashMap<>();
  private final EventExecutor executor = (listener, event) -> fireEvent(event);
  private final Map<Command, IFn> commands = new HashMap<>();

  @Override
  public void onLoad() {
    Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
    instance = this;
    logger = getLogger();
    try (InputStream stream = getResource("clojure.yml");
         Reader reader = new InputStreamReader(stream)) {
      clojureConfig = YamlConfiguration.loadConfiguration(reader);
      mainNs = clojureConfig.getString("clojure");
      if (mainNs == null) {
        setEnabled(false);
        throw new RuntimeException("The plugin couldn't be enabled due to Clojure ns missing.");
      }
      require.invoke(Clojure.read(mainNs));
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (!callIfExists("onLoad")) {
      callIfExists("on-load");
    }
  }

  @Override
  public void onEnable() {
    if (!callIfExists("onEnable")) {
      callIfExists("on-enable");
    }
  }

  @Override
  public void onDisable() {
    if (!callIfExists("onDisable")) {
      callIfExists("on-disable");
    }
  }

  private boolean callIfExists(String name) {
    return callIfExists(
          mainNs,
          name
    );
  }

  public boolean callIfExists(
        String namespace,
        String name,
        Object... args
  ) {
    require.invoke(namespace);
    IFn method = Clojure.var(
          namespace,
          name
    );
    if (method != null) {
      if (args != null && args.length != 0) {
        method.invoke(args);
      } else {
        method.invoke();
      }
      return true;
    }
    return false;
  }

  public void registerEvent(
        final Class<? extends Event> event,
        final EventPriority priority,
        final IFn method
  ) {
    registerEvent(
          event,
          priority,
          method,
          false
    );
  }

  @SuppressWarnings({ "WeakerAccess", "SameParameterValue" })
  public void registerEvent(
        final Class<? extends Event> event,
        final EventPriority priority,
        final IFn method,
        final boolean ignoreCancelled
  ) {
    Set<IFn> methods = events.getOrDefault(
          event,
          new HashSet<>()
    );
    methods.add(method);
    events.put(
          event,
          methods
    );
    getServer().getPluginManager().registerEvent(
          event,
          new Listener() {
          },
          priority,
          executor,
          this,
          ignoreCancelled
    );
  }

  private <T extends Event> void fireEvent(final T event) {
    Set<IFn> methods = events.get(event.getClass());
    if (methods == null) {
      return; // No events have been registered from the plugin, thus no need to fire any.
    }
    methods.forEach(it -> it.invoke(event));
  }

  public void registerCommand(
        final String name,
        final IFn method
  ) {
    PluginCommand command = getCommand(name);
    command.setExecutor(this);
    commands.put(
          command,
          method
    );
  }

  @Override
  public boolean onCommand(
        CommandSender sender,
        Command cmd,
        String label,
        String[] args
  ) {
    IFn command = commands.get(cmd);
    if (command == null) {
      return true;
    }
    try {
      command.invoke(
            sender,
            command,
            label,
            args
      );
    } catch (Throwable throwable) {
      //noinspection ConstantConditions
      if (throwable instanceof Return) {
        return true;
      }
      sender.sendMessage(ChatColor.RED + "An error occurred within the code. Ask an admin about the stacktrace.");
      getLogger().log(
            Level.SEVERE,
            "An error occurred!",
            throwable
      );
    }
    return true;
  }

  public static Logger sLogger() {
    if (logger == null) {
      return logger = getInstance().getLogger();
    }
    return logger;
  }

  public String getMainNs() {
    return mainNs;
  }

  public YamlConfiguration getClojureConfig() {
    return clojureConfig;
  }

  public static Main getInstance() {
    if (instance == null) {
      return instance = Main.getPlugin(Main.class);
    }
    return instance;
  }
}
