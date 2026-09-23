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
 * Esta classe centraliza a exportação simples em PDF sem depender
 * de bibliotecas externas. Ela recebe uma imagem da árvore e cria
 * um arquivo PDF em formato paisagem, adequado para entrega acadêmica.
 * =========================================================
 */
package br.uesb.edd.arvore.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Exportador de PDF extremamente simples.
 *
 * Explicação do programador:
 * O Java puro não possui uma API pronta para criar PDF. Para evitar erro
 * de dependência no IntelliJ e permitir que o professor execute o projeto
 * apenas com Java instalado, esta classe grava manualmente uma estrutura
 * PDF básica contendo uma imagem JPEG da árvore genealógica.
 */
public class ExportadorPdfSimples {

    /**
     * Exporta uma imagem da árvore em um PDF de página única.
     *
     * @param imagem imagem da árvore desenhada
     * @param destino caminho do arquivo PDF que será criado
     * @throws IOException quando não for possível salvar o arquivo
     */
    public void exportarImagemComoPdf(BufferedImage imagem, Path destino) throws IOException {
        if (imagem == null) {
            throw new IOException("Imagem da árvore não foi gerada.");
        }

        Path pasta = destino.getParent();
        if (pasta != null) {
            Files.createDirectories(pasta);
        }

        ByteArrayOutputStream imagemJpeg = new ByteArrayOutputStream();
        ImageIO.write(imagem, "jpg", imagemJpeg);
        byte[] bytesImagem = imagemJpeg.toByteArray();

        double larguraPagina = 842; // A4 paisagem em pontos
        double alturaPagina = 595;
        double margem = 28;

        double larguraDisponivel = larguraPagina - margem * 2;
        double alturaDisponivel = alturaPagina - margem * 2;
        double escala = Math.min(larguraDisponivel / imagem.getWidth(), alturaDisponivel / imagem.getHeight());
        double larguraImagem = imagem.getWidth() * escala;
        double alturaImagem = imagem.getHeight() * escala;
        double x = (larguraPagina - larguraImagem) / 2;
        double y = (alturaPagina - alturaImagem) / 2;

        String conteudoPagina = "q\n" +
                String.format(java.util.Locale.US, "%.2f 0 0 %.2f %.2f %.2f cm\n", larguraImagem, alturaImagem, x, y) +
                "/Im0 Do\n" +
                "Q\n";

        List<byte[]> objetos = new ArrayList<>();
        objetos.add(texto("<< /Type /Catalog /Pages 2 0 R >>"));
        objetos.add(texto("<< /Type /Pages /Kids [3 0 R] /Count 1 >>"));
        objetos.add(texto(String.format(java.util.Locale.US,
                "<< /Type /Page /Parent 2 0 R /MediaBox [0 0 %.0f %.0f] /Resources << /XObject << /Im0 4 0 R >> >> /Contents 5 0 R >>",
                larguraPagina, alturaPagina)));
        objetos.add(juntar(
                texto("<< /Type /XObject /Subtype /Image /Width " + imagem.getWidth() +
                        " /Height " + imagem.getHeight() +
                        " /ColorSpace /DeviceRGB /BitsPerComponent 8 /Filter /DCTDecode /Length " + bytesImagem.length + " >>\nstream\n"),
                bytesImagem,
                texto("\nendstream")
        ));
        objetos.add(juntar(
                texto("<< /Length " + conteudoPagina.getBytes(StandardCharsets.ISO_8859_1).length + " >>\nstream\n"),
                texto(conteudoPagina),
                texto("endstream")
        ));

        ByteArrayOutputStream pdf = new ByteArrayOutputStream();
        pdf.write(texto("%PDF-1.4\n"));

        List<Integer> deslocamentos = new ArrayList<>();
        deslocamentos.add(0);

        for (int i = 0; i < objetos.size(); i++) {
            deslocamentos.add(pdf.size());
            pdf.write(texto((i + 1) + " 0 obj\n"));
            pdf.write(objetos.get(i));
            pdf.write(texto("\nendobj\n"));
        }

        int inicioXref = pdf.size();
        pdf.write(texto("xref\n0 " + (objetos.size() + 1) + "\n"));
        pdf.write(texto("0000000000 65535 f \n"));

        for (int i = 1; i < deslocamentos.size(); i++) {
            pdf.write(texto(String.format(java.util.Locale.US, "%010d 00000 n \n", deslocamentos.get(i))));
        }

        pdf.write(texto("trailer\n<< /Size " + (objetos.size() + 1) + " /Root 1 0 R >>\n"));
        pdf.write(texto("startxref\n" + inicioXref + "\n%%EOF"));

        Files.write(destino, pdf.toByteArray());
    }

    private byte[] texto(String texto) {
        return texto.getBytes(StandardCharsets.ISO_8859_1);
    }

    private byte[] juntar(byte[]... partes) throws IOException {
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        for (byte[] parte : partes) {
            saida.write(parte);
        }
        return saida.toByteArray();
    }
}
