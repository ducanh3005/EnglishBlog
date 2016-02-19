package com.tune.englishblog.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class CollectionUtils {
    public <T> T[] array(T... elements) {
        return (T[]) new ArrayList().toArray();
    }

    public <T> List<T> list(T... elements) {
        return Arrays.asList(elements);
    }

    @SafeVarargs
    public static <K, V> LinkedHashMap<K, V> map(Pair<K, ? extends V>... pairs) {
        return fill(new LinkedHashMap<K, V>(), pairs);
    }

    @SafeVarargs
    public static <K, V> LinkedHashMap<K, V> fill(LinkedHashMap<K, V> map, Pair<K, ? extends V>... pairs) {
        if (map != null && pairs != null && pairs.length > 0) {
            for (Pair<K, ? extends V> pair: pairs) {
                if (pair != null && pair.valid()) {
                    map.put(pair.key(), pair.value());
                }
            }
        }

        return map;
    }

    public static <T> Map<T, T> flatten(List<Map<String, T>> pairs, String keyName, String valueName) {
        Map<T, T> result = new HashMap<>();
        for (Map<String, T> pair : pairs) {
            result.put(pair.get(keyName), pair.get(valueName));
        }
        return result;
    }

    public static boolean isEmpty(Iterable<?> iterable) {
        return (iterable == null || iterable.iterator() == null || iterable.iterator().hasNext() == false);
    }

    public static boolean isEmpty(String... strings) {
        return (strings == null || strings.length < 1);
    }

    public static boolean contains(Collection<String> collection, String text) {
        if (StringUtils.isBlank(text)) return false;

        if (!isEmpty(collection)) {
            for (String item : collection) {
                if (text.equalsIgnoreCase(item)) return true;
            }
        }
        return false;
    }

    public static boolean contains(String[] collection, String text) {
        if (StringUtils.isBlank(text)) return false;

        if (!isEmpty(collection)) {
            for (String item : collection) {
                if (text.equalsIgnoreCase(item)) return true;
            }
        }
        return false;
    }

    public static boolean contains(Integer what, Integer... collection) {
        for (int item : collection) if (item == what) return true;
        return false;
    }

    public static boolean contains(int what, int... collection) {
        for (int item : collection) if (item == what) return true;
        return false;
    }

    @SafeVarargs
    public static <T> Set<T> newSetFromLists(List<T>... arrays) {
        Set<T> result = new HashSet<T>();

        if (arrays != null) {
            for (List<T> list : arrays) {
                if (!isEmpty(list)) {
                    result.addAll(list);
                }
            }
        }
        return result;
    }


}
