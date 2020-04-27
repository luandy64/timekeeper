# Sundial

A responsive Clojurescript + Reagent clock

## Developing

- Start REPL
```
$ lein repl
2020-04-25 17:32:21.312:INFO::main: Logging initialized @16942ms to org.eclipse.jetty.util.log.StdErrLog
nREPL server started on port 60175 on host 127.0.0.1 - nrepl://127.0.0.1:60175
REPL-y 0.4.3, nREPL 0.7.0
Clojure 1.10.1
Java HotSpot(TM) 64-Bit Server VM 1.8.0_191-b12
    Docs: (doc function-name-here)
          (find-doc "part-of-name-here")
  Source: (source function-name-here)
 Javadoc: (javadoc java-object-or-class-here)
    Exit: Control+D or (exit) or (quit)
 Results: Stored in vars *1, *2, *3, an exception in *e

sundial.repl=>
```
- Get the hot reloading working and the cljs REPL
```
sundial.repl=> (start-figwheel!)
```
- Potential troubleshooting server issues
```
sundial.repl=> (cljs-repl)
cljs.user=> (reset-autobuild)
```
- Emacs + nREPL in another terminal
  - Connect to nREPL server
```
M-x cider-connect-cljs
localhost
<whatever port it picked>
figwheel
```

## Building for release

```
lein do clean, uberjar
```
