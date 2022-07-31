package lev;

import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("unused")
public class LMergeList<T extends LMergable> implements Iterable<T> {
    ArrayList<T> list = new ArrayList<>();

    public LMergeList() {
    }

    public void add(T in) {
        int index;
        if ((index = this.list.indexOf(in)) != -1) {
            this.list.get(index).mergeIn(in);
        } else {
            this.list.add(in);
        }

    }

    public boolean contains(T in) {
        return this.list.contains(in);
    }

    public void remove(T in) {
        this.list.remove(in);
    }

    public int size() {
        return this.list.size();
    }

    public T get(int i) {
        return this.list.get(i);
    }

    public Iterator<T> iterator() {
        return this.list.iterator();
    }
}
