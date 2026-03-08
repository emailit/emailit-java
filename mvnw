#!/bin/sh
# Maven Wrapper script - downloads and runs Maven
set -e

MAVEN_HOME="$HOME/.m2/wrapper/dists/apache-maven-3.9.9"
MAVEN_BIN="$MAVEN_HOME/bin/mvn"

if [ ! -f "$MAVEN_BIN" ]; then
    echo "Downloading Apache Maven 3.9.9..."
    mkdir -p "$MAVEN_HOME"
    TMPFILE=$(mktemp)
    curl -sL "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.9/apache-maven-3.9.9-bin.tar.gz" -o "$TMPFILE"
    tar xzf "$TMPFILE" -C "$MAVEN_HOME" --strip-components=1
    rm -f "$TMPFILE"
    echo "Maven installed to $MAVEN_HOME"
fi

exec "$MAVEN_BIN" "$@"
