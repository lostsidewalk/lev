package lev.gui;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class LSwingTreeNode extends DefaultMutableTreeNode {
    public LSwingTreeNode() {
    }

    public boolean contains(LSwingTreeNode node) {
        return this.get(node) != null;
    }

    public LSwingTreeNode get(LSwingTreeNode node) {
        if (this.children != null) {

            for (Object rhs : this.children) {
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
        ArrayList<LSwingTreeNode> out = new ArrayList<>();
        if (this.children != null) {

            for (Object o : this.children) {
                LSwingTreeNode n = (LSwingTreeNode) o;
                out.add(n);
                if (recursive) {
                    //noinspection ConstantConditions
                    out.addAll(n.getAllObjects(recursive));
                }
            }
        }

        return out;
    }

    public void print(int depth) {

        for (LSwingTreeNode n : this.getAllObjects()) {
            n.print(depth + 1);
        }
    }

    public void mergeIn(LSwingTreeNode node) {
        LSwingTreeNode existing = this.get(node);
        if (existing != null) {

            for (LSwingTreeNode lSwingTreeNode : node.getAllObjects()) {
                existing.mergeIn(lSwingTreeNode);
            }
        } else {
            this.add(node);
        }
    }
}
