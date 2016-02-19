package com.tune.englishblog.util;

import java.util.Map;

public class Pair<K, V> {
    private K key;
    private V value;

    public K key() { return this.key; }
    public V value() { return this.value; }

    //this is to mimic value progression
    public K from() { return this.key; }
    public V to() { return this.value; }

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public static <K, V> Pair<K, V> pair(K key, V value) { return new Pair<K, V>(key, value); }
    public static <K, V> Pair<K, V> of(Map.Entry<K, V> entry) { return new Pair<K, V>(entry.getKey(), entry.getValue()); }

    public boolean valid() { return this.key != null && this.value != null; }
    public boolean lenientValid() { return this.key != null || this.value != null; }
    public boolean validKey() { return this.key != null; }
    public boolean validValue() { return this.value != null; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        Pair<K, V> other = (Pair<K, V>) o;
        return
                (this.validKey() && other.validKey()
                        ? this.key.equals(other.key)
                        : this.key == other.key)
                        &&
                        (this.validValue() && other.validValue()
                                ? this.value.equals(other.value)
                                : this.value == other.value)
                ;

    }
}
