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
 * Este painel desenha a árvore genealógica em formato visual, com a
 * pessoa escolhida na parte inferior e seus ancestrais acima dela.
 * Essa organização segue melhor a lógica de genealogia: trisavós no topo,
 * bisavós abaixo, avós, pais e a pessoa principal na base.
 * =========================================================
 */
package br.uesb.edd.arvore.tela;

import br.uesb.edd.arvore.modelo.PessoaGenealogica;
import br.uesb.edd.arvore.util.ConstantesSistema;
import br.uesb.edd.arvore.util.ExportadorPdfSimples;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Classe responsável pelo desenho manual da árvore genealógica.
 *
 * Explicação do programador:
 * O JTree já mostra os dados em forma de lista hierárquica, mas este painel
 * foi criado para desenhar a árvore como uma genealogia visual, parecida com
 * os exemplos enviados: cartões centralizados, linhas de ligação e níveis bem
 * definidos.
 *
 * A lógica usada aqui é diferente da árvore comum de cima para baixo.
 * Como a raiz do trabalho é a pessoa escolhida, os filhos do nó representam
 * seus ancestrais. Por isso, o desenho coloca a raiz embaixo e os ancestrais
 * em cima.
 */
public class PainelArvoreDesenhada extends JPanel implements Printable {

    /** Nó principal da árvore. Exemplo: Salomão. */
    private DefaultMutableTreeNode raiz;

    /** Guarda a posição visual de cada nó para desenhar os cartões e as linhas. */
    private final Map<DefaultMutableTreeNode, Rectangle> posicoesDosNos = new HashMap<>();

    /** Largura fixa dos cartões, evitando que o desenho fique irregular. */
    private final int larguraCartao = 190;

    /** Altura fixa dos cartões, suficiente para nome, parentesco e referência bíblica. */
    private final int alturaCartao = 92;

    /** Espaço entre cartões no mesmo nível. */
    private final int espacoHorizontal = 54;

    /** Espaço entre os níveis da genealogia. */
    private final int espacoVertical = 88;

    /** Margem de segurança para o desenho não ficar colado na borda da tela. */
    private final int margem = 50;

    /**
     * Construtor do painel.
     * Define cor de fundo e tamanho inicial, mantendo o consumo de memória baixo.
     */
    public PainelArvoreDesenhada() {
        setBackground(ConstantesSistema.FUNDO);
        setPreferredSize(new Dimension(1280, 760));
        setOpaque(true);
    }

    /**
     * Recebe a raiz atual da árvore e solicita novo desenho.
     * Sempre que o CRUD altera a árvore, este método deve ser chamado.
     */
    public void definirRaiz(DefaultMutableTreeNode raiz) {
        this.raiz = raiz;
        recalcularTamanhoDoPainel();
        repaint();
    }

    /**
     * Calcula um tamanho seguro para a área de desenho.
     * Isso evita cortes quando a árvore cresce e permite usar JScrollPane.
     */
    private void recalcularTamanhoDoPainel() {
        if (raiz == null) {
            setPreferredSize(new Dimension(1280, 760));
            revalidate();
            return;
        }

        int larguraNecessaria = Math.max(1280, calcularLarguraSubarvore(raiz) + margem * 2);
        int alturaNecessaria = Math.max(760, (calcularProfundidade(raiz) + 1) * (alturaCartao + espacoVertical) + margem * 2);

        setPreferredSize(new Dimension(larguraNecessaria, alturaNecessaria));
        revalidate();
    }

    /**
     * Método principal de pintura do Swing.
     * Ele limpa a tela, calcula posições, desenha linhas e depois desenha os cartões.
     */
    @Override
    protected void paintComponent(Graphics grafico) {
        super.paintComponent(grafico);

        Graphics2D grafico2d = (Graphics2D) grafico.create();
        grafico2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        grafico2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        desenharTituloDiscreto(grafico2d);

        if (raiz == null) {
            desenharMensagemSemArvore(grafico2d);
            grafico2d.dispose();
            return;
        }

        posicoesDosNos.clear();

        int larguraTotal = calcularLarguraSubarvore(raiz);
        int xInicial = Math.max(margem, (getWidth() - larguraTotal) / 2);
        int yDaRaiz = margem + calcularProfundidade(raiz) * (alturaCartao + espacoVertical);

        posicionarNosComAncestraisAcima(raiz, xInicial, yDaRaiz);
        desenharLigacoesFamiliares(grafico2d, raiz);
        desenharCartoes(grafico2d, raiz);

        grafico2d.dispose();
    }

    /**
     * Desenha um pequeno cabeçalho dentro do painel.
     * Esse texto ajuda a identificar o sistema sem poluir a interface.
     */
    private void desenharTituloDiscreto(Graphics2D grafico2d) {
        grafico2d.setFont(new Font("SansSerif", Font.BOLD, 15));
        grafico2d.setColor(ConstantesSistema.VERDE_ESCURO);
        grafico2d.drawString("EDD Árvore Genealógica Bíblica Inteligente", 18, 24);

        grafico2d.setFont(new Font("SansSerif", Font.PLAIN, 11));
        grafico2d.setColor(Color.GRAY);
        grafico2d.drawString("Autor: Thiago Ferreira Prates Neves • thiagoprates.com.br", 18, 42);
    }

    /**
     * Exibe mensagem quando ainda não existe árvore carregada.
     */
    private void desenharMensagemSemArvore(Graphics2D grafico2d) {
        grafico2d.setFont(new Font("SansSerif", Font.BOLD, 18));
        grafico2d.setColor(Color.DARK_GRAY);
        grafico2d.drawString("Nenhuma árvore carregada.", margem, 100);
    }

    /**
     * Calcula a largura que uma subárvore precisa ocupar.
     *
     * Explicação do programador:
     * Se o nó não tem ancestrais/filhos, ele ocupa apenas um cartão.
     * Se tem filhos, a largura será a soma da largura dos filhos.
     * Isso impede que os cartões se sobreponham.
     */
    private int calcularLarguraSubarvore(DefaultMutableTreeNode no) {
        if (no == null || no.getChildCount() == 0) {
            return larguraCartao + espacoHorizontal;
        }

        int largura = 0;
        Enumeration<?> filhos = no.children();

        while (filhos.hasMoreElements()) {
            DefaultMutableTreeNode filho = (DefaultMutableTreeNode) filhos.nextElement();
            largura += calcularLarguraSubarvore(filho);
        }

        return Math.max(largura, larguraCartao + espacoHorizontal);
    }

    /**
     * Calcula a profundidade da árvore.
     * Esse método é recursivo e ajuda a saber em qual altura a raiz deve ficar.
     */
    private int calcularProfundidade(DefaultMutableTreeNode no) {
        if (no == null || no.getChildCount() == 0) {
            return 0;
        }

        int maiorProfundidade = 0;
        Enumeration<?> filhos = no.children();

        while (filhos.hasMoreElements()) {
            DefaultMutableTreeNode filho = (DefaultMutableTreeNode) filhos.nextElement();
            maiorProfundidade = Math.max(maiorProfundidade, 1 + calcularProfundidade(filho));
        }

        return maiorProfundidade;
    }

    /**
     * Posiciona os nós com os ancestrais acima da pessoa principal.
     *
     * Explicação do programador:
     * O nó atual fica centralizado em relação aos seus filhos.
     * Como os filhos representam ancestrais, eles são desenhados acima do nó atual.
     */
    private void posicionarNosComAncestraisAcima(DefaultMutableTreeNode no, int x, int y) {
        int larguraTotal = calcularLarguraSubarvore(no);
        int xCentralizado = x + larguraTotal / 2 - larguraCartao / 2;

        posicoesDosNos.put(no, new Rectangle(xCentralizado, y, larguraCartao, alturaCartao));

        int xFilho = x;
        Enumeration<?> filhos = no.children();

        while (filhos.hasMoreElements()) {
            DefaultMutableTreeNode filho = (DefaultMutableTreeNode) filhos.nextElement();
            int larguraFilho = calcularLarguraSubarvore(filho);
            posicionarNosComAncestraisAcima(filho, xFilho, y - alturaCartao - espacoVertical);
            xFilho += larguraFilho;
        }
    }

    /**
     * Desenha as linhas de parentesco entre pessoa e ancestrais.
     */
    private void desenharLigacoesFamiliares(Graphics2D grafico2d, DefaultMutableTreeNode no) {
        Rectangle retanguloAtual = posicoesDosNos.get(no);

        if (retanguloAtual == null) {
            return;
        }

        grafico2d.setStroke(new BasicStroke(2.2f));
        grafico2d.setColor(ConstantesSistema.DOURADO.darker());

        Enumeration<?> filhos = no.children();

        while (filhos.hasMoreElements()) {
            DefaultMutableTreeNode filho = (DefaultMutableTreeNode) filhos.nextElement();
            Rectangle retanguloFilho = posicoesDosNos.get(filho);

            if (retanguloFilho != null) {
                int xPessoa = retanguloAtual.x + retanguloAtual.width / 2;
                int yPessoa = retanguloAtual.y;
                int xAncestral = retanguloFilho.x + retanguloFilho.width / 2;
                int yAncestral = retanguloFilho.y + retanguloFilho.height;
                int yMeio = yAncestral + (yPessoa - yAncestral) / 2;

                grafico2d.drawLine(xAncestral, yAncestral, xAncestral, yMeio);
                grafico2d.drawLine(xAncestral, yMeio, xPessoa, yMeio);
                grafico2d.drawLine(xPessoa, yMeio, xPessoa, yPessoa);
            }

            desenharLigacoesFamiliares(grafico2d, filho);
        }
    }

    /**
     * Desenha todos os cartões da árvore.
     * O método também é recursivo, pois visita cada nó da árvore.
     */
    private void desenharCartoes(Graphics2D grafico2d, DefaultMutableTreeNode no) {
        Rectangle retangulo = posicoesDosNos.get(no);

        if (retangulo == null) {
            return;
        }

        Object objeto = no.getUserObject();
        if (!(objeto instanceof PessoaGenealogica pessoa)) {
            return;
        }

        desenharCartaoPessoa(grafico2d, retangulo, pessoa);

        Enumeration<?> filhos = no.children();
        while (filhos.hasMoreElements()) {
            desenharCartoes(grafico2d, (DefaultMutableTreeNode) filhos.nextElement());
        }
    }

    /**
     * Desenha um cartão individual da pessoa.
     */
    private void desenharCartaoPessoa(Graphics2D grafico2d, Rectangle retangulo, PessoaGenealogica pessoa) {
        Color corBorda = escolherCorDaBorda(pessoa);
        Color corCirculo = escolherCorDoCirculo(pessoa);

        grafico2d.setColor(new Color(0, 0, 0, 25));
        grafico2d.fill(new RoundRectangle2D.Double(retangulo.x + 3, retangulo.y + 4, retangulo.width, retangulo.height, 22, 22));

        grafico2d.setColor(ConstantesSistema.CARTAO);
        grafico2d.fill(new RoundRectangle2D.Double(retangulo.x, retangulo.y, retangulo.width, retangulo.height, 22, 22));

        grafico2d.setColor(corBorda);
        grafico2d.setStroke(new BasicStroke(2.2f));
        grafico2d.draw(new RoundRectangle2D.Double(retangulo.x, retangulo.y, retangulo.width, retangulo.height, 22, 22));

        grafico2d.setColor(corCirculo);
        grafico2d.fillOval(retangulo.x + 12, retangulo.y + 15, 42, 42);

        grafico2d.setColor(Color.WHITE);
        grafico2d.setFont(new Font("SansSerif", Font.BOLD, 20));
        grafico2d.drawString(escolherLetraIcone(pessoa), retangulo.x + 26, retangulo.y + 43);

        grafico2d.setColor(ConstantesSistema.VERDE_ESCURO);
        grafico2d.setFont(new Font("SansSerif", Font.BOLD, 14));
        grafico2d.drawString(encurtarTexto(pessoa.getNome(), 19), retangulo.x + 63, retangulo.y + 25);

        grafico2d.setColor(Color.DARK_GRAY);
        grafico2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
        grafico2d.drawString(encurtarTexto(pessoa.getGrauParentesco(), 27), retangulo.x + 63, retangulo.y + 45);

        String referencia = pessoa.getReferenciaBiblica() == null ? "" : pessoa.getReferenciaBiblica();
        grafico2d.setColor(new Color(105, 87, 40));
        grafico2d.setFont(new Font("SansSerif", Font.PLAIN, 11));
        grafico2d.drawString(referencia.isBlank() ? "Sem referência informada" : encurtarTexto("Ref.: " + referencia, 29), retangulo.x + 14, retangulo.y + 76);
    }

    /**
     * Define a cor da borda conforme o tipo da pessoa.
     */
    private Color escolherCorDaBorda(PessoaGenealogica pessoa) {
        String observacao = textoSeguro(pessoa.getObservacao()).toLowerCase(Locale.ROOT);

        if (observacao.contains("rei")) {
            return ConstantesSistema.DOURADO.darker();
        }

        if ("Feminino".equalsIgnoreCase(pessoa.getGenero())) {
            return new Color(154, 82, 124);
        }

        if ("Masculino".equalsIgnoreCase(pessoa.getGenero())) {
            return new Color(65, 115, 88);
        }

        return Color.GRAY;
    }

    /**
     * Define a cor do círculo interno.
     */
    private Color escolherCorDoCirculo(PessoaGenealogica pessoa) {
        String observacao = textoSeguro(pessoa.getObservacao()).toLowerCase(Locale.ROOT);

        if (observacao.contains("rei")) {
            return new Color(188, 143, 32);
        }

        if ("Feminino".equalsIgnoreCase(pessoa.getGenero())) {
            return new Color(184, 105, 150);
        }

        if ("Masculino".equalsIgnoreCase(pessoa.getGenero())) {
            return new Color(82, 137, 103);
        }

        return new Color(110, 110, 110);
    }

    /**
     * Usa letras em vez de emoji para evitar erro de fonte em alguns computadores.
     */
    private String escolherLetraIcone(PessoaGenealogica pessoa) {
        String observacao = textoSeguro(pessoa.getObservacao()).toLowerCase(Locale.ROOT);

        if (observacao.contains("rei")) {
            return "R";
        }

        if ("Feminino".equalsIgnoreCase(pessoa.getGenero())) {
            return "F";
        }

        if ("Masculino".equalsIgnoreCase(pessoa.getGenero())) {
            return "M";
        }

        return "?";
    }

    /**
     * Evita NullPointerException em campos vazios.
     */
    private String textoSeguro(String texto) {
        return texto == null ? "" : texto;
    }

    /**
     * Encurta textos longos para não ultrapassar o cartão.
     */
    private String encurtarTexto(String texto, int limite) {
        if (texto == null) {
            return "";
        }

        if (texto.length() <= limite) {
            return texto;
        }

        return texto.substring(0, Math.max(0, limite - 3)) + "...";
    }


    /**
     * Gera uma imagem completa do painel da árvore.
     *
     * Explicação do programador:
     * A imagem é criada a partir do tamanho preferido do painel, não apenas
     * da parte visível na tela. Assim, mesmo que a árvore esteja dentro de
     * uma barra de rolagem, a exportação sai inteira e centralizada.
     */
    public BufferedImage gerarImagemCompletaDaArvore() {
        Dimension tamanhoOriginal = getSize();
        Dimension tamanhoExportacao = getPreferredSize();

        int largura = Math.max(1, tamanhoExportacao.width);
        int altura = Math.max(1, tamanhoExportacao.height);

        BufferedImage imagem = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
        Graphics2D grafico2d = imagem.createGraphics();
        grafico2d.setColor(getBackground());
        grafico2d.fillRect(0, 0, largura, altura);

        setSize(tamanhoExportacao);
        printAll(grafico2d);
        setSize(tamanhoOriginal);

        grafico2d.dispose();
        return imagem;
    }

    /**
     * Exporta a árvore desenhada como imagem PNG.
     */
    public void exportarImagemPng(Path destino) throws IOException {
        Path pasta = destino.getParent();
        if (pasta != null) {
            java.nio.file.Files.createDirectories(pasta);
        }
        ImageIO.write(gerarImagemCompletaDaArvore(), "png", destino.toFile());
    }

    /**
     * Exporta a árvore desenhada como PDF em formato paisagem.
     */
    public void exportarPdf(Path destino) throws IOException {
        new ExportadorPdfSimples().exportarImagemComoPdf(gerarImagemCompletaDaArvore(), destino);
    }

    /**
     * Abre a janela de impressão em formato paisagem.
     */
    public void imprimirPaisagem() throws PrinterException {
        PrinterJob trabalhoImpressao = PrinterJob.getPrinterJob();
        PageFormat formatoPagina = trabalhoImpressao.defaultPage();
        formatoPagina.setOrientation(PageFormat.LANDSCAPE);
        trabalhoImpressao.setPrintable(this, formatoPagina);

        if (trabalhoImpressao.printDialog()) {
            trabalhoImpressao.print();
        }
    }

    /**
     * Método usado pelo Java para imprimir o painel em papel.
     * Ele reduz automaticamente a escala para caber na página.
     */
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D grafico2d = (Graphics2D) graphics;
        double escalaX = pageFormat.getImageableWidth() / Math.max(1, getPreferredSize().getWidth());
        double escalaY = pageFormat.getImageableHeight() / Math.max(1, getPreferredSize().getHeight());
        double escala = Math.min(escalaX, escalaY);

        grafico2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        grafico2d.scale(escala, escala);
        printAll(grafico2d);

        return PAGE_EXISTS;
    }
}
