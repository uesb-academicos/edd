$ErrorActionPreference = 'Stop'
Push-Location $PSScriptRoot
try {
    New-Item -ItemType Directory -Force out | Out-Null
    $sources = Get-ChildItem src,tests -Recurse -Filter *.java | ForEach-Object FullName
    javac -encoding UTF-8 --release 21 -d out $sources
    if ($LASTEXITCODE -ne 0) { throw 'Falha na compilacao.' }
    java '-Djava.awt.headless=true' -cp out TesteArvoreGenealogica
    if ($LASTEXITCODE -ne 0) { throw 'Falha nos testes.' }
    jar --create --file out/EDD-Arvore-Genealogica.jar --main-class br.uesb.edd.arvore.principal.AplicacaoPrincipal -C out ArvoreGenealogica.class -C out br
    if ($LASTEXITCODE -ne 0) { throw 'Falha no empacotamento.' }
} finally { Pop-Location }

