package lev.gui;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.*;

@SuppressWarnings("unused")
public class LList<T> extends LHelpComponent implements Iterable<T> {
    protected static int spacing = 15;
    protected final DefaultListModel<T> model = new DefaultListModel<>();
    protected final JList<T> list;
    protected final JScrollPane scroll;
    protected boolean unique;
    protected Comparator<T> compare;

    public LList(String title) {
        super(title);
        this.list = new JList<>(this.model);
        this.scroll = new JScrollPane(this.list);
        this.scroll.setVisible(true);
        this.add(this.scroll);
    }

    public void setSize(int width, int height) {
        super.setSize(width, height);
        this.scroll.setSize(width, height);
    }

    public void addElement(T o) {
        if (!this.unique || !this.model.contains(o)) {
            this.model.addElement(o);
        }

    }

    public void addElements(Collection<T> in) {

        for (T t : in) {
            this.addElement(t);
        }
    }

    public List<T> getSelectedElements() {
        return this.list.getSelectedValuesList();
    }

    public T getSelectedElement() {
        return this.list.getSelectedValue();
    }

    public void setSelectedElement(int index) {
        this.list.setSelectedIndex(index);
    }

    public void removeSelected() {

        for (T o : this.list.getSelectedValuesList()) {
            this.model.removeElement(o);
        }
    }

    public int numItems() {
        return this.model.size();
    }

    public boolean isEmpty() {
        return this.model.isEmpty();
    }

    public void clear() {
        this.model.clear();
    }

    public void setUnique(boolean on) {
        this.unique = on;
    }

    public void addListSelectionListener(ListSelectionListener l) {
        this.list.addListSelectionListener(l);
    }

    public void addMouseListener(MouseListener m) {
        super.addMouseListener(m);
        this.scroll.addMouseListener(m);
        this.list.addMouseListener(m);
    }

    public Iterator<T> iterator() {
        return this.getAll().iterator();
    }

    public void highlightChanged() {
        this.list.setBackground(new Color(224, 121, 147));
    }

    public void clearHighlight() {
        this.list.setBackground(Color.white);
    }

    public ArrayList<T> getAll() {
        ArrayList<T> out = new ArrayList<>(this.model.size());
        Object[] arr$ = this.model.toArray();
        int len$ = arr$.length;

        for (Object o : arr$) {
            out.add((T) o);
        }

        return out;
    }

    protected void addHelpHandler(boolean hoverListener) {
        this.addMouseListener(new LHelpComponent.HelpMouseHandler());
    }
}
