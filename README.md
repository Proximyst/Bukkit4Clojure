# Bukkit4Clojure

This wrapper is for any developer who wishes to develop Bukkit using the LISP-based language Clojure.

## Installation

Simply download from the Releases tab or download the current (WIP) source and install that.

## Usage

##### Loading the plugin
Add this to your plugin.yml:

```yaml
main: com.proximyst.bukkitforclojure.Main
```
Assuming you didn't use the plugin [redbadger/shade](https://github.com/redbadger/shade) or alike to relocate.

Relocating is however highly recommended, nonetheless, as more than 1 plugin may be defined.

And add this to your clojure.yml:

```yaml
clojure: yourns.yourclj
```

In order to get data about the actual `Plugin` instance, use `Main/getInstance` to get an instance, or call something like this, but with the info you wish to get:

```clojure
(def logger (.getLogger (Main/instance)) ; Main also has Main/getLogger defined for this.
```

##### Actual use of plugin
Standard methods like onEnable and onDisable are also allowed to be used:

```clojure
(defn onLoad ; Can also be on-load.
  []
  (.info (Main/getLogger) "o it loaded")
  )
(defn onEnable ; Can also be on-enable.
  []
  (.info (Main/getLogger) "o it enabled")
  )
(defn onDisable ; Can also be on-disable.
  []
  (.info (Main/getLogger) "o it disabled")
  )
```
##### Registering events
To register events you'll first need a method with an event as a param, whether it's type hinted or not:
```clojure
(defn playerQuit
  [^PlayerQuitEvent event]
  (.info (Main/getLogger) (str (-> event (.getPlayer) (.getName)) " has quit the server."))
  )
```

To now register this, you'll need to do this in the onEnable, but remember to call use on it first:
```clojure
(.registerEvent (Main/getInstance) PlayerQuitEvent (EventPriority/NORMAL) playerQuit))
```

##### Register commands
To register commands, you'll need a pretty standard onCommand method somewhere, but it will only work for the commands you assign to the specific method:
```clojure
(defn clojureCommand
  [^CommandSender sender
   ^Command command
   ^String label
   & args] ; Type hinting String arrays isnt possible, thus we don't have any type of it, and rather just use it like a collection within clojure.
  (.info (Main/getLogger) (str (-> command (.getName) (.toUpperCase)) " was executed!"))
  )
```
And now onto actually registering the command, again in onEnable:
```clojure
(.registerCommand (Main/getInstance) "pluginYmlName" clojureCommand)
```
And register the command like you always would in the plugin.yml.
All exceptions thrown are caught with output, unless they're instance of Return.

### Bugs

Currently no bugs are known.

## TO-DO/Plans

- [ ] Write directly to Bukkit's CommandMap.

## [License](/LICENSE)

```
MIT License

Copyright (c) 2018 Mariell Hoversholm

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
```