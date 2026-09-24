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

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Monta os modelos genealógicos prontos usados no sistema.
 */
public class ModelosGenealogicosBiblicos {

    private final ServicoArvoreGenealogica servico;

    public ModelosGenealogicosBiblicos(ServicoArvoreGenealogica servico) {
        this.servico = servico;
    }

    public DefaultMutableTreeNode criarModeloSalomao() {
        servico.getIdentificador().reiniciar();
        DefaultMutableTreeNode salomao = no("Salomão", "Pessoa escolhida / Rei de Israel", "Masculino", "Filho de Davi e Bate-Seba.", "2 Samuel 12:24", "Nascimento de Salomão, filho de Davi e Bate-Seba.");
        DefaultMutableTreeNode davi = no("Davi", "Pai", "Masculino", "Rei de Israel.", "Rute 4:17-22", "A genealogia apresenta Obede, Jessé e Davi.");
        DefaultMutableTreeNode bateseba = no("Bate-Seba", "Mãe", "Feminino", "Mãe de Salomão.", "2 Samuel 12:24", "Bate-Seba é citada como mãe de Salomão.");
        DefaultMutableTreeNode jesse = no("Jessé", "Avô paterno", "Masculino", "Pai de Davi.", "Rute 4:17-22", "Jessé aparece como pai de Davi.");
        DefaultMutableTreeNode elia = no("Eliã", "Avô materno", "Masculino", "Pai de Bate-Seba.", "2 Samuel 11:3", "Bate-Seba é apresentada como filha de Eliã.");
        DefaultMutableTreeNode obede = no("Obede", "Bisavô paterno", "Masculino", "Pai de Jessé.", "Rute 4:17-22", "Obede gerou Jessé.");
        DefaultMutableTreeNode desconhecidaMaeJesse = no("Desconhecida", "Bisavó paterna", "Feminino", "Nome não localizado na referência principal usada.", "Rute 4:17-22", "A referência destaca a linhagem principal até Davi.");
        DefaultMutableTreeNode boaz = no("Boaz", "Trisavô paterno", "Masculino", "Pai de Obede.", "Rute 4:17-22", "Boaz gerou Obede.");
        DefaultMutableTreeNode rute = no("Rute", "Trisavó paterna", "Feminino", "Mãe de Obede.", "Rute 4:17-22", "Rute é ligada a Obede, Jessé e Davi.");
        DefaultMutableTreeNode desconhecidoMaterno = no("Desconhecido", "Bisavô materno", "Masculino", "Ancestral materno não detalhado no modelo.", "2 Samuel 11:3", "Foi usado 'Desconhecido' para não inventar nomes.");

        DefaultMutableTreeNode avoPaterna = no("Desconhecida", "Avó paterna", "Feminino", "Mãe de Davi não nomeada na referência adotada.", "", "");
        DefaultMutableTreeNode avoMaterna = no("Desconhecida", "Avó materna", "Feminino", "Mãe de Bate-Seba não nomeada na referência adotada.", "", "");
        salomao.add(davi); salomao.add(bateseba);
        davi.add(jesse); davi.add(avoPaterna); bateseba.add(elia); bateseba.add(avoMaterna);
        jesse.add(obede); jesse.add(desconhecidaMaeJesse); elia.add(desconhecidoMaterno);
        obede.add(boaz); obede.add(rute);
        return salomao;
    }

    public DefaultMutableTreeNode criarModeloAdao() {
        servico.getIdentificador().reiniciar();
        DefaultMutableTreeNode adao = no("Adão", "Pessoa escolhida", "Masculino", "Primeiro homem na genealogia de Gênesis.", "Gênesis 5", "Genealogia de Adão e seus descendentes.");
        DefaultMutableTreeNode sete = no("Sete", "Filho", "Masculino", "Filho de Adão.", "Gênesis 5", "Sete aparece na sequência genealógica.");
        DefaultMutableTreeNode enos = no("Enos", "Neto", "Masculino", "Filho de Sete.", "Gênesis 5", "Enos aparece como descendente de Sete.");
        DefaultMutableTreeNode caina = no("Cainã", "Bisneto", "Masculino", "Descendente de Enos.", "Gênesis 5", "Cainã aparece na genealogia de Gênesis.");
        DefaultMutableTreeNode maalalel = no("Maalalel", "Trisneto", "Masculino", "Descendente de Cainã.", "Gênesis 5", "Maalalel completa o quinto nível deste exemplo.");
        adao.add(sete); sete.add(enos); enos.add(caina); caina.add(maalalel);
        return adao;
    }

    public DefaultMutableTreeNode criarModeloAbraao() {
        servico.getIdentificador().reiniciar();
        DefaultMutableTreeNode abraao = no("Abraão", "Pessoa escolhida", "Masculino", "Patriarca bíblico.", "Gênesis 11", "Genealogia que conduz até Abraão.");
        DefaultMutableTreeNode tera = no("Terá", "Pai", "Masculino", "Pai de Abraão.", "Gênesis 11", "Terá aparece como pai de Abrão/Abraão.");
        DefaultMutableTreeNode naor = no("Naor", "Avô", "Masculino", "Pai de Terá.", "Gênesis 11", "Naor aparece na linhagem de Terá.");
        DefaultMutableTreeNode serugue = no("Serugue", "Bisavô", "Masculino", "Pai de Naor.", "Gênesis 11", "Serugue aparece antes de Naor.");
        DefaultMutableTreeNode reu = no("Reú", "Trisavô", "Masculino", "Pai de Serugue.", "Gênesis 11", "Reú aparece na sequência genealógica.");
        abraao.add(tera); tera.add(naor); naor.add(serugue); serugue.add(reu);
        return abraao;
    }

    public DefaultMutableTreeNode criarModeloJesus() {
        servico.getIdentificador().reiniciar();
        DefaultMutableTreeNode jesus = no("Jesus", "Pessoa escolhida", "Masculino", "Figura central do cristianismo.", "Mateus 1", "Genealogia messiânica apresentada em Mateus.");
        DefaultMutableTreeNode jose = no("José", "Pai legal", "Masculino", "Esposo de Maria.", "Mateus 1", "Mateus apresenta a linhagem legal por José.");
        DefaultMutableTreeNode maria = no("Maria", "Mãe", "Feminino", "Mãe de Jesus.", "Mateus 1", "Maria aparece no nascimento de Jesus.");
        DefaultMutableTreeNode jaco = no("Jacó", "Avô na linhagem de José", "Masculino", "Pai de José segundo Mateus.", "Mateus 1", "Jacó gerou José.");
        DefaultMutableTreeNode mata = no("Matã", "Bisavô na linhagem de José", "Masculino", "Pai de Jacó.", "Mateus 1", "Matã aparece antes de Jacó.");
        DefaultMutableTreeNode eleazar = no("Eleazar", "Trisavô na linhagem de José", "Masculino", "Pai de Matã.", "Mateus 1", "Eleazar aparece antes de Matã.");
        jesus.add(jose); jesus.add(maria); jose.add(jaco); jaco.add(mata); mata.add(eleazar);
        return jesus;
    }

    public DefaultMutableTreeNode criarModeloFamiliaAtual(String nomePrincipal) {
        servico.getIdentificador().reiniciar();
        return no(nomePrincipal, "Pessoa principal", "Não informado", "Família atual criada manualmente pelo usuário.", "", "");
    }

    private DefaultMutableTreeNode no(String nome, String parentesco, String genero, String observacao, String referencia, String texto) {
        PessoaGenealogica pessoa = servico.criarPessoa(nome, parentesco, genero, observacao, referencia, texto);
        return new DefaultMutableTreeNode(pessoa);
    }
}
