# Executar no GitHub Codespaces

A configuração usa Java 21 e desktop remoto para a interface Swing. Ao criar o ambiente, o script `scripts/validar.sh` compila o projeto, executa os testes e gera o JAR.

Antes dos comandos, a partir da raiz do repositório, execute:

```sh
cd "java/Árvore Genealógica"
```

## Console

```sh
java -cp out ArvoreGenealogica
```

## Interface gráfica

1. No terminal do Codespace, execute:

```sh
bash scripts/iniciar.sh
```

2. Na aba Ports, abra a porta 6080 no navegador.
3. Clique em Connect e use a senha padrão `vscode` do desktop-lite.
4. Mantenha a porta com visibilidade Private: o acesso deve passar pela sua conta do GitHub.

Para recompilar: `bash scripts/validar.sh`.

Codespaces fornece um ambiente de desenvolvimento. Este projeto continua sendo um aplicativo Swing; o desktop remoto permite acessá-lo pelo navegador. Pare o Codespace ao terminar de usar.

Referências: https://docs.github.com/en/codespaces/developing-in-a-codespace/creating-a-codespace-for-a-repository e https://github.com/devcontainers/features/tree/main/src/desktop-lite.

## Atualizar um Codespace já existente

Alterar o arquivo no GitHub não modifica automaticamente o contêiner em execução. No terminal do editor do Codespace:

```sh
cd /workspaces/edd
git pull --ff-only
```

Depois abra a paleta de comandos (Ctrl+Shift+P) e selecione **Codespaces: Rebuild Container**. A reconstrução instala também o servidor SSH necessário ao GitHub CLI. Ao concluir:

```sh
cd "/workspaces/edd/java/Árvore Genealógica"
bash scripts/iniciar.sh
```

A porta 6080 mostra o desktop remoto; o aplicativo aparece depois de executar o comando acima. Uma página de login do encaminhamento de portas requer autenticação do GitHub, não a senha do VNC.
