#!/usr/bin/env bash

if ./runcrud.sh; then
    firefox http://localhost:8080/crud/api/v1/tasks
else
    echo "runcrud.sh has failed to run"
fi