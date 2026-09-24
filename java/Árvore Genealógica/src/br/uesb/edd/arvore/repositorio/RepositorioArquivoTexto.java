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
package br.uesb.edd.arvore.repositorio;

import br.uesb.edd.arvore.servico.ServicoArvoreGenealogica;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

/**
 * Repositório simples em arquivo texto.
 *
 * Explicação do programador:
 * Para esta atividade acadêmica, foi evitado o uso obrigatório de um banco externo.
 * Assim, o sistema roda em qualquer computador que tenha Java instalado.
 * O arquivo gerado serve como relatório e backup textual da árvore.
 */
public class RepositorioArquivoTexto {

    public void salvarRelatorioTexto(ServicoArvoreGenealogica servico, Path caminhoArquivo) throws IOException {
        if (caminhoArquivo.getParent() != null) {
            Files.createDirectories(caminhoArquivo.getParent());
        }
        String conteudo = "Árvore Genealógica\n\n" + servico.imprimirArvoreTexto() + "\n" + servico.gerarUmlTextual();
        Files.writeString(caminhoArquivo, conteudo, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
