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
 * Esta classe funciona como um catálogo acadêmico de referências bíblicas
 * relacionadas a genealogias. Os textos são resumos explicativos em
 * português, usados para orientar o aluno e o usuário do sistema.
 * =========================================================
 */
package br.uesb.edd.arvore.biblia;

import java.util.*;

/**
 * Banco simples de referências bíblicas usadas no projeto.
 *
 * Explicação do programador:
 * Não é gravado aqui o texto bíblico completo de uma tradução específica,
 * para evitar dependência de direitos autorais e manter o projeto leve.
 * Em vez disso, o sistema registra capítulo, resumo lógico e nomes principais
 * destacados. Assim, o usuário sabe exatamente onde procurar na Bíblia.
 */
public class BancoReferenciasBiblicas {

    /** Mapa ordenado para manter as referências na sequência em que foram cadastradas. */
    private final Map<String, ReferenciaBiblica> referencias = new LinkedHashMap<>();

    /**
     * Construtor que carrega as principais passagens genealógicas da Bíblia.
     * A proposta é demonstrar uma pesquisa ampla, sem afirmar que todos os
     * capítulos da Bíblia possuem genealogia direta.
     */
    public BancoReferenciasBiblicas() {
        cadastrar("Gênesis 4", "Mostra descendentes de Caim e a formação de uma linhagem antiga.", "Caim, Enoque, Lameque");
        cadastrar("Gênesis 5", "Apresenta a genealogia desde Adão, passando por Sete até Noé.", "Adão, Sete, Enos, Noé");
        cadastrar("Gênesis 10", "Conhecido como a tabela das nações, apresenta descendentes dos filhos de Noé.", "Sem, Cam, Jafé");
        cadastrar("Gênesis 11", "Apresenta a genealogia de Sem até Terá e Abraão.", "Sem, Arfaxade, Serugue, Naor, Terá, Abraão");
        cadastrar("Gênesis 22:20-24", "Lista familiares ligados à casa de Naor, parentes de Abraão.", "Naor, Milca, Rebeca");
        cadastrar("Gênesis 25", "Registra descendentes de Abraão, Ismael e Isaque.", "Abraão, Ismael, Isaque, Jacó, Esaú");
        cadastrar("Gênesis 36", "Apresenta a genealogia de Esaú, também chamado Edom.", "Esaú, Edom");
        cadastrar("Gênesis 46", "Lista os descendentes de Jacó que foram ao Egito.", "Jacó, Rúben, Simeão, Levi, Judá");
        cadastrar("Êxodo 6:14-27", "Registra genealogias das famílias de Israel, incluindo a linhagem de Moisés e Arão.", "Levi, Coate, Anrão, Moisés, Arão");
        cadastrar("Números 1", "Apresenta contagem e chefes das tribos de Israel no deserto.", "Rúben, Simeão, Judá, Levi");
        cadastrar("Números 2", "Organiza as tribos de Israel por acampamento e liderança.", "Judá, Issacar, Zebulom, Rúben");
        cadastrar("Números 26", "Nova contagem das famílias e clãs de Israel.", "Tribos de Israel, famílias, clãs");
        cadastrar("Rute 4:17-22", "Mostra a sequência genealógica de Perez até Davi, passando por Boaz, Obede e Jessé.", "Perez, Boaz, Obede, Jessé, Davi");
        cadastrar("1 Crônicas 1", "Recapitula genealogias desde Adão e passa por patriarcas e povos antigos.", "Adão, Noé, Sem, Abraão");
        cadastrar("1 Crônicas 2", "Apresenta descendentes de Israel e Judá, chegando à família de Davi.", "Israel, Judá, Perez, Davi");
        cadastrar("1 Crônicas 3", "Apresenta filhos de Davi, incluindo Salomão, e descendentes reais.", "Davi, Salomão, Roboão");
        cadastrar("1 Crônicas 4", "Registra genealogias de Judá e Simeão.", "Judá, Simeão");
        cadastrar("1 Crônicas 5", "Registra genealogias de Rúben, Gade e meia tribo de Manassés.", "Rúben, Gade, Manassés");
        cadastrar("1 Crônicas 6", "Apresenta genealogias sacerdotais da tribo de Levi.", "Levi, Coate, Arão");
        cadastrar("1 Crônicas 7", "Apresenta genealogias de Issacar, Benjamim, Naftali, Manassés, Efraim e Aser.", "Issacar, Benjamim, Efraim, Aser");
        cadastrar("1 Crônicas 8", "Detalha a genealogia de Benjamim.", "Benjamim, Saul");
        cadastrar("1 Crônicas 9", "Registra famílias que habitaram Jerusalém após o retorno.", "Judá, Benjamim, Levi");
        cadastrar("2 Samuel 11:3", "Apresenta Bate-Seba como filha de Eliã.", "Bate-Seba, Eliã");
        cadastrar("2 Samuel 12:24", "Registra o nascimento de Salomão, filho de Davi e Bate-Seba.", "Salomão, Davi, Bate-Seba");
        cadastrar("Esdras 2", "Lista famílias que retornaram do exílio babilônico.", "Famílias de Israel, sacerdotes, levitas");
        cadastrar("Neemias 7", "Reapresenta listas familiares do retorno do exílio.", "Famílias, chefes, sacerdotes");
        cadastrar("Mateus 1", "Apresenta a genealogia de Jesus ligada a Abraão e Davi.", "Jesus, Abraão, Davi, José");
        cadastrar("Lucas 3:23-38", "Apresenta outra linha genealógica de Jesus, chegando até Adão.", "Jesus, José, Davi, Abraão, Adão");
    }

    private void cadastrar(String referencia, String textoResumo, String nomesDestacados) {
        referencias.put(referencia, new ReferenciaBiblica(referencia, textoResumo, nomesDestacados));
    }

    public Collection<ReferenciaBiblica> listarTodas() {
        return referencias.values();
    }

    public ReferenciaBiblica buscar(String referencia) {
        return referencias.get(referencia);
    }
}
