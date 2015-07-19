#!/bin/bash

source ci/common.sh

call_gradlew assembleAlpha -PdisablePreDex
tar jcvf ci/artifacts/crashlytics_alpha_artifacts.tbz mobile/build/outputs/mappings/alpha/* mobile/build/intermediates/manifests/full/alpha/*

