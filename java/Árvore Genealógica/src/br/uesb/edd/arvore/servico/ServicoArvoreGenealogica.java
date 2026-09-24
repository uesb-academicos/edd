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
package br.uesb.edd.arvore.servico;

import br.uesb.edd.arvore.modelo.PessoaGenealogica;
import br.uesb.edd.arvore.modelo.ResultadoBusca;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.*;

/**
 * Serviço principal da árvore genealógica.
 *
 * Explicação do programador:
 * Aqui ficam as operações de estrutura de dados: adicionar, editar, remover,
 * buscar, contar nós e imprimir recursivamente. A tela apenas chama este serviço.
 */
public class ServicoArvoreGenealogica {

    private DefaultMutableTreeNode raiz;
    private final ServicoIdentificador identificador = new ServicoIdentificador();

    public DefaultMutableTreeNode getRaiz() { return raiz; }
    public void setRaiz(DefaultMutableTreeNode raiz) { this.raiz = raiz; }
    public ServicoIdentificador getIdentificador() { return identificador; }

    public PessoaGenealogica criarPessoa(String nome, String parentesco, String genero,
                                         String observacao, String referencia, String textoBiblico) {
        return new PessoaGenealogica(identificador.gerarNovoCodigo(), nome, parentesco, genero,
                observacao, referencia, textoBiblico);
    }

    public DefaultMutableTreeNode adicionarPessoa(DefaultMutableTreeNode noPai, PessoaGenealogica pessoa) {
        DefaultMutableTreeNode novoNo = new DefaultMutableTreeNode(pessoa);
        noPai.add(novoNo);
        return novoNo;
    }

    public void editarPessoa(DefaultMutableTreeNode no, String nome, String parentesco, String genero,
                             String observacao, String referencia, String textoBiblico) {
        PessoaGenealogica pessoa = (PessoaGenealogica) no.getUserObject();
        pessoa.setNome(nome);
        pessoa.setGrauParentesco(parentesco);
        pessoa.setGenero(genero);
        pessoa.setObservacao(observacao);
        pessoa.setReferenciaBiblica(referencia);
        pessoa.setTextoBiblico(textoBiblico);
    }

    public boolean removerPessoa(DefaultMutableTreeNode no) {
        if (no == null || no == raiz) return false;
        no.removeFromParent();
        return true;
    }

    public List<ResultadoBusca> buscarPorNome(String termo) {
        List<ResultadoBusca> resultados = new ArrayList<>();
        if (raiz == null || termo == null || termo.trim().isEmpty()) return resultados;
        buscarRecursivo(raiz, termo.toLowerCase(Locale.ROOT), resultados);
        return resultados;
    }

    private void buscarRecursivo(DefaultMutableTreeNode noAtual, String termo, List<ResultadoBusca> resultados) {
        PessoaGenealogica pessoa = (PessoaGenealogica) noAtual.getUserObject();
        if (pessoa.getNome().toLowerCase(Locale.ROOT).contains(termo)) {
            resultados.add(new ResultadoBusca(noAtual, pessoa));
        }
        Enumeration<?> filhos = noAtual.children();
        while (filhos.hasMoreElements()) {
            buscarRecursivo((DefaultMutableTreeNode) filhos.nextElement(), termo, resultados);
        }
    }

    public int contarNos() {
        if (raiz == null) return 0;
        return contarNosRecursivo(raiz);
    }

    private int contarNosRecursivo(DefaultMutableTreeNode noAtual) {
        int total = 1;
        Enumeration<?> filhos = noAtual.children();
        while (filhos.hasMoreElements()) {
            total += contarNosRecursivo((DefaultMutableTreeNode) filhos.nextElement());
        }
        return total;
    }

    public String imprimirArvoreTexto() {
        StringBuilder texto = new StringBuilder();
        if (raiz != null) imprimirRecursivo(raiz, 0, texto);
        return texto.toString();
    }

    /**
     * Método recursivo exigido pela atividade.
     * Ele visita o nó atual, imprime com indentação e depois visita os filhos.
     */
    private void imprimirRecursivo(DefaultMutableTreeNode noAtual, int nivel, StringBuilder texto) {
        texto.append("    ".repeat(Math.max(0, nivel)));
        texto.append(noAtual.getUserObject()).append(System.lineSeparator());
        Enumeration<?> filhos = noAtual.children();
        while (filhos.hasMoreElements()) {
            imprimirRecursivo((DefaultMutableTreeNode) filhos.nextElement(), nivel + 1, texto);
        }
    }

    public String gerarUmlTextual() {
        StringBuilder texto = new StringBuilder("UML textual das ligações:\n\n");
        if (raiz != null) gerarUmlRecursivo(raiz, texto);
        return texto.toString();
    }

    private void gerarUmlRecursivo(DefaultMutableTreeNode noAtual, StringBuilder texto) {
        PessoaGenealogica pessoaPai = (PessoaGenealogica) noAtual.getUserObject();
        Enumeration<?> filhos = noAtual.children();
        while (filhos.hasMoreElements()) {
            DefaultMutableTreeNode noFilho = (DefaultMutableTreeNode) filhos.nextElement();
            PessoaGenealogica pessoaFilha = (PessoaGenealogica) noFilho.getUserObject();
            texto.append(pessoaPai.getNome()).append(" --> ").append(pessoaFilha.getNome())
                    .append(" : ").append(pessoaFilha.getGrauParentesco()).append("\n");
            gerarUmlRecursivo(noFilho, texto);
        }
    }
}
