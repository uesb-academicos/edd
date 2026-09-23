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
package br.uesb.edd.arvore.tela;

import br.uesb.edd.arvore.modelo.PessoaGenealogica;
import br.uesb.edd.arvore.util.ConstantesSistema;

import javax.swing.*;
import java.awt.*;

/**
 * Janela usada para cadastrar e editar pessoas.
 *
 * Explicação do programador:
 * Esta classe evita repetir campos de formulário em várias partes da tela.
 * Ela valida os dados principais e retorna uma PessoaGenealogica pronta.
 */
public class DialogoPessoa extends JDialog {

    private JTextField campoNome;
    private JTextField campoParentesco;
    private JComboBox<String> campoGenero;
    private JTextField campoReferencia;
    private JTextArea campoObservacao;
    private JTextArea campoTextoBiblico;
    private boolean confirmado;
    private PessoaGenealogica pessoaResultado;

    public DialogoPessoa(Window dono, String titulo, PessoaGenealogica pessoaOriginal, int novoCodigo) {
        super(dono, titulo, ModalityType.APPLICATION_MODAL);
        montarTela(pessoaOriginal, novoCodigo);
    }

    private void montarTela(PessoaGenealogica pessoaOriginal, int novoCodigo) {
        setSize(560, 520);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(ConstantesSistema.FUNDO);

        JLabel titulo = new JLabel("Cadastro de Pessoa da Árvore", SwingConstants.CENTER);
        titulo.setFont(ConstantesSistema.FONTE_TITULO);
        titulo.setForeground(ConstantesSistema.VERDE_ESCURO);
        add(titulo, BorderLayout.NORTH);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(ConstantesSistema.FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        campoNome = new JTextField();
        campoParentesco = new JTextField();
        campoGenero = new JComboBox<>(new String[]{"Masculino", "Feminino", "Não informado"});
        campoReferencia = new JTextField();
        campoObservacao = new JTextArea(4, 30);
        campoTextoBiblico = new JTextArea(5, 30);
        campoObservacao.setLineWrap(true);
        campoTextoBiblico.setLineWrap(true);
        campoObservacao.setWrapStyleWord(true);
        campoTextoBiblico.setWrapStyleWord(true);

        adicionarLinha(painel, c, 0, "Nome:", campoNome);
        adicionarLinha(painel, c, 1, "Grau de parentesco:", campoParentesco);
        adicionarLinha(painel, c, 2, "Gênero:", campoGenero);
        adicionarLinha(painel, c, 3, "Referência bíblica:", campoReferencia);
        adicionarArea(painel, c, 4, "Observação:", campoObservacao);
        adicionarArea(painel, c, 5, "Texto/resumo bíblico:", campoTextoBiblico);

        if (pessoaOriginal != null) {
            campoNome.setText(pessoaOriginal.getNome());
            campoParentesco.setText(pessoaOriginal.getGrauParentesco());
            campoGenero.setSelectedItem(pessoaOriginal.getGenero());
            campoReferencia.setText(pessoaOriginal.getReferenciaBiblica());
            campoObservacao.setText(pessoaOriginal.getObservacao());
            campoTextoBiblico.setText(pessoaOriginal.getTextoBiblico());
        }

        add(painel, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoes.setBackground(ConstantesSistema.FUNDO);
        JButton botaoSalvar = criarBotao("Salvar");
        JButton botaoCancelar = criarBotao("Cancelar");
        botaoSalvar.addActionListener(e -> confirmarDados(pessoaOriginal, novoCodigo));
        botaoCancelar.addActionListener(e -> dispose());
        botoes.add(botaoCancelar);
        botoes.add(botaoSalvar);
        add(botoes, BorderLayout.SOUTH);
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFocusPainted(false);
        botao.setBackground(ConstantesSistema.VERDE_ESCURO);
        botao.setForeground(Color.WHITE);
        return botao;
    }

    private void adicionarLinha(JPanel painel, GridBagConstraints c, int linha, String rotulo, JComponent campo) {
        c.gridx = 0; c.gridy = linha; c.weightx = 0;
        painel.add(new JLabel(rotulo), c);
        c.gridx = 1; c.weightx = 1;
        painel.add(campo, c);
    }

    private void adicionarArea(JPanel painel, GridBagConstraints c, int linha, String rotulo, JTextArea area) {
        c.gridx = 0; c.gridy = linha; c.weightx = 0; c.anchor = GridBagConstraints.NORTH;
        painel.add(new JLabel(rotulo), c);
        c.gridx = 1; c.weightx = 1; c.anchor = GridBagConstraints.CENTER;
        painel.add(new JScrollPane(area), c);
    }

    private void confirmarDados(PessoaGenealogica pessoaOriginal, int novoCodigo) {
        if (campoNome.getText().trim().isEmpty() || campoParentesco.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe nome e grau de parentesco.", "Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int codigo = pessoaOriginal == null ? novoCodigo : pessoaOriginal.getCodigo();
        pessoaResultado = new PessoaGenealogica(codigo, campoNome.getText().trim(), campoParentesco.getText().trim(),
                String.valueOf(campoGenero.getSelectedItem()), campoObservacao.getText().trim(),
                campoReferencia.getText().trim(), campoTextoBiblico.getText().trim());
        confirmado = true;
        dispose();
    }

    public boolean isConfirmado() { return confirmado; }
    public PessoaGenealogica getPessoaResultado() { return pessoaResultado; }
}
