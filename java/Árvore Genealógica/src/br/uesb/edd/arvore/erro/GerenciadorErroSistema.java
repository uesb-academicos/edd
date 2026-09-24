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
package br.uesb.edd.arvore.erro;

import javax.swing.*;
import java.awt.*;

/**
 * Classe centralizada para mensagens de erro, aviso e sucesso.
 *
 * Explicação do programador:
 * Centralizar as mensagens evita repetição de JOptionPane espalhado pelo sistema.
 * Também deixa o programa mais fácil de manter, porque qualquer mudança visual
 * nas mensagens pode ser feita em um único lugar.
 */
public class GerenciadorErroSistema {

    private GerenciadorErroSistema() {}

    public static void informar(Component componentePai, String mensagem) {
        JOptionPane.showMessageDialog(componentePai, mensagem, "Mensagem do Sistema", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void avisar(Component componentePai, String mensagem) {
        JOptionPane.showMessageDialog(componentePai, mensagem, "Atenção", JOptionPane.WARNING_MESSAGE);
    }

    public static void erro(Component componentePai, String mensagem, Exception excecao) {
        String detalhe = excecao == null ? "" : "\n\nDetalhe técnico: " + excecao.getMessage();
        JOptionPane.showMessageDialog(componentePai, mensagem + detalhe, "Erro do Sistema", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean confirmar(Component componentePai, String mensagem) {
        int resposta = JOptionPane.showConfirmDialog(componentePai, mensagem, "Confirmar operação", JOptionPane.YES_NO_OPTION);
        return resposta == JOptionPane.YES_OPTION;
    }
}
