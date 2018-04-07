package got.util;

import java.util.ArrayList;
import java.util.Map;

public class PairList<F, S> extends ArrayList<Pair<F,S>> {

    private static final long serialVersionUID = 9201488225520343128L;

	public PairList<F,S> add(F f, S s) {
        add(new Pair<F,S>(f, s));
        return this;
    }

    public static<K,V> PairList<K,V> fromMap(Map<K,V> map) {
        PairList<K,V> pairs = new PairList<>();
        for(Map.Entry<K,V> e: map.entrySet()) {
            pairs.add(e.getKey(), e.getValue());
        }
        return pairs;
    }
}