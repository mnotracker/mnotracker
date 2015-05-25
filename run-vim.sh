#!/bin/bash

PROJECT_NAME=$(basename `pwd`)
echo "project name is '${PROJECT_NAME}'"

sbt eclipse
vi -c ":ProjectCreate . -p ${PROJECT_NAME} -n scala -n java" -c ":ProjectOpen" -c ":ProjectBuild" -c ":ProjectTreeToggle"
vi -c ":ProjectClose" -c ":q"
