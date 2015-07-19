#!/bin/bash

set -e

function call_gradlew() {
    # Disable gradle log outputting
    TERM=dumb

    ./gradlew --no-daemon $*
}
