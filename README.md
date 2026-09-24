# Estrutura de Dados — UESB 2026.1

Atividades acadêmicas do curso de Análise e Desenvolvimento de Sistemas.

## Java

- [Árvore Genealógica](java/%C3%81rvore%20Geneal%C3%B3gica/README.md): aplicação de console e Swing, com cinco níveis de ancestralidade.

```text
edd/
├── .devcontainer/
├── .github/workflows/
└── java/
    └── Árvore Genealógica/
        ├── src/
        ├── tests/
        ├── docs/
        ├── scripts/
        ├── validar.ps1
        └── README.md
```

## Executar

No terminal do Codespace ou Linux:

```sh
cd "java/Árvore Genealógica"
bash scripts/validar.sh
java -cp out ArvoreGenealogica
```

No PowerShell:

```powershell
cd "java/Árvore Genealógica"
./validar.ps1
java -cp out ArvoreGenealogica
```

Para a interface Swing e o desktop remoto, consulte [Codespaces](java/%C3%81rvore%20Geneal%C3%B3gica/docs/CODESPACES.md).
