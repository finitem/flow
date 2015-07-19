#!/bin/bash

source ci/common.sh

call_gradlew clean assembleRelease -PdisablePreDex
