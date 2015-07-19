#!/bin/bash

source ci/common.sh

call_gradlew assembleDebug -PdisablePreDex
