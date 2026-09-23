# Executar no GitHub Codespaces

A configuração usa Java 21 e desktop remoto para a interface Swing. Ao criar o ambiente, o script `scripts/validar.sh` compila o projeto, executa os testes e gera o JAR.

## Console

```sh
java -cp out ArvoreGenealogica
```

## Interface gráfica

1. No terminal do Codespace, execute:

```sh
java -jar out/EDD-Arvore-Genealogica.jar
```

2. Na aba Ports, abra a porta 6080 no navegador.
3. Clique em Connect e use a senha padrão `vscode` do desktop-lite.
4. Mantenha a porta com visibilidade Private: o acesso deve passar pela sua conta do GitHub.

Para recompilar: `bash scripts/validar.sh`.

Codespaces fornece um ambiente de desenvolvimento. Este projeto continua sendo um aplicativo Swing; o desktop remoto permite acessá-lo pelo navegador. Pare o Codespace ao terminar de usar.

Referências: https://docs.github.com/en/codespaces/developing-in-a-codespace/creating-a-codespace-for-a-repository e https://github.com/devcontainers/features/tree/main/src/desktop-lite.
