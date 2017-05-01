(defproject bukkit4clojure "0.1.0-SNAPSHOT"
  :description "A Clojure wrapper for Bukkit."
  :url "https://github.com/Proximyst/Bukkit4Clojure"
  :license {:name "MIT License"
            :url "https://github.com/Proximyst/Bukkit4Clojure/blob/master/LICENSE"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :repositories {"spigot-repo" "https://hub.spigotmc.org/nexus/content/repositories/snapshots/"}
  :main com.proximyst.bukkitforclojure.example.example
  :java-source-paths ["src"]
  :source-paths ["src"]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :provided {:dependencies [[org.spigotmc/spigot-api "1.11.2-R0.1-SNAPSHOT"]]}})
