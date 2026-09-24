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
 * editar, remover e buscar pessoas, desenha a árvore em tela, imprime,
 * exporta PNG/PDF e apresenta referências bíblicas organizadas.
 * =========================================================
 */
package br.uesb.edd.arvore.tela;

import br.uesb.edd.arvore.biblia.BancoReferenciasBiblicas;
import br.uesb.edd.arvore.biblia.ReferenciaBiblica;
import br.uesb.edd.arvore.erro.GerenciadorErroSistema;
import br.uesb.edd.arvore.modelo.PessoaGenealogica;
import br.uesb.edd.arvore.modelo.ResultadoBusca;
import br.uesb.edd.arvore.repositorio.RepositorioArquivoTexto;
import br.uesb.edd.arvore.servico.ModelosGenealogicosBiblicos;
import br.uesb.edd.arvore.servico.ServicoArvoreGenealogica;
import br.uesb.edd.arvore.util.ConstantesSistema;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.print.PrinterException;
import java.nio.file.Path;
import java.util.List;

/**
 * Tela principal do sistema.
 */
public class TelaPrincipal extends JFrame {

    /** Serviço central da árvore, responsável pelas regras de negócio. */
    private final ServicoArvoreGenealogica servicoArvore = new ServicoArvoreGenealogica();

    /** Classe que cria os modelos bíblicos prontos. */
    private final ModelosGenealogicosBiblicos modelos = new ModelosGenealogicosBiblicos(servicoArvore);

    /** Banco local com referências e capítulos bíblicos genealógicos. */
    private final BancoReferenciasBiblicas bancoReferencias = new BancoReferenciasBiblicas();

    /** Repositório simples usado para salvar relatório textual. */
    private final RepositorioArquivoTexto repositorio = new RepositorioArquivoTexto();

    /** Árvore padrão do Java solicitada na atividade. */
    private JTree arvoreJava;

    /** Modelo do JTree para recarregar a tela após CRUD. */
    private DefaultTreeModel modeloJTree;

    /** Painel central que desenha a genealogia em formato visual. */
    private PainelArvoreDesenhada painelDesenho;

    /** Área lateral direita com detalhes da pessoa selecionada. */
    private JTextArea areaDetalhes;

    /** Rodapé com mensagens do sistema. */
    private JLabel rotuloStatus;

    /**
     * Construtor da janela principal.
     */
    public TelaPrincipal() {
        super(ConstantesSistema.NOME_SISTEMA);
        configurarJanela();
        montarInterface();
        carregarModeloSalomao();
    }

    /**
     * Configura tamanho, posição e comportamento da janela.
     */
    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1360, 790);
        setMinimumSize(new Dimension(1040, 640));
        setLocationRelativeTo(null);
    }

    /**
     * Monta as regiões principais da interface.
     */
    private void montarInterface() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(ConstantesSistema.FUNDO);
        setJMenuBar(criarBarraMenu());
        add(criarTopo(), BorderLayout.NORTH);
        add(criarAreaCentral(), BorderLayout.CENTER);
        add(criarRodapeCompleto(), BorderLayout.SOUTH);
    }

    /**
     * Cria a barra de menu superior tradicional.
     */
    private JMenuBar criarBarraMenu() {
        JMenuBar barra = new JMenuBar();

        JMenu menuArquivo = new JMenu("Arquivo");
        JMenuItem itemSalvar = new JMenuItem("Salvar relatório textual");
        JMenuItem itemExportarPng = new JMenuItem("Exportar árvore em PNG");
        JMenuItem itemExportarPdf = new JMenuItem("Exportar árvore em PDF");
        JMenuItem itemImprimir = new JMenuItem("Imprimir árvore em paisagem");
        JMenuItem itemSair = new JMenuItem("Sair");
        itemSalvar.addActionListener(e -> salvarRelatorio());
        itemExportarPng.addActionListener(e -> exportarArvorePng());
        itemExportarPdf.addActionListener(e -> exportarArvorePdf());
        itemImprimir.addActionListener(e -> imprimirArvore());
        itemSair.addActionListener(e -> dispose());
        menuArquivo.add(itemSalvar);
        menuArquivo.add(itemExportarPng);
        menuArquivo.add(itemExportarPdf);
        menuArquivo.add(itemImprimir);
        menuArquivo.addSeparator();
        menuArquivo.add(itemSair);

        JMenu menuModelos = new JMenu("Modelos bíblicos");
        JMenuItem salomao = new JMenuItem("Salomão");
        JMenuItem adao = new JMenuItem("Adão");
        JMenuItem abraao = new JMenuItem("Abraão");
        JMenuItem jesus = new JMenuItem("Jesus");
        salomao.addActionListener(e -> carregarModeloSalomao());
        adao.addActionListener(e -> carregarRaiz(modelos.criarModeloAdao(), "Modelo de Adão carregado."));
        abraao.addActionListener(e -> carregarRaiz(modelos.criarModeloAbraao(), "Modelo de Abraão carregado."));
        jesus.addActionListener(e -> carregarRaiz(modelos.criarModeloJesus(), "Modelo de Jesus carregado."));
        menuModelos.add(salomao);
        menuModelos.add(adao);
        menuModelos.add(abraao);
        menuModelos.add(jesus);

        JMenu menuAjuda = new JMenu("Ajuda");
        JMenuItem itemReferencias = new JMenuItem("Capítulos genealógicos da Bíblia");
        JMenuItem itemSobre = new JMenuItem("Sobre");
        itemReferencias.addActionListener(e -> mostrarReferencias());
        itemSobre.addActionListener(e -> mostrarSobre());
        menuAjuda.add(itemReferencias);
        menuAjuda.add(itemSobre);

        barra.add(menuArquivo);
        barra.add(menuModelos);
        barra.add(menuAjuda);
        return barra;
    }

    /**
     * Cria o cabeçalho visual da aplicação.
     */
    private JPanel criarTopo() {
        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(ConstantesSistema.VERDE_ESCURO);
        topo.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));

        JLabel titulo = new JLabel("Árvore Genealógica Bíblica");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);

        JLabel autor = new JLabel("Thiago Ferreira Prates Neves | thiagoprates.com.br");
        autor.setForeground(new Color(235, 235, 220));

        topo.add(titulo, BorderLayout.WEST);
        topo.add(autor, BorderLayout.EAST);
        return topo;
    }

    /**
     * Cria a área central dividida em três partes: estrutura, desenho e detalhes.
     */
    private JSplitPane criarAreaCentral() {
        arvoreJava = new JTree();
        arvoreJava.setFont(ConstantesSistema.FONTE_NORMAL);
        arvoreJava.addTreeSelectionListener(e -> atualizarDetalhes());

        JScrollPane rolagemJTree = new JScrollPane(arvoreJava);
        rolagemJTree.setPreferredSize(new Dimension(300, 0));
        rolagemJTree.setBorder(BorderFactory.createTitledBorder("Estrutura Java - DefaultMutableTreeNode"));

        painelDesenho = new PainelArvoreDesenhada();
        JScrollPane rolagemDesenho = new JScrollPane(painelDesenho);
        rolagemDesenho.setBorder(BorderFactory.createTitledBorder("Árvore visual centralizada"));

        areaDetalhes = new JTextArea();
        areaDetalhes.setEditable(false);
        areaDetalhes.setLineWrap(true);
        areaDetalhes.setWrapStyleWord(true);
        areaDetalhes.setFont(ConstantesSistema.FONTE_NORMAL);

        JScrollPane rolagemDetalhes = new JScrollPane(areaDetalhes);
        rolagemDetalhes.setPreferredSize(new Dimension(330, 0));
        rolagemDetalhes.setBorder(BorderFactory.createTitledBorder("Detalhes, parentesco e referência bíblica"));

        JSplitPane centroDireita = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, rolagemDesenho, rolagemDetalhes);
        centroDireita.setResizeWeight(0.72);
        centroDireita.setDividerLocation(760);

        JSplitPane tudo = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, rolagemJTree, centroDireita);
        tudo.setResizeWeight(0.22);
        tudo.setDividerLocation(300);
        return tudo;
    }

    /**
     * Cria o rodapé completo com faixa verde de botões e status abaixo.
     */
    private JPanel criarRodapeCompleto() {
        JPanel rodape = new JPanel(new BorderLayout());
        rodape.add(criarFaixaVerdeDeAcoes(), BorderLayout.CENTER);
        rodape.add(criarStatus(), BorderLayout.SOUTH);
        return rodape;
    }

    /**
     * Cria a nova faixa de botões na parte de baixo, conforme solicitado.
     */
    private JPanel criarFaixaVerdeDeAcoes() {
        JPanel faixa = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        faixa.setBackground(ConstantesSistema.VERDE_CLARO);
        faixa.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));

        faixa.add(criarGrupo("Árvores bíblicas"));
        faixa.add(criarBotao("Salomão", e -> carregarModeloSalomao()));
        faixa.add(criarBotao("Adão", e -> carregarRaiz(modelos.criarModeloAdao(), "Modelo de Adão carregado.")));
        faixa.add(criarBotao("Abraão", e -> carregarRaiz(modelos.criarModeloAbraao(), "Modelo de Abraão carregado.")));
        faixa.add(criarBotao("Jesus", e -> carregarRaiz(modelos.criarModeloJesus(), "Modelo de Jesus carregado.")));

        faixa.add(criarSeparadorVertical());
        faixa.add(criarGrupo("Funções"));
        faixa.add(criarBotao("Adicionar", e -> adicionarPessoa()));
        faixa.add(criarBotao("Editar", e -> editarPessoa()));
        faixa.add(criarBotao("Remover", e -> removerPessoa()));
        faixa.add(criarBotao("Buscar", e -> buscarPessoa()));

        faixa.add(criarSeparadorVertical());
        faixa.add(criarGrupo("Sistema"));
        faixa.add(criarBotao("Referências", e -> mostrarReferencias()));
        faixa.add(criarBotao("PNG", e -> exportarArvorePng()));
        faixa.add(criarBotao("PDF", e -> exportarArvorePdf()));
        faixa.add(criarBotao("Imprimir", e -> imprimirArvore()));
        faixa.add(criarBotao("Salvar", e -> salvarRelatorio()));
        faixa.add(criarBotao("Sobre", e -> mostrarSobre()));

        return faixa;
    }

    /**
     * Cria um texto de grupo para separar visualmente os botões.
     */
    private JLabel criarGrupo(String texto) {
        JLabel grupo = new JLabel(texto.toUpperCase());
        grupo.setFont(new Font("SansSerif", Font.BOLD, 11));
        grupo.setForeground(ConstantesSistema.VERDE_ESCURO);
        return grupo;
    }

    /**
     * Cria divisor visual entre grupos de botões.
     */
    private JSeparator criarSeparadorVertical() {
        JSeparator separador = new JSeparator(SwingConstants.VERTICAL);
        separador.setPreferredSize(new Dimension(2, 34));
        separador.setForeground(ConstantesSistema.VERDE_ESCURO);
        return separador;
    }

    /**
     * Cria botão padronizado, leve e sem exagero de memória.
     */
    private JButton criarBotao(String texto, java.awt.event.ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.setFocusPainted(false);
        botao.setBackground(Color.WHITE);
        botao.setForeground(ConstantesSistema.VERDE_ESCURO);
        botao.setFont(new Font("SansSerif", Font.BOLD, 12));
        botao.setMargin(new Insets(6, 10, 6, 10));
        botao.addActionListener(acao);
        return botao;
    }

    /**
     * Cria barra de status com mensagens centralizadas do sistema.
     */
    private JPanel criarStatus() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(ConstantesSistema.VERDE_ESCURO);
        rotuloStatus = new JLabel("Sistema pronto.");
        rotuloStatus.setForeground(Color.WHITE);
        rotuloStatus.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        painel.add(rotuloStatus, BorderLayout.WEST);
        return painel;
    }

    /** Carrega o modelo principal de Salomão. */
    private void carregarModeloSalomao() {
        carregarRaiz(modelos.criarModeloSalomao(), "Modelo principal de Salomão carregado com cinco níveis quando possível.");
    }

    /**
     * Carrega qualquer raiz na tela e atualiza todos os componentes.
     */
    private void carregarRaiz(DefaultMutableTreeNode raiz, String mensagem) {
        servicoArvore.setRaiz(raiz);
        modeloJTree = new DefaultTreeModel(raiz);
        arvoreJava.setModel(modeloJTree);
        expandirTodos();
        painelDesenho.definirRaiz(raiz);
        areaDetalhes.setText(servicoArvore.imprimirArvoreTexto() + "\n" + servicoArvore.gerarUmlTextual());
        rotuloStatus.setText(mensagem + " Total de nós: " + servicoArvore.contarNos());
    }

    /** Obtém o nó selecionado pelo usuário no JTree. */
    private DefaultMutableTreeNode obterNoSelecionado() {
        TreePath caminho = arvoreJava.getSelectionPath();
        if (caminho == null) return null;
        return (DefaultMutableTreeNode) caminho.getLastPathComponent();
    }

    /** Adiciona uma nova pessoa abaixo da pessoa selecionada. */
    private void adicionarPessoa() {
        DefaultMutableTreeNode noPai = obterNoSelecionado();
        if (noPai == null) {
            GerenciadorErroSistema.avisar(this, "Selecione primeiro uma pessoa na árvore para receber o novo familiar.");
            return;
        }
        DialogoPessoa dialogo = new DialogoPessoa(this, "Adicionar pessoa", null, servicoArvore.getIdentificador().gerarNovoCodigo());
        dialogo.setVisible(true);
        if (dialogo.isConfirmado()) {
            DefaultMutableTreeNode novoNo = new DefaultMutableTreeNode(dialogo.getPessoaResultado());
            noPai.add(novoNo);
            atualizarTela("Pessoa adicionada com sucesso.");
        }
    }

    /** Edita a pessoa selecionada. */
    private void editarPessoa() {
        DefaultMutableTreeNode no = obterNoSelecionado();
        if (no == null) {
            GerenciadorErroSistema.avisar(this, "Selecione uma pessoa para editar.");
            return;
        }
        PessoaGenealogica pessoa = (PessoaGenealogica) no.getUserObject();
        DialogoPessoa dialogo = new DialogoPessoa(this, "Editar pessoa", pessoa, pessoa.getCodigo());
        dialogo.setVisible(true);
        if (dialogo.isConfirmado()) {
            PessoaGenealogica nova = dialogo.getPessoaResultado();
            servicoArvore.editarPessoa(no, nova.getNome(), nova.getGrauParentesco(), nova.getGenero(), nova.getObservacao(), nova.getReferenciaBiblica(), nova.getTextoBiblico());
            atualizarTela("Pessoa editada com sucesso.");
        }
    }

    /** Remove a pessoa selecionada, exceto a raiz. */
    private void removerPessoa() {
        DefaultMutableTreeNode no = obterNoSelecionado();
        if (no == null) {
            GerenciadorErroSistema.avisar(this, "Selecione uma pessoa para remover.");
            return;
        }
        if (no == servicoArvore.getRaiz()) {
            GerenciadorErroSistema.avisar(this, "A pessoa principal não pode ser removida.");
            return;
        }
        if (GerenciadorErroSistema.confirmar(this, "Deseja realmente remover esta pessoa e seus descendentes?")) {
            servicoArvore.removerPessoa(no);
            atualizarTela("Pessoa removida com sucesso.");
        }
    }

    /** Busca uma pessoa pelo nome. */
    private void buscarPessoa() {
        String termo = JOptionPane.showInputDialog(this, "Digite o nome ou parte do nome:", "Buscar", JOptionPane.QUESTION_MESSAGE);
        if (termo == null || termo.trim().isEmpty()) return;
        List<ResultadoBusca> resultados = servicoArvore.buscarPorNome(termo);
        if (resultados.isEmpty()) {
            GerenciadorErroSistema.informar(this, "Nenhuma pessoa encontrada.");
            return;
        }
        DefaultMutableTreeNode no = resultados.get(0).getNoEncontrado();
        TreePath caminho = new TreePath(no.getPath());
        arvoreJava.setSelectionPath(caminho);
        arvoreJava.scrollPathToVisible(caminho);
        atualizarDetalhes();
        rotuloStatus.setText("Busca concluída. Encontrados: " + resultados.size());
    }

    /** Atualiza JTree, desenho e status após ações do usuário. */
    private void atualizarTela(String mensagem) {
        modeloJTree.reload();
        expandirTodos();
        painelDesenho.definirRaiz(servicoArvore.getRaiz());
        atualizarDetalhes();
        rotuloStatus.setText(mensagem + " Total de nós: " + servicoArvore.contarNos());
    }

    /** Mostra os detalhes da pessoa selecionada. */
    private void atualizarDetalhes() {
        DefaultMutableTreeNode no = obterNoSelecionado();
        if (no == null) {
            areaDetalhes.setText(servicoArvore.imprimirArvoreTexto() + "\n" + servicoArvore.gerarUmlTextual());
            return;
        }
        PessoaGenealogica p = (PessoaGenealogica) no.getUserObject();
        areaDetalhes.setText("Nome: " + p.getNome() + "\n" +
                "Grau de parentesco: " + p.getGrauParentesco() + "\n" +
                "Gênero: " + p.getGenero() + "\n" +
                "Referência: " + p.getReferenciaBiblica() + "\n\n" +
                "Observação:\n" + p.getObservacao() + "\n\n" +
                "Texto/Resumo bíblico:\n" + p.getTextoBiblico());
    }

    /**
     * Mostra o catálogo de capítulos bíblicos com conteúdo genealógico.
     */
    private void mostrarReferencias() {
        StringBuilder html = new StringBuilder("<html><body style='font-family:sans-serif;width:760px'>");
        html.append("<h2>Capítulos bíblicos usados como base genealógica</h2>");
        html.append("<p>O sistema não copia uma tradução bíblica completa. Ele apresenta referências, resumos e nomes destacados para orientar a conferência na Bíblia.</p>");
        for (ReferenciaBiblica r : bancoReferencias.listarTodas()) {
            html.append("<p><b>").append(r.getReferencia()).append("</b><br>")
                    .append(r.getTextoResumo()).append("<br><span style='background:#fff2a8'>Nomes destacados: ")
                    .append(r.getNomesDestacados()).append("</span></p>");
        }
        html.append("</body></html>");
        JScrollPane rolagem = new JScrollPane(new JLabel(html.toString()));
        rolagem.setPreferredSize(new Dimension(820, 560));
        JOptionPane.showMessageDialog(this, rolagem, "Referências bíblicas genealógicas", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Imprime a árvore em modo paisagem. */
    private void imprimirArvore() {
        try {
            painelDesenho.imprimirPaisagem();
        } catch (PrinterException e) {
            GerenciadorErroSistema.erro(this, "Não foi possível imprimir a árvore.", e);
        }
    }

    /** Exporta a árvore como imagem PNG. */
    private void exportarArvorePng() {
        JFileChooser seletor = criarSeletorArquivo("arvore-genealogica.png", "Imagem PNG", "png");
        if (seletor.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                Path destino = garantirExtensao(seletor.getSelectedFile().toPath(), ".png");
                painelDesenho.exportarImagemPng(destino);
                GerenciadorErroSistema.informar(this, "Imagem exportada em: " + destino.toAbsolutePath());
            } catch (Exception e) {
                GerenciadorErroSistema.erro(this, "Não foi possível exportar PNG.", e);
            }
        }
    }

    /** Exporta a árvore como PDF paisagem. */
    private void exportarArvorePdf() {
        JFileChooser seletor = criarSeletorArquivo("arvore-genealogica.pdf", "Arquivo PDF", "pdf");
        if (seletor.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                Path destino = garantirExtensao(seletor.getSelectedFile().toPath(), ".pdf");
                painelDesenho.exportarPdf(destino);
                GerenciadorErroSistema.informar(this, "PDF exportado em: " + destino.toAbsolutePath());
            } catch (Exception e) {
                GerenciadorErroSistema.erro(this, "Não foi possível exportar PDF.", e);
            }
        }
    }

    /** Cria seletor de arquivo padronizado. */
    private JFileChooser criarSeletorArquivo(String nomeSugerido, String descricao, String extensao) {
        JFileChooser seletor = new JFileChooser();
        seletor.setSelectedFile(new java.io.File(nomeSugerido));
        seletor.setFileFilter(new FileNameExtensionFilter(descricao, extensao));
        return seletor;
    }

    /** Garante extensão correta no arquivo exportado. */
    private Path garantirExtensao(Path caminho, String extensao) {
        String texto = caminho.toString().toLowerCase();
        if (!texto.endsWith(extensao)) {
            return Path.of(caminho + extensao);
        }
        return caminho;
    }

    /** Salva relatório textual do projeto. */
    private void salvarRelatorio() {
        try {
            Path caminho = Path.of("relatorios", "relatorio-arvore.txt");
            repositorio.salvarRelatorioTexto(servicoArvore, caminho);
            GerenciadorErroSistema.informar(this, "Relatório salvo em: " + caminho.toAbsolutePath());
        } catch (Exception e) {
            GerenciadorErroSistema.erro(this, "Não foi possível salvar o relatório.", e);
        }
    }

    /** Exibe informações acadêmicas do sistema. */
    private void mostrarSobre() {
        String texto = ConstantesSistema.NOME_SISTEMA + "\n\n" +
                "UESB — Curso ADS\n" +
                "Disciplina: Estrutura de Dados\n" +
                "Professor: Murilo Silva Santana\n\n" +
                "Autor: " + ConstantesSistema.AUTOR_COMPLETO + "\n" +
                "Site: " + ConstantesSistema.SITE_AUTOR + "\n\n" +
                "Sistema criado para demonstrar árvores, recursividade, CRUD, exportação e genealogia bíblica em Java.";
        GerenciadorErroSistema.informar(this, texto);
    }

    /** Expande todas as linhas do JTree. */
    private void expandirTodos() {
        for (int i = 0; i < arvoreJava.getRowCount(); i++) {
            arvoreJava.expandRow(i);
        }
    }
}
