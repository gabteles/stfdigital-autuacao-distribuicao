#!/usr/bin/env bash
set -e

./shared/scripts/wait-up.sh "https://docker:8765/processos/manage/info" 300
./shared/scripts/wait-up.sh "https://docker:8765/distribuicao/manage/info" 300