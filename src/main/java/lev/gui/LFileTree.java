package lev.gui;

import lev.Ln;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

@SuppressWarnings("unused")
public class LFileTree {
    protected DefaultTreeModel tree;
    protected DefaultMutableTreeNode root = new DefaultMutableTreeNode("");

    public LFileTree() {
        this.tree = new DefaultTreeModel(this.root);
    }

    public void addFile(File f) {
        this.addFile(f.getPath());
    }

    public void addFile(String path) {
        path = Ln.standardizeFilePath(path);
        ArrayList<String> list = new ArrayList<>(Arrays.asList(path.split("/")));
        this.addFile(this.root, list);
    }

    void addFile(DefaultMutableTreeNode node, ArrayList<String> path) {
        if (!path.isEmpty()) {
            String target = path.get(0);
            path.remove(0);
            Enumeration<?> e = node.children();

            DefaultMutableTreeNode nodeC;
            do {
                if (!e.hasMoreElements()) {
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(target);
                    node.add(newNode);
                    this.addFile(newNode, path);
                    return;
                }

                nodeC = (DefaultMutableTreeNode) e.nextElement();
            } while (!nodeC.toString().equalsIgnoreCase(target));

            this.addFile(nodeC, path);
        }
    }

    public ArrayList<String> getList() {
        ArrayList<String> out = new ArrayList<>();
        Enumeration<?> e = this.root.depthFirstEnumeration();

        while (e.hasMoreElements()) {
            DefaultMutableTreeNode nodeC = (DefaultMutableTreeNode) e.nextElement();
            out.add(nodeC.toString());
        }

        return out;
    }

    public String print(String fluff) {
        String out = "";

        DefaultMutableTreeNode nodeC;
        for (Enumeration<?> e = this.root.children(); e.hasMoreElements(); out = out + this.print(nodeC, 0, fluff)) {
            nodeC = (DefaultMutableTreeNode) e.nextElement();
        }

        return out;
    }

    String print(DefaultMutableTreeNode node, int depth, String fluff) {
        StringBuilder out = new StringBuilder();

        out.append(String.valueOf(fluff).repeat(Math.max(0, depth)));

        out.append(node.toString());
        if (!node.isLeaf()) {
            out.append("/");
        }

        out.append("\n");
        ++depth;

        DefaultMutableTreeNode nodeC;
        for (Enumeration<?> e = node.children(); e.hasMoreElements(); out.append(this.print(nodeC, depth, fluff))) {
            nodeC = (DefaultMutableTreeNode) e.nextElement();
        }

        return out.toString();
    }
}
