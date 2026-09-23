# Relatório da atividade de árvore genealógica

Autor: Thiago Ferreira Prates Neves. UESB, ADS, Estrutura de Dados, 2026.1.

## Pessoa e fontes

A pessoa escolhida é Salomão. O modelo segue os textos bíblicos de 2 Samuel 12:24 (Davi e Bate-Seba), 2 Samuel 11:3 (Eliã) e Rute 4:13-22 (Obede, Jessé, Davi, Boaz e Rute). Essas são as referências adotadas pelo trabalho original, não uma verificação histórica independente.

## Quantidade de nós

A versão preparada contém **12 nós**, incluindo quatro registros de ancestrais desconhecidos. A distribuição por nível é 1, 2, 4, 3 e 2. A altura é de quatro arestas, correspondendo a cinco níveis. O ZIP original continha dez nós; foram acrescentadas as duas avós desconhecidas para representar explicitamente os quatro avós solicitados.

## Recursividade

Recursividade ocorre quando um método chama a si mesmo para resolver partes menores de um problema. `imprimirArvore` imprime o nó atual e chama a si mesmo para cada filho retornado por `children()`, aumentando o nível em uma unidade. Uma folha não tem filhos e encerra esse ramo da execução. Quatro espaços por nível tornam a hierarquia visível. O percurso visita cada nó uma vez: tempo O(n) e pilha O(h), com h igual à altura da árvore.

Nesta representação, os filhos da estrutura são ancestrais da pessoa acima. O percurso segue de Salomão em direção aos pais e às gerações anteriores.

## Ancestrais desconhecidos

As duas avós, a mãe de Jessé e o pai de Eliã aparecem como desconhecidos. Os demais ramos sem informação não são expandidos. A opção evita inventar nomes ou pressupor uma árvore completa de 31 pessoas. A folha mais profunda continua no quinto nível.

## Maior dificuldade

Pendente de relato pessoal do autor. A documentação original não contém essa resposta; a revisão técnica não deve inventar uma experiência em seu nome.
