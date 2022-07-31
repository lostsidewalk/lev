package lev.gui;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Iterator;

public class LSwingTreeNode extends DefaultMutableTreeNode {
    public LSwingTreeNode() {
    }

    public boolean contains(LSwingTreeNode node) {
        return this.get(node) != null;
    }

    public LSwingTreeNode get(LSwingTreeNode node) {
        if (this.children != null) {
            Iterator i$ = this.children.iterator();

            while (i$.hasNext()) {
                Object rhs = i$.next();
                if (node.equals(rhs)) {
                    return (LSwingTreeNode) rhs;
                }
            }
        }

        return null;
    }

    public ArrayList<LSwingTreeNode> getAllObjects() {
        return this.getAllObjects(false);
    }

    public ArrayList<LSwingTreeNode> getAllObjects(boolean recursive) {
        ArrayList<LSwingTreeNode> out = new ArrayList();
        if (this.children != null) {
            Iterator i$ = this.children.iterator();

            while (i$.hasNext()) {
                Object o = i$.next();
                LSwingTreeNode n = (LSwingTreeNode) o;
                out.add(n);
                if (recursive) {
                    out.addAll(n.getAllObjects(recursive));
                }
            }
        }

        return out;
    }

    public void print(int depth) {
        Iterator i$ = this.getAllObjects().iterator();

        while (i$.hasNext()) {
            Object o = i$.next();
            if (o instanceof LSwingTreeNode) {
                LSwingTreeNode n = (LSwingTreeNode) o;
                n.print(depth + 1);
            }
        }

    }

    public void mergeIn(LSwingTreeNode node) {
        LSwingTreeNode existing = this.get(node);
        if (existing != null) {
            Iterator i$ = node.getAllObjects().iterator();

            while (i$.hasNext()) {
                Object o = i$.next();
                if (o instanceof LSwingTreeNode) {
                    existing.mergeIn((LSwingTreeNode) o);
                }
            }
        } else {
            this.add(node);
        }

    }
}
