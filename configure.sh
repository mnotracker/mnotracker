#!/bin/bash

function cd_project_dir() {
    cd $(dirname "$0")
    echo "current directory is $(pwd)"
}

set -e
cd_project_dir

ln -sf ../../scripts/pre-commit .git/hooks/pre-commit
mkdir -p lib
ln -sf "${ANDROID_HOME}/extras/android/support/v4/android-support-v4.jar" lib/

sbt exit
