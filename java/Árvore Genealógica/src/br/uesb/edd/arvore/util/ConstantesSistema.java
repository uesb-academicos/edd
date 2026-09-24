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
package br.uesb.edd.arvore.util;

import java.awt.*;

/**
 * Constantes visuais e textos fixos do sistema.
 */
public class ConstantesSistema {
    public static final String NOME_SISTEMA = "EDD Árvore Genealógica Bíblica Inteligente";
    public static final String AUTOR_COMPLETO = "Thiago Ferreira Prates Neves";
    public static final String SITE_AUTOR = "thiagoprates.com.br";
    public static final Color VERDE_ESCURO = new Color(46, 83, 57);
    public static final Color VERDE_CLARO = new Color(222, 235, 216);
    public static final Color DOURADO = new Color(184, 134, 11);
    public static final Color FUNDO = new Color(245, 243, 235);
    public static final Color CARTAO = new Color(255, 252, 241);
    public static final Font FONTE_TITULO = new Font("SansSerif", Font.BOLD, 18);
    public static final Font FONTE_NORMAL = new Font("SansSerif", Font.PLAIN, 13);

    private ConstantesSistema() {}
}
