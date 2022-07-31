package lev.gui;

import javax.swing.*;
import java.util.*;

public class SortedListModel<T> extends AbstractListModel {
    protected final SortedSet<T> model = new TreeSet();

    public SortedListModel() {
    }

    public int getSize() {
        return this.model.size();
    }

    public T getElementAt(int index) {
        return (T) this.model.toArray()[index];
    }

    public void add(T element) {
        if (this.model.add(element)) {
            this.fireContentsChanged(this, 0, this.getSize());
        }

    }

    public void addAll(T[] elements) {
        Collection c = Arrays.asList(elements);
        this.model.addAll(c);
        this.fireContentsChanged(this, 0, this.getSize());
    }

    public void clear() {
        this.model.clear();
        this.fireContentsChanged(this, 0, this.getSize());
    }

    public boolean contains(T element) {
        return this.model.contains(element);
    }

    public T firstElement() {
        return this.model.first();
    }

    public Iterator iterator() {
        return this.model.iterator();
    }

    public T lastElement() {
        return this.model.last();
    }

    public boolean removeElement(T element) {
        boolean removed = this.model.remove(element);
        if (removed) {
            this.fireContentsChanged(this, 0, this.getSize());
        }

        return removed;
    }
}
