(defproject bukkit4clojure "0.2.0-SNAPSHOT"
  :description "A Clojure wrapper for Bukkit."
  :url "https://github.com/Proximyst/Bukkit4Clojure"
  :license {:name "MIT License"
            :url "https://github.com/Proximyst/Bukkit4Clojure/blob/master/LICENSE"}
  :dependencies []
  :repositories {"spigot-repo" "https://hub.spigotmc.org/nexus/content/repositories/snapshots/"}
  :java-source-paths ["src"]
  :source-paths ["src"]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :provided {:aot :all :dependencies [[org.clojure/clojure "1.8.0"] [org.spigotmc/spigot-api "1.11.2-R0.1-SNAPSHOT"]]}}
  )
