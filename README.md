# Intro

A game like Risk.

To play, pull down the source code and run

```
./gradlew runMain
```

If you haven't installed gradle, please do - https://gradle.org/

# Troubleshooting

In order to be able to view the SVG image of the game board, you will need Graphviz.

To install,

```
brew install graphviz
```

Depending on which version of graphviz was installed, you may need to update GRAPHVIZ_DOT in build.gradle, e.g.

```
systemProperty 'GRAPHVIZ_DOT', '/usr/local/Cellar/graphviz/2.40.1/bin/dot'
```
