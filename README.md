# Árvore genealógica em Java

Atividade de Estrutura de Dados (2026.1), curso de Análise e Desenvolvimento de Sistemas da Universidade Estadual do Sudoeste da Bahia (UESB).

Autor: Thiago Ferreira Prates Neves. Repositório de destino: https://github.com/uesb-academicos/edd.

## Versão acadêmica

`src/ArvoreGenealogica.java` segue a estrutura do código-base: cria manualmente os nós no `main`, conecta com `add()` e chama `imprimirArvore(DefaultMutableTreeNode node, int nivel)`. O percurso usa recursão e `children()`, com quatro espaços por nível.

O modelo de Salomão possui **12 nós e 5 níveis**: pessoa principal, pais, avós, bisavós e trisavós. Nomes não localizados são identificados como desconhecidos; ramos sem informação adicional terminam antecipadamente. Consulte [o relatório](docs/RELATORIO.md).

## Compilar e executar

Requisito: JDK 21 com `java`, `javac` e `jar` no PATH. Não requer dependências externas ou IDE específica.

No PowerShell, dentro da pasta do projeto:

```powershell
./validar.ps1
java -cp out ArvoreGenealogica
java -cp out br.uesb.edd.arvore.principal.AplicacaoPrincipal
```

No Linux/macOS:

```sh
mkdir -p out
find src tests -name '*.java' > sources.txt
javac -encoding UTF-8 --release 21 -d out @sources.txt
java -Djava.awt.headless=true -cp out TesteArvoreGenealogica
java -cp out ArvoreGenealogica
java -cp out br.uesb.edd.arvore.principal.AplicacaoPrincipal
```

A interface Swing exige ambiente gráfico. Na IDE, marque `src` como pasta de fontes e escolha uma das duas classes principais.

## Aplicação Swing

A aplicação original foi mantida em `src/br/uesb/edd/arvore`. Oferece JTree, desenho da árvore, cadastro, edição, remoção, busca, referências e exportações. O modelo de Salomão é o modelo acadêmico. O modelo de Adão representa descendentes e é apenas um exemplo adicional; não corresponde à ascendência exigida no roteiro.

As edições são mantidas em memória. Salvar relatório exporta texto, sem reimportação da árvore. O editor permite alterações livres e não garante os cinco níveis após edições. Impressão, diálogos e exportações gráficas ainda precisam de conferência interativa.

## Organização

- `src/ArvoreGenealogica.java`: entrega de console independente.
- `src/br/uesb/edd/arvore/`: aplicação Swing organizada em pacotes.
- `tests/`: validação automática sem dependências externas.
- `docs/RELATORIO.md`: respostas técnicas da atividade.
- `docs/ANALISE.md`: revisão da entrega original e alterações.
- `.github/workflows/java-ci.yml`: compilação, testes e JAR no GitHub Actions.

## Autoria e referências

O projeto original é de Thiago Ferreira Prates Neves. Esta preparação acrescenta a versão de console, testes e ajustes documentados na análise. Fontes genealógicas adotadas: 2 Samuel 12:24; 2 Samuel 11:3; Rute 4:13-22. As relações são apresentadas conforme esses textos bíblicos.

Não foi adicionada licença de redistribuição: a indicação original de finalidade educacional não equivale a uma licença de software.

## GitHub Codespaces

Abra o repositório no GitHub e selecione Code > Codespaces > Create codespace on main. A configuração inclui JDK 21, testes automáticos e desktop para Swing. Veja [como usar](docs/CODESPACES.md).
