#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
if [[ -z "${DISPLAY:-}" ]]; then
    echo 'Desktop indisponível: execute no terminal do Codespace com desktop-lite ativo.' >&2
    exit 1
fi
bash scripts/validar.sh
exec java -jar out/EDD-Arvore-Genealogica.jar
