# Bukkit4Clojure

This wrapper is for any developer who wishes to develop Bukkit using the LISP-based language Clojure.

## Installation

Simply download from the Releases tab or download the current (WIP) source and install that.

## Usage

Add this to your plugin.yml:

    main: com.coalesce.bukkitforclojure.Main
And add this to your wrapper.yml:

    clojure: yourns.yourclj
Your main clj file should also include the following method, which gets called before your onEnable if it exists, no matter you use the params or not:

    (defn setData
      [^Main wrapperMain
       ^File dataFolder
       ^FileConfiguration config
       ^PluginDescriptionFile pdf
       ^Server server
       ^Logger logger] ; Will also be in Main/sLogger as a static method.
       )

Standard methods like onEnable and onDisable are also allowed to be used:

    (defn onEnable
      []
      (println "o it enabled")
      )
    (defn onDisable
      []
      (println "o it disabled")
      )
### Bugs

Currently we have no bugs which we know of as it's not even developed currently.

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