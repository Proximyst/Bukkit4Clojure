(ns com.proximyst.bukkitforclojure.example.command
  (:gen-class
    :main false)
  (:import [org.bukkit.command CommandSender Command]
           [org.bukkit.entity Player]
           [org.bukkit Sound]))

(defn clojureCommand
  [^CommandSender sender
   ^Command command
   ^String label
   args]
  (.sendMessage sender "You successfully ran a command in Clojure.")
  (when (instance? Player sender)
    (let [^Player player (cast Player sender)]
      (.playSound player (.getLocation player) (Sound/BLOCK_NOTE_BASEDRUM) (float 1.0) (float 1.0))))
  )