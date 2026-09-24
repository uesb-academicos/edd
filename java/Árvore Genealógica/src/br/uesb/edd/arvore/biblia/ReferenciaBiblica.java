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
package br.uesb.edd.arvore.biblia;

/**
 * Classe simples para guardar uma referência bíblica e uma explicação.
 */
public class ReferenciaBiblica {
    private final String referencia;
    private final String textoResumo;
    private final String nomesDestacados;

    public ReferenciaBiblica(String referencia, String textoResumo, String nomesDestacados) {
        this.referencia = referencia;
        this.textoResumo = textoResumo;
        this.nomesDestacados = nomesDestacados;
    }

    public String getReferencia() { return referencia; }
    public String getTextoResumo() { return textoResumo; }
    public String getNomesDestacados() { return nomesDestacados; }
}
