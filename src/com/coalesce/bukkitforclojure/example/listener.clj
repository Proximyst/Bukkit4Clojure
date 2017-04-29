(ns com.coalesce.bukkitforclojure.example.listener
  (:import [org.bukkit.event.player PlayerJoinEvent]
           [org.bukkit Bukkit ChatColor]
           [org.bukkit.entity Player]))

(defn playerJoinedHandler
  [^PlayerJoinEvent event]
  (Bukkit/broadcastMessage (ChatColor/translateAlternateColorCodes \& (str "&c" (-> (.getPlayer event) (.getName)) " has joined the Clojure-run server!")))
  )