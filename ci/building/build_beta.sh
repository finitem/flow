#!/bin/bash

source ci/common.sh

call_gradlew assembleBeta -PdisablePreDex
tar jcvf ci/artifacts/crashlytics_beta_artifacts.tbz mobile/build/outputs/mappings/beta/* mobile/build/intermediates/manifests/full/beta/*
