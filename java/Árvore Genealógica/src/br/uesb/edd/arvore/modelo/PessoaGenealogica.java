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

import java.io.Serializable;

/**
 * Classe que representa uma pessoa dentro da árvore genealógica.
 *
 * Explicação do programador:
 * Cada objeto desta classe guarda os dados principais de uma pessoa.
 * Esses dados são colocados dentro de um nó DefaultMutableTreeNode.
 * Assim, a árvore continua sendo uma estrutura de dados do Java, mas
 * cada nó passa a ter informações mais ricas do que apenas um nome.
 */
public class PessoaGenealogica implements Serializable {

    private final int codigo;
    private String nome;
    private String grauParentesco;
    private String genero;
    private String observacao;
    private String referenciaBiblica;
    private String textoBiblico;

    public PessoaGenealogica(int codigo, String nome, String grauParentesco, String genero,
                             String observacao, String referenciaBiblica, String textoBiblico) {
        this.codigo = codigo;
        this.nome = nome;
        this.grauParentesco = grauParentesco;
        this.genero = genero;
        this.observacao = observacao;
        this.referenciaBiblica = referenciaBiblica;
        this.textoBiblico = textoBiblico;
    }

    public int getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public String getGrauParentesco() { return grauParentesco; }
    public String getGenero() { return genero; }
    public String getObservacao() { return observacao; }
    public String getReferenciaBiblica() { return referenciaBiblica; }
    public String getTextoBiblico() { return textoBiblico; }

    public void setNome(String nome) { this.nome = nome; }
    public void setGrauParentesco(String grauParentesco) { this.grauParentesco = grauParentesco; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public void setReferenciaBiblica(String referenciaBiblica) { this.referenciaBiblica = referenciaBiblica; }
    public void setTextoBiblico(String textoBiblico) { this.textoBiblico = textoBiblico; }

    /**
     * O JTree usa este método para mostrar o texto do nó.
     */
    @Override
    public String toString() {
        String icone = "Feminino".equalsIgnoreCase(genero) ? "👩" : "Masculino".equalsIgnoreCase(genero) ? "👨" : "👤";
        return icone + " " + nome + " — " + grauParentesco;
    }
}
