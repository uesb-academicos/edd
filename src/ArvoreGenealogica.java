import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Enumeration;

/** Versão de console baseada no roteiro obrigatório da atividade. */
public class ArvoreGenealogica {
    public static void main(String[] args) {
        // Nível 1: pessoa escolhida. Cada filho estrutural representa um ancestral.
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Salomão");
        DefaultMutableTreeNode davi = new DefaultMutableTreeNode("Davi");
        DefaultMutableTreeNode bateSeba = new DefaultMutableTreeNode("Bate-Seba");
        DefaultMutableTreeNode jesse = new DefaultMutableTreeNode("Jessé");
        DefaultMutableTreeNode avoPaterna = new DefaultMutableTreeNode("Desconhecida (avó paterna)");
        DefaultMutableTreeNode elia = new DefaultMutableTreeNode("Eliã");
        DefaultMutableTreeNode avoMaterna = new DefaultMutableTreeNode("Desconhecida (avó materna)");
        DefaultMutableTreeNode obede = new DefaultMutableTreeNode("Obede");
        DefaultMutableTreeNode bisavoPaterna = new DefaultMutableTreeNode("Desconhecida (mãe de Jessé)");
        DefaultMutableTreeNode bisavoMaterno = new DefaultMutableTreeNode("Desconhecido (pai de Eliã)");
        DefaultMutableTreeNode boaz = new DefaultMutableTreeNode("Boaz");
        DefaultMutableTreeNode rute = new DefaultMutableTreeNode("Rute");

        raiz.add(davi); raiz.add(bateSeba);
        davi.add(jesse); davi.add(avoPaterna);
        bateSeba.add(elia); bateSeba.add(avoMaterna);
        jesse.add(obede); jesse.add(bisavoPaterna);
        elia.add(bisavoMaterno);
        obede.add(boaz); obede.add(rute);

        // Trecho preservado do código-base.
        System.out.println("=== Árvore Genealógica ===\n");
        imprimirArvore(raiz, 0);
    }

    public static void imprimirArvore(DefaultMutableTreeNode node, int nivel) {
        System.out.println("    ".repeat(nivel) + node.getUserObject());
        Enumeration<?> filhos = node.children();
        // Nas folhas não há filhos: o laço termina, encerrando a recursão.
        while (filhos.hasMoreElements()) {
            imprimirArvore((DefaultMutableTreeNode) filhos.nextElement(), nivel + 1);
        }
    }
}
