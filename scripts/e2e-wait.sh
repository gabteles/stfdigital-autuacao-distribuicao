#!/usr/bin/env bash
set -e

./scripts/shared/wait-up.sh "https://docker:8765/distribuicao/info" 600
