#!/usr/bin/env bash
set -e

./shared/scripts/wait-up.sh "https://docker:8765/distribuicao/info" 600