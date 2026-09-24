/**
 * =========================================================
 * UNIVERSIDADE ESTADUAL DO SUDOESTE DA BAHIA (UESB)
 * CURSO: ADS
 * DISCIPLINA: Estrutura de Dados
 * PROFESSOR: Murilo Silva Santana
 * =========================================================
 *
 * ATIVIDADE PRÁTICA — ÁRVORE GENEALÓGICA EM JAVA
 *
 * SISTEMA:
 * EDD Árvore Genealógica Bíblica Inteligente
 *
 * AUTOR:
 * Thiago Ferreira Prates Neves
 *
 * SITE:
 * thiagoprates.com.br
 *
 * FLUXO E REGRAS DE NEGÓCIO:
 * O sistema carrega modelos genealógicos bíblicos, permite cadastrar,
 * editar, remover e buscar pessoas, desenha a árvore em tela, imprime
 * a estrutura com recursividade e apresenta referências bíblicas.
 * =========================================================
 */
package br.uesb.edd.arvore.modelo;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Guarda o resultado de uma busca dentro da árvore.
 *
 * Explicação do programador:
 * Em vez de retornar apenas o nome da pessoa encontrada, retornamos o nó.
 * Isso permite selecionar o item na tela, editar, remover e visualizar detalhes.
 */
public class ResultadoBusca {
    private final DefaultMutableTreeNode noEncontrado;
    private final PessoaGenealogica pessoa;

    public ResultadoBusca(DefaultMutableTreeNode noEncontrado, PessoaGenealogica pessoa) {
        this.noEncontrado = noEncontrado;
        this.pessoa = pessoa;
    }

    public DefaultMutableTreeNode getNoEncontrado() { return noEncontrado; }
    public PessoaGenealogica getPessoa() { return pessoa; }
}
