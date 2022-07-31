package lev.gui;

import lev.Ln;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

public class LTree extends JTree {
    protected DefaultTreeModel model;
    String state;

    public LTree(int width, int height) {
        this();
        this.setSize(width, height);
    }

    public LTree() {
        this.model = (DefaultTreeModel) this.getModel();
        this.setRowHeight(20);
        this.setVisible(true);
    }

    public void clearTree() {
        this.setModel(new DefaultTreeModel(new DefaultMutableTreeNode()));
    }

    public void expandToDepth(int depth) {
        Ln.expandToDepth(this, depth);
    }

    public void expand(Boolean b) {
        Ln.expandAll(this, b);
    }

    public void expandRoot() {
        this.expandPath(new TreePath(this.getModel().getRoot()));
    }

    public int getTotalRowHeight() {
        return this.getRowCount() * this.getRowHeight();
    }

    public ArrayList<Integer> getExpandedRows() {
        ArrayList<Integer> out = new ArrayList();

        for (int i = 0; i < this.getRowCount(); ++i) {
            TreePath path = this.getPathForRow(i);
            if (this.isExpanded(path)) {
                out.add(i);
            }
        }

        return out;
    }

    public void expandRows(ArrayList<Integer> rows) {
        Iterator i$ = rows.iterator();

        while (i$.hasNext()) {
            int i = (Integer) i$.next();
            TreePath path = this.getPathForRow(i);
            this.expandPath(path);
        }

    }

    public TreeNode getRoot() {
        return (TreeNode) this.getModel().getRoot();
    }

    public void setRoot(TreeNode n) {
        this.setModel(new DefaultTreeModel(n));
    }

    public ArrayList<Integer> rootRows() {
        ArrayList<Integer> out = new ArrayList();
        TreePath rootPath = new TreePath(this.getRoot());
        TreeNode root = (TreeNode) rootPath.getLastPathComponent();
        if (root.getChildCount() >= 0) {
            Enumeration e = root.children();

            while (e.hasMoreElements()) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = rootPath.pathByAddingChild(n);
                out.add(this.getRowForPath(path));
            }
        }

        return out;
    }

    public String getExpansionState(int row) {
        return Ln.getExpansionState(this, row);
    }

    public void restoreExpansionState(int row, String state) {
        Ln.restoreExpanstionState(this, row, state);
    }

    public void saveExpansionState() {
        this.state = this.getExpansionState(0);
    }

    public void restoreExpansionState() {
        this.restoreExpansionState(0, this.state);
    }

    public void nodeChanged(TreeNode node) {
        this.model.nodeChanged(node);
    }

    public void setBackground(Color c, boolean selected) {
        if (selected) {
            DefaultTreeCellRenderer r = (DefaultTreeCellRenderer) this.getCellRenderer();
            r.setBackgroundSelectionColor(c);
        } else {
            this.setBackground(c);
        }

    }

    public void setForeground(Color c, boolean selected) {
        DefaultTreeCellRenderer r = (DefaultTreeCellRenderer) this.getCellRenderer();
        if (selected) {
            r.setTextSelectionColor(c);
        } else {
            r.setTextNonSelectionColor(c);
        }

    }
}
