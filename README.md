# Bukkit4Clojure

This wrapper is for any developer who wishes to develop Bukkit using the LISP-based language Clojure.

## Installation

Simply download from the Releases tab or download the current (WIP) source and install that.

## Usage

##### Loading the plugin
Add this to your plugin.yml:
```yaml
main: com.coalesce.bukkitforclojure.Main
```
And add this to your clojure.yml:

```yaml
clojure: yourns.yourclj
```
Your main clj file should also include the following method, which gets called before your onEnable if it exists, no matter you use the params or not:

```clojure
(defn setData
  [^Main wrapperMain
   ^File dataFolder
   ^FileConfiguration config
   ^PluginDescriptionFile pdf
   ^Server server
   ^Logger logger] ; Will also be in Main/sLogger as a static method.
   )
```

##### Actual use of plugin
Standard methods like onEnable and onDisable are also allowed to be used:

```clojure
(defn onLoad
  []
  (prn "o it loaded")
  )
(defn onEnable
  []
  (prn "o it enabled")
  )
(defn onDisable
  []
  (prn "o it disabled")
  )
```
##### Registering events
To register events you'll first need a method with an event as a param, whether it's type hinted or not:
```clojure
(defn playerQuit
  [^PlayerQuitEvent event]
  (prn (-> event (.getPlayer) (.getName)) " has quit the server.")
  )
```

To now register this, you'll need to do this in the onEnable:
```clojure
(defn onEnable
  []
  (-> (Main/getInstance) (.registerEvent PlayerQuitEvent (EventPriority/NORMAL) #(playerQuit %)))
  )
```

##### Register commands
To register commands, you'll need a pretty standard onCommand method somewhere, but it will only work for the commands you assign to the specific method:
```clojure
(defn clojureCommand
  [^CommandSender sender
   ^Command command
   ^String label
   #^"[Ljava.lang.String;" args] ; Type hinting String arrays isnt possible, thus this workaround.
  (prn (-> command (.getName) (.toUpperCase)) " was executed!")
  )
```
And now onto actually registering the command, again in onEnable:
```clojure
(defn onEnable
 []
 (-> (Main/getInstance) (.registerCommand "pluginYmlName" #(clojureCommand %1 %2 %3 %4)))
 )
```
And register the command like you always would in the plugin.yml.
All exceptions thrown are catched with no output, thus a nice return statement.
### Bugs

Currently we have no bugs which we know of as it hasn't ever been tested, at least for now.

## License

MIT License

Copyright (c) 2017 Project Coalesce

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.