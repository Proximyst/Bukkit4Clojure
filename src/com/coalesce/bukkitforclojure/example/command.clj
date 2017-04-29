(ns com.coalesce.bukkitforclojure.example.command
  (:import [org.bukkit.command CommandSender Command]
           [org.bukkit.entity Player]
           [org.bukkit Sound]))

(defn clojureCommand
  [^CommandSender sender
   ^Command command
   ^String label
   #^"[Ljava.lang.String;" args]
  (.sendMessage sender "You successfully ran a command in Clojure.")
  (if (instance? Player sender)
    (let [player (cast Player sender)]
      (.playSound player (.getLocation player) (Sound/BLOCK_NOTE_BASEDRUM) 1.0 1.0)))
  )