#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
mkdir -p out
find src tests -name '*.java' | sort > sources.txt
javac -encoding UTF-8 --release 21 -d out @sources.txt
java -Djava.awt.headless=true -cp out TesteArvoreGenealogica
jar --create --file out/EDD-Arvore-Genealogica.jar --main-class br.uesb.edd.arvore.principal.AplicacaoPrincipal -C out ArvoreGenealogica.class -C out br
