# Timekeeper

A responsive Clojurescript + Reagent clock

## Developing

TODO: Fill this in

## Building for release

```
lein do clean, uberjar
```

Then copy some files around

```
cp target/cljsbuild/public/js/app.js docs/main.js
cp resources/public/css/site.min.css docs/site.css
```
