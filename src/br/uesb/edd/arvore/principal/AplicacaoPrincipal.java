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
package br.uesb.edd.arvore.principal;

import br.uesb.edd.arvore.erro.GerenciadorErroSistema;
import br.uesb.edd.arvore.tela.TelaPrincipal;

import javax.swing.*;

/**
 * Classe principal do programa.
 *
 * Explicação do programador:
 * Esta classe contém o método main, que é o ponto de entrada da aplicação.
 * Ela configura uma aparência simples do Swing e abre a tela principal.
 */
public class AplicacaoPrincipal {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                TelaPrincipal telaPrincipal = new TelaPrincipal();
                telaPrincipal.setVisible(true);
            } catch (Exception excecao) {
                GerenciadorErroSistema.erro(null, "Erro ao iniciar a aplicação.", excecao);
            }
        });
    }
}
