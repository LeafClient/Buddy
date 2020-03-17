package fr.shyrogan.buddy.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class MapHelper {

    public static  <K, V> List<V> getValueArrayToList(Map<K, V[]> map, K key) {
        V[] values = map.get(key);
        if(values == null)
            return new ArrayList<>();

        return new ArrayList<>(Arrays.asList(values));
    }

}
