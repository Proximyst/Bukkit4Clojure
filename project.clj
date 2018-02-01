(defproject org.clojars.proximyst/bukkit-for-clojure "1.0.0"
  :description "A Clojure wrapper for Bukkit."
  :url "https://github.com/Proximyst/Bukkit4Clojure"
  :license {:name "MIT License"
            :url "https://github.com/Proximyst/Bukkit4Clojure/blob/master/LICENSE"}
  :dependencies []
  :repositories {"spigot-repo" "https://hub.spigotmc.org/nexus/content/repositories/snapshots/"}
  :java-source-paths ["src"]
  :source-paths ["src"]
  :target-path "target/%s"
  :profiles {:provided {:aot :all :dependencies [[org.clojure/clojure "1.8.0"] [org.spigotmc/spigot-api "1.11.2-R0.1-SNAPSHOT"]]}}
  )
