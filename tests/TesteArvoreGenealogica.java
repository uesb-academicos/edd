import br.uesb.edd.arvore.servico.*;
import br.uesb.edd.arvore.modelo.PessoaGenealogica;
import br.uesb.edd.arvore.repositorio.RepositorioArquivoTexto;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class TesteArvoreGenealogica {
    private static int checks;
    private static void check(boolean ok, String message) {
        checks++;
        if (!ok) throw new AssertionError(message);
    }
    public static void main(String[] args) throws Exception {
        var service = new ServicoArvoreGenealogica();
        var root = new ModelosGenealogicosBiblicos(service).criarModeloSalomao();
        service.setRaiz(root);
        check(service.contarNos() == 12, "Modelo deve ter 12 nos");
        check(root.getDepth() == 4, "Modelo deve ter cinco niveis");
        int[] levels = new int[5];
        var nodes = root.preorderEnumeration();
        Set<Integer> ids = new HashSet<>();
        List<String> expected = new ArrayList<>();
        while (nodes.hasMoreElements()) {
            var n = (DefaultMutableTreeNode) nodes.nextElement();
            var person = (PessoaGenealogica) n.getUserObject();
            levels[n.getLevel()]++;
            check(ids.add(person.getCodigo()), "Codigo duplicado");
            check(n.getChildCount() <= 2, "Mais de dois ancestrais diretos");
            expected.add("    ".repeat(n.getLevel()) + person.getNome());
        }
        check(Arrays.equals(levels, new int[]{1,2,4,3,2}), "Distribuicao de niveis incorreta");
        var buffer = new ByteArrayOutputStream();
        var original = System.out;
        try (var capture = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setOut(capture);
            ArvoreGenealogica.main(new String[0]);
        } finally { System.setOut(original); }
        var actual = buffer.toString(StandardCharsets.UTF_8).lines().skip(2).toList();
        check(actual.size() == expected.size(), "Console deve imprimir todos os nos");
        for (int i=0; i<actual.size(); i++) {
            String line = actual.get(i);
            check(line.startsWith(expected.get(i)), "Ordem, nome ou indentacao incorretos: " + line);
            check(line.length() - line.stripLeading().length() == expected.get(i).length() - expected.get(i).stripLeading().length(), "Indentacao extra");
        }
        check(service.buscarPorNome("dAvI").size() == 1, "Busca sem distinguir maiusculas");
        check(service.buscarPorNome(" ").isEmpty(), "Busca vazia");
        check(!service.removerPessoa(root), "Raiz nao pode ser removida");
        var added = service.adicionarPessoa(root, service.criarPessoa("Teste", "Teste", "", "", "", ""));
        check(service.contarNos() == 13, "Cadastro");
        service.editarPessoa(added, "Editado", "Teste", "", "", "", "");
        check(service.buscarPorNome("Editado").size() == 1, "Edicao");
        check(service.removerPessoa(added) && service.contarNos() == 12, "Remocao");
        var path = Path.of("teste-relatorio-" + UUID.randomUUID() + ".txt");
        try {
            new RepositorioArquivoTexto().salvarRelatorioTexto(service, path);
            check(Files.readString(path).contains("Salomão"), "Exportacao relativa UTF-8");
        } finally { Files.deleteIfExists(path); }
        System.out.println(checks + " verificacoes passaram.");
    }
}
