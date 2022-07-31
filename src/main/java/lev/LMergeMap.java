package lev;

import java.util.*;

@SuppressWarnings("unused")
public class LMergeMap<K, V> implements Iterable<V> {
    Map<K, ArrayList<V>> map;
    boolean sorted;
    boolean unique;

    public LMergeMap(Boolean sorted) {
        this(sorted, true);
    }

    public LMergeMap(Boolean sorted, Boolean unique) {
        if (sorted) {
            this.map = new TreeMap<>();
        } else {
            this.map = new HashMap<>();
        }

        this.sorted = sorted;
        this.unique = unique;
    }

    public void clear() {
        this.map.clear();
    }

    public void addAll(LMergeMap<K, V> in) {

        for (K k : in.keySet()) {
            this.map.put(k, in.get(k));
        }
    }

    public boolean containsKey(K key) {
        return this.map.containsKey(key);
    }

    public boolean containsValue(V value) {

        for (ArrayList<V> vals : this.map.values()) {

            for (V v : vals) {
                if (v.equals(value)) {
                    return true;
                }
            }
        }

        return false;
    }

    public Set<Map.Entry<K, ArrayList<V>>> entrySet() {
        return this.map.entrySet();
    }

    public ArrayList<V> get(K key) {
        return this.map.get(key);
    }

    public int hashcode() {
        return this.map.hashCode();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public Set<K> keySet() {
        return this.map.keySet();
    }

    public void put(K key, V value) {
        if (!this.map.containsKey(key)) {
            this.map.put(key, new ArrayList<>());
        }

        if (!this.unique || !this.map.get(key).contains(value)) {
            this.map.get(key).add(value);
        }

    }

    public void put(K key, ArrayList<V> value) {
        if (!this.map.containsKey(key)) {
            this.map.put(key, value);
        } else {

            for (V v : value) {
                this.put(key, v);
            }
        }
    }

    public void remove(K key) {
        if (this.map.containsKey(key)) {
            this.map.get(key).clear();
            this.map.remove(key);
        }

    }

    public int size() {
        return this.map.size();
    }

    public int numVals() {
        int sum = 0;

        ArrayList<V> vals;
        for (Iterator<ArrayList<V>> i$ = this.map.values().iterator(); i$.hasNext(); sum += vals.size()) {
            vals = i$.next();
        }

        return sum;
    }

    public Collection<ArrayList<V>> values() {
        return this.map.values();
    }

    public ArrayList<V> valuesFlat() {
        ArrayList<V> out = new ArrayList<>();

        for (ArrayList<V> vals : this.map.values()) {
            out.addAll(vals);
        }

        return out;
    }

    public Map<K, V> flatten() {
        Map<K, V> out;
        if (this.sorted) {
            out = new TreeMap<>();
        } else {
            out = new HashMap<>();
        }

        for (K key : this.map.keySet()) {
            ArrayList<V> val = this.map.get(key);
            if (val.size() > 0) {
                out.put(key, val.get(0));
            }
        }

        return out;
    }

//    public LMergeMap<V, K> flip() {
//        LMergeMap<V, K> flip = new LMergeMap<>(this.sorted, this.unique);
//        Iterator<K> i$ = this.map.keySet().iterator();
//
//        while(i$.hasNext()) {
//            K key = i$.next();
//            Iterator<ArrayList<V>> j$ = ((ArrayList)this.map.get(key)).iterator();
//
//            while(j$.hasNext()) {
//                ArrayList<V> val = j$.next();
//                flip.put(val, key);
//            }
//        }
//
//        return flip;
//    }

    public ArrayList<String> print() {
        ArrayList<String> out = new ArrayList<>();
        Iterator<K> i$ = this.map.keySet().iterator();

        while (i$.hasNext()) {
            K key = i$.next();
            out.add(key.toString());
            Iterator<V> j$ = this.get(key).iterator();

            while (i$.hasNext()) {
                V vals = j$.next();
                out.add("   " + vals.toString());
            }
        }

        return out;
    }

    public Iterator<V> iterator() {
        return this.valuesFlat().iterator();
    }
}
